import contextlib
import io
import tempfile
import unittest
from pathlib import Path

from check_project import check_project
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


if __name__ == "__main__":
    unittest.main()
