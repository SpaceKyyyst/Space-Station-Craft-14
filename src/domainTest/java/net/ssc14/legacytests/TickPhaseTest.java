package net.ssc14.legacytests;

import java.nio.file.Path;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingFile;
import net.ssc14.core.diagnostics.TickPhase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class TickPhaseTest {
    @TempDir Path directory;

    @Test void recordsNamedSubsystemAndWorkCount() throws Exception {
        Path output = directory.resolve("phase.jfr");
        try (var recording = new Recording()) {
            recording.enable(TickPhase.class);
            recording.start();
            try (var phase = TickPhase.start("fixture", 256)) {
                assertEquals(256, phase.workItems);
            }
            recording.stop();
            recording.dump(output);
        }
        var phases = RecordingFile.readAllEvents(output).stream()
                .filter(event -> event.getEventType().getName().equals("ssc14.TickPhase")).toList();
        assertEquals(1, phases.size());
        assertEquals("fixture", phases.getFirst().getString("subsystem"));
        assertEquals(256, phases.getFirst().getInt("workItems"));
        assertFalse(phases.getFirst().getDuration().isNegative());
    }
}
