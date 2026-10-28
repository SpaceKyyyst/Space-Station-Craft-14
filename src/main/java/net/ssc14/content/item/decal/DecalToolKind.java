package net.ssc14.content.item.decal;

/** Explicit role and menu identity for a decal administration tool. */
public enum DecalToolKind {
    SPAWNER_1(1),
    SPAWNER_2(2),
    SPAWNER_3(3),
    SPAWNER_4(4),
    SPAWNER_5(5),
    SPAWNER_6(6),
    DELETION(0);

    private final int spawnerNumber;

    DecalToolKind(int spawnerNumber) {
        this.spawnerNumber = spawnerNumber;
    }

    public boolean isSpawner() {
        return spawnerNumber > 0;
    }

    public String menuTitle() {
        if (!isSpawner()) {
            throw new IllegalStateException("Deletion tool has no menu");
        }
        return "DECAL " + spawnerNumber + " spawner";
    }
}
