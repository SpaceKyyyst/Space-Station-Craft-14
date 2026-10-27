package net.mcreator.ssc.audit;

import net.ssc14.content.energy.CableType;
import net.ssc14.content.energy.EnergyNetwork;

import net.ssc14.common.atmosphere.model.GasType;
import net.ssc14.common.atmosphere.reaction.GasReaction;
import net.ssc14.content.atmosphere.runtime.AtmosphereManager;
import net.ssc14.content.atmosphere.runtime.AtmosphereSavedData;
import net.ssc14.content.atmosphere.runtime.AtmosRegion;
import net.ssc14.integration.neoforge.atmosphere.AtmosCell;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.mcreator.ssc.*;
import net.mcreator.ssc.block.*;
import net.ssc14.content.block.energy.ApcBlockEntity;
import net.mcreator.ssc.init.Ssc14ModBlocks;
import net.mcreator.ssc.procedures.BaseAirlockOpenCloseProcedure;

@EventBusSubscriber(modid="ssc_14")
public final class AuditTests {
    private static final Map<String, Consumer<GameTestHelper>> TESTS = new LinkedHashMap<>();
    static {
        TESTS.put("airlock_ai_authority_wire_and_interlocks", h -> {
            var level = h.getLevel();
            var pos = h.absolutePos(new BlockPos(3, 3, 3));
            level.setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            var data = level.getBlockEntity(pos).getPersistentData();
            var open = net.mcreator.ssc.access.AirlockAiControl.Action.OPEN;
            net.mcreator.ssc.access.AirlockAiControl.Authority allowed = (world, target) -> target.equals(pos);
            h.assertTrue(!net.mcreator.ssc.access.AirlockAiControl.request(level, pos, allowed, open), "AI operated without power");
            net.mcreator.ssc.access.AirlockPower.supply(level, pos, true);
            h.assertTrue(!net.mcreator.ssc.access.AirlockAiControl.request(level, pos, (world, target) -> false, open), "AI bypassed authority");
            data.putBoolean("ai_access", false);
            h.assertTrue(!net.mcreator.ssc.access.AirlockAiControl.request(level, pos, allowed, open), "AI bypassed disabled wire");
            data.putBoolean("ai_access", true);
            data.putBoolean("bolted", true);
            h.assertTrue(!net.mcreator.ssc.access.AirlockAiControl.request(level, pos, allowed, open), "AI opened bolted door");
            data.putBoolean("_ssc14_bolt_wire_cut", true);
            h.assertTrue(!net.mcreator.ssc.access.AirlockAiControl.request(level, pos, allowed,
                    net.mcreator.ssc.access.AirlockAiControl.Action.BOLTS_UP), "AI raised bolts through cut wire");
            data.putBoolean("_ssc14_bolt_wire_cut", false);
            h.assertTrue(net.mcreator.ssc.access.AirlockAiControl.request(level, pos, allowed,
                    net.mcreator.ssc.access.AirlockAiControl.Action.BOLTS_UP), "AI could not raise intact powered bolts");
            data.putBoolean("welded", true);
            h.assertTrue(!net.mcreator.ssc.access.AirlockAiControl.request(level, pos, allowed, open), "AI opened welded door");
            data.putBoolean("welded", false);
            h.assertTrue(net.mcreator.ssc.access.AirlockAiControl.request(level, pos.above(), allowed, open), "AI could not address upper door part");
            h.runAfterDelay(15, () -> {
                h.assertTrue(level.getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) == 11, "Authorized AI did not open door");
                h.succeed();
            });
        });
        TESTS.put("airlock_unsafe_crush_and_release", h -> {
            var level = h.getLevel();
            var pos = h.absolutePos(new BlockPos(3, 3, 3));
            level.setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            net.mcreator.ssc.access.AirlockPower.supply(level, pos, true);
            var door = level.getBlockEntity(pos);
            door.getPersistentData().putBoolean("safe", false);
            door.getPersistentData().putBoolean(net.mcreator.ssc.access.AirlockSignals.HOLD, true);
            BaseAirlockOpenCloseProcedure.requestSignal(level, pos, true, false);
            h.runAfterDelay(15, () -> {
                var victim = h.spawn(net.minecraft.world.entity.EntityType.COW, new BlockPos(3, 3, 3));
                victim.setNoAi(true);
                victim.setNoGravity(true);
                victim.setPos(pos.getX() + .5, pos.getY(), pos.getZ() + .5);
                float health = victim.getHealth();
                BaseAirlockOpenCloseProcedure.requestSignal(level, pos, false, false);
                h.runAfterDelay(15, () -> {
                    h.assertTrue(victim.getHealth() < health, "Unsafe close caused no crushing damage");
                    h.runAfterDelay(50, () -> {
                        h.assertTrue(level.getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) == 11, "Door did not release crushed entity");
                        h.succeed();
                    });
                });
            });
        });
        TESTS.put("airlock_configurator_inserted_card_only", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            h.getLevel().setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            player.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            var configurator = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.ACCESS_CONFIG.get());
            var card = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.ID_CARD_PASSANGER.get());
            net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA, card,
                    tag -> tag.putBoolean("Technical", true));
            configurator.set(net.minecraft.core.component.DataComponents.CONTAINER,
                    net.minecraft.world.item.component.ItemContainerContents.fromItems(java.util.List.of(card)));
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, configurator);
            player.getInventory().setItem(8, new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.ID_CARD_CAPITAN.get()));
            var buffer = new net.minecraft.network.FriendlyByteBuf(io.netty.buffer.Unpooled.buffer());
            buffer.writeBlockPos(pos);
            var menu = new net.mcreator.ssc.world.inventory.AccessConfigMENUMenu(1, player.getInventory(), buffer);
            buffer.release();
            player.containerMenu = menu;
            menu.getMenuState().put("1:Technical", true);
            menu.getMenuState().put("1:Capitan", true);
            net.mcreator.ssc.access.AirlockConfigurator.synchronize(player, false);
            var data = h.getLevel().getBlockEntity(pos).getPersistentData();
            h.assertTrue(data.getBooleanOr("Technical", false), "Inserted card did not grant edit rights");
            h.assertTrue(!data.getBooleanOr("Capitan", false), "Inventory card granted configurator rights");
            configurator.remove(net.minecraft.core.component.DataComponents.CONTAINER);
            menu.getMenuState().put("1:Technical", false);
            net.mcreator.ssc.access.AirlockConfigurator.synchronize(player, false);
            h.assertTrue(data.getBooleanOr("Technical", false), "Removed card retained edit rights");
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, configurator.copy());
            h.assertTrue(!menu.stillValid(player), "Replacing the bound configurator kept menu valid");
            h.succeed();
        });
        TESTS.put("airlock_logs_bounded_and_erased_by_wire", h -> {
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            h.getLevel().setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            var door = h.getLevel().getBlockEntity(pos);
            for (int i = 0; i < 40; i++) net.mcreator.ssc.access.AirlockEventLog.append(door, "EVENT_" + i);
            var data = door.getPersistentData();
            h.assertTrue(data.getIntOr("_ssc14_log_size", 0) == 16, "Log exceeded capacity");
            h.assertTrue(data.getStringOr("_ssc14_log_15", "").endsWith("EVENT_24"), "Wrong history eviction");
            net.mcreator.ssc.access.AirlockWirePulses.start(door, 5);
            net.mcreator.ssc.access.AirlockEventLog.append(door, "DISABLED");
            h.assertTrue(!data.contains("_ssc14_log_0") && !data.contains("_ssc14_log_size"), "Disabled logging retained history");
            h.succeed();
        });
        TESTS.put("airlock_redstone_open_hold_and_bolts", h -> {
            var level = h.getLevel();
            var pos = h.absolutePos(new BlockPos(3, 3, 3));
            level.setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            net.mcreator.ssc.access.AirlockPower.supply(level, pos, true);
            var facing = level.getBlockState(pos).getValue(net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING);
            level.setBlock(pos.below(), net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
            level.setBlock(pos.relative(facing.getCounterClockWise()), net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
            h.runAfterDelay(130, () -> {
                h.assertTrue(level.getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) == 11, "Hold failed to keep signal-opened door open");
                level.removeBlock(pos.relative(facing.getCounterClockWise()), false);
                level.removeBlock(pos.below(), false);
                h.runAfterDelay(120, () -> {
                    h.assertTrue(level.getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) == 11, "Hold pulse must latch until closure");
                    BaseAirlockOpenCloseProcedure.requestSignal(level, pos, false, false);
                    h.runAfterDelay(15, () -> {
                    h.assertTrue(level.getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) == 0, "Explicit close failed while held open");
                    h.assertTrue(!level.getBlockEntity(pos).getPersistentData().getBooleanOr(net.mcreator.ssc.access.AirlockSignals.HOLD, true), "Closing did not reset hold latch");
                    level.setBlock(pos.relative(facing), net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
                    level.setBlock(pos.relative(facing.getCounterClockWise()), net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK.defaultBlockState(), 3);
                    h.runAfterDelay(15, () -> {
                        h.assertTrue(level.getBlockEntity(pos).getPersistentData().getBooleanOr("bolted", false), "Bolt signal ignored");
                        h.assertTrue(level.getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) != 11, "Open signal bypassed bolts");
                        h.succeed();
                    });
                    });
                });
            });
        });
        TESTS.put("airlock_apc_twenty_watts_and_cable_break", h -> {
            for (int x = 1; x <= 7; x++) h.setBlock(x, 1, 1,
                    Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true));
            h.setBlock(1, 2, 1, Ssc14ModBlocks.APC.get());
            var pos = h.absolutePos(new BlockPos(7, 3, 1));
            h.getLevel().setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            var door = h.getLevel().getBlockEntity(pos);
            h.assertTrue(!door.getPersistentData().getBooleanOr("powered", true), "New isolated door has free power");
            var apc = (ApcBlockEntity) h.getLevel().getBlockEntity(h.absolutePos(new BlockPos(1, 2, 1)));
            apc.setStoredEnergySimulation(100000);
            EnergyNetworkManager.updatePosition(h.getLevel(), h.absolutePos(new BlockPos(3, 1, 1)));
            awaitEnergy(h, () -> {
                h.assertTrue(door.getPersistentData().getBooleanOr("powered", false), "Connected airlock has no power");
                long before = apc.getStoredEnergy();
                h.runAfterDelay(20, () -> {
                    h.assertTrue(before - apc.getStoredEnergy() == 20, "Airlock did not draw exactly 20 J in 20 ticks");
                    h.getLevel().destroyBlock(h.absolutePos(new BlockPos(3, 1, 1)), false);
                    h.runAfterDelay(1, () -> {
                        h.assertTrue(!door.getPersistentData().getBooleanOr("powered", true), "Airlock stayed powered after cable cut");
                        h.succeed();
                    });
                });
            });
        });
        TESTS.put("airlock_crowbar_respects_bolts_and_opens_without_access", h -> {
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            h.getLevel().setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            player.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 1.5);
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,
                    new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.CROWBAR.get()));
            var data = h.getLevel().getBlockEntity(pos).getPersistentData();
            data.putBoolean("powered", false);
            data.putBoolean("Capitan", true);
            data.putBoolean("bolted", true);
            net.mcreator.ssc.access.AirlockManualOpening.interact(player, pos);
            h.runAfterDelay(41, () -> {
                net.mcreator.ssc.access.AirlockManualOpening.advance(player);
                h.assertTrue(h.getLevel().getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) != 11, "Crowbar bypassed bolts");
                data.putBoolean("bolted", false);
                net.mcreator.ssc.access.AirlockManualOpening.interact(player, pos);
                h.runAfterDelay(41, () -> {
                    net.mcreator.ssc.access.AirlockManualOpening.advance(player);
                    h.runAfterDelay(14, () -> {
                        h.assertTrue(h.getLevel().getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) == 11, "Crowbar failed to open unpowered door without ID");
                        h.assertTrue(h.getLevel().getBlockState(pos.above()).is(Ssc14ModBlocks.AIRLOCK_UP_PLUG_OPEN.get()), "Upper collision did not open");
                        net.mcreator.ssc.access.AirlockManualOpening.interact(player, pos);
                        h.runAfterDelay(41, () -> {
                            net.mcreator.ssc.access.AirlockManualOpening.advance(player);
                            h.runAfterDelay(14, () -> {
                                h.assertTrue(h.getLevel().getBlockState(pos).getValue(BaseAirlockD1Block.ARL_VARIAT) == 9, "Crowbar failed to close unpowered door");
                                h.succeed();
                            });
                        });
                    });
                });
            });
        });
        TESTS.put("airlock_multitool_pulse_restores_without_mending", h -> {
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            h.getLevel().setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            var door = h.getLevel().getBlockEntity(pos);
            var data = door.getPersistentData();
            net.mcreator.ssc.access.AirlockPower.supply(h.getLevel(), pos, true);
            net.mcreator.ssc.access.AirlockWirePulses.start(door, 0);
            h.assertTrue(!data.getBooleanOr("powered", true), "Either power wire pulse must disable the door");
            net.mcreator.ssc.access.AirlockWirePulses.start(door, 1);
            h.assertTrue(!data.getBooleanOr("powered", true), "Two power pulses must disable power");
            net.mcreator.ssc.access.AirlockWirePulses.start(door, 2);
            h.assertTrue(!data.getBooleanOr("bolted", false), "Unpowered pulse changed bolts");
            net.mcreator.ssc.access.AirlockWirePulses.start(door, 7);
            h.assertTrue(!data.getBooleanOr("safe", true), "Safety pulse not applied");
            // A physical cut during the pulse must survive expiry.
            for (int i = 1; i <= 8; i++) if (data.getIntOr("log_cab_" + i, -1) == 7) data.putBoolean("cabel_" + i, true);
            h.runAfterDelay(202, () -> {
                h.assertTrue(data.getBooleanOr("powered", false), "Power pulse did not expire");
                h.assertTrue(!data.getBooleanOr("safe", true), "Pulse expiry mended a physical wire");
                net.mcreator.ssc.access.AirlockWirePulses.start(door, 2);
                h.assertTrue(data.getBooleanOr("bolted", false), "Powered pulse did not lower bolts");
                net.mcreator.ssc.access.AirlockWirePulses.start(door, 2);
                h.assertTrue(!data.getBooleanOr("bolted", true), "Second pulse did not raise bolts");
                h.succeed();
            });
        });
        TESTS.put("airlock_welding_completion_and_movement_cancel", h -> {
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            h.getLevel().setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            player.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 1.5);
            var tool = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.ACTIVE_WELDER.get());
            var fuel = new net.minecraft.nbt.CompoundTag();
            fuel.putDouble("fuel", 1000);
            tool.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(fuel));
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, tool);
            net.mcreator.ssc.access.AirlockWelding.interact(player, pos.above());
            var data = h.getLevel().getBlockEntity(pos).getPersistentData();
            h.assertTrue(!data.getBooleanOr("welded", false), "Welding completed immediately");
            h.runAfterDelay(101, () -> {
                net.mcreator.ssc.access.AirlockWelding.advance(player);
                h.assertTrue(data.getBooleanOr("welded", false), "Welding did not complete");
                h.assertTrue(tool.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA).copyTag().getDoubleOr("fuel", 0) == 900, "Incorrect welding fuel charge");
                net.mcreator.ssc.access.AirlockWelding.interact(player, pos);
                player.setPos(player.getX() + 1, player.getY(), player.getZ() - 1);
                net.mcreator.ssc.access.AirlockWelding.advance(player);
                h.runAfterDelay(101, () -> {
                    net.mcreator.ssc.access.AirlockWelding.advance(player);
                    h.assertTrue(data.getBooleanOr("welded", false), "Movement failed to cancel unwelding");
                    h.assertTrue(tool.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA).copyTag().getDoubleOr("fuel", 0) == 900, "Cancelled welding consumed fuel");
                    h.succeed();
                });
            });
        });
        TESTS.put("airlock_floor_preserves_tile_collision", h -> {
            var level = h.getLevel();
            var root = h.absolutePos(new BlockPos(2, 3, 2));
            var floor = root.below();
            level.setBlock(root, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            h.assertTrue(level.getBlockState(floor).is(Ssc14ModBlocks.AIRLOCK_THRESHOLD.get()), "Missing below-floor seal");
            var crawl = Block.box(2, 0, 2, 14, 10, 14);
            h.assertTrue(net.minecraft.world.phys.shapes.Shapes.joinIsNotEmpty(
                    level.getBlockState(floor).getCollisionShape(level, floor), crawl, net.minecraft.world.phys.shapes.BooleanOp.AND),
                    "Closed door permits crawling underneath");
            level.setBlock(floor.below(), Ssc14ModBlocks.SHEATHING.get().defaultBlockState(), 3);
            level.setBlock(floor, Ssc14ModBlocks.TITLE_STEEL.get().defaultBlockState(), 3);
            var closed = level.getBlockState(floor).getCollisionShape(level, floor);
            h.assertTrue(net.minecraft.world.phys.shapes.Shapes.joinIsNotEmpty(closed, crawl, net.minecraft.world.phys.shapes.BooleanOp.AND),
                    "Installing a tile removed the door seal");
            level.setBlock(root, level.getBlockState(root).setValue(net.mcreator.ssc.block.BaseAirlockD1Block.ARL_VARIAT, 11), 3);
            var open = level.getBlockState(floor).getCollisionShape(level, floor);
            h.assertTrue(!net.minecraft.world.phys.shapes.Shapes.joinIsNotEmpty(open, crawl, net.minecraft.world.phys.shapes.BooleanOp.AND),
                    "Open doorway retains closed underfloor collision");
            h.assertTrue(net.minecraft.world.phys.shapes.Shapes.joinIsNotEmpty(open, Block.box(2, 13, 2, 14, 16, 14),
                    net.minecraft.world.phys.shapes.BooleanOp.AND), "Opening door removed tile collision");
            level.setBlock(root, Blocks.AIR.defaultBlockState(), 3);
            h.assertTrue(level.getBlockState(floor).is(Ssc14ModBlocks.TITLE_STEEL.get()), "Removing door destroyed tile");
            h.succeed();
        });
        TESTS.put("airlock_seven_panel_indicators", h -> {
            var data = new net.minecraft.nbt.CompoundTag();
            String[] functions = {"powered", "bolted", "timer", "ai_access", "logs", "diods", "safe"};
            for (String function : functions) data.putBoolean(function, true);
            data.putBoolean("energy_cabel_1", true);
            data.putBoolean("energy_cabel_2", true);
            for (String function : functions) {
                h.assertTrue(net.mcreator.ssc.access.AirlockPanelIndicators.lit(data, function, 0), "Enabled indicator missing: " + function);
                if (!function.equals("powered")) {
                    data.putBoolean(function, false);
                    h.assertTrue(!net.mcreator.ssc.access.AirlockPanelIndicators.lit(data, function, 0), "Disabled indicator still lit: " + function);
                    data.putBoolean(function, true);
                }
            }
            data.putBoolean("energy_cabel_1", false);
            h.assertTrue(net.mcreator.ssc.access.AirlockPanelIndicators.lit(data, "powered", 0)
                    && !net.mcreator.ssc.access.AirlockPanelIndicators.lit(data, "powered", 10), "Single power wire must produce blinking indication");
            data.putBoolean("powered", false);
            for (String function : functions)
                h.assertTrue(!net.mcreator.ssc.access.AirlockPanelIndicators.lit(data, function, 0), "Unpowered indicator still lit: " + function);
            h.succeed();
        });
        TESTS.put("airlock_controller_target_and_cut_bolts", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            var level = h.getLevel();
            level.setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState(), 3);
            player.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            net.mcreator.ssc.access.AirlockPower.supply(level, pos, true);
            var controller = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.AIRLOCK_CONTROLLER.get());
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, controller);
            var data = level.getBlockEntity(pos).getPersistentData();
            net.mcreator.ssc.procedures.AirlockController_DEBUG_prProcedure.execute(level,
                    pos.getX(), pos.getY() + 1, pos.getZ(), level.getBlockState(pos.above()), player, controller);
            h.assertTrue(data.getBooleanOr("bolted", false), "Upper-part click did not lower bolts on the root");
            data.putBoolean("_ssc14_bolt_wire_cut", true);
            net.mcreator.ssc.procedures.AirlockController_DEBUG_prProcedure.execute(level,
                    pos.getX(), pos.getY(), pos.getZ(), level.getBlockState(pos), player, controller);
            h.assertTrue(data.getBooleanOr("bolted", false), "Controller lifted bolts with a cut bolt wire");
            data.putBoolean("_ssc14_bolt_wire_cut", false);
            net.mcreator.ssc.procedures.AirlockController_DEBUG_prProcedure.execute(level,
                    pos.getX(), pos.getY(), pos.getZ(), level.getBlockState(pos), player, controller);
            h.assertTrue(!data.getBooleanOr("bolted", false), "Controller did not lift bolts after wire repair");
            net.mcreator.ssc.procedures.AirlockController_Mode_SwitchingProcedure.execute(controller);
            net.mcreator.ssc.procedures.AirlockController_DEBUG_prProcedure.execute(level,
                    pos.getX(), pos.getY(), pos.getZ(), level.getBlockState(pos), player, controller);
            h.assertTrue(data.getBooleanOr("emergency_acs", false), "Emergency mode was not enabled");
            data.putBoolean("powered", false);
            net.mcreator.ssc.procedures.AirlockController_DEBUG_prProcedure.execute(level,
                    pos.getX(), pos.getY(), pos.getZ(), level.getBlockState(pos), player, controller);
            h.assertTrue(data.getBooleanOr("emergency_acs", false), "Unpowered door accepted mode changes");
            h.succeed();
        });
        TESTS.put("airlock_disassembly_requires_isolation", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            var door = Ssc14ModBlocks.BASE_AIRLOCK_D_1.get();
            h.getLevel().setBlock(pos, door.defaultBlockState()
                    .setValue(net.mcreator.ssc.block.BaseAirlockD1Block.PANEL_OPEN, true), 3);
            player.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            player.setShiftKeyDown(true);
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,
                    new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.SPANNER.get()));
            var data = h.getLevel().getBlockEntity(pos).getPersistentData();
            data.putBoolean("welded", true);
            data.putBoolean("powered", true);
            net.mcreator.ssc.access.AirlockDisassembly.interact(player, pos);
            h.assertTrue(h.getLevel().getBlockState(pos).is(door), "Powered door was dismantled");
            data.putBoolean("powered", false);
            data.putInt(net.mcreator.ssc.access.AirlockMaintenance.ARMOR, 2);
            net.mcreator.ssc.access.AirlockDisassembly.interact(player, pos);
            h.assertTrue(h.getLevel().getBlockState(pos).is(door), "Armored door was dismantled");
            data.putInt(net.mcreator.ssc.access.AirlockMaintenance.ARMOR, 0);
            data.putBoolean("welded", false);
            net.mcreator.ssc.access.AirlockDisassembly.interact(player, pos);
            h.assertTrue(h.getLevel().getBlockState(pos).is(door), "Unwelded door was dismantled");
            data.putBoolean("welded", true);
            net.mcreator.ssc.access.AirlockDisassembly.interact(player, pos);
            h.assertTrue(h.getLevel().getBlockState(pos).isAir() && h.getLevel().getBlockState(pos.above()).isAir(),
                    "Disassembly left part of the door behind");
            net.mcreator.ssc.access.AirlockDisassembly.interact(player, pos);
            int count = h.getLevel().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class,
                    new net.minecraft.world.phys.AABB(pos).inflate(1)).stream()
                    .filter(e -> e.getItem().is(door.asItem())).mapToInt(e -> e.getItem().getCount()).sum();
            h.assertTrue(count == 1, "Disassembly must drop exactly one door");
            h.succeed();
        });
        TESTS.put("airlock_reinforcement_materials_and_fuel", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            h.getLevel().setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState()
                    .setValue(BaseAirlockD1Block.PANEL_OPEN, true), 3);
            player.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            protectionSteps(h, player, pos, new int[][]{{0,1,40},{1,2,60},{1,1,200},{2,0,80}}, 0);
        });
        TESTS.put("airlock_plasteel_grille_outer_layer", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            h.getLevel().setBlock(pos, Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState()
                    .setValue(BaseAirlockD1Block.PANEL_OPEN, true), 3);
            h.getLevel().getBlockEntity(pos).getPersistentData().putBoolean("powered", false);
            player.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            protectionSteps(h, player, pos, new int[][]{
                    {3,3,40},{1,4,100},{4,5,20},{3,6,40},{1,7,100},
                    {1,6,300},{2,5,80},{5,4,10},{1,3,300},{2,0,80}}, 0);
        });
        TESTS.put("airlock_protection_cancels_when_panel_closes", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            var open = Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState().setValue(BaseAirlockD1Block.PANEL_OPEN, true);
            h.getLevel().setBlock(pos, open, 3);
            player.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
            var stack = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.STEEL.get(), 2);
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, stack);
            net.mcreator.ssc.access.AirlockMaintenance.interact(player, pos);
            h.getLevel().setBlock(pos, open.setValue(BaseAirlockD1Block.PANEL_OPEN, false), 3);
            h.runAfterDelay(41, () -> {
                net.mcreator.ssc.access.AirlockMaintenance.advance(player);
                h.assertTrue(stack.getCount() == 2, "Canceled job consumed plates");
                h.assertTrue(!net.mcreator.ssc.access.AirlockMaintenance.isArmored(h.getLevel(), pos), "Closed panel accepted armor");
                h.succeed();
            });
        });
        TESTS.put("airlock_wires_bound_to_panel", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var pos = h.absolutePos(new BlockPos(2, 2, 2));
            var other = pos.offset(2, 0, 0);
            var door = Ssc14ModBlocks.BASE_AIRLOCK_D_1.get().defaultBlockState()
                    .setValue(net.mcreator.ssc.block.BaseAirlockD1Block.PANEL_OPEN, true);
            h.getLevel().setBlock(pos, door, 3);
            h.getLevel().setBlock(other, door, 3);
            player.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 1.5);
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,
                    new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.NIPPERS.get()));
            var buffer = new net.minecraft.network.FriendlyByteBuf(io.netty.buffer.Unpooled.buffer());
            try {
                buffer.writeBlockPos(pos);
                player.containerMenu = new net.mcreator.ssc.world.inventory.CabelPannelAirlockMenu(1, player.getInventory(), buffer);
            } finally { buffer.release(); }
            var data = h.getLevel().getBlockEntity(pos).getPersistentData();
            for (int wire = 1; wire <= 8; wire++) {
                net.mcreator.ssc.access.AirlockWireService.toggle(player, pos, wire);
                h.assertTrue(data.getBooleanOr("cabel_" + wire, false), "Wire was not cut on the bound door");
                net.mcreator.ssc.access.AirlockWireService.toggle(player, pos, wire);
                h.assertTrue(!data.getBooleanOr("cabel_" + wire, false), "Wire was not mended");
            }
            net.mcreator.ssc.access.AirlockWireService.toggle(player, other, 1);
            h.assertTrue(!h.getLevel().getBlockEntity(other).getPersistentData().getBooleanOr("cabel_1", false),
                    "Panel edited another door");
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, net.minecraft.world.item.ItemStack.EMPTY);
            net.mcreator.ssc.access.AirlockWireService.toggle(player, pos, 1);
            h.assertTrue(!data.getBooleanOr("cabel_1", false), "Wire cut without nippers");
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,
                    new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.MULTITUL.get()));
            data.putInt("log_cab_1", 7);
            data.putBoolean("cabel_1", true);
            net.mcreator.ssc.access.AirlockWireService.toggle(player, pos, 1);
            h.assertTrue(!data.contains("_ssc14_wire_pulse_7"), "Multitool pulsed a severed wire");
            data.putBoolean("cabel_1", false);
            net.mcreator.ssc.access.AirlockWireService.toggle(player, pos, 1);
            h.assertTrue(data.contains("_ssc14_wire_pulse_7") && !data.getBooleanOr("cabel_1", true),
                    "Multitool must pulse without cutting the wire");
            h.getLevel().setBlock(pos, h.getLevel().getBlockState(pos)
                    .setValue(net.mcreator.ssc.block.BaseAirlockD1Block.PANEL_OPEN, false), 3);
            h.assertTrue(!player.containerMenu.stillValid(player), "Closed panel remained usable");
            h.succeed();
        });
        TESTS.put("airlock_current_inventory_access", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var requirement = new net.minecraft.nbt.CompoundTag();
            requirement.putBoolean("Medical", true);
            player.getPersistentData().putBoolean("Medical", true);
            h.assertTrue(!net.mcreator.ssc.access.AirlockAccessPolicy.permits(requirement, player, false),
                    "Stale player permissions granted entry without an ID card");
            var card = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.ID_CARD_MEDIC.get());
            player.getInventory().setItem(9, card);
            h.assertTrue(net.mcreator.ssc.access.AirlockAccessPolicy.permits(requirement, player, false),
                    "Card in inventory must grant access immediately without a player tick");
            net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA,
                    card, tag -> tag.putBoolean("Medical", false));
            h.assertTrue(!net.mcreator.ssc.access.AirlockAccessPolicy.permits(requirement, player, false),
                    "Edited card permissions must take effect immediately");
            player.getInventory().setItem(9, new net.minecraft.world.item.ItemStack(
                    net.mcreator.ssc.init.Ssc14ModItems.ID_CARD_MEDIC.get()));
            net.mcreator.ssc.access.PlayerAccessService.tick(player);
            player.getInventory().setItem(9, net.minecraft.world.item.ItemStack.EMPTY);
            h.assertTrue(!net.mcreator.ssc.access.AirlockAccessPolicy.permits(requirement, player, false),
                    "Removing the card must revoke access before the next player tick");
            h.assertTrue(net.mcreator.ssc.access.AirlockAccessPolicy.permits(requirement, player, true),
                    "Emergency access must remain available");
            h.assertTrue(net.mcreator.ssc.access.AirlockAccessPolicy.permits(new net.minecraft.nbt.CompoundTag(), player, false),
                    "Unrestricted doors must remain accessible");
            h.succeed();
        });
        TESTS.put("energy_many_lamps_exact_demand", h -> {
            var world = h.getLevel();
            var origin = h.absolutePos(new BlockPos(0, 35, 0));
            for (int x = 0; x < 8; x++) for (int z = 0; z < 8; z++) {
                world.setBlockAndUpdate(origin.offset(x, 0, z), Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true));
                world.setBlockAndUpdate(origin.offset(x, 3, z), Ssc14ModBlocks.LAMP.get().defaultBlockState());
            }
            world.setBlockAndUpdate(origin.above(), Ssc14ModBlocks.APC.get().defaultBlockState());
            var apc = (ApcBlockEntity) world.getBlockEntity(origin.above());
            apc.setStoredEnergySimulation(100000);
            EnergyNetworkManager.updatePosition(world, origin);
            awaitEnergy(h, () -> {
                long before = apc.getStoredEnergy();
                h.runAfterDelay(20, () -> {
                    h.assertTrue(before - apc.getStoredEnergy() == 64 * 20, "Incorrect 64-lamp demand");
                    for (int x = 0; x < 8; x++) for (int z = 0; z < 8; z++) {
                        h.assertTrue(world.getBlockState(origin.offset(x, 3, z)).getValue(LampBlock.ACTIVE), "Lamp missing from batch");
                    }
                    world.destroyBlock(origin.above(), false);
                    EnergyNetworkManager.invalidate(world, origin.above());
                    h.runAfterDelay(1, () -> {
                        for (int x = 0; x < 8; x++) for (int z = 0; z < 8; z++) {
                            var lamp = origin.offset(x, 3, z);
                            h.assertTrue(!world.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Lamp powered without source");
                            world.setBlockAndUpdate(lamp, Blocks.AIR.defaultBlockState());
                            world.setBlockAndUpdate(origin.offset(x, 0, z), Blocks.AIR.defaultBlockState());
                        }
                        h.succeed();
                    });
                });
            });
        });
        TESTS.put("energy_cable_proximity_index", h -> {
            var network = new EnergyNetwork(CableType.LV);
            for (int x = -256; x < 256; x++) network.addCable(new BlockPos(x, 20, -17));
            for (int x : new int[]{-257, -16, 0, 255, 272}) for (int y : new int[]{4, 5, 20, 35, 36}) {
                var position = new BlockPos(x, y, -16);
                boolean expected = network.getCables().stream().anyMatch(cable -> position.closerThan(cable, 16));
                h.assertTrue(network.hasCableWithin(position, 16) == expected, "Proximity index differs at " + position);
            }
            h.succeed();
        });
        TESTS.put("energy_shared_lamp_single_debit", h -> {
            var world = h.getLevel();
            for (int z : new int[]{1, 5}) {
                h.setBlock(1, 1, z, Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true));
                h.setBlock(1, 2, z, Ssc14ModBlocks.APC.get());
                ((ApcBlockEntity) world.getBlockEntity(h.absolutePos(new BlockPos(1, 2, z)))).setStoredEnergySimulation(1000);
                EnergyNetworkManager.updatePosition(world, h.absolutePos(new BlockPos(1, 1, z)));
            }
            var lamp = new BlockPos(1, 3, 3);
            h.setBlock(lamp, Ssc14ModBlocks.LAMP.get());
            EnergyNetworkManager.invalidate(world, h.absolutePos(lamp));
            awaitEnergy(h, () -> {
                var first = (ApcBlockEntity) world.getBlockEntity(h.absolutePos(new BlockPos(1, 2, 1)));
                var second = (ApcBlockEntity) world.getBlockEntity(h.absolutePos(new BlockPos(1, 2, 5)));
                long before = first.getStoredEnergy() + second.getStoredEnergy();
                h.runAfterDelay(20, () -> {
                    h.assertTrue(h.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Shared lamp is off");
                    h.assertTrue(before - first.getStoredEnergy() - second.getStoredEnergy() == 20, "Shared lamp charged twice");
                    h.succeed();
                });
            });
        });
        TESTS.put("energy_lamp_changes_and_depletion", h -> {
            var world = h.getLevel();
            h.setBlock(1, 1, 1, Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true));
            h.setBlock(1, 2, 1, Ssc14ModBlocks.APC.get());
            var lamp = new BlockPos(1, 3, 1);
            h.setBlock(lamp, Ssc14ModBlocks.LAMP.get());
            var apc = (ApcBlockEntity) world.getBlockEntity(h.absolutePos(new BlockPos(1, 2, 1)));
            apc.setStoredEnergySimulation(1000);
            EnergyNetworkManager.updatePosition(world, h.absolutePos(new BlockPos(1, 1, 1)));
            awaitEnergy(h, () -> {
                h.assertTrue(h.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Connected lamp is off");
                h.setBlock(lamp, h.getBlockState(lamp).setValue(LampBlock.BROKEN, true));
                long before = apc.getStoredEnergy();
                h.runAfterDelay(1, () -> {
                    h.assertTrue(!h.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Broken lamp remains active");
                    h.assertTrue(apc.getStoredEnergy() == before, "Broken lamp consumes energy");
                    h.setBlock(lamp, h.getBlockState(lamp).setValue(LampBlock.BROKEN, false).setValue(LampBlock.HAVE_LAMP, false));
                    h.runAfterDelay(1, () -> {
                        h.assertTrue(apc.getStoredEnergy() == before, "Empty fixture consumes energy");
                        h.setBlock(lamp, h.getBlockState(lamp).setValue(LampBlock.HAVE_LAMP, true));
                        apc.setStoredEnergySimulation(1);
                        h.runAfterDelay(1, () -> {
                            h.assertTrue(h.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Last joule did not power lamp");
                            h.assertTrue(apc.getStoredEnergy() == 0, "Incorrect last-joule debit");
                            h.runAfterDelay(1, () -> {
                                h.assertTrue(!h.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Depleted battery powers lamp");
                                h.succeed();
                            });
                        });
                    });
                });
            });
        });
        TESTS.put("energy_large_rebuild_budget_and_restart", h -> {
            var world = h.getLevel();
            var origin = h.absolutePos(new BlockPos(0, 50, 0));
            var cableState = Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true);
            for (int x = 0; x < 33; x++) for (int z = 0; z < 16; z++) {
                if (x != 16 || z == 0) world.setBlockAndUpdate(origin.offset(x, 0, z), cableState);
            }
            var bridge = origin.offset(16, 0, 0);
            var lamp = origin.offset(32, 2, 15);
            world.setBlockAndUpdate(origin.above(), Ssc14ModBlocks.APC.get().defaultBlockState());
            world.setBlockAndUpdate(lamp, Ssc14ModBlocks.LAMP.get().defaultBlockState());
            var apc = (ApcBlockEntity) world.getBlockEntity(origin.above());
            apc.setStoredEnergySimulation(1000000);
            EnergyNetworkManager.updatePosition(world, bridge);
            for (int n = 0; n < 10; n++) EnergyNetworkManager.refreshPending(world);
            h.assertTrue(net.mcreator.ssc.energy.EnergyTopologyService.rebuilding(world), "Large graph rebuilt synchronously");
            h.assertTrue(net.mcreator.ssc.energy.EnergyTopologyService.workUnitsThisTick(world) <= 128, "Repeated calls exceeded shared tick budget");
            awaitEnergy(h, () -> {
                h.assertTrue(world.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Large connected circuit did not power lamp");
                world.destroyBlock(bridge, false);
                long charge = apc.getStoredEnergy();
                h.runAfterDelay(1, () -> {
                    h.assertTrue(!world.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Cable cut did not switch lamp off on next tick");
                    h.assertTrue(apc.getStoredEnergy() == charge, "Invalid circuit debited battery");
                    world.setBlockAndUpdate(bridge, cableState);
                    EnergyNetworkManager.updatePosition(world, bridge);
                    world.destroyBlock(bridge, false);
                    awaitEnergy(h, () -> {
                        h.assertTrue(!world.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Restart committed stale connected graph");
                        world.setBlockAndUpdate(bridge, cableState);
                        EnergyNetworkManager.updatePosition(world, bridge);
                        awaitEnergy(h, () -> {
                            h.assertTrue(world.getBlockState(lamp).getValue(LampBlock.ACTIVE), "Reconnection failed");
                            for (int x = 0; x < 33; x++) for (int z = 0; z < 16; z++) {
                                if (x != 16 || z == 0) world.setBlockAndUpdate(origin.offset(x, 0, z), Blocks.AIR.defaultBlockState());
                            }
                            world.setBlockAndUpdate(origin.above(), Blocks.AIR.defaultBlockState());
                            world.setBlockAndUpdate(lamp, Blocks.AIR.defaultBlockState());
                            h.succeed();
                        });
                    });
                });
            });
        });
        TESTS.put("energy_neighbor_chunk_refreshes_devices", h -> {
            var world = h.getLevel();
            var base = h.absolutePos(new BlockPos(3, 3, 3));
            var cable = new BlockPos((base.getX() & ~15) + 15, base.getY() + 20, base.getZ());
            var lamp = cable.offset(1, 2, 0);
            h.assertTrue(world.hasChunkAt(lamp), "Fixture requires neighboring loaded chunk");
            world.setBlockAndUpdate(lamp, Blocks.AIR.defaultBlockState());
            world.setBlockAndUpdate(cable, Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true));
            EnergyNetworkManager.updatePosition(world, cable);
            world.setBlockAndUpdate(lamp, Ssc14ModBlocks.LAMP.get().defaultBlockState());
            net.mcreator.ssc.energy.EnergyTopologyService.chunkAvailabilityChanged(world,
                    new net.minecraft.world.level.ChunkPos(lamp.getX() >> 4, lamp.getZ() >> 4));
            EnergyNetworkManager.refreshPending(world);
            awaitEnergy(h, () -> {
            h.assertTrue(EnergyNetworkManager.getNetworks(world, CableType.LV).stream()
                    .anyMatch(n -> n.getCables().contains(cable) && n.getConsumers().contains(lamp)),
                    "Neighbor chunk availability did not refresh device attachments");
            world.setBlockAndUpdate(cable, Blocks.AIR.defaultBlockState());
            world.setBlockAndUpdate(lamp, Blocks.AIR.defaultBlockState());
            EnergyNetworkManager.updatePosition(world, cable);
            h.succeed();
            });
        });
        TESTS.put("id_cache_refreshes_nested_card", h -> {
            var card = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.ID_CARD_PASSANGER.get());
            var container = new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.CHEST);
            container.set(net.minecraft.core.component.DataComponents.CONTAINER,
                    net.minecraft.world.item.component.ItemContainerContents.fromItems(java.util.List.of(card)));
            long before = net.mcreator.ssc.access.PlayerAccessService.permissions(container);
            h.assertTrue(before != 0, "Nested ID card was ignored");
            var denied = new net.minecraft.nbt.CompoundTag(); denied.putBoolean("spawned", true);
            card.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(denied));
            container.set(net.minecraft.core.component.DataComponents.CONTAINER,
                    net.minecraft.world.item.component.ItemContainerContents.fromItems(java.util.List.of(card)));
            h.assertTrue(net.mcreator.ssc.access.PlayerAccessService.permissions(container) == 0,
                    "Cached nested permissions survived card reprogramming");
            h.succeed();
        });
        TESTS.put("energy_rebuild_preserves_unrelated_network", h -> {
            var world = h.getLevel();
            for (int x = 1; x <= 5; x++) for (int z : new int[]{1, 6}) {
                h.setBlock(x, 1, z, Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true));
            }
            var first = h.absolutePos(new BlockPos(1, 1, 1));
            var unrelated = h.absolutePos(new BlockPos(1, 1, 6));
            EnergyNetworkManager.updatePosition(world, first);
            EnergyNetworkManager.updatePosition(world, unrelated);
            awaitEnergy(h, () -> {
            var kept = EnergyNetworkManager.getNetworks(world, CableType.LV).stream()
                    .filter(n -> n.getCables().contains(unrelated)).findFirst().orElseThrow();
            h.setBlock(3, 1, 1, Blocks.AIR);
            EnergyNetworkManager.updatePosition(world, h.absolutePos(new BlockPos(3, 1, 1)));
            h.assertTrue(EnergyNetworkManager.getNetworks(world, CableType.LV).contains(kept), "Unrelated network was rebuilt");
            h.assertTrue(kept.isTopologyReady(), "Unrelated circuit lost power");
            awaitEnergy(h, () -> {
            var left = EnergyNetworkManager.getNetworks(world, CableType.LV).stream()
                    .filter(n -> n.getCables().contains(first)).findFirst().orElseThrow();
            var right = h.absolutePos(new BlockPos(5, 1, 1));
            h.assertTrue(!left.getCables().contains(right), "Broken cable did not split component");
            h.setBlock(3, 1, 1, Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true));
            EnergyNetworkManager.updatePosition(world, h.absolutePos(new BlockPos(3, 1, 1)));
            awaitEnergy(h, () -> {
            h.assertTrue(EnergyNetworkManager.getNetworks(world, CableType.LV).stream()
                    .anyMatch(n -> n.getCables().contains(first) && n.getCables().contains(right)), "Reconnected cable did not merge components");
            h.succeed();
            });
            });
            });
        });
        TESTS.put("id_card_grants_and_revokes_access", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND,
                    new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.ID_CARD_PASSANGER.get()));
            var event = new net.neoforged.neoforge.event.tick.PlayerTickEvent.Post(player);
            net.mcreator.ssc.procedures.IDCardAccessProcedure.onPlayerTick(event);
            h.assertTrue(player.getPersistentData().getBooleanOr("Technical", false), "ID card did not grant access");
            player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, net.minecraft.world.item.ItemStack.EMPTY);
            net.mcreator.ssc.procedures.IDCardAccessProcedure.onPlayerTick(event);
            h.assertTrue(!player.getPersistentData().getBooleanOr("Technical", false), "Removed card retained access");
            h.succeed();
        });
        TESTS.put("region_work_budget_and_fairness", h -> {
            var region = new AtmosRegion(0, 0, 0);
            boolean[] required = new boolean[GasType.COUNT]; required[0] = true;
            float[] consumption = new float[GasType.COUNT]; consumption[0] = 0.1f;
            var reaction = GasReaction.create("budget", required, 1, 5000, consumption,
                    new float[GasType.COUNT], 0, 0);
            for (int i = 0; i < 4096; i++) {
                var pos = region.unpackCellIndex(i, BlockPos.ZERO);
                region.getCellAt(pos).setMoles(0, 1);
                region.markCellDirty(pos);
            }
            region.tick(0.2f, new GasReaction[]{reaction});
            int processed = 0;
            for (int i = 0; i < 4096; i++) {
                if (region.getCellAt(region.unpackCellIndex(i, BlockPos.ZERO)).getMoles(0) < 1) processed++;
            }
            h.assertTrue(processed == 256, "Region exceeded its 256-cell work budget: " + processed);
            for (int i = 1; i < 16; i++) region.tick(0.2f, new GasReaction[]{reaction});
            for (int i = 0; i < 4096; i++) {
                h.assertTrue(Math.abs(region.getCellAt(region.unpackCellIndex(i, BlockPos.ZERO)).getMoles(0) - 0.9f) < 1e-6,
                        "Work queue starved or repeated cell " + i);
            }
            h.succeed();
        });
        TESTS.put("empty_region_allocates_cells_lazily", h -> {
            var region = new AtmosRegion(0, 0, 0);
            for (int i = 0; i < 4096; i++) {
                h.assertTrue(region.peekCellAt(region.unpackCellIndex(i, BlockPos.ZERO)) == null,
                        "Empty region eagerly allocated a gas mixture");
            }
            region.getCellAt(BlockPos.ZERO).setMoles(0, 1);
            h.assertTrue(region.peekCellAt(BlockPos.ZERO).getMoles(0) == 1, "Lazy cell lost its state");
            h.succeed();
        });
        TESTS.put("region_reaction_continues", h -> {
            var region = new AtmosRegion(0, 0, 0);
            var pos = new BlockPos(1, 1, 1);
            var cell = region.getCellAt(pos);
            cell.setMoles(0, 1);
            boolean[] required = new boolean[GasType.COUNT]; required[0] = true;
            float[] consumption = new float[GasType.COUNT]; consumption[0] = 0.1f;
            var reaction = GasReaction.create("test", required, 1, 5000, consumption,
                    new float[GasType.COUNT], 0, 0);
            region.markCellDirty(pos);
            for (int i = 0; i < 3; i++) region.tick(0.2f, new GasReaction[]{reaction});
            h.assertTrue(Math.abs(cell.getMoles(0) - 0.7f) < 0.0001f, "Reaction stopped after its first step");
            h.succeed();
        });
        TESTS.put("trace_gas_crosses_region_boundary", h -> {
            var level = h.getLevel();
            var base = h.absolutePos(new BlockPos(3, 3, 3));
            var start = new BlockPos(base.getX(), ((base.getY() >> 4) + 1) * 16 - 1, base.getZ());
            for (int y = -1; y <= 3; y++) for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++) {
                level.setBlockAndUpdate(start.offset(x, y, z),
                        (x == 0 && z == 0 && y >= 0 && y <= 2 ? Blocks.AIR : Blocks.GLASS).defaultBlockState());
            }
            var manager = AtmosphereManager.get(level);
            var first = manager.getCellAt(start);
            first.setMoles(0, 0.0005f);
            manager.onBlockChanged(start);
            for (int i = 0; i < 80; i++) manager.tick();
            var second = manager.getCellAt(start.above());
            var third = manager.getCellAt(start.above(2));
            h.assertTrue(third.getMoles(0) > 0, "Trace gas stopped at the region boundary");
            h.assertTrue(Math.abs(first.getMoles(0) + second.getMoles(0) + third.getMoles(0) - 0.0005f) < 1e-8,
                    "Closed chamber lost trace gas");
            h.succeed();
        });
        TESTS.put("pure_nitrous_oxide_decays", h -> {
            try {
                AtmosphereManager.get(h.getLevel());
                var field = AtmosphereManager.class.getDeclaredField("reactions"); field.setAccessible(true);
                var recipe = java.util.Arrays.stream((GasReaction[]) field.get(null))
                        .filter(r -> r.id().equals("n2o_decay")).findFirst().orElseThrow();
                var gas = new float[GasType.COUNT]; gas[GasType.NITROUS_OXIDE.ordinal()] = 1;
                h.assertTrue(recipe.apply(gas, new float[]{1000}), "Pure N2O incorrectly requires nitrogen to decay");
                h.assertTrue(Math.abs(gas[1] - 0.05f) < 1e-6 && Math.abs(gas[0] - 0.025f) < 1e-6,
                        "N2O decay does not preserve nitrogen and oxygen atoms");
                h.succeed();
            } catch (ReflectiveOperationException e) { throw new RuntimeException(e); }
        });
        for (String name : new String[]{"plasma_burn","tritium_burn","freon_synthesis","freon_cool","n2o_decay","ammonia_scrub"}) {
            TESTS.put("reaction_"+name, h -> reaction(h,name));
        }
        TESTS.put("gas_conservation", h -> {
            AtmosCell a=new AtmosCell(), b=new AtmosCell(); a.setMoles(0,10); a.setMoles(1,30);
            for(int i=0;i<100;i++) a.diffuseTo(b,1,0.05f);
            h.assertTrue(Math.abs(a.getTotalMoles()+b.getTotalMoles()-40)<0.001,"Diffusion must conserve moles");h.succeed();
        });
        TESTS.put("cell_rejects_nan", h -> {
            AtmosCell a=new AtmosCell(); a.setMoles(0,Float.NaN);
            h.assertTrue(Float.isFinite(a.getPressure()),"NaN gas input poisoned pressure: "+a.getPressure());h.succeed();
        });
        TESTS.put("analyser_packet_roundtrip", h -> {
            AtmosCell cell = new AtmosCell(); cell.setMoles(0,10); cell.setMoles(1,30);
            var expected = net.mcreator.ssc.network.GasAnalyserData.from("minecraft:overworld", BlockPos.ZERO, cell);
            var buffer = new net.minecraft.network.FriendlyByteBuf(io.netty.buffer.Unpooled.buffer());
            try {
                net.mcreator.ssc.network.GasAnalyserData.CODEC.encode(buffer, expected);
                var actual = net.mcreator.ssc.network.GasAnalyserData.CODEC.decode(buffer);
                h.assertTrue(actual.dimension().equals(expected.dimension()) && actual.position().equals(expected.position()), "Snapshot location changed");
                h.assertTrue(actual.pressure()==cell.getPressure() && actual.temperature()==cell.getTemperature(), "Snapshot measurements changed");
                h.assertTrue(java.util.Arrays.equals(actual.moles(),expected.moles()), "Gas amounts changed");
                actual.moles()[0] = 0;
                h.assertTrue(actual.moles()[0]==10, "Snapshot array is mutable");
                h.succeed();
            } finally { buffer.release(); }
        });
        TESTS.put("energy_demand", h -> energy(h,false));
        TESTS.put("energy_break", h -> energy(h,true));
        TESTS.put("energy_idle", h -> energyBudget(h, false));
        TESTS.put("energy_multiple_sources", h -> energyBudget(h, true));
        TESTS.put("airlock_open_close", h -> door(h,false));
        TESTS.put("airlock_denies_access", h -> door(h,true));
    }
    static {
        ReagentContainerConfig.CAPACITIES.forEach((itemId,capacity)->TESTS.put("container_"+itemId.getPath(),h->{
            var item=net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(itemId);
            h.assertTrue(item instanceof net.ssc14.content.item.reagent.ReagentContainerItem,"Configured container is not registered as ReagentContainerItem: "+itemId);
            var stack=new net.minecraft.world.item.ItemStack(item);
            h.assertTrue(ModReagents.getAvailableSpace(stack)==capacity,"Capacity mismatch for "+itemId);
            h.assertTrue(ModReagents.addReagent(stack,"water",capacity),"Cannot fill empty container");
            h.assertTrue(!ModReagents.addReagent(stack,"water",1),"Overfilled container");
            h.assertTrue(ModReagents.removeReagent(stack,"water",capacity),"Cannot drain full container");
            h.assertTrue(ModReagents.getAvailableSpace(stack)==capacity,"Drain lost capacity");h.succeed();
        }));
        TESTS.put("chem_negative_remove",h->{
            var s=glass();ModReagents.addReagent(s,"water",10);
            boolean accepted=ModReagents.removeReagent(s,"water",-5);
            h.assertTrue(!accepted && ModReagents.getReagents(s).get("water")==10,"Removing -5 creates reagent: "+ModReagents.getReagents(s));h.succeed();
        });
        TESTS.put("drink_mixture_conserves_volume",h->{
            var s=glass();ModReagents.addReagent(s,"water",10);ModReagents.addReagent(s,"sugar",11);
            int reported=ModReagents.drinkReagents(s,10,null);
            int remaining=ModReagents.getReagents(s).values().stream().mapToInt(Integer::intValue).sum();
            h.assertTrue(21-remaining==reported,"Drink reports "+reported+" but removed "+(21-remaining)+" units");h.succeed();
        });
        TESTS.put("network_saved_data_roundtrip",h->{
            var d=new NetworkSavedData();var a=new BlockPos(1,2,3);var b=new BlockPos(4,5,6);
            d.addConnection(a,"press",b,"open");d.addConnection(a,"press",b,"open");
            h.assertTrue(d.connections.size()==1,"Duplicate signal connection");
            var encoded=NetworkSavedData.CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE,d).getOrThrow();
            var restored=NetworkSavedData.CODEC.parse(net.minecraft.nbt.NbtOps.INSTANCE,encoded).getOrThrow();
            h.assertTrue(restored.connections.size()==1 && restored.connections.getFirst().targetPos.equals(b),"Signal connection serialization failed");
            restored.removeConnection(a,"press",b,"open");h.assertTrue(restored.connections.isEmpty(),"Remove connection failed");h.succeed();
        });
        TESTS.put("crawl_toggle",h->{
            var p=h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);var pos=h.absolutePos(new BlockPos(3,3,3));p.setPos(pos.getX()+0.5,pos.getY(),pos.getZ()+0.5);
            double before=p.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED);
            net.mcreator.ssc.procedures.CrawlPrProcedure.execute(p);
            h.assertTrue(p.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)<before,"Crawl did not slow player");
            net.mcreator.ssc.procedures.CrawlPrProcedure.execute(p);
            h.assertTrue(Math.abs(p.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)-before)<1e-6,"Uncrawl did not restore speed");h.succeed();
        });
        TESTS.put("empty_armor",h->{
            var p=h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            h.assertTrue(ArmorResistanceHelper.applyArmorResistance(p,"blunt",10)==10,"Empty armor changed damage");h.succeed();
        });
        TESTS.put("gravity_charge_discharge",h->{
            var local=new BlockPos(3,2,3);var pos=h.absolutePos(local);h.setBlock(local,Ssc14ModBlocks.GRAVITY_GENERATOR.get());
            net.mcreator.ssc.access.AirlockPower.supply(h.getLevel(), pos, true);
        var be=h.getLevel().getBlockEntity(pos);be.getPersistentData().putBoolean("active",true);
            for(int i=0;i<402;i++)net.mcreator.ssc.procedures.GravGenTICprocedureProcedure.execute(h.getLevel(),pos.getX(),pos.getY(),pos.getZ(),h.getBlockState(local));
            var prop=(net.minecraft.world.level.block.state.properties.BooleanProperty)h.getBlockState(local).getBlock().getStateDefinition().getProperty("gravity");
            h.assertTrue(h.getBlockState(local).getValue(prop),"Charged generator did not enable gravity");
            be.getPersistentData().putBoolean("active",false);
            for(int i=0;i<402;i++)net.mcreator.ssc.procedures.GravGenTICprocedureProcedure.execute(h.getLevel(),pos.getX(),pos.getY(),pos.getZ(),h.getBlockState(local));
            h.assertTrue(!h.getBlockState(local).getValue(prop),"Discharged generator retained gravity");h.succeed();
        });
    }
    static {
        TESTS.put("physiology_commands",h->{
            var player=net.neoforged.neoforge.common.util.FakePlayerFactory.getMinecraft(h.getLevel());
            var source=h.getLevel().getServer().createCommandSourceStack().withLevel(h.getLevel()).withEntity(player).withSuppressedOutput();
            try {
                for(int value:new int[]{0,200,125}) {
                    h.getLevel().getServer().getCommands().getDispatcher().execute("ssc14 hunger set @s "+value,source);
                    h.getLevel().getServer().getCommands().getDispatcher().execute("ssc14 thirsty set @s "+value,source);
                    h.assertTrue(player.getAttributeValue(net.mcreator.ssc.init.Ssc14ModAttributes.NUTRIENTS)==value,"Hunger command did not set attribute");
                    h.assertTrue(player.getAttributeValue(net.mcreator.ssc.init.Ssc14ModAttributes.LIQUID)==value,"Thirst command did not set attribute");
                }
                player.getAttribute(net.mcreator.ssc.init.Ssc14ModAttributes.DIGESTIVE_PROCESSES).setBaseValue(8);
                h.getLevel().getServer().getCommands().getDispatcher().execute("ssc14 hunger digestive clear @s",source);
                h.assertTrue(player.getAttributeValue(net.mcreator.ssc.init.Ssc14ModAttributes.DIGESTIVE_PROCESSES)==0,"Digestive clear failed");
                h.succeed();
            }catch(com.mojang.brigadier.exceptions.CommandSyntaxException e){h.fail(e.getMessage());}
        });
        TESTS.put("invalid_interaction_slot",h->{
            var player=net.neoforged.neoforge.common.util.FakePlayerFactory.getMinecraft(h.getLevel());
            net.mcreator.ssc.procedures.UniversalInteractCommandProcedure.execute(player,Integer.MAX_VALUE);
            net.mcreator.ssc.procedures.UniversalInteractCommandProcedure.execute(player,-1);h.succeed();
        });
        TESTS.put("magnetic_boots_toggle",h->{
            var player=h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var container=new net.minecraft.world.SimpleContainer(1);
            var original=new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get());
            original.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME,net.minecraft.network.chat.Component.literal("Audit Boots"));container.setItem(0,original);
            var slot=new net.minecraft.world.inventory.Slot(container,0,0,0);
            h.assertTrue(net.mcreator.ssc.procedures.ItemInteractionHandlerProcedure.tryInteract(player,slot),"Boots not interactive");
            h.assertTrue(slot.getItem().is(net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ACTIVE_ITEM.get()),"Boots did not activate");
            net.mcreator.ssc.procedures.ItemInteractionHandlerProcedure.tryInteract(player,slot);
            h.assertTrue(slot.getItem().is(net.mcreator.ssc.init.Ssc14ModItems.MAGNETIC_BOOTS_ITEM.get()),"Boots did not deactivate");
            h.assertTrue(slot.getItem().getHoverName().getString().equals("Audit Boots"),"Boot toggle lost custom name");h.succeed();
        });
    }
    static {
        TESTS.put("shutter_signal_without_player",h->{
            var local=new BlockPos(3,2,3);var target=h.absolutePos(local);var source=h.absolutePos(new BlockPos(1,2,1));
            h.setBlock(local,Ssc14ModBlocks.SHUTTERS.get());
            NetworkSavedData.get(h.getLevel()).addConnection(source,"audit",target,"open");
            NetworkManager.trigger(h.getLevel(),source,"audit");
            h.runAfterDelay(12,()->{h.assertTrue(h.getBlockState(local).getValue(ShuttersBlock.OPEN),"Signal does not open shutter without nearby player");h.succeed();});
        });
        TESTS.put("shutter_direct_control",h->{
            var local=new BlockPos(3,2,3);var pos=h.absolutePos(local);h.setBlock(local,Ssc14ModBlocks.SHUTTERS.get());
            var player=h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            net.mcreator.ssc.procedures.ShuttersOpeningPRProcedure.execute(h.getLevel(),pos.getX(),pos.getY(),pos.getZ(),h.getBlockState(local),player);
            h.runAfterDelay(12,()->{
                h.assertTrue(h.getBlockState(local).getValue(ShuttersBlock.OPEN),"Direct shutter open failed");
                net.mcreator.ssc.procedures.ShuttersClosingPRProcedure.execute(h.getLevel(),pos.getX(),pos.getY(),pos.getZ(),h.getBlockState(local),player);
                h.runAfterDelay(12,()->{h.assertTrue(!h.getBlockState(local).getValue(ShuttersBlock.OPEN),"Direct shutter close failed");h.succeed();});
            });
        });
        TESTS.put("welder_preserves_name",h->{
            var p=h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);p.setShiftKeyDown(true);
            var item=new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.WELDING.get());
            item.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME,net.minecraft.network.chat.Component.literal("Audit Welder"));
            net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA,item,t->t.putDouble("fuel",1000));
            p.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND,item);
            var pos=h.absolutePos(new BlockPos(3,2,3));
            net.mcreator.ssc.procedures.WelderOnOffPrProcedure.execute(h.getLevel(),pos.getX(),pos.getY(),pos.getZ(),p);
            h.assertTrue(p.getMainHandItem().is(net.mcreator.ssc.init.Ssc14ModItems.ACTIVE_WELDER.get()),"Welder activation fixture failed");
            h.assertTrue(p.getMainHandItem().getHoverName().getString().equals("Audit Welder"),"Activating welder destroys custom name");h.succeed();
        });
    }
    static {
        TESTS.put("atmosphere_saved_data_roundtrip", h -> {
            var pos = h.absolutePos(new BlockPos(3, 2, 3));
            var manager = AtmosphereManager.get(h.getLevel());
            var cell = manager.getCellAt(pos);
            cell.setMoles(0, 0.001f);
            cell.setMoles(1, 30f);
            cell.setTemperature(320f);
            var data = AtmosphereSavedData.get(h.getLevel());
            var tag = AtmosphereSavedData.CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE, data).getOrThrow();
            var decoded = AtmosphereSavedData.CODEC.parse(net.minecraft.nbt.NbtOps.INSTANCE, tag).getOrThrow();
            var encoded = (net.minecraft.nbt.CompoundTag) AtmosphereSavedData.CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE, decoded).getOrThrow();
            var regions = encoded.getList("regions").orElseThrow();
            boolean found = false;
            for (int index = 0; index < regions.size(); index++) {
                var regionTag = regions.getCompound(index).orElseThrow();
                if (regionTag.getInt("x").orElseThrow() != (pos.getX() >> 4)
                        || regionTag.getInt("y").orElseThrow() != (pos.getY() >> 4)
                        || regionTag.getInt("z").orElseThrow() != (pos.getZ() >> 4)) continue;
                var region = new AtmosRegion(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
                region.readFromNBT(regionTag);
                var restored = region.getCellAt(pos);
                h.assertTrue(restored.getMoles(0) == 0.001f && restored.getMoles(1) == 30f && restored.getTemperature() == 320f,
                        "Atmosphere save lost trace gas, composition or temperature");
                h.assertTrue(region.isActive(), "Restored gas will not resume simulation");
                found = true;
            }
            h.assertTrue(found && data.isDirty(), "Mutated atmosphere is not scheduled for saving");
            h.succeed();
        });
        TESTS.put("welder_fuel_keeps_hand_model", h -> {
            var item = net.mcreator.ssc.init.Ssc14ModItems.ACTIVE_WELDER.get();
            var before = new net.minecraft.world.item.ItemStack(item);
            var after = before.copy();
            net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA, after, tag -> tag.putDouble("fuel", 500));
            h.assertTrue(!item.shouldCauseReequipAnimation(before, after, false), "Fuel update resets hand animation");
            h.assertTrue(item.shouldCauseReequipAnimation(before, after, true), "Slot change lost equip animation");
            h.assertTrue(item.shouldCauseReequipAnimation(before, glass(), false), "Item change lost equip animation");
            h.succeed();
        });
        TESTS.put("reagent_fluids_registered", h -> {
            for (var reagent : ModReagents.CONFIG_REAGENTS) {
                if (reagent.getId().equals("water")) continue;
                var id = Identifier.fromNamespaceAndPath("ssc_14", reagent.getId());
                var fluid = net.minecraft.core.registries.BuiltInRegistries.FLUID.getValue(id);
                h.assertTrue(fluid instanceof net.neoforged.neoforge.fluids.BaseFlowingFluid, "Missing registered reagent fluid: " + id);
                var flowing = (net.neoforged.neoforge.fluids.BaseFlowingFluid) fluid;
                h.assertTrue(flowing.getSource() == fluid && flowing.getFlowing() != null, "Invalid fluid suppliers: " + id);
                var container = glass();
                ModReagents.addReagent(container, reagent.getId(), 1);
                h.assertTrue(!new ModReagents.ReagentFluidHandler(container, 100).getFluidInTank(0).isEmpty(), "Reagent fluid adapter returned empty: " + id);
            }
            h.succeed();
        });
        TESTS.put("welder_empty_fuel_preserves_name", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            var stack = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.ACTIVE_WELDER.get());
            stack.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME, net.minecraft.network.chat.Component.literal("Empty Welder"));
            net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("fuel", 150));
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, stack);
            net.mcreator.ssc.procedures.Welding_Fuel_chekProcedure.execute(h.getLevel(), 0, 0, 0, player, stack);
            h.assertTrue(player.getMainHandItem().is(net.mcreator.ssc.init.Ssc14ModItems.WELDING.get()), "Empty welder remained active");
            h.assertTrue(player.getMainHandItem().getHoverName().getString().equals("Empty Welder"), "Automatic shutdown lost custom name");
            h.succeed();
        });
        TESTS.put("welder_network_roundtrip", h -> {
            var player = h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            player.setShiftKeyDown(true);
            var stack = new net.minecraft.world.item.ItemStack(net.mcreator.ssc.init.Ssc14ModItems.WELDING.get());
            stack.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME, net.minecraft.network.chat.Component.literal("Network Welder"));
            net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("fuel", 1000));
            player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, stack);
            for (int toggle = 0; toggle < 2; toggle++) {
                net.mcreator.ssc.procedures.WelderOnOffPrProcedure.execute(h.getLevel(), 0, 0, 0, player);
                var actual = player.getMainHandItem();
                var buffer = new net.minecraft.network.RegistryFriendlyByteBuf(io.netty.buffer.Unpooled.buffer(), h.getLevel().registryAccess());
                try {
                    net.minecraft.world.item.ItemStack.STREAM_CODEC.encode(buffer, actual);
                    var decoded = net.minecraft.world.item.ItemStack.STREAM_CODEC.decode(buffer);
                    h.assertTrue(decoded.getHoverName().getString().equals("Network Welder"), "Network transfer lost custom name");
                    h.assertTrue(java.util.Objects.equals(decoded.get(net.minecraft.core.component.DataComponents.ITEM_MODEL),
                            decoded.getItem().components().get(net.minecraft.core.component.DataComponents.ITEM_MODEL)), "Variant retained the previous item model");
                } finally { buffer.release(); }
            }
            h.succeed();
        });
    }
    private static net.minecraft.world.item.ItemStack glass(){return new net.minecraft.world.item.ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.getValue(Identifier.parse("ssc_14:reagent_container")));}
    private static Identifier id(String name){return Identifier.fromNamespaceAndPath("ssc_14","audit_"+name);}
    @SubscribeEvent public static void functions(RegisterEvent event){
        event.register(Registries.TEST_FUNCTION, r -> TESTS.forEach((name,body)->r.register(id(name),body)));
    }
    @SubscribeEvent public static void tests(RegisterGameTestsEvent event){
        TESTS.forEach((name,body)->{
            var env=event.registerEnvironment(id(name));
            var data=new TestData<>(env,Identifier.parse("ssc_14:audit_empty"),name.equals("airlock_plasteel_grille_outer_layer") ? 1400 : 600,0,true);
            event.registerTest(id(name),new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION,id(name)),data));
        });
    }
    private static void reaction(GameTestHelper h,String name){
        try {
            AtmosphereManager.get(h.getLevel());
            var field=AtmosphereManager.class.getDeclaredField("reactions");field.setAccessible(true);
            GasReaction r=java.util.Arrays.stream((GasReaction[])field.get(null)).filter(x->x.id().equals(name)).findFirst().orElseThrow();
            float[] input=new float[GasType.COUNT]; boolean[] req=r.requiredGases();float[] consume=r.consumptionPerTick();
            AtmosCell cell=new AtmosCell();float temp=(r.minTemperatureK()+r.maxTemperatureK())/2;
            for(int i=0;i<input.length;i++){input[i]=(req[i]||consume[i]>0)?10:0;cell.setMoles(i,input[i]);}
            cell.setTemperature(temp);
            h.assertTrue(r.apply(input.clone(),new float[]{temp}),"Fixture invalid: direct recipe rejected");
            boolean changed=cell.tryReactions(new GasReaction[]{r});
            h.assertTrue(changed,"Registered recipe "+name+" accepts mixture at "+temp+" K, but AtmosCell blocks reaction");h.succeed();
        } catch(ReflectiveOperationException e){throw new RuntimeException(e);}
    }
    private static void awaitEnergy(GameTestHelper h, Runnable action) {
        h.runAfterDelay(1, () -> {
            if (net.mcreator.ssc.energy.EnergyTopologyService.rebuilding(h.getLevel())) awaitEnergy(h, action);
            else action.run();
        });
    }
    private static void energy(GameTestHelper h,boolean cut){
        for(int x=1;x<=5;x++) h.setBlock(x,1,1,Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV,true));
        h.setBlock(1,2,1,Ssc14ModBlocks.APC.get());h.setBlock(5,3,1,Ssc14ModBlocks.LAMP.get());
        ApcBlockEntity apc=(ApcBlockEntity)h.getLevel().getBlockEntity(h.absolutePos(new BlockPos(1,2,1)));
        apc.setStoredEnergySimulation(1000000);
        EnergyNetworkManager.updatePosition(h.getLevel(),h.absolutePos(new BlockPos(3,1,1)));
        awaitEnergy(h,()->{
            h.assertTrue(h.getBlockState(new BlockPos(5,3,1)).getValue(LampBlock.ACTIVE),"Fixture: connected lamp did not turn on");
            long before=apc.getStoredEnergy();
            if(cut) h.getLevel().destroyBlock(h.absolutePos(new BlockPos(3,1,1)),false);
            h.runAfterDelay(cut ? 1 : 20,()->{
                if(cut) h.assertTrue(!h.getBlockState(new BlockPos(5,3,1)).getValue(LampBlock.ACTIVE),"Lamp remains powered after bridge destruction");
                else h.assertTrue(before-apc.getStoredEnergy()==20,"One lamp requested ~20 J but APC spent "+(before-apc.getStoredEnergy())+" J");
                h.succeed();
            });
        });
    }
    private static void energyBudget(GameTestHelper h, boolean lamp) {
        for (int x = 1; x <= 5; x++) {
            h.setBlock(x, 1, 1, Ssc14ModBlocks.SHEATHING.get().defaultBlockState().setValue(SheathingBlock.LV, true));
        }
        h.setBlock(1, 2, 1, Ssc14ModBlocks.APC.get());
        h.setBlock(2, 2, 1, Ssc14ModBlocks.APC.get());
        if (lamp) h.setBlock(5, 3, 1, Ssc14ModBlocks.LAMP.get());
        var first = (ApcBlockEntity) h.getLevel().getBlockEntity(h.absolutePos(new BlockPos(1, 2, 1)));
        var second = (ApcBlockEntity) h.getLevel().getBlockEntity(h.absolutePos(new BlockPos(2, 2, 1)));
        first.setStoredEnergySimulation(1000);
        second.setStoredEnergySimulation(1000);
        EnergyNetworkManager.updatePosition(h.getLevel(), h.absolutePos(new BlockPos(3, 1, 1)));
        awaitEnergy(h, () -> {
            long before = first.getStoredEnergy() + second.getStoredEnergy();
            h.runAfterDelay(20, () -> {
                long spent = before - first.getStoredEnergy() - second.getStoredEnergy();
                h.assertTrue(spent == (lamp ? 20 : 0), "Sources spent " + spent + " J");
                h.succeed();
            });
        });
    }
    private static void protectionSteps(GameTestHelper h, net.minecraft.world.entity.player.Player player,
            BlockPos pos, int[][] steps, int index) {
        if (index == steps.length) {
            h.succeed(); return;
        }
        net.minecraft.world.item.Item item = switch (steps[index][0]) {
            case 0 -> net.mcreator.ssc.init.Ssc14ModItems.STEEL.get();
            case 1 -> net.mcreator.ssc.init.Ssc14ModItems.ACTIVE_WELDER.get();
            case 2 -> net.mcreator.ssc.init.Ssc14ModItems.CROWBAR.get();
            case 3 -> net.mcreator.ssc.init.Ssc14ModItems.PLASTEEL.get();
            case 4 -> net.mcreator.ssc.init.Ssc14ModItems.ROOD.get();
            default -> net.mcreator.ssc.init.Ssc14ModItems.NIPPERS.get();
        };
        var stack = new net.minecraft.world.item.ItemStack(item, steps[index][0] == 0 || steps[index][0] == 3 || steps[index][0] == 4 ? 2 : 1);
        if (steps[index][0] == 1)
            net.minecraft.world.item.component.CustomData.update(net.minecraft.core.component.DataComponents.CUSTOM_DATA, stack, tag -> tag.putDouble("fuel", 1000));
        player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, stack);
        int before = h.getLevel().getBlockEntity(pos).getPersistentData().getIntOr(net.mcreator.ssc.access.AirlockMaintenance.ARMOR, 0);
        net.mcreator.ssc.access.AirlockMaintenance.interact(player, pos);
        net.mcreator.ssc.access.AirlockMaintenance.interact(player, pos);
        h.assertTrue(h.getLevel().getBlockEntity(pos).getPersistentData().getIntOr(net.mcreator.ssc.access.AirlockMaintenance.ARMOR, 0) == before, "Work completed without delay");
        h.runAfterDelay(steps[index][2] + 1, () -> {
            var area = new net.minecraft.world.phys.AABB(pos).inflate(2);
            int dropsBefore = h.getLevel().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, area).stream().mapToInt(e -> e.getItem().getCount()).sum();
            net.mcreator.ssc.access.AirlockMaintenance.advance(player);
            h.assertTrue(h.getLevel().getBlockEntity(pos).getPersistentData().getIntOr(net.mcreator.ssc.access.AirlockMaintenance.ARMOR, 0) == steps[index][1], "Wrong protection stage " + index);
            if (steps[index][0] == 1)
                h.assertTrue(stack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA).copyTag().getDoubleOr("fuel", 0) == 900, "Welder charged incorrectly");
            h.getLevel().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class,
                    new net.minecraft.world.phys.AABB(pos).inflate(2)).forEach(drop -> {
                        drop.setNoGravity(true); drop.setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
                    });
            int dropsAfter = h.getLevel().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, area).stream().mapToInt(e -> e.getItem().getCount()).sum();
            h.assertTrue(dropsAfter - dropsBefore == (steps[index][0] == 2 || steps[index][0] == 5 ? 2 : 0), "Wrong material refund at stage " + index);
            net.mcreator.ssc.access.AirlockMaintenance.advance(player);
            h.assertTrue(h.getLevel().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, area).stream().mapToInt(e -> e.getItem().getCount()).sum() == dropsAfter, "Repeated completion duplicated materials");
            protectionSteps(h, player, pos, steps, index + 1);
        });
    }
    private static void door(GameTestHelper h,boolean denied){
        BlockPos local=new BlockPos(3,2,3), pos=h.absolutePos(local);
        h.setBlock(local,Ssc14ModBlocks.BASE_AIRLOCK_D_1.get());
        net.mcreator.ssc.access.AirlockPower.supply(h.getLevel(), pos, true);
        var be=h.getLevel().getBlockEntity(pos);
        if(denied)be.getPersistentData().putBoolean("Capitan",true);
        var player=h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
        BaseAirlockOpenCloseProcedure.execute(h.getLevel(),pos.getX(),pos.getY(),pos.getZ(),h.getBlockState(local),player);
        h.runAfterDelay(15,()->{
            int state=h.getBlockState(local).getValue(BaseAirlockD1Block.ARL_VARIAT);
            h.assertTrue(denied?state==0:state==11,"Unexpected door state after interaction: "+state);
            if(denied){h.succeed();return;}
            h.runAfterDelay(120,()->{h.assertTrue(h.getBlockState(local).getValue(BaseAirlockD1Block.ARL_VARIAT)==0,"Door failed auto-close");h.succeed();});
        });
    }
}
