"""Validate reachable models and the textures their rendered faces actually use."""
import json
from pathlib import Path
import sys


def check_resources(root: Path) -> list[str]:
    assets = root / "src/main/resources/assets/ssc_14"
    pending = [p for folder in ("items", "blockstates") for p in (assets / folder).glob("*.json")]
    visited = set()
    errors = []
    rendered_models = set()
    models = {}

    def visit(node, source):
        if isinstance(node, dict):
            for key, value in node.items():
                if key in ("model", "parent") and isinstance(value, str) and value.startswith("ssc_14:"):
                    target = assets / "models" / (value.split(":", 1)[1] + ".json")
                    if not target.is_file():
                        errors.append(f"{source.relative_to(root)}: missing {value}")
                    elif target not in visited:
                        pending.append(target)
                    if key == "model":
                        rendered_models.add(target)
                else:
                    visit(value, source)
        elif isinstance(node, list):
            for child in node:
                visit(child, source)

    while pending:
        source = pending.pop()
        if source in visited:
            continue
        visited.add(source)
        try:
            data = json.loads(source.read_text(encoding="utf-8-sig"))
            models[source] = data
            visit(data, source)
        except (ValueError, OSError) as error:
            errors.append(f"{source.relative_to(root)}: {error}")
    resolved = {}

    def inherit(source, chain):
        if source in chain:
            errors.append(f"{source.relative_to(root)}: cyclic model parent")
            return {}
        if source in resolved:
            return resolved[source]
        data = models.get(source, {})
        parent = data.get("parent", "")
        base = {}
        if parent.startswith("ssc_14:"):
            target = assets / "models" / (parent.split(":", 1)[1] + ".json")
            base = inherit(target, chain | {source})
        result = {**base, **data}
        result["textures"] = {**base.get("textures", {}), **data.get("textures", {})}
        resolved[source] = result
        return result

    for source in sorted(rendered_models):
        data = inherit(source, set())
        textures = data.get("textures", {})
        references = [face["texture"] for element in data.get("elements", [])
                      for face in element.get("faces", {}).values() if "texture" in face]
        references.extend("#" + key for key in textures
                          if key == "particle" or key.startswith("layer"))
        # Vanilla parents supply their own geometry, unavailable in this source tree.
        if "elements" not in data:
            references.extend("#" + key for key in textures)
        for reference in set(references):
            seen = set()
            value = reference
            while value.startswith("#"):
                if value in seen or value[1:] not in textures:
                    errors.append(f"{source.relative_to(root)}: unresolved texture {reference}")
                    value = ""
                    break
                seen.add(value)
                value = textures[value[1:]]
            if value.startswith("ssc_14:"):
                target = assets / "textures" / (value.split(":", 1)[1] + ".png")
                if not target.is_file():
                    errors.append(f"{source.relative_to(root)}: missing texture {value}")
    return sorted(set(errors))


if __name__ == "__main__":
    issues = check_resources(Path(__file__).resolve().parents[2])
    for issue in issues:
        print(issue)
    print(f"Invalid reachable model/texture references: {len(issues)}")
    sys.exit(bool(issues))
