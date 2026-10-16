"""Validate build/metadata consistency without downloading Minecraft."""
import re
import subprocess
import sys
import tomllib
from pathlib import Path, PurePosixPath

ROOT = Path(__file__).resolve().parents[2]
LOCAL_DIRECTORIES = frozenset({"superpowers", ".superpowers", ".codex", ".agents"})
LOCAL_FILENAMES = frozenset({"agents.md", "skill.md"})


def check_tracked_files(root: Path) -> list[tuple[str, str]]:
    """Check the Git index; ignored and untracked local files remain private."""
    try:
        result = subprocess.run(
            ["git", "-C", str(root), "ls-files", "--cached", "-z"],
            check=True, capture_output=True,
        )
    except (OSError, subprocess.CalledProcessError):
        return [(".gitignore", "Cannot inspect tracked files; Git checkout is required")]

    errors = []
    for raw_path in result.stdout.split(b"\0"):
        if not raw_path:
            continue
        path = raw_path.decode("utf-8", errors="replace")
        parts = PurePosixPath(path).parts
        if (any(part.casefold() in LOCAL_DIRECTORIES for part in parts)
                or parts[-1].casefold() in LOCAL_FILENAMES):
            errors.append((path, "Local tool files and instructions must not be tracked"))
    return errors


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
    issues = check_project(ROOT) + check_tracked_files(ROOT)
    for path, message in issues:
        annotation_path = escape(path).replace(":", "%3A").replace(",", "%2C")
        print(f"::error file={annotation_path}::{escape(message)}")
    if not issues:
        print("Project metadata, required build files and tracked paths are valid.")
    sys.exit(bool(issues))
