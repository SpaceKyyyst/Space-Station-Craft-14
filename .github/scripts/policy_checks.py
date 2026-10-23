"""Validate pull request metadata and changed files without network access."""

import argparse
import json
import re
import subprocess
import sys
from dataclasses import dataclass
from pathlib import Path, PurePosixPath


CONVENTIONAL_SUBJECT = re.compile(
    r"^(?P<type>feat|fix|docs|refactor|test|build|ci|chore|perf|style|revert)"
    r"\((?P<scope>[a-z0-9][a-z0-9._/-]*)\)(?:!)?: (?P<description>\S.*)$"
)
CYRILLIC = re.compile(r"[А-Яа-яЁё]")
LATIN = re.compile(r"[A-Za-z]")
HTML_COMMENT = re.compile(r"<!--.*?-->", re.DOTALL)
PRIVATE_DIRECTORIES = frozenset(
    {"superpowers", ".superpowers", ".codex", ".agents"}
)
PRIVATE_FILENAMES = frozenset({"agents.md", "skill.md"})
GENERATED_DIRECTORIES = frozenset(
    {"build", ".gradle", ".idea", ".cache", "node_modules", "out", "target"}
)
FORBIDDEN_ARTIFACT_SUFFIXES = frozenset(
    {
        ".class",
        ".dll",
        ".dylib",
        ".exe",
        ".gz",
        ".log",
        ".mov",
        ".mp4",
        ".pdb",
        ".rar",
        ".so",
        ".tar",
        ".webm",
        ".zip",
        ".7z",
    }
)
RESOURCE_BINARY_SUFFIXES = frozenset(
    {".gif", ".jpeg", ".jpg", ".ogg", ".otf", ".png", ".ttf", ".wav", ".webp"}
)
RESOURCE_ROOTS = (
    "src/main/resources/",
    "src/test/resources/",
    "src/gametest/resources/",
)
AUTOMATION_ACTORS = frozenset({"dependabot[bot]"})


@dataclass(frozen=True)
class ChangedFile:
    status: str
    path: str
    old_path: str | None = None
    binary: bool = False


def validate_commit_message(message: str, *, require_bilingual: bool = True) -> list[str]:
    lines = message.strip().splitlines()
    subject = lines[0].strip() if lines else ""
    match = CONVENTIONAL_SUBJECT.fullmatch(subject)
    if match is None:
        return [
            "Commit subject must use Conventional Commits as type(scope): description"
        ]
    if not require_bilingual:
        return []

    errors = []
    description = match.group("description")
    russian, separator, english = description.partition(" / ")
    if not separator or not CYRILLIC.search(russian) or not LATIN.search(english):
        errors.append(
            "Commit subject must contain Russian / English descriptions"
        )

    body = "\n".join(lines[1:])
    ru_match = re.search(r"(?m)^RU:\s*$", body)
    en_match = re.search(r"(?m)^EN:\s*$", body)
    if ru_match is None or en_match is None or ru_match.start() >= en_match.start():
        errors.append("Commit body must contain ordered non-empty RU: and EN: sections")
        return errors
    ru_content = body[ru_match.end():en_match.start()].strip()
    en_content = body[en_match.end():].strip()
    if not ru_content or not en_content:
        errors.append("Commit body must contain ordered non-empty RU: and EN: sections")
    return errors


def validate_pr_body(body: str) -> list[str]:
    visible = HTML_COMMENT.sub("", body or "")
    headings = list(re.finditer(r"(?mi)^##\s+(RU|EN)\s*$", visible))
    ru = next((match for match in headings if match.group(1).upper() == "RU"), None)
    en = next((match for match in headings if match.group(1).upper() == "EN"), None)
    if ru is None or en is None:
        missing = []
        if ru is None:
            missing.append("RU")
        if en is None:
            missing.append("EN")
        return [f"Pull request body is missing {' and '.join(missing)} section content"]
    if ru.start() >= en.start():
        return ["Pull request RU and EN sections are in the wrong order"]

    errors = []
    ru_content = _meaningful_markdown(visible[ru.end():en.start()])
    english_tail = visible[en.end():]
    next_section = re.search(r"(?m)^##\s+\S.*$", english_tail)
    if next_section is not None:
        english_tail = english_tail[:next_section.start()]
    en_content = _meaningful_markdown(english_tail)
    if not ru_content:
        errors.append("Pull request RU section must contain content")
    if not en_content:
        errors.append("Pull request EN section must contain content")
    return errors


def validate_labels(labels: list[str]) -> list[str]:
    folded = [label.casefold() for label in labels]
    types = [label for label in folded if label.startswith("type:")]
    areas = [label for label in folded if label.startswith("area:")]
    errors = []
    if len(types) != 1:
        errors.append("Pull request must have exactly one type:* label")
    if not areas:
        errors.append("Pull request must have at least one area:* label")
    return errors


def validate_changed_files(changes: list[ChangedFile]) -> list[str]:
    errors = []
    for change in changes:
        if change.status.upper().startswith("D"):
            continue
        paths = [change.path]
        if change.old_path is not None:
            paths.insert(0, change.old_path)
        for path in paths:
            reason = _forbidden_path_reason(path)
            if reason is not None:
                errors.append(f"{path}: {reason}")

        destination_reason = _forbidden_path_reason(change.path)
        if destination_reason is not None:
            continue
        normalized = _normalize_path(change.path)
        suffix = PurePosixPath(normalized).suffix.casefold()
        if suffix in FORBIDDEN_ARTIFACT_SUFFIXES:
            errors.append(f"{change.path}: generated or debug binary artifact is forbidden")
        elif change.binary and not _is_allowed_binary(normalized, suffix):
            errors.append(f"{change.path}: unknown binary file is not allowlisted")
    return errors


