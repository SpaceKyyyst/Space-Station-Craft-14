#!/usr/bin/env python3
import tempfile
import unittest
from pathlib import Path

from check_menu_security import MATRIX, MENU_DIR, check, classify, discover


class MenuSecurityTest(unittest.TestCase):
    def setUp(self) -> None:
        self.temporary = tempfile.TemporaryDirectory()
        self.root = Path(self.temporary.name)
        (self.root / MENU_DIR).mkdir(parents=True)
        (self.root / MATRIX).parent.mkdir(parents=True)

    def tearDown(self) -> None:
        self.temporary.cleanup()

    def test_classifies_strict_and_generated_policies(self) -> None:
        self.assertEqual(
            ("airlock-wire-service", "server-strict"),
            classify("return AirlockWireService.canUsePanel(player, pos);"),
        )
        self.assertEqual(
            ("access-configurator", "server-strict"),
            classify("getMainHandItem(); world.mayInteract(player, pos);"),
        )
        self.assertEqual(
            ("generated-bound", "unbound-allowed"),
            classify("if (this.bound) { return false; } return true;"),
        )

    def test_discovers_slots_and_policy(self) -> None:
        (self.root / MENU_DIR / "ExampleMenu.java").write_text(
            "class ExampleMenu { boolean x(){ if (this.bound) return false; return true; } "
            "void y(){ addSlot(one); addSlot(two); } }",
            encoding="utf-8",
        )
        self.assertEqual(
            ("ExampleMenu | generated-bound | unbound-allowed | slots=2",),
            discover(self.root),
        )

    def test_detects_matrix_drift(self) -> None:
        (self.root / MENU_DIR / "ExampleMenu.java").write_text(
            "class ExampleMenu { boolean x(){ if (this.bound) return false; return true; } }",
            encoding="utf-8",
        )
        (self.root / MATRIX).write_text(
            "ExampleMenu | generated-bound | unbound-allowed | slots=1\n",
            encoding="utf-8",
        )
        self.assertEqual(1, len(check(self.root)))


if __name__ == "__main__":
    unittest.main()
