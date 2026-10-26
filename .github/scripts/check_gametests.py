"""Require completed, successful mod tests from Minecraft's XML report."""

import sys
import xml.etree.ElementTree as ET
from pathlib import Path


EXPECTED_TESTS = {
    "ssc_14:atmosphere_producer_and_wall",
    "ssc_14:atmosphere_cross_region",
    "ssc_14:atmosphere_reaction_tick",
    "ssc_14:airlock_gas_seal_frames",
    "ssc_14:atmosphere_chunk_resume",
    "ssc_14:energy_bridge_removal",
    "ssc_14:energy_supply_chain",
    "ssc_14:energy_idle_storage",
    "ssc_14:audit_energy_large_rebuild_budget_and_restart",
    "ssc_14:audit_energy_many_lamps_exact_demand",
    "ssc_14:audit_energy_shared_lamp_single_debit",
    "ssc_14:audit_energy_lamp_changes_and_depletion",
    "ssc_14:audit_pure_nitrous_oxide_decays",
    "ssc_14:audit_trace_gas_crosses_region_boundary",
    "ssc_14:compatibility_registry_ids",
    "ssc_14:compatibility_network_contracts",
    "ssc_14:compatibility_attribute_contract",
    "ssc_14:compatibility_block_banner_contract",
    "ssc_14:compatibility_block_fence_contract",
    "ssc_14:compatibility_block_chair_contract",
    "ssc_14:compatibility_block_table_contract",
    "ssc_14:compatibility_block_armchair_contract",
    "ssc_14:compatibility_block_planetary_contract",
    "ssc_14:compatibility_block_window_contract",
    "ssc_14:compatibility_block_structure_contract",
    "ssc_14:compatibility_block_grille_contract",
    "ssc_14:compatibility_block_catwalk_contract",
    "ssc_14:compatibility_block_floor_tile_contract",
    "ssc_14:compatibility_block_ceiling_tile_contract",
    "ssc_14:compatibility_block_station_wall_contract",
    "ssc_14:compatibility_block_closet_contract",
    "ssc_14:compatibility_block_lighting_contract",
    "ssc_14:compatibility_block_machine_frame_contract",
    "ssc_14:compatibility_block_sheathing_contract",
    "ssc_14:compatibility_block_energy_machine_contract",
    "ssc_14:compatibility_block_energy_device_contract",
    "ssc_14:compatibility_block_sale_pallet_contract",
    "ssc_14:compatibility_block_order_pallet_contract",
    "ssc_14:compatibility_block_fuel_tank_contract",
    "ssc_14:compatibility_block_debug_13_contract",
    "ssc_14:compatibility_block_airlock_threshold_contract",
    "ssc_14:compatibility_block_upper_closet_plug_contract",
    "ssc_14:compatibility_block_secure_closet_contract",
    "ssc_14:compatibility_block_extinguisher_cabinet_contract",
    "ssc_14:compatibility_block_vending_plug_contract",
    "ssc_14:compatibility_block_portable_sheater_contract",
    "ssc_14:compatibility_block_failure_sentinel_contract",
    "ssc_14:compatibility_block_debug_gravity_contract",
    "ssc_14:compatibility_block_c4_contract",
    "ssc_14:compatibility_block_nuclear_warhead_contract",
    "ssc_14:compatibility_block_gravity_generator_plug_contract",
    "ssc_14:compatibility_block_gravity_generator_contract",
    "ssc_14:compatibility_block_computer_contract",
    "ssc_14:compatibility_block_gas_producer_o2_contract",
    "ssc_14:compatibility_block_communication_servers_contract",
    "ssc_14:compatibility_block_communication_console_contract",
    "ssc_14:compatibility_block_microwave_contract",
    "ssc_14:compatibility_block_commerce_console_contract",
    "ssc_14:compatibility_block_vending_machine_contract",
    "ssc_14:compatibility_block_network_button_contract",
    "ssc_14:compatibility_block_id_console_contract",
    "ssc_14:compatibility_block_shutters_contract",
    "ssc_14:compatibility_block_airlock_upper_plugs_contract",
    "ssc_14:compatibility_block_base_airlock_contract",
    "ssc_14:compatibility_block_department_airlocks_contract",
    "ssc_14:compatibility_item_material_contract",
    "ssc_14:compatibility_item_machine_component_contract",
    "ssc_14:compatibility_item_lighting_component_contract",
    "ssc_14:compatibility_item_encryption_key_contract",
    "ssc_14:compatibility_item_scarf_contract",
}


def check_report(path: Path) -> list[str]:
    try:
        root = ET.parse(path).getroot()
    except (OSError, ET.ParseError) as error:
        return [f"Cannot read GameTest report {path}: {error}"]

    cases = list(root.iter("testcase"))
    names = {case.get("name") for case in cases}
    errors = [f"GameTest did not run: {name}" for name in sorted(EXPECTED_TESTS - names)]
    for case in cases:
        for result in case:
            if result.tag in {"failure", "error", "skipped"}:
                detail = result.get("message") or result.text or result.tag
                errors.append(f"GameTest {case.get('name', '<unnamed>')}: {detail.strip()}")
    return errors


def main() -> int:
    if len(sys.argv) != 2:
        print("Usage: check_gametests.py <report.xml>", file=sys.stderr)
        return 2
    errors = check_report(Path(sys.argv[1]))
    for error in errors:
        escaped = error.replace("%", "%25").replace("\r", "%0D").replace("\n", "%0A")
        print(f"::error title=GameTest report::{escaped}")
    if errors:
        return 1
    print(f"All {len(EXPECTED_TESTS)} required mod GameTests passed.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
