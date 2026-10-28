#!/usr/bin/env python3
"""Dry-run release planner; never builds, tags, publishes, or sends notifications."""
from __future__ import annotations

import argparse
import re
import subprocess
from pathlib import Path


VERSION_RE = re.compile(r"^version\s*=\s*['\"]([^'\"]+)['\"]", re.MULTILINE)
TAG_RE = re.compile(r"^ssc_14-(?P<version>.+?)-build\.")


def run(*args: str) -> str:
    return subprocess.check_output(
        args, text=True, encoding="utf-8", errors="replace", stderr=subprocess.DEVNULL
    ).strip()


def current_version(root: Path) -> str:
    match = VERSION_RE.search((root / "build.gradle").read_text(encoding="utf-8"))
    if not match:
        raise ValueError("build.gradle version is missing")
    return match.group(1)


def existing_versions() -> set[str]:
    versions: set[str] = set()
    for tag in run("git", "tag", "--list", "ssc_14-*").splitlines():
        match = TAG_RE.match(tag)
        if match:
            versions.add(match.group("version"))
    return versions


def summary(root: Path, version: str) -> list[str]:
    previous = run("git", "log", "-1", "--format=%H")
    commits = run("git", "log", "--pretty=format:%s", "-20").splitlines()
    return [f"{line}" for line in commits if line][:10] or [f"Version {version} at {previous[:12]}"]


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--root", type=Path, default=Path("."))
    args = parser.parse_args()
    version = current_version(args.root)
    duplicate = version in existing_versions()
    print(f"version={version}")
    normalized = version.lower()
    if any(x in normalized for x in ('alpha', 'beta', 'rc')):
        release_kind = 'prerelease'
    elif any(x in normalized for x in ('fix', 'patch', 'hotfix')):
        release_kind = 'fix'
    else:
        release_kind = 'stable'
    print(f"release_kind={release_kind}")
    print(f"duplicate_version={'yes' if duplicate else 'no'}")
    print("description:")
    for line in summary(args.root, version):
        print(f"- {line}")
    print(f"decision={'skip_duplicate' if duplicate else 'build_release'}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
