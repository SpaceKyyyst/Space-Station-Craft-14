import tempfile
import unittest
from pathlib import Path

from check_architecture import analyze_architecture, parse_java_source, read_client_baseline


class ArchitectureChecksTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.root = Path(self.directory.name)
        (self.root / "src/main/java/net/ssc14/core").mkdir(parents=True)
        (self.root / "config/architecture").mkdir(parents=True)
        (self.root / "config/architecture/legacy-client-imports.txt").write_text(
            "# Empty baseline.\n", encoding="utf-8"
        )

    def tearDown(self):
        self.directory.cleanup()

    def write_java(self, relative_path: str, content: str) -> Path:
        path = self.root / "src/main/java" / relative_path
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding="utf-8")
        return path

    def test_accepts_canonical_source(self):
        self.write_java("net/ssc14/core/Example.java", "package net.ssc14.core;\nfinal class Example {}\n")
        self.assertEqual([], analyze_architecture(self.root))

    def test_rejects_package_path_mismatch(self):
        self.write_java("net/ssc14/core/Example.java", "package net.ssc14.common;\nfinal class Example {}\n")
        issues = analyze_architecture(self.root)
        self.assertTrue(any("does not match path package" in message for _, message in issues))

    def test_rejects_server_source_importing_client_api(self):
        self.write_java(
            "net/ssc14/common/Example.java",
            "package net.ssc14.common;\nimport net.minecraft.client.Minecraft;\nfinal class Example {}\n",
        )
        issues = analyze_architecture(self.root)
        self.assertTrue(any("cannot import client API family" in message for _, message in issues))

    def test_rejects_core_import_outside_allowed_platform_apis(self):
        self.write_java(
            "net/ssc14/core/Example.java",
            "package net.ssc14.core;\nimport net.minecraft.world.level.Level;\nfinal class Example {}\n",
        )
        issues = analyze_architecture(self.root)
        self.assertTrue(any("core may import only" in message for _, message in issues))

    def test_java_parser_ignores_comments_and_literals(self):
        unit = parse_java_source(
            "// import net.minecraft.client.Minecraft;\n"
            "package net.ssc14.core;\n"
            "final class Example { String text = \"import net.minecraft.client.Minecraft;\"; }\n"
        )
        self.assertEqual("net.ssc14.core", unit.package)
        self.assertEqual((), unit.imports)

    def test_empty_baseline_is_valid(self):
        baseline = self.root / "config/architecture/legacy-client-imports.txt"
        self.assertEqual(frozenset(), read_client_baseline(baseline))


if __name__ == "__main__":
    unittest.main()
