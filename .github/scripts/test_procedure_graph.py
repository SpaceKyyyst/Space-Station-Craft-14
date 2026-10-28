#!/usr/bin/env python3
import tempfile
import unittest
from pathlib import Path

from check_procedure_graph import MANIFEST, PROCEDURE_DIR, check, discover_edges


class ProcedureGraphTest(unittest.TestCase):
    def setUp(self) -> None:
        self.temporary = tempfile.TemporaryDirectory()
        self.root = Path(self.temporary.name)
        (self.root / PROCEDURE_DIR).mkdir(parents=True)
        (self.root / MANIFEST).parent.mkdir(parents=True)

    def tearDown(self) -> None:
        self.temporary.cleanup()

    def test_discovers_sorted_unique_direct_edges(self) -> None:
        (self.root / PROCEDURE_DIR / "CallerProcedure.java").write_text(
            "class CallerProcedure { void run() { LeafProcedure.execute(); "
            "LeafProcedure.execute(); OtherProcedure.execute(1); } }",
            encoding="utf-8",
        )
        self.assertEqual(
            ("CallerProcedure -> LeafProcedure", "CallerProcedure -> OtherProcedure"),
            discover_edges(self.root),
        )

    def test_reports_missing_and_stale_edges(self) -> None:
        (self.root / PROCEDURE_DIR / "CallerProcedure.java").write_text(
            "class CallerProcedure { void run() { LeafProcedure.execute(); } }",
            encoding="utf-8",
        )
        (self.root / MANIFEST).write_text(
            "CallerProcedure -> RemovedProcedure\n", encoding="utf-8"
        )
        self.assertEqual(
            [
                "unmanifested procedure edge: CallerProcedure -> LeafProcedure",
                "stale procedure edge: CallerProcedure -> RemovedProcedure",
            ],
            check(self.root),
        )

    def test_accepts_exact_manifest(self) -> None:
        (self.root / PROCEDURE_DIR / "CallerProcedure.java").write_text(
            "class CallerProcedure { void run() { LeafProcedure.execute(); } }",
            encoding="utf-8",
        )
        (self.root / MANIFEST).write_text(
            "CallerProcedure -> LeafProcedure\n", encoding="utf-8"
        )
        self.assertEqual([], check(self.root))


if __name__ == "__main__":
    unittest.main()
