package net.mcreator.ssc.atmosphere;

import java.util.ArrayList;
import java.util.List;
import net.mcreator.ssc.GasReaction;
import net.mcreator.ssc.GasType;

/** Gameplay reaction definitions, independent of world and transport scheduling. */
public final class AtmosphereReactions {
    private AtmosphereReactions() { }
    public static GasReaction[] create() {
        java.util.function.Function<int[], boolean[]> flags = indices -> {
            boolean[] arr = new boolean[GasType.COUNT];
            int total = indices.length;
            for (int i = 0; i != total; i++) {
                int idx = indices[i];
                if (idx >= 0 && idx < GasType.COUNT) arr[idx] = true;
            }
            return arr;
        };

        java.util.function.Function<float[], float[]> values = v -> {
            float[] arr = new float[GasType.COUNT];
            System.arraycopy(v, 0, arr, 0, Math.min(v.length, GasType.COUNT));
            return arr;
        };

        List<GasReaction> list = new ArrayList<>();
        list.add(GasReaction.create("plasma_burn",
            flags.apply(new int[]{GasType.PLASMA.ordinal(), GasType.OXYGEN.ordinal()}),
            373f, 1643f,
            values.apply(new float[]{0,0,0,0,0.05f,0,0,0,0,0}),
            values.apply(new float[]{0,0,0.04f,0,0,0,0.0075f,0,0,0}),
            5000f, 0.1f));

        list.add(GasReaction.create("tritium_burn",
            flags.apply(new int[]{GasType.TRITIUM.ordinal(), GasType.OXYGEN.ordinal()}),
            373f, 1643f,
            values.apply(new float[]{0,0,0,0,0,0,0.05f,0,0,0}),
            values.apply(new float[]{0,0,0.03f,0,0,0,0,0,0,0.03f}),
            8000f, 0.1f));

        list.add(GasReaction.create("freon_synthesis",
            flags.apply(new int[]{GasType.NITROGEN.ordinal(), GasType.OXYGEN.ordinal(), GasType.TRITIUM.ordinal()}),
            73f, 253f,
            values.apply(new float[]{0,0.01f,0,0,0,0,0.02f,0,0,0}),
            values.apply(new float[]{0,0.01f,0,0,0,0,0,0.015f,0,0}),
            -2000f, 0.05f));

        list.add(GasReaction.create("freon_cool",
            flags.apply(new int[]{GasType.FREON.ordinal(), GasType.NITROGEN.ordinal()}),
            23f, 250f,
            values.apply(new float[]{0,0.02f,0,0,0,0,0,0.01f,0,0}),
            values.apply(new float[]{0,0.02f,0,0.01f,0,0,0,0,0,0}),
            -3000f, 0.05f));

        list.add(GasReaction.create("n2o_decay",
            flags.apply(new int[]{GasType.NITROUS_OXIDE.ordinal()}),
            850f, 5000f,
            values.apply(new float[]{0,0,0,0.05f,0,0,0,0,0,0}),
            values.apply(new float[]{0.025f,0.05f,0,0,0,0,0,0,0,0}),
            1000f, 0.1f));

        list.add(GasReaction.create("ammonia_scrub",
            flags.apply(new int[]{GasType.NITROGEN.ordinal()}),
            323f, 350f,
            values.apply(new float[]{0,0.01f,0,0,0,0.02f,0,0,0,0}),
            values.apply(new float[]{0,0,0,0.01f,0,0,0,0,0,0.01f}),
            -500f, 0.1f));

        return list.toArray(new GasReaction[0]);
    }
}
