package net.ssc14.content.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.ssc14.common.Ssc14Identity;
import net.ssc14.content.block.airlock.NamedAirlockBlockEntity;

@EventBusSubscriber(modid = Ssc14Identity.MOD_ID)
public final class Ssc14BlocksDepartmentAirlocksGameTests {
    private static final Identifier TEST_ID = id("compatibility_block_department_airlocks_contract");
    private static final List<DeferredBlock<Block>> BLOCKS = List.of(
            Ssc14Blocks.TECH_AIRLOCK_D_1, Ssc14Blocks.ATMOSPHERICS_AIRLOCK_D_1,
            Ssc14Blocks.CARGO_AIRLOCK_D_1, Ssc14Blocks.CENT_COM_AIRLOCK_D_1,
            Ssc14Blocks.CHEMISTRY_AIRLOCK_D_1, Ssc14Blocks.COMMAND_AIRLOCK_D_1,
            Ssc14Blocks.ENGINEERING_AIRLOCK_D_1, Ssc14Blocks.HYDROPONICS_AIRLOCK_D_1,
            Ssc14Blocks.MEDICAL_AIRLOCK_D_1, Ssc14Blocks.SALVAGE_AIRLOCK_D_1,
            Ssc14Blocks.SCIENCE_AIRLOCK_D_1, Ssc14Blocks.SECURITY_AIRLOCK_D_1,
            Ssc14Blocks.SYNDICATE_AIRLOCK_D_1, Ssc14Blocks.VIROLOGY_AIRLOCK_D_1,
            Ssc14Blocks.GLASS_AIRLOCK_D_1, Ssc14Blocks.BASE_GLASS_AIRLOCK_D_1);
    private static final List<String> IDS = List.of(
            "tech_airlock_d_1", "atmospherics_airlock_d_1", "cargo_airlock_d_1",
            "cent_com_airlock_d_1", "chemistry_airlock_d_1", "command_airlock_d_1",
            "engineering_airlock_d_1", "hydroponics_airlock_d_1", "medical_airlock_d_1",
            "salvage_airlock_d_1", "science_airlock_d_1", "security_airlock_d_1",
            "syndicate_airlock_d_1", "virology_airlock_d_1", "glass_airlock_d_1",
            "base_glass_airlock_d_1");
    private static final List<String> DISPLAY_NAMES = List.of(
            "Tech Airlock", "Atmospherics Airlock", "Cargo Airlock", "CentComm Airlock",
            "Chemistry Airlock", "Command Airlock", "Engineering Airlock",
            "Hydroponics Airlock", "Medical Airlock", "Salvage Airlock", "Science Airlock",
            "Security Airlock", "Syndicate Airlock", "Virology Airlock", "Glass Airlock",
            "Base Glass Airlock");

    private Ssc14BlocksDepartmentAirlocksGameTests() { }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, registry -> registry.register(
                TEST_ID, Ssc14BlocksDepartmentAirlocksGameTests::verifyContract));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("block_department_airlocks_environment"));
        event.registerTest(TEST_ID, new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, TEST_ID),
                new TestData<>(environment, id("audit_empty"), 80, 1, true)));
    }

    private static void verifyContract(GameTestHelper helper) {
        BlockPos relative = new BlockPos(2, 2, 2);
        BlockPos absolute = helper.absolutePos(relative);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int index = 0; index < BLOCKS.size(); index++) {
            Block block = BLOCKS.get(index).get();
            String expectedId = IDS.get(index);
            helper.assertTrue(id(expectedId).equals(BuiltInRegistries.BLOCK.getKey(block)),
                    expectedId + " block ID changed");
            helper.setBlock(relative, Blocks.AIR);
            helper.setBlock(relative.above(), Blocks.AIR);
            helper.setBlock(relative, block.defaultBlockState());
            var entity = helper.getLevel().getBlockEntity(absolute);
            helper.assertTrue(entity instanceof NamedAirlockBlockEntity,
                    expectedId + " block entity changed");
            NamedAirlockBlockEntity airlock = (NamedAirlockBlockEntity) entity;
            helper.assertTrue(expectedId.equals(airlock.getDefaultName().getString())
                            && DISPLAY_NAMES.get(index).equals(airlock.getDisplayName().getString())
                            && airlock.getContainerSize() == NamedAirlockBlockEntity.SLOT_COUNT
                            && id(expectedId).equals(BuiltInRegistries.BLOCK_ENTITY_TYPE
                                    .getKey(airlock.getType()))
                            && airlock.createMenu(index, player.getInventory()) != null,
                    expectedId + " inventory contract changed");
            var capability = helper.getLevel().getCapability(
                    Capabilities.Item.BLOCK, absolute, null);
            IItemHandler handler = capability == null ? null : IItemHandler.of(capability);
            helper.assertTrue(handler != null
                            && handler.getSlots() == NamedAirlockBlockEntity.SLOT_COUNT,
                    expectedId + " item capability changed");
        }
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Ssc14Identity.MOD_ID, path);
    }
}
