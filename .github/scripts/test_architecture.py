import tempfile
import textwrap
import unittest
from pathlib import Path

from check_architecture import (
    JavaUnit,
    analyze_architecture,
    collect_legacy_client_debt,
    parse_java_source,
    read_client_baseline,
)


class ArchitectureMatrixTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.root = Path(self.directory.name)
        (self.root / "config/architecture").mkdir(parents=True)
        (self.root / "config/architecture/legacy-client-imports.txt").write_text(
            "# No legacy client imports in this fixture.\n", encoding="utf-8"
        )

    def write_java(self, relative_path: str, source: str) -> None:
        path = self.root / "src/main/java" / relative_path
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(textwrap.dedent(source), encoding="utf-8")

    def test_ignores_import_text_in_comments_but_checks_real_and_static_imports(self):
        source = """
            package net.ssc14.core.example;
            // import net.ssc14.client.LineComment;
            /* import net.ssc14.content.BlockComment; */
            import java.util.List;
            import static net.ssc14.client.RealClient.VALUE;

            final class Example {
                String text = "import net.ssc14.integration.BodyString;";
            }
        """
        unit = parse_java_source(textwrap.dedent(source))
        self.assertEqual(
            JavaUnit(
                package="net.ssc14.core.example",
                imports=("java.util.List", "net.ssc14.client.RealClient.VALUE"),
            ),
            unit,
        )
        self.write_java("net/ssc14/core/example/Example.java", source)

        self.assertEqual(
            [
                (
                    "src/main/java/net/ssc14/core/example/Example.java",
                    "target layer 'core' cannot import target layer 'client': "
                    "net.ssc14.client.RealClient.VALUE",
                )
            ],
            analyze_architecture(self.root),
        )

    def test_accepts_every_allowed_target_edge(self):
        allowed = {
            "core": ("core",),
            "common": ("core", "common"),
            "integration": ("core", "common", "integration"),
            "content": ("core", "common", "integration", "content"),
            "client": ("core", "common", "integration", "content", "client"),
        }
        for source_layer, target_layers in allowed.items():
            imports = "\n".join(
                f"import net.ssc14.{target}.api.Dependency;" for target in target_layers
            )
            self.write_java(
                f"net/ssc14/{source_layer}/example/Allowed.java",
                f"package net.ssc14.{source_layer}.example;\n{imports}\nfinal class Allowed {{}}\n",
            )

        self.assertEqual([], analyze_architecture(self.root))

    def test_accepts_composition_root_edges_from_integration(self):
        self.write_java(
            "net/ssc14/integration/Ssc14Bootstrap.java",
            """
            package net.ssc14.integration;
            import net.ssc14.client.api.Dependency;
            import net.ssc14.content.api.Dependency;
            final class Ssc14Bootstrap {}
            """,
        )

        self.assertEqual([], analyze_architecture(self.root))

    def test_rejects_every_forbidden_target_edge(self):
        allowed = {
            "core": {"core"},
            "common": {"core", "common"},
            "integration": {"core", "common", "integration"},
            "content": {"core", "common", "integration", "content"},
            "client": {"core", "common", "integration", "content", "client"},
        }
        layers = ("core", "common", "integration", "content", "client")
        expected = []
        for source_layer in layers:
            for target_layer in layers:
                if target_layer in allowed[source_layer]:
                    continue
                class_name = f"From{source_layer.title()}To{target_layer.title()}"
                relative_path = f"net/ssc14/{source_layer}/forbidden/{class_name}.java"
                imported = f"net.ssc14.{target_layer}.api.Dependency"
                self.write_java(
                    relative_path,
                    f"package net.ssc14.{source_layer}.forbidden;\n"
                    f"import {imported};\nfinal class {class_name} {{}}\n",
                )
                expected.append(
                    (
                        f"src/main/java/{relative_path}",
                        f"target layer '{source_layer}' cannot import target layer "
                        f"'{target_layer}': {imported}",
                    )
                )

        self.assertEqual(sorted(expected), analyze_architecture(self.root))

    def test_rejects_unknown_direct_target_layer(self):
        self.write_java(
            "net/ssc14/unknown/Example.java",
            "package net.ssc14.unknown;\nfinal class Example {}\n",
        )

        self.assertEqual(
            [
                (
                    "src/main/java/net/ssc14/unknown/Example.java",
                    "unknown direct target layer 'unknown'",
                )
            ],
            analyze_architecture(self.root),
        )

    def test_core_accepts_jdk_and_rejects_platform_third_party_and_legacy_imports(self):
        self.write_java(
            "net/ssc14/core/example/Example.java",
            """
            package net.ssc14.core.example;
            import java.util.List;
            import jdk.jfr.Event;
            import net.minecraft.world.level.Level;
            import net.neoforged.bus.api.Event;
            import com.google.common.collect.ImmutableList;
            import net.mcreator.ssc.Legacy;
            final class Example {}
            """,
        )
        path = "src/main/java/net/ssc14/core/example/Example.java"

        self.assertEqual(
            sorted(
                [
                (
                    path,
                    "core may import only java.* and jdk.* outside net.ssc14: "
                    "com.google.common.collect.ImmutableList",
                ),
                (
                    path,
                    "target code must not import legacy namespace: net.mcreator.ssc.Legacy",
                ),
                (
                    path,
                    "core may import only java.* and jdk.* outside net.ssc14: "
                    "net.minecraft.world.level.Level",
                ),
                (
                    path,
                    "core may import only java.* and jdk.* outside net.ssc14: "
                    "net.neoforged.bus.api.Event",
                ),
                ]
            ),
            analyze_architecture(self.root),
        )

    def test_reports_malformed_or_missing_package_for_target_source(self):
        self.write_java(
            "net/ssc14/core/Missing.java",
            "final class Missing {}\n",
        )
        self.write_java(
            "net/ssc14/core/Wrong.java",
            "package net.ssc14.common;\nfinal class Wrong {}\n",
        )

        self.assertEqual(
            [
                (
                    "src/main/java/net/ssc14/core/Missing.java",
                    "target source must declare package 'net.ssc14.core' or a subpackage",
                ),
                (
                    "src/main/java/net/ssc14/core/Wrong.java",
                    "target source package 'net.ssc14.common' does not match path package "
                    "'net.ssc14.core'",
                ),
            ],
            analyze_architecture(self.root),
        )

    def test_enforces_ordinary_and_static_wildcard_imports(self):
        self.write_java(
            "net/ssc14/core/Wildcard.java",
            """
            package net.ssc14.core;
            import static net.ssc14.client.ClientConstants.*;
            final class Wildcard {}
            """,
        )
        self.write_java(
            "net/mcreator/ssc/WildcardLegacy.java",
            """
            package net.mcreator.ssc;
            import net.minecraft.client.*;
            final class WildcardLegacy {}
            """,
        )

        self.assertEqual(
            [
                (
                    "src/main/java/net/mcreator/ssc/WildcardLegacy.java",
                    "unapproved client import for family 'minecraft-client'",
                ),
                (
                    "src/main/java/net/ssc14/core/Wildcard.java",
                    "target layer 'core' cannot import target layer 'client': "
                    "net.ssc14.client.ClientConstants.*",
                ),
            ],
            analyze_architecture(self.root),
        )

    def test_parses_multiline_same_line_and_commented_imports(self):
        source = """
            package net.ssc14.common; import java.util.
                List; import net.minecraft.client.Minecraft;
            import net.ssc14./* package separator */client.Screen;
            final class Formatted {}
        """
        self.assertEqual(
            JavaUnit(
                package="net.ssc14.common",
                imports=(
                    "java.util.List",
                    "net.minecraft.client.Minecraft",
                    "net.ssc14.client.Screen",
                ),
            ),
            parse_java_source(textwrap.dedent(source)),
        )
        self.write_java("net/ssc14/common/Formatted.java", source)

        self.assertEqual(
            [
                (
                    "src/main/java/net/ssc14/common/Formatted.java",
                    "target layer 'common' cannot import client API family "
                    "'minecraft-client': net.minecraft.client.Minecraft",
                ),
                (
                    "src/main/java/net/ssc14/common/Formatted.java",
                    "target layer 'common' cannot import target layer 'client': "
                    "net.ssc14.client.Screen",
                ),
                (
                    "src/main/java/net/ssc14/common/Formatted.java",
                    "unapproved client import for family 'minecraft-client'",
                ),
            ],
            analyze_architecture(self.root),
        )

    def test_rejects_target_packages_declared_outside_target_path(self):
        self.write_java(
            "net/mcreator/ssc/MisplacedCore.java",
            """
            package net.ssc14.core;
            import net.minecraft.world.level.Level;
            final class MisplacedCore {}
            """,
        )
        self.write_java(
            "net/mcreator/ssc/MisplacedUnknown.java",
            "package net.ssc14.unknown;\nfinal class MisplacedUnknown {}\n",
        )

        self.assertEqual(
            [
                (
                    "src/main/java/net/mcreator/ssc/MisplacedCore.java",
                    "core may import only java.* and jdk.* outside net.ssc14: "
                    "net.minecraft.world.level.Level",
                ),
                (
                    "src/main/java/net/mcreator/ssc/MisplacedCore.java",
                    "target package 'net.ssc14.core' must be located under "
                    "'src/main/java/net/ssc14/core'",
                ),
                (
                    "src/main/java/net/mcreator/ssc/MisplacedUnknown.java",
                    "target package 'net.ssc14.unknown' must be located under "
                    "'src/main/java/net/ssc14/unknown'",
                ),
                (
                    "src/main/java/net/mcreator/ssc/MisplacedUnknown.java",
                    "unknown direct target layer 'unknown'",
                ),
            ],
            analyze_architecture(self.root),
        )


