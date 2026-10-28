#!/usr/bin/env python3
import tempfile
import unittest
from pathlib import Path

from check_dedicated_server_startup import check


class DedicatedServerStartupTest(unittest.TestCase):
    def setUp(self) -> None:
        self.temporary = tempfile.TemporaryDirectory()
        self.log = Path(self.temporary.name) / "latest.log"

    def tearDown(self) -> None:
        self.temporary.cleanup()

    def write_log(self, *lines: str) -> None:
        self.log.write_text("\n".join(lines) + "\n", encoding="utf-8")

    def test_accepts_confirmed_dedicated_gametest_server_startup(self) -> None:
        self.write_log(
            "Starting FancyModLoader version 11.0.15 (DEDICATED_SERVER in DEV)",
            "[Server thread/INFO] [GameTestServer/]: Started game test server",
        )

        self.assertEqual([], check(self.log))

    def test_reports_missing_startup_marker(self) -> None:
        self.write_log(
            "Starting FancyModLoader version 11.0.15 (DEDICATED_SERVER in DEV)"
        )

        self.assertEqual(
            ["dedicated server log has no successful startup marker"], check(self.log)
        )

    def test_rejects_startup_without_dedicated_distribution_marker(self) -> None:
        self.write_log(
            "Starting FancyModLoader version 11.0.15 (CLIENT in DEV)",
            "[Server thread/INFO] [GameTestServer/]: Started game test server",
        )

        self.assertEqual(
            ["server log does not identify a DEDICATED_SERVER distribution"],
            check(self.log),
        )

    def test_reports_client_classloading_on_dedicated_server(self) -> None:
        self.write_log(
            "Starting FancyModLoader version 11.0.15 (DEDICATED_SERVER in DEV)",
            "[Server thread/INFO] [GameTestServer/]: Started game test server",
            "Attempted to load class net/minecraft/client/Minecraft for invalid dist DEDICATED_SERVER",
        )

        self.assertEqual(
            [
                "dedicated server startup failure marker: Attempted to load class "
                "net/minecraft/client/Minecraft for invalid dist DEDICATED_SERVER"
            ],
            check(self.log),
        )

    def test_reports_missing_log(self) -> None:
        self.assertEqual(
            [f"missing dedicated server log: {self.log}"], check(self.log)
        )


if __name__ == "__main__":
    unittest.main()
