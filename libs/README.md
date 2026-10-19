# Handy API prototype dependency

`handy-0.1.0.jar` is the unmodified JAR supplied for this integration.

- SHA-256: `C388E4EFA4D6C971947BA63DDC5BCCDC0F969386CA815208F13596906D5F303E`
- Its metadata declares GPL 3.0, NeoForge >= 26.1.2.109 and LDLib2 >= 26.1.2.39.
- Gradle loads this local dependency through the existing `libs/*.jar` file tree.
- Install Handy and LDLib2 alongside the SSC14 JAR on both client and server.
- LDLib2 is resolved separately from Modrinth; no dependencies are embedded in SSC14.

Replace the local JAR with a published, pinned dependency when Handy has a stable distribution endpoint.