class LegacyClientDebtTest(unittest.TestCase):
    def setUp(self):
        self.directory = tempfile.TemporaryDirectory()
        self.addCleanup(self.directory.cleanup)
        self.root = Path(self.directory.name)
        self.source_root = self.root / "src/main/java"
        self.source_root.mkdir(parents=True)
        self.baseline = self.root / "config/architecture/legacy-client-imports.txt"
        self.baseline.parent.mkdir(parents=True)

    def write_java(self, relative_path: str, imports: tuple[str, ...]) -> Path:
        path = self.source_root / relative_path
        path.parent.mkdir(parents=True, exist_ok=True)
        package = ".".join(Path(relative_path).parent.parts)
        import_lines = "\n".join(f"import {name};" for name in imports)
        path.write_text(
            f"package {package};\n{import_lines}\nfinal class Fixture {{}}\n",
            encoding="utf-8",
        )
        return path

    def write_baseline(self, *rows: str) -> None:
        content = "# Frozen legacy client imports; entries may only be removed.\n"
        if rows:
            content += "\n".join(rows) + "\n"
        self.baseline.write_text(content, encoding="utf-8")

    def test_accepts_exact_legacy_client_baseline(self):
        legacy = self.write_java(
            "net/mcreator/ssc/procedures/Legacy.java",
            ("com.mojang.blaze3d.vertex.PoseStack", "net.minecraft.client.Minecraft"),
        )
        self.write_java(
            "net/mcreator/ssc/client/ApprovedLegacy.java",
            ("net.minecraft.client.Minecraft",),
        )
        self.write_java(
            "net/ssc14/client/ApprovedTarget.java",
            ("org.lwjgl.glfw.GLFW",),
        )
        self.write_baseline(
            "blaze3d src/main/java/net/mcreator/ssc/procedures/Legacy.java",
            "minecraft-client src/main/java/net/mcreator/ssc/procedures/Legacy.java",
        )
        units = {legacy: parse_java_source(legacy.read_text(encoding="utf-8"))}

        self.assertEqual(
            frozenset(
                {
                    ("blaze3d", "src/main/java/net/mcreator/ssc/procedures/Legacy.java"),
                    (
                        "minecraft-client",
                        "src/main/java/net/mcreator/ssc/procedures/Legacy.java",
                    ),
                }
            ),
            read_client_baseline(self.baseline),
        )
        self.assertEqual(
            read_client_baseline(self.baseline),
            collect_legacy_client_debt(self.root, units),
        )
        self.assertEqual([], analyze_architecture(self.root))

    def test_rejects_new_client_family_for_already_allowlisted_file(self):
        self.write_java(
            "net/mcreator/ssc/procedures/Legacy.java",
            ("net.minecraft.client.Minecraft", "org.lwjgl.glfw.GLFW"),
        )
        self.write_baseline(
            "minecraft-client src/main/java/net/mcreator/ssc/procedures/Legacy.java"
        )

        self.assertEqual(
            [
                (
                    "src/main/java/net/mcreator/ssc/procedures/Legacy.java",
                    "unapproved client import for family 'lwjgl'",
                )
            ],
            analyze_architecture(self.root),
        )

    def test_rejects_stale_legacy_client_entry(self):
        self.write_java("net/mcreator/ssc/procedures/Legacy.java", ())
        self.write_baseline(
            "minecraft-client src/main/java/net/mcreator/ssc/procedures/Legacy.java"
        )

        self.assertEqual(
            [
                (
                    "src/main/java/net/mcreator/ssc/procedures/Legacy.java",
                    "stale baseline entry for client API family 'minecraft-client'",
                )
            ],
            analyze_architecture(self.root),
        )

    def test_rejects_duplicate_unsorted_malformed_and_unknown_family_rows(self):
        self.write_java("net/mcreator/ssc/Legacy.java", ())
        cases = {
            "duplicate": (
                "minecraft-client src/main/java/net/mcreator/ssc/Legacy.java\n"
                "minecraft-client src/main/java/net/mcreator/ssc/Legacy.java\n",
                "duplicate baseline row at line 2",
            ),
            "unsorted": (
                "neoforge-client src/main/java/net/mcreator/ssc/Legacy.java\n"
                "minecraft-client src/main/java/net/mcreator/ssc/Legacy.java\n",
                "baseline rows must be lexicographically sorted (line 2)",
            ),
            "malformed": (
                "minecraft-client only-two no-third\n",
                "baseline row 1 must contain exactly two whitespace-separated columns",
            ),
            "unknown-family": (
                "fabric-client src/main/java/net/mcreator/ssc/Legacy.java\n",
                "baseline row 1 uses unknown client family 'fabric-client'",
            ),
        }
        for name, (content, expected_message) in cases.items():
            with self.subTest(name=name):
                self.baseline.write_text(content, encoding="utf-8")
                self.assertEqual(
                    [("config/architecture/legacy-client-imports.txt", expected_message)],
                    analyze_architecture(self.root),
                )

    def test_rejects_missing_source_or_baseline_root(self):
        missing_source = self.root / "missing-source"
        missing_source.mkdir()
        (missing_source / "config/architecture").mkdir(parents=True)
        (missing_source / "config/architecture/legacy-client-imports.txt").write_text(
            "# Empty.\n", encoding="utf-8"
        )
        self.write_java("net/mcreator/ssc/Legacy.java", ())

        self.assertEqual(
            [("src/main/java", "Java source root is missing")],
            analyze_architecture(missing_source),
        )
        self.assertEqual(
            [
                (
                    "config/architecture/legacy-client-imports.txt",
                    "Legacy client import baseline is missing",
                )
            ],
            analyze_architecture(self.root),
        )


class TargetArchitectureDocumentationTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.root = Path(__file__).resolve().parents[2]
        cls.target_path = cls.root / "docs/TARGET_STRUCTURE.md"

    def test_documents_equivalent_russian_and_english_technical_contracts(self):
        self.assertTrue(self.target_path.is_file(), "docs/TARGET_STRUCTURE.md is missing")
        document = self.target_path.read_text(encoding="utf-8")
        ru_marker = "<!-- architecture-contract:ru -->"
        en_marker = "<!-- architecture-contract:en -->"
        self.assertEqual(1, document.count(ru_marker))
        self.assertEqual(1, document.count(en_marker))
        russian = document.split(ru_marker, 1)[1].split(en_marker, 1)[0]
        english = document.split(en_marker, 1)[1]

        shared_terms = (
            "net.ssc14.core",
            "net.ssc14.common",
            "net.ssc14.integration",
            "net.ssc14.content",
            "net.ssc14.client",
            "net.mcreator.ssc",
            "core -> core",
            "common -> core, common",
            "integration -> core, common, integration",
            "content -> core, common, integration, content",
            "client -> core, common, integration, content, client",
            "net.minecraft.client",
            "net.neoforged.neoforge.client",
            "com.mojang.blaze3d",
            "org.lwjgl",
            "characterize -> move -> facade -> consumers -> compatibility verification "
            "-> legacy removal",
            "py -3 .github/scripts/test_architecture.py",
            "py -3 .github/scripts/check_architecture.py",
            "py -3 .github/scripts/check_project.py",
            "git diff --check",
            ".\\gradlew.bat runGameTestServer --no-daemon --no-build-cache --console=plain",
        )
        for section_name, section in (("RU", russian), ("EN", english)):
            for term in shared_terms:
                with self.subTest(section=section_name, term=term):
                    self.assertIn(term, section)

        for term in (
            "legacy-адаптер",
            "composition root",
            "сервер является источником истины",
            "совместимость persistence и network",
            "пустые пакеты и классы запрещены",
        ):
            self.assertIn(term.casefold(), russian.casefold())
        for term in (
            "legacy adapter",
            "composition root",
            "server is authoritative",
            "persistence and network compatibility",
            "empty packages and classes are forbidden",
        ):
            self.assertIn(term.casefold(), english.casefold())

    def test_current_architecture_navigation_links_to_target_contract(self):
        readme = (self.root / "README.md").read_text(encoding="utf-8")
        current = (self.root / "docs/ARCHITECTURE.md").read_text(encoding="utf-8")

        self.assertIn("docs/TARGET_STRUCTURE.md", readme)
        self.assertIn("TARGET_STRUCTURE.md", current)


if __name__ == "__main__":
    unittest.main()
