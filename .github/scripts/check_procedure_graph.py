#!/usr/bin/env python3
from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
PROCEDURE_DIR = Path("src/main/java/net/ssc14/content/procedure")
MANIFEST = Path("config/compatibility/procedure-call-graph.txt")
CALL_PATTERN = re.compile(r"\b([A-Za-z_$][\w$]*Procedure)\.execute\s*\(")


def discover_edges(root: Path) -> tuple[str, ...]:
    procedure_root = root / PROCEDURE_DIR
    edges: set[str] = set()
    for source in sorted(procedure_root.glob("*.java")):
        caller = source.stem
        text = source.read_text(encoding="utf-8-sig")
        for callee in CALL_PATTERN.findall(text):
            if callee != caller:
                edges.add(f"{caller} -> {callee}")
    return tuple(sorted(edges))


def read_manifest(root: Path) -> tuple[str, ...]:
    path = root / MANIFEST
    if not path.is_file():
        raise FileNotFoundError(f"missing procedure call-graph manifest: {MANIFEST}")
    entries = tuple(
        line.strip()
        for line in path.read_text(encoding="utf-8").splitlines()
        if line.strip() and not line.lstrip().startswith("#")
    )
    if entries != tuple(sorted(set(entries))):
        raise ValueError(f"{MANIFEST} must contain unique sorted entries")
    return entries


def check(root: Path) -> list[str]:
    try:
        expected = read_manifest(root)
    except (FileNotFoundError, ValueError) as error:
        return [str(error)]
    actual = discover_edges(root)
    missing = sorted(set(actual) - set(expected))
    stale = sorted(set(expected) - set(actual))
    return [
        *(f"unmanifested procedure edge: {edge}" for edge in missing),
        *(f"stale procedure edge: {edge}" for edge in stale),
    ]


def main() -> int:
    errors = check(ROOT)
    if errors:
        for error in errors:
            print(f"::error::{error}")
        return 1
    print(f"Procedure call graph is stable ({len(discover_edges(ROOT))} edges).")
    return 0


if __name__ == "__main__":
    sys.exit(main())
