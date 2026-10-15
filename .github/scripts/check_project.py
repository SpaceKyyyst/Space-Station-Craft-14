"""Validate build/metadata consistency without downloading Minecraft."""
import re
import sys
import tomllib
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]


def check_project(root: Path) -> list[tuple[str, str]]:
    errors = []
    required = ("gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.jar",
                "gradle/wrapper/gradle-wrapper.properties", "Dependency.gradle")
    for name in required:
        if not (root / name).is_file():
            errors.append((name, "Required build file is missing"))
    metadata_path = "src/main/resources/META-INF/neoforge.mods.toml"
    try:
        metadata = tomllib.loads((root / metadata_path).read_text(encoding="utf-8"))
        build = (root / "build.gradle").read_text(encoding="utf-8")
        version = re.search(r"(?m)^version\s*=\s*['\"]([^'\"]+)['\"]", build)
        mods = metadata.get("mods", [])
        if not version or len(mods) != 1 or mods[0].get("version") != version.group(1):
            errors.append((metadata_path, "Mod version must match build.gradle"))
        if len(mods) != 1 or mods[0].get("modId") != "ssc_14":
            errors.append((metadata_path, "Expected stable mod ID ssc_14"))
    except (OSError, ValueError) as error:
        errors.append((metadata_path, str(error)))
    return errors


def escape(value: str) -> str:
    return value.replace("%", "%25").replace("\r", "%0D").replace("\n", "%0A")


if __name__ == "__main__":
    issues = check_project(ROOT)
    for path, message in issues:
        print(f"::error file={path}::{escape(message)}")
    if not issues:
        print("Project metadata and required build files are consistent.")
    sys.exit(bool(issues))
