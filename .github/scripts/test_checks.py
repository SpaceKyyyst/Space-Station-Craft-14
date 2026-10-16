import contextlib
import io
import subprocess
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

from check_project import check_project, check_tracked_files
from report_checks import report


class ProjectChecksTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.root = Path(self.directory.name)
        for name in ("gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.jar",
                     "gradle/wrapper/gradle-wrapper.properties", "Dependency.gradle"):
            self.write(name, "fixture")
        self.write("build.gradle", "version = '0.1.27'\n")
        self.metadata = "src/main/resources/META-INF/neoforge.mods.toml"
        self.write(self.metadata, '[[mods]]\nmodId="ssc_14"\nversion="0.1.27"\n')

    def write(self, name, content):
        path = self.root / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding="utf-8")

    def test_accepts_consistent_project(self):
        self.assertEqual([], check_project(self.root))

    def test_reports_missing_wrapper(self):
        (self.root / "gradlew").unlink()
        self.assertEqual("gradlew", check_project(self.root)[0][0])

    def test_reports_version_drift(self):
        self.write("build.gradle", "version = '0.1.28'\n")
        self.assertIn("version must match", check_project(self.root)[0][1])

    def test_reports_invalid_metadata(self):
        self.write(self.metadata, "invalid = [")
        self.assertTrue(check_project(self.root))

    def test_reports_registry_id_change(self):
        self.write(self.metadata, '[[mods]]\nmodId="changed"\nversion="0.1.27"\n')
        self.assertIn("stable mod ID", check_project(self.root)[0][1])

    def test_annotations_include_compilation_style_and_test_errors(self):
        self.write("build-output.log", "src/Example.java:12: error: cannot find symbol\n")
        self.write("build/reports/checkstyle/main.xml",
                   '<checkstyle><file name="src/Example.java">'
                   '<error line="7" message="bad style"/></file></checkstyle>')
        self.write("build/test-results/domainTest/TEST-Example.xml",
                   '<testsuite><testcase classname="Example" name="calculates">'
                   '<failure message="expected 1"/></testcase></testsuite>')
        output = io.StringIO()
        with contextlib.redirect_stdout(output):
            report(self.root)
        self.assertIn("::error file=src/Example.java,line=12::cannot find symbol", output.getvalue())
        self.assertIn("line=7::bad style", output.getvalue())
        self.assertIn("Example.calculates: expected 1", output.getvalue())


class TrackedFilesTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.root = Path(self.directory.name)
        self.git("init", "--quiet")

    def git(self, *args):
        return subprocess.run(["git", "-C", str(self.root), *args],
                              check=True, capture_output=True)

    def write(self, name):
        path = self.root / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text("local fixture", encoding="utf-8")

    def test_rejects_tracked_local_files_case_insensitively(self):
        paths = ["docs/superpowers/plans/plan.md", "SUPERPOWERS/module.py",
                 ".superpowers/config.json", ".codex/config.toml",
                 ".agents/settings.json", "nested/AgEnTs.Md", "SKILL.md"]
        for path in paths:
            self.write(path)
        self.git("add", "--", *paths)
        self.assertEqual(set(paths), {path for path, _ in check_tracked_files(self.root)})

    def test_preserves_untracked_and_ignored_local_files(self):
        paths = ["SUPERPOWERS/module.py", "AGENTS.md", ".codex/config.toml"]
        for path in paths:
            self.write(path)
        (self.root / ".gitignore").write_text("SUPERPOWERS/\nAGENTS.md\n", encoding="utf-8")
        self.git("add", ".gitignore")
        self.assertEqual([], check_tracked_files(self.root))
        for path in paths:
            self.assertEqual("local fixture", (self.root / path).read_text(encoding="utf-8"))

    def test_accepts_project_paths_with_similar_names(self):
        paths = ["docs/superpowers-guide.md", "src/SuperpowersManager.java",
                 "docs/agent-skills.md", "README.md"]
        for path in paths:
            self.write(path)
        self.git("add", "--", *paths)
        self.assertEqual([], check_tracked_files(self.root))

    def test_reports_missing_git_checkout(self):
        self.assertTrue(check_tracked_files(self.root / "missing"))

    def test_reports_unavailable_git_executable(self):
        with patch("check_project.subprocess.run", side_effect=FileNotFoundError):
            self.assertTrue(check_tracked_files(self.root))


if __name__ == "__main__":
    unittest.main()
