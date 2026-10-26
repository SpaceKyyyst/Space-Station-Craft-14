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

    def test_energy_machine_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_energy_machine_contract", EXPECTED_TESTS)

    def test_energy_device_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_energy_device_contract", EXPECTED_TESTS)

    def test_sale_pallet_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_sale_pallet_contract", EXPECTED_TESTS)

    def test_order_pallet_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_order_pallet_contract", EXPECTED_TESTS)

    def test_fuel_tank_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_fuel_tank_contract", EXPECTED_TESTS)

    def test_debug_13_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_debug_13_contract", EXPECTED_TESTS)

    def test_airlock_threshold_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_airlock_threshold_contract", EXPECTED_TESTS)

    def test_upper_closet_plug_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_upper_closet_plug_contract", EXPECTED_TESTS)

    def test_secure_closet_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_secure_closet_contract", EXPECTED_TESTS)

    def test_extinguisher_cabinet_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_extinguisher_cabinet_contract", EXPECTED_TESTS)

    def test_vending_plug_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_vending_plug_contract", EXPECTED_TESTS)

    def test_portable_sheater_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_portable_sheater_contract", EXPECTED_TESTS)

    def test_failure_sentinel_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_failure_sentinel_contract", EXPECTED_TESTS)

    def test_debug_gravity_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_debug_gravity_contract", EXPECTED_TESTS)

    def test_c4_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_c4_contract", EXPECTED_TESTS)

    def test_nuclear_warhead_block_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_nuclear_warhead_contract", EXPECTED_TESTS)

    def test_gravity_generator_plug_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_gravity_generator_plug_contract", EXPECTED_TESTS)

    def test_gravity_generator_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_gravity_generator_contract", EXPECTED_TESTS)

    def test_computer_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_computer_contract", EXPECTED_TESTS)

    def test_gas_producer_o2_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_gas_producer_o2_contract", EXPECTED_TESTS)

    def test_communication_servers_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_communication_servers_contract", EXPECTED_TESTS)

    def test_communication_console_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_communication_console_contract", EXPECTED_TESTS)

    def test_microwave_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_microwave_contract", EXPECTED_TESTS)

    def test_commerce_console_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_commerce_console_contract", EXPECTED_TESTS)

    def test_vending_machine_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_vending_machine_contract", EXPECTED_TESTS)

    def test_network_button_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_network_button_contract", EXPECTED_TESTS)

    def test_id_console_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_id_console_contract", EXPECTED_TESTS)

    def test_shutters_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_shutters_contract", EXPECTED_TESTS)

    def test_airlock_upper_plugs_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_airlock_upper_plugs_contract", EXPECTED_TESTS)

    def test_base_airlock_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_base_airlock_contract", EXPECTED_TESTS)

    def test_department_airlocks_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_block_department_airlocks_contract", EXPECTED_TESTS)

    def test_material_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_material_contract", EXPECTED_TESTS)

    def test_machine_component_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_machine_component_contract", EXPECTED_TESTS)

    def test_lighting_component_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_lighting_component_contract", EXPECTED_TESTS)

    def test_encryption_key_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_encryption_key_contract", EXPECTED_TESTS)

    def test_scarf_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_scarf_contract", EXPECTED_TESTS)

    def test_food_packaging_waste_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_food_packaging_waste_contract", EXPECTED_TESTS)

    def test_uniform_accessory_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_uniform_accessory_contract", EXPECTED_TESTS)

    def test_uniform_jumpsuit_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_uniform_jumpsuit_contract", EXPECTED_TESTS)

    def test_passive_equipment_items_contract_is_required(self):
        self.assertIn("ssc_14:compatibility_item_passive_equipment_contract", EXPECTED_TESTS)

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
