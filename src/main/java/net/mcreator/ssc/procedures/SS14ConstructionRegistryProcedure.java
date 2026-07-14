
package net.mcreator.ssc.procedures;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.mcreator.ssc.init.*;
import net.mcreator.ssc.block.SheathingBlock;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SS14ConstructionRegistryProcedure {

    public record InteractionKey(Block block, Item tool, Predicate<BlockState> statePredicate) {}

    public record ConstructionStage(
        int[] delays,
        boolean dropBlockAsItem,
        Item dropItem,
        int dropAmount,
        double dropYOffset,
        Block changeToBlock,
        String targetProperty,
        int nextPropertyValue,
        boolean updateNeighbors,
        Predicate<Direction> sideCheck,
        Consumer<RunContext> finalAction
    ) {}

    public record RunContext(ServerLevel world, BlockPos pos, BlockState state, Entity entity) {}

    private static final List<Map.Entry<InteractionKey, ConstructionStage>> REGISTRY = new ArrayList<>();

    static {
        int[] fastDelays = {1, 1, 1, 1, 1, 1};
        int[] normalDelays = {3, 3, 3, 3, 3, 3};
        int[] slowDelays = {10, 10, 10, 10, 10, 10};

        // === 1. ЛОМ (CROWBAR) ===
        Item crowbar = Ssc14ModItems.CROWBAR.get();

        registerTagCheck("ssc14:tiles", crowbar, bs -> true, 
            new ConstructionStage(new int[]{1,1,1,1,1,1}, true, null, 1, 1.0, null, null, -1, false, hit -> hit == Direction.UP, null));

        registerTagCheck("ssc14:tiles_up", crowbar, bs -> true, 
            new ConstructionStage(new int[]{33,33,33,33,33,33}, true, null, 1, 1.0, null, null, -1, false, null, null));

        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), crowbar, bs -> getInt(bs, "blockstate") == 3,
            new ConstructionStage(new int[]{3,3,3,3,3,3}, false, null, 1, 0.0, null, "blockstate", 4, false, null, null));
        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), crowbar, bs -> getInt(bs, "blockstate") == 6,
            new ConstructionStage(new int[]{3,3,3,3,3,3}, false, null, 1, 0.0, null, "blockstate", 7, false, null, null));

        registerBlock(Ssc14ModBlocks.ARMORED_WINDOW.get(), crowbar, bs -> getInt(bs, "blockstate") == 2,
            new ConstructionStage(new int[]{3,3,3,3,3,3}, false, null, 1, 0.0, null, "blockstate", 3, false, null, null));

        // === 2. СВАРКА (ACTIVE WELDER) ===
        Item welder = Ssc14ModItems.ACTIVE_WELDER.get();

        registerBlock(Ssc14ModBlocks.STEEL_WALL.get(), welder, bs -> true,
            new ConstructionStage(new int[]{20,20,20,20,20,20}, false, Ssc14ModItems.STEEL.get(), 1, 0.5, Ssc14ModBlocks.WALL_CARCASE.get(), null, -1, true, null, null));

        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), welder, bs -> getInt(bs, "blockstate") == 2,
            new ConstructionStage(new int[]{13,13,13,13,13,13}, false, null, 1, 0.0, null, "blockstate", 3, true, null, null));
        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), welder, bs -> getInt(bs, "blockstate") == 5,
            new ConstructionStage(new int[]{27,27,27,27,27,27}, false, null, 1, 0.0, null, "blockstate", 6, true, null, null));

        registerBlock(Ssc14ModBlocks.ARMORED_WINDOW.get(), welder, bs -> getInt(bs, "blockstate") == 0,
            new ConstructionStage(new int[]{20,20,20,20,20,20}, false, null, 1, 0.0, null, "blockstate", 1, true, null, null));
        registerBlock(Ssc14ModBlocks.ARMORED_WINDOW.get(), welder, bs -> getInt(bs, "blockstate") == 3,
            new ConstructionStage(new int[]{27,27,27,27,27,27}, false, null, 1, 0.0, null, "blockstate", 4, true, null, null));

        registerTagCheck("ssc14:airlocks", welder, 
            bs -> {
                int arl = getInt(bs, "arl_variat");
                return arl != 11 && !(arl >= 12 && arl <= 15);
            },
            new ConstructionStage(new int[]{25,25,25,25,25,25}, false, null, 1, 0.0, null, "arl_variat", 0, true, null, 
            ctx -> {
                BlockEntity be = ctx.world().getBlockEntity(ctx.pos());
                if (be != null) {
                    CompoundTag nbt = be.getPersistentData();
                    boolean wasWelded = nbt.getBoolean("welded").orElse(false); // ИСПРАВЛЕНО
                    nbt.putBoolean("welded", !wasWelded);
                    be.setChanged();
                }
            })
        );
        // === 3. КУСАЧКИ (NIPPERS) ===
        Item nippers = Ssc14ModItems.NIPPERS.get();

        registerBlock(Ssc14ModBlocks.SHEATHING.get(), nippers, bs -> bs.hasProperty(SheathingBlock.LV) && bs.getValue(SheathingBlock.LV),
            new ConstructionStage(fastDelays, false, Ssc14ModItems.LOW_VOLTAGE_CABLE.get(), 1, 1.0, null, null, -1, false, hit -> hit == Direction.UP, null));
        registerBlock(Ssc14ModBlocks.SHEATHING.get(), nippers, bs -> bs.hasProperty(SheathingBlock.MV) && bs.getValue(SheathingBlock.MV),
            new ConstructionStage(fastDelays, false, Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get(), 1, 1.0, null, null, -1, false, hit -> hit == Direction.UP, null));
        registerBlock(Ssc14ModBlocks.SHEATHING.get(), nippers, bs -> bs.hasProperty(SheathingBlock.HV) && bs.getValue(SheathingBlock.HV),
            new ConstructionStage(fastDelays, false, Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(), 1, 1.0, null, null, -1, false, hit -> hit == Direction.UP, null));

        registerBlock(Ssc14ModBlocks.ROD_FLOOR.get(), nippers, bs -> getInt(bs, "blockstate") == 0,
            new ConstructionStage(fastDelays, false, Ssc14ModItems.ROOD.get(), 1, 1.0, null, null, -1, false, null, null));
        registerBlock(Ssc14ModBlocks.ROD_UP_FLOOR.get(), nippers, bs -> getInt(bs, "blockstate") == 0,
            new ConstructionStage(fastDelays, false, Ssc14ModItems.ROOD.get(), 1, 0.0, null, null, -1, false, null, null));

        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), nippers, bs -> getInt(bs, "blockstate") == 0,
            new ConstructionStage(normalDelays, false, null, 1, 1.0, null, "blockstate", 1, false, null, null));
        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), nippers, bs -> getInt(bs, "blockstate") == 8,
            new ConstructionStage(new int[]{3,1,2,1,2,1}, false, Ssc14ModItems.PLASTEEL.get(), 1, 0.0, Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get(), null, -1, false, null, null));

        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get(), nippers, bs -> true,
            new ConstructionStage(new int[]{3,3,3,1,3,3}, false, Ssc14ModItems.PLASTEEL.get(), 1, 0.0, Ssc14ModBlocks.WALL_CARCASE.get(), null, -1, false, null, null));

        registerBlock(Ssc14ModBlocks.GRILLE.get(), nippers, bs -> true,
            new ConstructionStage(new int[]{1,2,1,2,1,2}, false, Ssc14ModItems.ROOD.get(), 1, 0.1, Blocks.AIR, null, -1, false, null, null));

        registerBlock(Ssc14ModBlocks.BROKEN_GRILLE.get(), nippers, bs -> true,
            new ConstructionStage(fastDelays, false, null, 1, 0.5, Blocks.AIR, null, -1, false, null, null));

        // === 4. ПОЖАРНЫЙ ТОПОР (FIRE AXE) ===
        Item fireAxe = Ssc14ModItems.FIRE_AXE.get();

        registerBlock(Ssc14ModBlocks.SHEATHING.get(), fireAxe, bs -> true,
            new ConstructionStage(slowDelays, false, null, 1, 1.0, Ssc14ModBlocks.ROD_FLOOR.get(), null, -1, false, hit -> hit == Direction.UP, 
            ctx -> dropBlockEntityWithNBT(ctx, Ssc14ModBlocks.TITLE_STEEL.get())));
        
        registerBlock(Ssc14ModBlocks.UPER_SHEATHING.get(), fireAxe, bs -> true,
            new ConstructionStage(slowDelays, false, null, 1, 0.5, Ssc14ModBlocks.ROD_UP_FLOOR.get(), null, -1, false, hit -> hit == Direction.DOWN, 
            ctx -> dropBlockEntityWithNBT(ctx, Ssc14ModBlocks.TITLE_STEEL.get())));

        // === 5. ОТВЕРТКА (SCREWDRIVER) ===
        Item screwdriver = Ssc14ModItems.SCREWDRIVER.get();

        registerBlock(Ssc14ModBlocks.BASE_WINDOW.get(), screwdriver, bs -> getInt(bs, "window_disassembly") == 0,
            new ConstructionStage(normalDelays, false, null, 1, 0.0, null, "window_disassembly", 1, false, null, 
            ctx -> updatePropertyRaw(ctx, "blockstate", 1)));

        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), screwdriver, bs -> getInt(bs, "blockstate") == 1,
            new ConstructionStage(normalDelays, false, null, 1, 0.0, null, "blockstate", 2, false, null, null));
        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), screwdriver, bs -> getInt(bs, "blockstate") == 7,
            new ConstructionStage(new int[]{2,1,2,1,2,1}, false, null, 1, 0.0, null, "blockstate", 8, false, null, null));

        registerBlock(Ssc14ModBlocks.ARMORED_WINDOW.get(), screwdriver, bs -> getInt(bs, "blockstate") == 1,
            new ConstructionStage(new int[]{2,1,2,1,2,1}, false, null, 1, 0.0, null, "blockstate", 2, false, null, null));
        registerBlock(Ssc14ModBlocks.ARMORED_WINDOW.get(), screwdriver, bs -> getInt(bs, "blockstate") == 4,
            new ConstructionStage(normalDelays, false, null, 1, 0.0, null, "blockstate", 5, false, null, null));

        registerTagCheck("ssc14:airlocks", screwdriver, bs -> true,
            new ConstructionStage(normalDelays, false, null, 1, 0.0, null, null, -1, true, null, 
            ctx -> {
                for (Property<?> p : ctx.state().getProperties()) {
                    if (p.getName().equals("panel_open") && p instanceof net.minecraft.world.level.block.state.properties.BooleanProperty bp) {
                        boolean currentOpen = ctx.state().getValue(bp);
                        BlockState newState = ctx.state().setValue(bp, !currentOpen);
                        ctx.world().setBlock(ctx.pos(), newState, 3);
                        if (!currentOpen) {
                            try { BaseAirlockD1PutProcedure.execute(ctx.world(), ctx.pos().getX(), ctx.pos().getY(), ctx.pos().getZ(), newState); } catch(Exception ignored) {}
                        }
                        break;
                    }
                }
            })
        );

        registerBlock(Ssc14ModBlocks.MACHINE_FRAME_2.get(), screwdriver, 
            bs -> {
                for (Property<?> p : bs.getProperties()) {
                    if (p.getName().equals("ready") && p instanceof net.minecraft.world.level.block.state.properties.BooleanProperty bp) return bs.getValue(bp);
                }
                return false;
            },
            new ConstructionStage(normalDelays, false, null, 1, 0.0, null, null, -1, false, null, ctx -> handleMachineFrameFinal(ctx)));

        registerBlock(Ssc14ModBlocks.PODSTATION.get(), screwdriver, bs -> true,
            new ConstructionStage(normalDelays, false, null, 1, 0.0, null, null, -1, false, null, ctx -> handleStationFinal(ctx, Ssc14ModBlocks.PODSTATION.get())));
        registerBlock(Ssc14ModBlocks.SMES.get(), screwdriver, bs -> true,
            new ConstructionStage(normalDelays, false, null, 1, 0.0, null, null, -1, false, null, ctx -> handleStationFinal(ctx, Ssc14ModBlocks.SMES.get())));

        // === 6. ГАЕЧНЫЙ КЛЮЧ (SPANNER) ===
        Item spanner = Ssc14ModItems.SPANNER.get();

        registerBlock(Ssc14ModBlocks.WALL_CARCASE.get(), spanner, bs -> true,
            new ConstructionStage(fastDelays, false, null, 1, 0.0, null, null, -1, false, null, ctx -> spawnEntityAndBreak(ctx, Ssc14ModEntities.WALL_CARCASE_ENTIT.get())));
        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL_CARCASE.get(), spanner, bs -> true,
            new ConstructionStage(fastDelays, false, null, 1, 0.0, null, null, -1, false, null, ctx -> spawnEntityAndBreak(ctx, Ssc14ModEntities.PLASSTEEL_WALL_CARCASE_ENTIT.get())));
        registerBlock(Ssc14ModBlocks.CONSOLE_OF_ID.get(), spanner, bs -> true,
            new ConstructionStage(fastDelays, false, null, 1, 0.0, null, null, -1, false, null, ctx -> spawnEntityAndBreak(ctx, Ssc14ModEntities.ID_CONSOLE_ENTITY.get())));

        registerBlock(Ssc14ModBlocks.BASE_WINDOW.get(), spanner, bs -> getInt(bs, "window_disassembly") == 1,
            new ConstructionStage(slowDelays, false, Ssc14ModItems.GLASS.get(), 2, 0.5, null, null, -1, false, null, ctx -> ctx.world().setBlock(ctx.pos(), Blocks.AIR.defaultBlockState(), 3)));

        registerBlock(Ssc14ModBlocks.ARMORED_WINDOW.get(), spanner, bs -> getInt(bs, "blockstate") == 5,
            new ConstructionStage(slowDelays, false, Ssc14ModItems.ARM_GLASS.get(), 2, 0.5, null, null, -1, false, null, ctx -> ctx.world().setBlock(ctx.pos(), Blocks.AIR.defaultBlockState(), 3)));

        registerBlock(Ssc14ModBlocks.PLASTEEL_WALL.get(), spanner, bs -> getInt(bs, "blockstate") == 4,
            new ConstructionStage(normalDelays, false, null, 1, 0.0, null, "blockstate", 5, false, null, null));
    }
    private static void registerBlock(Block block, Item tool, Predicate<BlockState> pred, ConstructionStage stage) {
        REGISTRY.add(new AbstractMap.SimpleEntry<>(new InteractionKey(block, tool, pred), stage));
    }

    private static void registerTagCheck(String tagNamespace, Item tool, Predicate<BlockState> pred, ConstructionStage stage) {
        Predicate<BlockState> tagAndCustomPred = bs -> bs.is(net.minecraft.tags.BlockTags.create(net.minecraft.resources.ResourceLocation.parse(tagNamespace))) && pred.test(bs);
        REGISTRY.add(new AbstractMap.SimpleEntry<>(new InteractionKey(null, tool, tagAndCustomPred), stage));
    }

    public static ConstructionStage findStage(BlockState state, Item tool) {
        for (var entry : REGISTRY) {
            InteractionKey key = entry.getKey();
            if (key.block() != null && key.block() != state.getBlock()) continue;
            if (key.tool() != tool) continue;
            if (key.statePredicate().test(state)) return entry.getValue();
        }
        return null;
    }

    private static int getInt(BlockState state, String name) {
        for (Property<?> p : state.getProperties()) {
            if (p.getName().equals(name) && p instanceof IntegerProperty ip) return state.getValue(ip);
        }
        return -1;
    }

    private static void updatePropertyRaw(RunContext ctx, String name, int val) {
        BlockState current = ctx.world().getBlockState(ctx.pos());
        for (Property<?> p : current.getProperties()) {
            if (p.getName().equals(name) && p instanceof IntegerProperty ip) {
                ctx.world().setBlock(ctx.pos(), current.setValue(ip, val), 3);
                return;
            }
        }
    }

    private static void spawnEntityAndBreak(RunContext ctx, EntityType<?> type) {
        Entity spawned = type.spawn(ctx.world(), BlockPos.containing(ctx.pos().getX() + 0.5, ctx.pos().getY() + 0.5, ctx.pos().getZ() + 0.5), EntitySpawnReason.MOB_SUMMONED);
        if (spawned != null) spawned.setDeltaMovement(0, 0, 0);
        ctx.world().setBlock(ctx.pos(), Blocks.AIR.defaultBlockState(), 3);
    }

    private static void dropBlockEntityWithNBT(RunContext ctx, Block dropBlock) {
        BlockEntity be = ctx.world().getBlockEntity(ctx.pos());
        CompoundTag nbt = (be != null) ? be.saveWithFullMetadata(ctx.world().registryAccess()) : null;
        ItemEntity entityDrop = new ItemEntity(ctx.world(), ctx.pos().getX() + 0.1, ctx.pos().getY() + 0.5, ctx.pos().getZ() + 0.1, new ItemStack(dropBlock));
        entityDrop.setPickUpDelay(1);
        ctx.world().addFreshEntity(entityDrop);
    }

    private static void handleMachineFrameFinal(RunContext ctx) {
        var handler = ctx.world().getCapability(Capabilities.ItemHandler.BLOCK, ctx.pos(), null);
        if (handler == null) return;
        ItemStack slot0 = handler.getStackInSlot(0);
        if (slot0.isEmpty()) return;
        BlockEntity be = ctx.world().getBlockEntity(ctx.pos());
        if (be == null) return;

        int plugCount = (int) be.getPersistentData().getDoubleOr("plug", 0.0);
        if (handler instanceof IItemHandlerModifiable mod) {
            for (int i = 0; i < 9; i++) { mod.setStackInSlot(i, ItemStack.EMPTY); plugCount++; }
        }
        be.getPersistentData().putDouble("plug", plugCount);
        ctx.world().sendBlockUpdated(ctx.pos(), ctx.state(), ctx.state(), 3);

        Block replace = null;
        if (slot0.getItem() == Ssc14ModItems.SUBSTATION_BOARD.get()) replace = Ssc14ModBlocks.PODSTATION.get();
        else if (slot0.getItem() == Ssc14ModItems.SMES_BOARD.get()) replace = Ssc14ModBlocks.SMES.get();

        if (replace != null) ctx.world().setBlock(ctx.pos(), replace.defaultBlockState(), 3);
    }

    private static void handleStationFinal(RunContext ctx, Block originalBlock) {
        BlockState targetBs = Ssc14ModBlocks.MACHINE_FRAME_2.get().defaultBlockState();
        BlockEntity be = ctx.world().getBlockEntity(ctx.pos());
        CompoundTag nbt = (be != null) ? be.saveWithFullMetadata(ctx.world().registryAccess()) : null;
        
        ctx.world().setBlock(ctx.pos(), targetBs, 3);
        
        var handler = ctx.world().getCapability(Capabilities.ItemHandler.BLOCK, ctx.pos(), null);
        if (handler instanceof IItemHandlerModifiable mod) {
            if (originalBlock == Ssc14ModBlocks.PODSTATION.get()) {
                mod.setStackInSlot(0, new ItemStack(Ssc14ModItems.SUBSTATION_BOARD.get()));
                mod.setStackInSlot(1, new ItemStack(Ssc14ModItems.MEDIUM_VOLTAGE_CABLE.get(), 5));
                mod.setStackInSlot(2, new ItemStack(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(), 5));
                mod.setStackInSlot(3, new ItemStack(Ssc14ModItems.LOW_BATTERIE.get()));
                mod.setStackInSlot(4, new ItemStack(Ssc14ModItems.CAPACITOR.get()));
            } else {
                mod.setStackInSlot(0, new ItemStack(Ssc14ModItems.SMES_BOARD.get()));
                mod.setStackInSlot(1, new ItemStack(Ssc14ModItems.HIGH_VOLTAGE_CABLE.get(), 10));
                mod.setStackInSlot(2, new ItemStack(Ssc14ModItems.CAPACITOR.get()));
                mod.setStackInSlot(3, new ItemStack(Ssc14ModItems.LOW_BATTERIE.get(), 4));
            }
        }
    }
}
