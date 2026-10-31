package net.mcreator.ssc;

import java.util.List;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.ssc14.content.item.reagent.ReagentContentsCodec;
import net.ssc14.content.item.reagent.ReagentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModReagentsTest {
    @BeforeAll
    static void bootstrapVanillaItems() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    void canonicalCodecNormalizesContentsAndPreservesUnrelatedRootFields() {
        CompoundTag root = new CompoundTag();
        CompoundTag contents = new CompoundTag();
        contents.putInt("water", 4);
        contents.putInt("empty", 0);
        contents.putString("invalid", "legacy");
        root.put(ReagentContentsCodec.NBT_KEY, contents);
        root.putString("other_mod", "preserved");

        assertEquals(Map.of("water", 4), ReagentContentsCodec.read(root));
        CompoundTag encoded = ReagentContentsCodec.write(Map.of("water", 4));
        assertEquals(4, encoded.getInt("water").orElseThrow());
        assertEquals("preserved", root.getString("other_mod").orElseThrow());
    }

    @Test
    void addingToCorruptNbtUsesNormalizedAmountsAndPreservesUnrelatedData() {
        CompoundTag reagents = new CompoundTag();
        reagents.putInt("water", -10);
        reagents.putInt(" ", 10);
        reagents.putInt("sugar", 2);
        reagents.putString("ethanol", "invalid");
        ItemStack stack = bottle(reagents);

        assertEquals(Map.of("sugar", 2), ReagentService.getReagents(stack));
        assertTrue(ReagentService.addReagent(stack, "water", 1));
        assertEquals(Map.of("water", 1, "sugar", 2), ReagentService.getReagents(stack));
        CompoundTag saved = stack.get(DataComponents.CUSTOM_DATA).copyTag();
        assertEquals("preserved", saved.getString("other_mod").orElseThrow());
        assertEquals(2, saved.getCompound("ssc_14_reagents").orElseThrow().keySet().size());
    }

    @Test
    void invalidRemovalCannotCreateReagentAndEmptyStacksCannotStoreIt() {
        CompoundTag reagents = new CompoundTag();
        reagents.putInt("water", 5);
        ItemStack stack = bottle(reagents);
        assertFalse(ReagentService.removeReagent(stack, "water", -1));
        assertFalse(ReagentService.removeReagent(stack, "water", 0));
        assertFalse(ReagentService.removeReagent(stack, "water", 6));
        assertFalse(ReagentService.addReagent(ItemStack.EMPTY, "water", 1));
        assertEquals(Map.of("water", 5), ReagentService.getReagents(stack));
        assertTrue(ReagentService.removeReagent(stack, "water", 5));
        assertEquals(Map.of(), ReagentService.getReagents(stack));
    }

    @Test
    void overfilledNbtCannotWrapVolumeAndAcceptAdditionalReagents() {
        CompoundTag reagents = new CompoundTag();
        reagents.putInt("water", Integer.MAX_VALUE);
        reagents.putInt("sugar", Integer.MAX_VALUE);
        ItemStack stack = bottle(reagents);
        assertEquals(0, ReagentService.getAvailableSpace(stack));
        assertFalse(ReagentService.addReagent(stack, "ethanol", 1));
        assertEquals(Map.of("water", Integer.MAX_VALUE, "sugar", Integer.MAX_VALUE),
            ReagentService.getReagents(stack));
    }

    @Test
    void reactionPreflightCombinesAllProductsAndLeavesOriginalNbtUnchanged() {
        CompoundTag reagents = new CompoundTag();
        reagents.putInt("water", 1);
        ItemStack stack = bottle(reagents);
        var overflowing = new ReagentService.Reaction(Map.of("water", 1), List.of(
            new ReagentService.ReagentGive("ice", Integer.MAX_VALUE), new ReagentService.ReagentGive("ice", 1)));
        var exact = new ReagentService.Reaction(Map.of("water", 1), List.of(
            new ReagentService.ReagentGive("ice", Integer.MAX_VALUE)));
        assertFalse(ReagentService.canApplyReaction(stack, overflowing));
        assertTrue(ReagentService.canApplyReaction(stack, exact));
        assertEquals(Map.of("water", 1), ReagentService.getReagents(stack));
    }

    private static ItemStack bottle(CompoundTag reagents) {
        // NBT-only tests do not load item components from datapacks or mutate registry holders.
        ItemStack stack = new ItemStack(Holder.direct(Items.GLASS_BOTTLE, DataComponentMap.EMPTY));
        CompoundTag root = new CompoundTag();
        root.put("ssc_14_reagents", reagents);
        root.putString("other_mod", "preserved");
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(root));
        return stack;
    }
}
