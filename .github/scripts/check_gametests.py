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
