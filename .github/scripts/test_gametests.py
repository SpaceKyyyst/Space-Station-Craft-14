"""Check that missing or failed server tests cannot produce a green CI result."""

import tempfile
import unittest
import xml.etree.ElementTree as ET
from pathlib import Path

from check_gametests import EXPECTED_TESTS, check_report


class GameTestReportTests(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.path = Path(self.directory.name) / "report.xml"

    def report(self, names=EXPECTED_TESTS, result=None):
        # Minecraft uses nested testsuite elements without summary counters.
        root = ET.Element("testsuite")
        suite = ET.SubElement(root, "testsuite")
        for name in sorted(names):
            case = ET.SubElement(suite, "testcase", name=name)
            if result:
                ET.SubElement(case, result, message="server assertion failed")
        ET.ElementTree(root).write(self.path)

    def test_complete_successful_suite_passes(self):
        self.report()
        self.assertEqual([], check_report(self.path))

    def test_each_unsuccessful_status_fails(self):
        for status in ("failure", "error", "skipped"):
            with self.subTest(status=status):
                self.report(result=status)
                errors = check_report(self.path)
                self.assertEqual(len(EXPECTED_TESTS), len(errors))
                self.assertTrue(all("server assertion failed" in error for error in errors))

    def test_missing_expected_test_fails(self):
        omitted = sorted(EXPECTED_TESTS)[0]
        self.report(EXPECTED_TESTS - {omitted})
        self.assertEqual([f"GameTest did not run: {omitted}"], check_report(self.path))

    def test_compatibility_registry_baseline_is_required(self):
        self.assertIn("ssc_14:compatibility_registry_ids", EXPECTED_TESTS)

    def test_compatibility_network_baseline_is_required(self):
        self.assertIn("ssc_14:compatibility_network_contracts", EXPECTED_TESTS)

    def test_attribute_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_attribute_contract", EXPECTED_TESTS)

    def test_banner_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_banner_contract", EXPECTED_TESTS)

    def test_fence_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_fence_contract", EXPECTED_TESTS)

    def test_chair_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_chair_contract", EXPECTED_TESTS)

    def test_table_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_table_contract", EXPECTED_TESTS)

    def test_armchair_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_armchair_contract", EXPECTED_TESTS)

    def test_planetary_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_planetary_contract", EXPECTED_TESTS)

    def test_window_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_window_contract", EXPECTED_TESTS)

    def test_structure_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_structure_contract", EXPECTED_TESTS)

    def test_grille_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_grille_contract", EXPECTED_TESTS)

    def test_catwalk_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_catwalk_contract", EXPECTED_TESTS)

    def test_floor_tile_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_floor_tile_contract", EXPECTED_TESTS)

    def test_ceiling_tile_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_ceiling_tile_contract", EXPECTED_TESTS)

    def test_station_wall_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_station_wall_contract", EXPECTED_TESTS)

    def test_closet_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_closet_contract", EXPECTED_TESTS)

    def test_lighting_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_lighting_contract", EXPECTED_TESTS)

    def test_machine_frame_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_machine_frame_contract", EXPECTED_TESTS)

    def test_sheathing_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_sheathing_contract", EXPECTED_TESTS)

    def test_empty_suite_fails(self):
        self.report(set())
        self.assertEqual(len(EXPECTED_TESTS), len(check_report(self.path)))

    def test_missing_report_fails(self):
        self.assertIn("Cannot read GameTest report", check_report(self.path)[0])

    def test_malformed_report_fails(self):
        self.path.write_text("<testsuite>", encoding="utf-8")
        self.assertIn("Cannot read GameTest report", check_report(self.path)[0])

    def test_additional_failed_test_also_fails(self):
        self.report()
        tree = ET.parse(self.path)
        case = ET.SubElement(tree.getroot(), "testcase", name="ssc_14:future_test")
        ET.SubElement(case, "failure", message="new test failed")
        tree.write(self.path)
        self.assertEqual(["GameTest ssc_14:future_test: new test failed"], check_report(self.path))


if __name__ == "__main__":
    unittest.main()
