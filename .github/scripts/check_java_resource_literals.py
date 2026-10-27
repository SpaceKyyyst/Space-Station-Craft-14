#!/usr/bin/env python3
"""Validate static Minecraft identifiers and Java asset references."""
from pathlib import Path
import re
import sys


JAVA_ROOT = Path("src/main/java")
ASSET_ROOT = Path("src/main/resources/assets")
BASELINE = Path("config/compatibility/java-resource-literals.txt")
RESOURCE_PREFIXES = (
    "font/",
    "models/",
    "particles/",
    "shaders/",
    "sounds/",
    "textures/",
)
NAMESPACE_PATTERN = re.compile(r"^[a-z0-9_.-]+$")
PATH_PATTERN = re.compile(r"^[a-z0-9/._-]+$")
PARSE_PATTERN = re.compile(
    r"(?:Identifier|ResourceLocation)\s*\.\s*parse\s*\(\s*"
    r'"((?:\\.|[^"\\])*)"\s*\)'
)
FROM_PARTS_PATTERN = re.compile(
    r"(?:Identifier|ResourceLocation)\s*\.\s*fromNamespaceAndPath\s*\(\s*"
    r'"((?:\\.|[^"\\])*)"\s*,\s*"((?:\\.|[^"\\])*)"\s*\)'
)


def _java_string(value: str) -> str:
    return value.replace('\\"', '"').replace('\\\\', '\\')


def _literal_identifiers(source: str):
    for match in PARSE_PATTERN.finditer(source):
        value = _java_string(match.group(1))
        if ":" in value:
            namespace, path = value.split(":", 1)
        else:
            namespace, path = "minecraft", value
        yield match.start(), namespace, path
    for match in FROM_PARTS_PATTERN.finditer(source):
        yield match.start(), _java_string(match.group(1)), _java_string(match.group(2))


def _is_valid(namespace: str, path: str) -> bool:
    return bool(NAMESPACE_PATTERN.fullmatch(namespace) and PATH_PATTERN.fullmatch(path))


def discover_issues(root: Path) -> list[str]:
    errors = []
    java_root = root / JAVA_ROOT
    for source_path in sorted(java_root.rglob("*.java")) if java_root.is_dir() else ():
        source = source_path.read_text(encoding="utf-8-sig")
        relative = source_path.relative_to(root).as_posix()
        for offset, namespace, path in _literal_identifiers(source):
            line = source.count("\n", 0, offset) + 1
            identifier = f"{namespace}:{path}"
            if not _is_valid(namespace, path):
                errors.append(
                    f"{relative}:{line}: invalid Java resource literal {identifier}"
                )
                continue
            if namespace != "ssc_14" or not path.startswith(RESOURCE_PREFIXES):
                continue
            target = root / ASSET_ROOT / namespace / Path(path)
            if not target.is_file():
                errors.append(
                    f"{relative}:{line}: missing Java resource literal {identifier}"
                )
    return sorted(set(errors))


def _expected(root: Path) -> tuple[str, ...]:
    path = root / BASELINE
    if not path.is_file():
        raise FileNotFoundError(f"missing Java resource literal baseline: {BASELINE}")
    entries = tuple(
        line.strip()
        for line in path.read_text(encoding="utf-8").splitlines()
        if line.strip() and not line.lstrip().startswith("#")
    )
    if entries != tuple(sorted(set(entries))):
        raise ValueError(f"{BASELINE} must contain unique sorted entries")
    return entries


def check(root: Path) -> list[str]:
    try:
        expected = _expected(root)
    except (FileNotFoundError, ValueError) as error:
        return [str(error)]
    actual = tuple(discover_issues(root))
    if actual == expected:
        return []
    return [
        "Java resource literal baseline drift:\nexpected:\n"
        + "\n".join(expected)
        + "\nactual:\n"
        + "\n".join(actual)
    ]


if __name__ == "__main__":
    issues = check(Path(__file__).resolve().parents[2])
    for issue in issues:
        print(issue)
    print(f"Java resource literal baseline violations: {len(issues)}")
    sys.exit(bool(issues))
