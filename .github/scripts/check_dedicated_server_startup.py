#!/usr/bin/env python3
"""Verify that the GameTest process reached a healthy dedicated-server startup."""
from pathlib import Path
import argparse
import sys


DEDICATED_MARKER = "(DEDICATED_SERVER"
STARTUP_MARKER = "Started game test server"
FAILURE_MARKERS = (
    "Attempted to load class ",
    "Exception in server tick loop",
    "Failed to start the minecraft server",
    "Loading errors encountered",
)


def check(log_path: Path) -> list[str]:
    if not log_path.is_file():
        return [f"missing dedicated server log: {log_path}"]

    log = log_path.read_text(encoding="utf-8-sig", errors="replace")
    errors = []
    if DEDICATED_MARKER not in log:
        errors.append("server log does not identify a DEDICATED_SERVER distribution")
    if STARTUP_MARKER not in log:
        errors.append("dedicated server log has no successful startup marker")
    for line in log.splitlines():
        if any(marker in line for marker in FAILURE_MARKERS):
            message = line.split("]: ", 1)[-1].strip()
            errors.append(f"dedicated server startup failure marker: {message}")
    return sorted(set(errors))


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("log", type=Path)
    args = parser.parse_args()
    errors = check(args.log)
    for error in errors:
        print(f"::error::{error}")
    if errors:
        return 1
    print(f"Dedicated server startup verified from {args.log}.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
