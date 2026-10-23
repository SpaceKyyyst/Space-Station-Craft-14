"""Enforce incremental package boundaries for the SSC14 migration."""

from __future__ import annotations

import re
import sys
from dataclasses import dataclass
from pathlib import Path, PurePosixPath
from typing import Mapping


ROOT = Path(__file__).resolve().parents[2]
TARGET_ROOT = "net.ssc14"
TARGET_LAYERS = ("core", "common", "integration", "content", "client")
ALLOWED_TARGET_IMPORTS = {
    "core": frozenset({"core"}),
    "common": frozenset({"core", "common"}),
    "integration": frozenset({"core", "common", "integration"}),
    "content": frozenset({"core", "common", "integration", "content"}),
    "client": frozenset(TARGET_LAYERS),
}
CLIENT_FAMILIES = {
    "minecraft-client": "net.minecraft.client",
    "neoforge-client": "net.neoforged.neoforge.client",
    "blaze3d": "com.mojang.blaze3d",
    "lwjgl": "org.lwjgl",
}
BASELINE_PATH = Path("config/architecture/legacy-client-imports.txt")
APPROVED_CLIENT_ROOTS = (
    "src/main/java/net/mcreator/ssc/client/",
    "src/main/java/net/ssc14/client/",
)


@dataclass(frozen=True)
class JavaUnit:
    package: str | None
    imports: tuple[str, ...]


def _strip_comments_and_literals(source: str) -> str:
    """Replace comments and literals with spaces while preserving newlines."""
    output: list[str] = []
    index = 0
    state = "code"
    while index < len(source):
        character = source[index]
        following = source[index + 1] if index + 1 < len(source) else ""
        if state == "code":
            if character == "/" and following == "/":
                output.extend((" ", " "))
                index += 2
                state = "line_comment"
                continue
            if character == "/" and following == "*":
                output.extend((" ", " "))
                index += 2
                state = "block_comment"
                continue
            if character == '"':
                output.append(" ")
                index += 1
                state = "string"
                continue
            if character == "'":
                output.append(" ")
                index += 1
                state = "character"
                continue
            output.append(character)
            index += 1
            continue

        if state == "line_comment":
            output.append("\n" if character == "\n" else " ")
            index += 1
            if character == "\n":
                state = "code"
            continue

        if state == "block_comment":
            if character == "*" and following == "/":
                output.extend((" ", " "))
                index += 2
                state = "code"
            else:
                output.append("\n" if character == "\n" else " ")
                index += 1
            continue

        quote = '"' if state == "string" else "'"
        if character == "\\" and following:
            output.append(" ")
            output.append("\n" if following == "\n" else " ")
            index += 2
        elif character == quote:
            output.append(" ")
            index += 1
            state = "code"
        else:
            output.append("\n" if character == "\n" else " ")
            index += 1
    return "".join(output)


def parse_java_source(source: str) -> JavaUnit:
    """Read package/import declarations from a Java compilation-unit preamble."""
    sanitized = _strip_comments_and_literals(source)
    package: str | None = None
    imports: list[str] = []
    declaration = re.compile(r"^[A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*$")
    package_line = re.compile(r"^package\s+([^;]+)\s*;\s*$")
    import_line = re.compile(r"^import\s+(?:static\s+)?([^;]+)\s*;\s*$")

    for raw_line in sanitized.splitlines():
        line = raw_line.strip()
        if not line:
            continue
        package_match = package_line.fullmatch(line)
        if package_match:
            candidate = package_match.group(1).strip()
            package = candidate if declaration.fullmatch(candidate) else None
            continue
        import_match = import_line.fullmatch(line)
        if import_match:
            candidate = import_match.group(1).strip()
            if declaration.fullmatch(candidate):
                imports.append(candidate)
            continue
        break
    return JavaUnit(package=package, imports=tuple(imports))


def _target_layer(qualified_name: str) -> str | None:
    prefix = f"{TARGET_ROOT}."
    if not qualified_name.startswith(prefix):
        return None
    return qualified_name[len(prefix):].split(".", 1)[0]


def _client_family(qualified_name: str) -> str | None:
    for family, prefix in CLIENT_FAMILIES.items():
        if qualified_name == prefix or qualified_name.startswith(f"{prefix}."):
            return family
    return None


def read_client_baseline(path: Path) -> frozenset[tuple[str, str]]:
    """Read and strictly validate the exact legacy client-debt ceiling."""
    rows: list[tuple[str, str]] = []
    seen: set[tuple[str, str]] = set()
    previous: tuple[str, str] | None = None
    for line_number, raw_line in enumerate(path.read_text(encoding="utf-8").splitlines(), 1):
        stripped = raw_line.strip()
        if not stripped or stripped.startswith("#"):
            continue
        columns = stripped.split()
        if len(columns) != 2:
            raise ValueError(
                f"baseline row {line_number} must contain exactly two "
                "whitespace-separated columns"
            )
        family, source_path = columns
        if family not in CLIENT_FAMILIES:
            raise ValueError(
                f"baseline row {line_number} uses unknown client family '{family}'"
            )
        normalized = PurePosixPath(source_path)
        if (
            "\\" in source_path
            or normalized.is_absolute()
            or ".." in normalized.parts
            or normalized.as_posix() != source_path
            or not source_path.startswith("src/main/java/")
            or not source_path.endswith(".java")
        ):
            raise ValueError(
                f"baseline row {line_number} has invalid repo-relative Java path "
                f"'{source_path}'"
            )
        row = (family, source_path)
        if row in seen:
            raise ValueError(f"duplicate baseline row at line {line_number}")
        if previous is not None and row < previous:
            raise ValueError(
                f"baseline rows must be lexicographically sorted (line {line_number})"
            )
        rows.append(row)
        seen.add(row)
        previous = row
    return frozenset(rows)


