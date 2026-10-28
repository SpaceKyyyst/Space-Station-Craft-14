#!/usr/bin/env python3
from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
JAVA_SOURCE_DIR = Path("src/main/java")
MATRIX = Path("config/compatibility/menu-security-matrix.txt")
SLOT_PATTERN = re.compile(r"\baddSlot\s*\(")
CONTAINER_MENU_PATTERN = re.compile(r"\bextends\s+AbstractContainerMenu\b")


def classify(source: str) -> tuple[str, str]:
    if "AirlockWireService.canUsePanel" in source:
        return "airlock-wire-service", "server-strict"
    if "world.mayInteract(player, pos)" in source and "getMainHandItem()" in source:
        return "access-configurator", "server-strict"
    if "if (this.bound)" in source and "return true;" in source:
        return "generated-bound", "unbound-allowed"
    return "unknown", "unknown"


def discover(root: Path) -> tuple[str, ...]:
    entries: list[str] = []
    for path in (root / JAVA_SOURCE_DIR).rglob("*Menu.java"):
        source = path.read_text(encoding="utf-8-sig")
        if not CONTAINER_MENU_PATTERN.search(source):
            continue
        policy, fallback = classify(source)
        slots = len(SLOT_PATTERN.findall(source))
        entries.append(f"{path.stem} | {policy} | {fallback} | slots={slots}")
    return tuple(sorted(entries))


def expected(root: Path) -> tuple[str, ...]:
    path = root / MATRIX
    if not path.is_file():
        raise FileNotFoundError(f"missing menu security matrix: {MATRIX}")
    entries = tuple(
        line.strip()
        for line in path.read_text(encoding="utf-8").splitlines()
        if line.strip() and not line.lstrip().startswith("#")
    )
    if entries != tuple(sorted(set(entries))):
        raise ValueError(f"{MATRIX} must contain unique sorted entries")
    return entries


def check(root: Path) -> list[str]:
    try:
        pinned = expected(root)
    except (FileNotFoundError, ValueError) as error:
        return [str(error)]
    actual = discover(root)
    return [] if actual == pinned else [
        "menu security matrix drift:\nexpected:\n"
        + "\n".join(pinned)
        + "\nactual:\n"
        + "\n".join(actual)
    ]


def main() -> int:
    errors = check(ROOT)
    if errors:
        for error in errors:
            print(f"::error::{error}")
        return 1
    entries = discover(ROOT)
    strict = sum("server-strict" in entry for entry in entries)
    print(f"Menu security matrix is stable ({len(entries)} menus, {strict} strict).")
    return 0


if __name__ == "__main__":
    sys.exit(main())
