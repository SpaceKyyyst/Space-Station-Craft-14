import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

from policy_checks import (
    ChangedFile,
    validate_changed_files,
    validate_commit_message,
    validate_labels,
    validate_pr_body,
    read_changed_files,
)


class CommitMessagePolicyTest(unittest.TestCase):
    def test_accepts_conventional_bilingual_commit(self):
        message = (
            "ci(policy): Проверить метаданные PR / Validate PR metadata\n\n"
            "RU:\n- Добавлена проверка метаданных.\n\n"
            "EN:\n- Added metadata validation.\n"
        )

        self.assertEqual([], validate_commit_message(message))

    def test_rejects_subject_without_russian_and_english_descriptions(self):
        errors = validate_commit_message(
            "ci(policy): Validate metadata\n\nRU:\n- Проверка.\n\nEN:\n- Validation.\n"
        )

        self.assertTrue(any("subject" in error.lower() for error in errors))

    def test_accepts_legacy_bilingual_migration_subject(self):
        message = (
            "Перенести compatibility bridge / Move compatibility bridge\n\n"
            "RU:\n- Перенесён bridge.\n\nEN:\n- Moved bridge.\n"
        )
        self.assertEqual([], validate_commit_message(message))

    def test_rejects_empty_or_reversed_language_sections(self):
        message = (
            "ci(policy): Проверить метаданные / Validate metadata\n\n"
            "EN:\n- Validation.\n\nRU:\n"
        )

        errors = validate_commit_message(message)

        self.assertTrue(any("RU:" in error and "EN:" in error for error in errors))

    def test_allows_recognized_automation_to_use_conventional_english_message(self):
        self.assertEqual(
            [],
            validate_commit_message(
                "build(deps): bump example from 1.0 to 1.1", require_bilingual=False
            ),
        )

    def test_still_rejects_non_conventional_automation_commit(self):
        errors = validate_commit_message("Bump example", require_bilingual=False)

        self.assertTrue(any("conventional" in error.lower() for error in errors))


class PullRequestBodyPolicyTest(unittest.TestCase):
    def test_accepts_non_empty_ordered_language_sections(self):
        body = (
            "## RU\n\n### Что сделано\nДобавлена проверка.\n\n"
            "## EN\n\n### What was done\nAdded validation.\n"
        )

        self.assertEqual([], validate_pr_body(body))

    def test_rejects_template_with_comments_but_no_content(self):
        body = "## RU\n<!-- Заполните -->\n## EN\n<!-- Fill this -->\n"

        errors = validate_pr_body(body)

        self.assertTrue(any("RU" in error for error in errors))
        self.assertTrue(any("EN" in error for error in errors))

    def test_does_not_treat_global_checklist_as_english_content(self):
        body = (
            "## RU\n<!-- Заполните -->\n"
            "## EN\n<!-- Fill this -->\n"
            "## Checklist\n- [ ] Reviewed\n"
        )

        errors = validate_pr_body(body)

        self.assertTrue(any("EN" in error for error in errors))

    def test_does_not_treat_intermediate_section_as_russian_content(self):
        body = (
            "## RU\n<!-- Заполните -->\n"
            "## Checklist\n- [x] Reviewed\n"
            "## EN\nImplemented validation.\n"
        )

        errors = validate_pr_body(body)

        self.assertTrue(any("RU" in error for error in errors))

    def test_rejects_english_section_before_russian_section(self):
        errors = validate_pr_body("## EN\nDone.\n## RU\nСделано.\n")

        self.assertTrue(any("order" in error.lower() for error in errors))


class LabelPolicyTest(unittest.TestCase):
    def test_accepts_one_type_and_at_least_one_area(self):
        self.assertEqual(
            [], validate_labels(["type:ci", "area:build", "area:governance"])
        )

    def test_rejects_missing_area_and_multiple_types(self):
        errors = validate_labels(["type:ci", "type:test", "documentation"])

        self.assertTrue(any("exactly one" in error.lower() for error in errors))
        self.assertTrue(any("area:" in error for error in errors))


class ChangedFilePolicyTest(unittest.TestCase):
    def test_rejects_private_paths_case_insensitively(self):
        changes = [
            ChangedFile("A", "Nested/AgEnTs.Md"),
            ChangedFile("A", ".CoDeX/config.toml"),
            ChangedFile("A", "notes/SSC14_STAGE3_CI_PLAN_PRIVATE.md"),
        ]

        errors = validate_changed_files(changes)

        for path in (change.path for change in changes):
            self.assertTrue(any(path in error for error in errors), path)

    def test_rejects_private_rename_source_and_destination(self):
        changes = [
            ChangedFile("R100", "docs/public.md", old_path="AGENTS.md"),
            ChangedFile("R100", "SKILL.md", old_path="docs/public.md"),
        ]

        errors = validate_changed_files(changes)

        self.assertTrue(any("AGENTS.md" in error for error in errors))
        self.assertTrue(any("SKILL.md" in error for error in errors))

    def test_accepts_similar_public_source_names(self):
        changes = [
            ChangedFile("M", "docs/agent-skills.md"),
            ChangedFile("A", "src/main/java/example/SuperpowersManager.java"),
        ]

        self.assertEqual([], validate_changed_files(changes))

    def test_rejects_build_outputs_and_binary_artifacts(self):
        changes = [
            ChangedFile("A", "build/libs/mod.jar", binary=True),
            ChangedFile("A", "native/PLUGIN.DLL", binary=True),
            ChangedFile("A", "debug/session.LOG"),
        ]

        errors = validate_changed_files(changes)

        for path in (change.path for change in changes):
            self.assertTrue(any(path in error for error in errors), path)

    def test_accepts_intentional_binary_resources(self):
        changes = [
            ChangedFile("A", "src/main/resources/assets/ssc_14/textures/item/tool.PNG", binary=True),
            ChangedFile("M", "src/main/resources/assets/ssc_14/sounds/tool.ogg", binary=True),
        ]

        self.assertEqual([], validate_changed_files(changes))

    def test_rejects_unknown_binary_even_with_harmless_extension(self):
        errors = validate_changed_files(
            [ChangedFile("A", "docs/reference.dat", binary=True)]
        )

        self.assertTrue(any("unknown binary" in error.lower() for error in errors))

    def test_allows_known_gradle_wrapper_binary(self):
        self.assertEqual(
            [],
            validate_changed_files(
                [ChangedFile("M", "gradle/wrapper/gradle-wrapper.jar", binary=True)]
            ),
        )


class PolicyCliTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.root = Path(self.directory.name)
        self.git("init", "--quiet")
        self.git("config", "user.name", "Policy Test")
        self.git("config", "user.email", "policy@example.invalid")
        (self.root / "README.md").write_text("base\n", encoding="utf-8")
        self.git("add", "README.md")
        self.git("commit", "--quiet", "-m", "chore(repo): База / Establish baseline",
                 "-m", "RU:\n- Создана база.\n\nEN:\n- Established baseline.")
        self.base = self.git("rev-parse", "HEAD").stdout.decode().strip()
        (self.root / "policy.txt").write_text("policy\n", encoding="utf-8")
        self.git("add", "policy.txt")
        self.git("commit", "--quiet", "-m", "ci(policy): Проверить PR / Validate PR",
                 "-m", "RU:\n- Добавлена проверка.\n\nEN:\n- Added validation.")
        self.head = self.git("rev-parse", "HEAD").stdout.decode().strip()
        self.event = self.root / "event.json"

    def git(self, *args):
        return subprocess.run(
            ["git", "-C", str(self.root), *args], check=True, capture_output=True
        )

    def run_cli(self, body, labels, *, user="contributor", sender=None, head=None):
        self.event.write_text(
            json.dumps(
                {
                    "pull_request": {
                        "body": body,
                        "labels": [{"name": label} for label in labels],
                        "base": {"sha": self.base},
                        "head": {"sha": head or self.head},
                        "user": {"login": user},
                    },
                    "sender": {"login": sender or user},
                }
            ),
            encoding="utf-8",
        )
        return subprocess.run(
            [
                sys.executable,
                str(Path(__file__).with_name("policy_checks.py")),
                "--event",
                str(self.event),
                "--repository",
                str(self.root),
            ],
            capture_output=True,
            text=True,
            encoding="utf-8",
        )

    def test_cli_accepts_valid_pull_request_range(self):
        body = "## RU\nДобавлена проверка.\n## EN\nAdded validation.\n"

        result = self.run_cli(body, ["type:ci", "area:build"])

        self.assertEqual(0, result.returncode, result.stdout + result.stderr)
        self.assertIn("Pull request policy checks passed", result.stdout)

    def test_cli_reports_invalid_metadata(self):
        result = self.run_cli("## RU\n<!-- empty -->\n## EN\n<!-- empty -->", [])

        self.assertEqual(1, result.returncode)
        self.assertIn("::error::", result.stdout)
        self.assertIn("type:", result.stdout)

    def test_cli_uses_pull_request_author_for_dependabot_exception(self):
        (self.root / "dependency.txt").write_text("2\n", encoding="utf-8")
        self.git("add", "dependency.txt")
        self.git("commit", "--quiet", "-m", "build(deps): bump example from 1 to 2")
        automation_head = self.git("rev-parse", "HEAD").stdout.decode().strip()
        body = "Bumps example from 1 to 2.\n"

        result = self.run_cli(
            body,
            ["type:ci", "area:build"],
            user="dependabot[bot]",
            sender="maintainer",
            head=automation_head,
        )

        self.assertEqual(0, result.returncode, result.stdout + result.stderr)


class GitDiffCollectionTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.root = Path(self.directory.name)
        self.git("init", "--quiet")
        self.git("config", "user.name", "Diff Test")
        self.git("config", "user.email", "diff@example.invalid")

    def git(self, *args):
        return subprocess.run(
            ["git", "-C", str(self.root), *args], check=True, capture_output=True
        )

    def test_collects_only_pr_changes_when_target_branch_has_advanced(self):
        legacy = self.root / "libs/legacy.jar"
        legacy.parent.mkdir(parents=True)
        legacy.write_bytes(b"\x00legacy")
        self.git("add", "libs/legacy.jar")
        self.git("commit", "--quiet", "-m", "base")
        target_branch = self.git("branch", "--show-current").stdout.decode().strip()

        self.git("switch", "--quiet", "-c", "feature")
        (self.root / "policy.txt").write_text("policy\n", encoding="utf-8")
        self.git("add", "policy.txt")
        self.git("commit", "--quiet", "-m", "feature")
        feature_head = self.git("rev-parse", "HEAD").stdout.decode().strip()

        self.git("switch", "--quiet", target_branch)
        self.git("rm", "--quiet", "libs/legacy.jar")
        self.git("commit", "--quiet", "-m", "remove legacy binary")
        target_head = self.git("rev-parse", "HEAD").stdout.decode().strip()

        changes = read_changed_files(self.root, target_head, feature_head)

        self.assertEqual(["policy.txt"], [change.path for change in changes])


if __name__ == "__main__":
    unittest.main()