def _meaningful_markdown(section: str) -> str:
    content = []
    for line in section.splitlines():
        stripped = line.strip()
        if stripped and not stripped.startswith("#"):
            content.append(stripped)
    return "\n".join(content)


def _normalize_path(path: str) -> str:
    normalized = path.replace("\\", "/")
    while normalized.startswith("./"):
        normalized = normalized[2:]
    return normalized


def _forbidden_path_reason(path: str) -> str | None:
    normalized = _normalize_path(path)
    parts = PurePosixPath(normalized).parts
    folded = tuple(part.casefold() for part in parts)
    if any(part in PRIVATE_DIRECTORIES for part in folded):
        return "private tool directory must not be tracked"
    if folded and folded[-1] in PRIVATE_FILENAMES:
        return "private instruction file must not be tracked"
    if folded and "_private." in folded[-1]:
        return "private plan or report must not be tracked"
    if any(part in GENERATED_DIRECTORIES for part in folded):
        return "generated output or cache directory must not be tracked"
    return None


def _is_allowed_binary(path: str, suffix: str) -> bool:
    folded = path.casefold()
    if folded == "gradle/wrapper/gradle-wrapper.jar":
        return True
    return suffix in RESOURCE_BINARY_SUFFIXES and folded.startswith(RESOURCE_ROOTS)


def _git(repository: Path, *args: str) -> bytes:
    return subprocess.run(
        ["git", "-C", str(repository), *args],
        check=True,
        capture_output=True,
    ).stdout


def read_commit_messages(repository: Path, base: str, head: str) -> list[str]:
    output = _git(repository, "log", "--format=%B%x00", f"{base}..{head}")
    return [message.decode("utf-8", errors="replace").strip()
            for message in output.split(b"\0") if message.strip()]


def read_changed_files(repository: Path, base: str, head: str) -> list[ChangedFile]:
    binary_paths = _read_binary_paths(repository, base, head)
    output = _git(
        repository, "diff", "--name-status", "-z", "--find-renames", base, head
    )
    fields = output.split(b"\0")
    changes = []
    index = 0
    while index < len(fields) and fields[index]:
        status = fields[index].decode("ascii", errors="replace")
        index += 1
        first_path = fields[index].decode("utf-8", errors="replace")
        index += 1
        if status.startswith(("R", "C")):
            destination = fields[index].decode("utf-8", errors="replace")
            index += 1
            changes.append(
                ChangedFile(
                    status,
                    destination,
                    old_path=first_path,
                    binary=_normalize_path(destination) in binary_paths,
                )
            )
        else:
            changes.append(
                ChangedFile(
                    status,
                    first_path,
                    binary=_normalize_path(first_path) in binary_paths,
                )
            )
    return changes


def _read_binary_paths(repository: Path, base: str, head: str) -> set[str]:
    output = _git(
        repository, "diff", "--numstat", "-z", "--no-renames", base, head
    )
    binary_paths = set()
    for record in output.split(b"\0"):
        if not record:
            continue
        fields = record.split(b"\t", 2)
        if len(fields) == 3 and fields[0] == b"-" and fields[1] == b"-":
            binary_paths.add(_normalize_path(fields[2].decode("utf-8", errors="replace")))
    return binary_paths


def run_policy_checks(event: dict, repository: Path) -> list[str]:
    pull_request = event.get("pull_request")
    if not isinstance(pull_request, dict):
        return ["GitHub event does not contain pull_request metadata"]
    body = pull_request.get("body") or ""
    labels = [label.get("name", "") for label in pull_request.get("labels", [])
              if isinstance(label, dict)]
    base = pull_request.get("base", {}).get("sha", "")
    head = pull_request.get("head", {}).get("sha", "")
    actor = event.get("sender", {}).get("login", "")
    if not base or not head:
        return ["Pull request event is missing immutable base/head SHAs"]

    errors = validate_pr_body(body) + validate_labels(labels)
    require_bilingual = actor.casefold() not in AUTOMATION_ACTORS
    for index, message in enumerate(read_commit_messages(repository, base, head), start=1):
        errors.extend(
            f"Commit {index}: {error}"
            for error in validate_commit_message(
                message, require_bilingual=require_bilingual
            )
        )
    errors.extend(validate_changed_files(read_changed_files(repository, base, head)))
    return errors


def _escape_annotation(value: str) -> str:
    return (value.replace("%", "%25").replace("\r", "%0D")
            .replace("\n", "%0A"))


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--event", type=Path, required=True)
    parser.add_argument("--repository", type=Path, default=Path.cwd())
    arguments = parser.parse_args(argv)
    try:
        event = json.loads(arguments.event.read_text(encoding="utf-8"))
        errors = run_policy_checks(event, arguments.repository)
    except (OSError, ValueError, subprocess.CalledProcessError) as error:
        errors = [f"Cannot evaluate pull request policy: {error}"]

    for error in errors:
        print(f"::error::{_escape_annotation(error)}")
    if errors:
        return 1
    print("Pull request policy checks passed.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
