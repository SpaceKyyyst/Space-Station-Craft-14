import base64
import contextlib
import io
import json
import os
import re
import shutil
import subprocess
import sys
import tempfile
import textwrap
import unittest
from pathlib import Path
from unittest.mock import patch

from check_project import check_project, check_tracked_files
from report_checks import report
from check_resources import check_resources


@unittest.skipUnless(os.name == "nt", "Windows workflow uses a batch wrapper")
class WindowsUnicodeWorkflowTest(unittest.TestCase):
    def test_separates_cyrillic_configuration_from_native_path_workers(self):
        powershell = shutil.which("pwsh") or shutil.which("powershell")
        if powershell is None:
            self.skipTest("PowerShell is required to execute the Windows workflow")
        workflow = (Path(__file__).resolve().parents[1] / "workflows/build.yml").read_text(
            encoding="utf-8")
        job = workflow.split("  windows-unicode-tests:\n", 1)[1].split("\n  release:", 1)[0]
        scripts = re.findall(r"        run: \|\n((?:          .*\n)+)", job)

        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            workspace = root / "ascii-checkout"
            runner_temp = root / "runner temp"
            workspace.mkdir()
            runner_temp.mkdir()
            capture = root / "invocations.jsonl"
            (workspace / "capture.py").write_text(
                "import json, os, sys\nfrom pathlib import Path\n"
                "with Path(os.environ['WORKFLOW_CAPTURE']).open('a', encoding='utf-8') as output:\n"
                " output.write(json.dumps({"
                "'wrapper_dir': str(Path(__file__).resolve().parent), "
                "'cwd': os.getcwd(), 'args': sys.argv[1:]}) + '\\n')\n"
                "if 'help' in sys.argv[1:]:\n Path('configuration-only.marker').touch()\n",
                encoding="utf-8")
            (workspace / "gradlew.bat").write_text(
                f'@"{sys.executable}" -B "%~dp0capture.py" %*\n', encoding="utf-8")
            environment = dict(os.environ, GITHUB_WORKSPACE=str(workspace),
                               RUNNER_TEMP=str(runner_temp), WORKFLOW_CAPTURE=str(capture))
            for script in scripts:
                command = base64.b64encode(
                    textwrap.dedent(script).encode("utf-16-le")).decode("ascii")
                result = subprocess.run(
                    [powershell, "-NoProfile", "-NonInteractive", "-EncodedCommand", command],
                    env=environment, cwd=workspace, capture_output=True, timeout=30)
                self.assertEqual(0, result.returncode, result.stderr.decode(errors="replace"))
            invocations = [json.loads(line) for line in capture.read_text(encoding="utf-8").splitlines()]
            self.assertEqual(2, len(invocations))
            configuration, workers = invocations
            unicode_checkout = runner_temp / "Тест плагина"
            native_checkout = runner_temp / "Tést plugin"
            self.assertTrue((unicode_checkout / "gradlew.bat").is_file())
            self.assertTrue((native_checkout / "gradlew.bat").is_file())
            self.assertTrue((unicode_checkout / "configuration-only.marker").is_file())
            self.assertFalse((native_checkout / "configuration-only.marker").exists(),
                             "Workers must use a fresh original-checkout copy")
            self.assertEqual(workspace, Path(configuration["wrapper_dir"]))
            self.assertEqual(unicode_checkout, Path(configuration["cwd"]))
            self.assertEqual(["--project-dir", ".", "help", "--no-daemon", "--no-build-cache"],
                             configuration["args"])
            self.assertEqual(native_checkout, Path(workers["wrapper_dir"]))
            self.assertEqual(native_checkout, Path(workers["cwd"]))
            self.assertEqual(["--project-dir", ".", "domainTest", "test",
                              "--no-daemon", "--no-build-cache"], workers["args"])
            for invocation in invocations:
                self.assertNotIn(str(unicode_checkout), invocation["args"])
                self.assertTrue(all(argument.isascii() for argument in invocation["args"]),
                                "Cyrillic checkout must stay out of Java launcher arguments")


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

    def test_pmd_namespaces_and_metrics_are_handled_separately(self):
        xml = ('<pmd xmlns="http://pmd.sourceforge.net/report/2.0.0">'
               '<file name="src/Example.java"><violation beginline="9" '
               'rule="BrokenNullCheck"> null guard </violation></file>'
               '<error filename="src/Bad.java" msg="parse failed"/></pmd>')
        self.write("build/reports/pmd/main.xml", xml)
        self.write("build/reports/pmd/method-metrics.xml", xml)
        output = io.StringIO()
        with contextlib.redirect_stdout(output):
            report(self.root)
        self.assertEqual(1, output.getvalue().count("line=9::BrokenNullCheck: null guard"))
        self.assertIn("file=src/Bad.java::parse failed", output.getvalue())


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


class ResourceChecksTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.root = Path(self.directory.name)
        self.assets = self.root / "src/main/resources/assets/ssc_14"
        self.write("items/example.json", {"model": {"model": "ssc_14:block/example"}})
        self.write("models/block/example.json", {
            "parent": "ssc_14:custom/base", "textures": {"0": "ssc_14:block/present"}})
        self.write("models/custom/base.json", {
            "elements": [{"faces": {"north": {"texture": "#0"}}}]})
        texture = self.assets / "textures/block/present.png"
        texture.parent.mkdir(parents=True)
        texture.touch()

    def write(self, name, data):
        path = self.assets / name
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(json.dumps(data), encoding="utf-8")

    def test_accepts_texture_supplied_by_child_model(self):
        self.assertEqual([], check_resources(self.root))

    def test_reports_missing_inherited_face_texture(self):
        (self.assets / "textures/block/present.png").unlink()
        self.assertIn("missing texture", check_resources(self.root)[0])

    def test_reports_unbound_inherited_slot(self):
        self.write("models/custom/base.json", {
            "elements": [{"faces": {"north": {"texture": "#2"}}}]})
        self.assertIn("unresolved texture #2", check_resources(self.root)[0])

    def test_reports_texture_alias_cycle(self):
        self.write("models/block/example.json", {
            "parent": "ssc_14:custom/base", "textures": {"0": "#1", "1": "#0"}})
        self.assertIn("unresolved texture", check_resources(self.root)[0])

    def test_reports_model_parent_cycle(self):
        self.write("models/custom/base.json", {"parent": "ssc_14:block/example"})
        self.assertIn("cyclic model parent", check_resources(self.root)[0])

    def test_reports_missing_generated_item_layer(self):
        self.write("models/block/example.json", {
            "parent": "minecraft:item/generated", "textures": {"layer0": "ssc_14:item/absent"}})
        self.assertIn("missing texture", check_resources(self.root)[0])


if __name__ == "__main__":
    unittest.main()
