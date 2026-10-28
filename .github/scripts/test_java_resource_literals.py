#!/usr/bin/env python3
import tempfile
import unittest
from pathlib import Path

from check_java_resource_literals import BASELINE, check, discover_issues


class JavaResourceLiteralsTest(unittest.TestCase):
    def setUp(self) -> None:
        self.temporary = tempfile.TemporaryDirectory()
        self.root = Path(self.temporary.name)
        self.java = self.root / "src/main/java/example/Example.java"
        self.java.parent.mkdir(parents=True)
        (self.root / BASELINE).parent.mkdir(parents=True)
        (self.root / BASELINE).write_text("", encoding="utf-8")

    def tearDown(self) -> None:
        self.temporary.cleanup()

    def write_java(self, source: str) -> None:
        self.java.write_text(source, encoding="utf-8")

    def write_asset(self, relative_path: str) -> None:
        target = self.root / "src/main/resources/assets/ssc_14" / relative_path
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_bytes(b"fixture")

    def test_accepts_existing_texture_literal(self) -> None:
        self.write_java(
            'class Example { Object texture = Identifier.parse('
            '"ssc_14:textures/screens/panel.png"); }'
        )
        self.write_asset("textures/screens/panel.png")

        self.assertEqual([], discover_issues(self.root))

    def test_reports_missing_literal_asset(self) -> None:
        self.write_java(
            'class Example { Object texture = Identifier.fromNamespaceAndPath('
            '"ssc_14", "textures/screens/missing.png"); }'
        )

        self.assertEqual(
            [
                "src/main/java/example/Example.java:1: missing Java resource "
                "literal ssc_14:textures/screens/missing.png"
            ],
            discover_issues(self.root),
        )

    def test_reports_invalid_identifier_literal(self) -> None:
        self.write_java(
            'class Example { Object id = Identifier.parse("ssc_14:Bad\\\\Path"); }'
        )

        self.assertEqual(
            [
                "src/main/java/example/Example.java:1: invalid Java resource "
                "literal ssc_14:Bad\\Path"
            ],
            discover_issues(self.root),
        )

    def test_ignores_registry_ids_and_dynamic_paths_for_existence(self) -> None:
        self.write_java(
            'class Example { Object registry = Identifier.parse("ssc_14:machine"); '
            'Object dynamic = Identifier.fromNamespaceAndPath("ssc_14", '
            '"textures/" + name + ".png"); }'
        )

        self.assertEqual([], discover_issues(self.root))

    def test_accepts_exact_sorted_baseline(self) -> None:
        self.write_java(
            'class Example { Object texture = Identifier.parse('
            '"ssc_14:textures/screens/missing.png"); }'
        )
        issue = (
            "src/main/java/example/Example.java:1: missing Java resource "
            "literal ssc_14:textures/screens/missing.png"
        )
        (self.root / BASELINE).write_text(issue + "\n", encoding="utf-8")

        self.assertEqual([], check(self.root))

    def test_reports_baseline_drift(self) -> None:
        self.write_java("class Example {}")
        (self.root / BASELINE).write_text("stale issue\n", encoding="utf-8")

        self.assertEqual(
            ["Java resource literal baseline drift:\nexpected:\nstale issue\nactual:\n"],
            check(self.root),
        )


if __name__ == "__main__":
    unittest.main()