def collect_legacy_client_debt(
    root: Path, units: Mapping[Path, JavaUnit]
) -> frozenset[tuple[str, str]]:
    """Collect exact source-path/client-family pairs outside client roots."""
    debt: set[tuple[str, str]] = set()
    for source_path, unit in units.items():
        relative_path = source_path.relative_to(root).as_posix()
        if any(relative_path.startswith(prefix) for prefix in APPROVED_CLIENT_ROOTS):
            continue
        for imported in unit.imports:
            family = _client_family(imported)
            if family is not None:
                debt.add((family, relative_path))
    return frozenset(debt)


def _target_diagnostics(path: str, layer: str, unit: JavaUnit) -> list[tuple[str, str]]:
    diagnostics: list[tuple[str, str]] = []
    for imported in unit.imports:
        imported_layer = _target_layer(imported)
        if imported_layer is not None:
            if imported_layer not in TARGET_LAYERS:
                diagnostics.append(
                    (path, f"target import uses unknown direct layer '{imported_layer}': {imported}")
                )
            elif imported_layer not in ALLOWED_TARGET_IMPORTS[layer]:
                diagnostics.append(
                    (
                        path,
                        f"target layer '{layer}' cannot import target layer "
                        f"'{imported_layer}': {imported}",
                    )
                )
            continue
        if imported == "net.mcreator.ssc" or imported.startswith("net.mcreator.ssc."):
            diagnostics.append(
                (path, f"target code must not import legacy namespace: {imported}")
            )
            continue
        family = _client_family(imported)
        if family is not None and layer != "client":
            diagnostics.append(
                (path, f"target layer '{layer}' cannot import client API family '{family}': {imported}")
            )
            continue
        if layer == "core" and not imported.startswith(("java.", "jdk.")):
            diagnostics.append(
                (path, f"core may import only java.* and jdk.* outside net.ssc14: {imported}")
            )
        elif layer == "common" and not imported.startswith(("java.", "jdk.", "net.minecraft.")):
            diagnostics.append(
                (path, f"common may import only JDK and server-safe Minecraft APIs: {imported}")
            )
    return diagnostics


def analyze_architecture(root: Path) -> list[tuple[str, str]]:
    """Return stable diagnostics for target package dependencies."""
    source_root = root / "src/main/java"
    if not source_root.is_dir():
        return [("src/main/java", "Java source root is missing")]
    baseline_path = root / BASELINE_PATH
    if not baseline_path.is_file():
        return [(BASELINE_PATH.as_posix(), "Legacy client import baseline is missing")]
    try:
        baseline = read_client_baseline(baseline_path)
    except (OSError, UnicodeError, ValueError) as error:
        return [(BASELINE_PATH.as_posix(), str(error))]

    diagnostics: list[tuple[str, str]] = []
    target_path_root = source_root / "net/ssc14"
    units = {
        source_path: parse_java_source(source_path.read_text(encoding="utf-8"))
        for source_path in sorted(source_root.rglob("*.java"))
    }
    for source_path, unit in units.items():
        try:
            target_relative = source_path.relative_to(target_path_root)
        except ValueError:
            continue
        path = source_path.relative_to(root).as_posix()
        if len(target_relative.parts) < 2:
            diagnostics.append((path, "target source must be inside a named layer"))
            continue
        layer = target_relative.parts[0]
        if layer not in TARGET_LAYERS:
            diagnostics.append((path, f"unknown direct target layer '{layer}'"))
            continue
        expected_package = ".".join((TARGET_ROOT, *target_relative.parts[:-1]))
        layer_package = f"{TARGET_ROOT}.{layer}"
        if unit.package is None:
            diagnostics.append(
                (path, f"target source must declare package '{layer_package}' or a subpackage")
            )
            continue
        if unit.package != expected_package:
            diagnostics.append(
                (
                    path,
                    f"target source package '{unit.package}' does not match path package "
                    f"'{expected_package}'",
                )
            )
            continue
        diagnostics.extend(_target_diagnostics(path, layer, unit))
    actual_debt = collect_legacy_client_debt(root, units)
    for family, path in sorted(actual_debt - baseline):
        diagnostics.append((path, f"unapproved client import for family '{family}'"))
    for family, path in sorted(baseline - actual_debt):
        diagnostics.append((path, f"stale baseline entry for client API family '{family}'"))
    return sorted(diagnostics)


def _escape_annotation(value: str) -> str:
    return value.replace("%", "%25").replace("\r", "%0D").replace("\n", "%0A")


if __name__ == "__main__":
    issues = analyze_architecture(ROOT)
    for issue_path, message in issues:
        annotation_path = _escape_annotation(issue_path).replace(":", "%3A").replace(",", "%2C")
        print(f"::error file={annotation_path}::{_escape_annotation(message)}")
    if not issues:
        print("Architecture boundaries are valid (0 violations).")
    sys.exit(bool(issues))
