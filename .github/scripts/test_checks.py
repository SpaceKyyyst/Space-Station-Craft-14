import contextlib
import io
import json
import tempfile
import unittest
from pathlib import Path

from check_project import check_project
from report_checks import report
from check_resources import check_resources


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
