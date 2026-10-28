package net.ssc14.content.menu.machinery;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.ssc14.content.item.Ssc14Items;

/** Immutable render snapshot for the machine-frame menu. */
public record MachineFrameDisplayState(
        boolean hasBoard,
        boolean ready,
        String title,
        String requirement1,
        String requirement2,
        String requirement3,
        String requirement4) {

    public enum BoardType {
        NONE,
        SUBSTATION,
        SMES
    }

    public static MachineFrameDisplayState read(LevelAccessor world, BlockPos pos) {
        ItemStack board = ItemStack.EMPTY;
        if (world instanceof ILevelExtension extension) {
            ResourceHandler<ItemResource> handler =
                    extension.getCapability(Capabilities.Item.BLOCK, pos, null);
            if (handler != null && handler.size() > 0) {
                board = ItemUtil.getStack(handler, 0);
            }
        }

        BoardType type = BoardType.NONE;
        if (board.is(Ssc14Items.SUBSTATION_BOARD.get())) {
            type = BoardType.SUBSTATION;
        } else if (board.is(Ssc14Items.SMES_BOARD.get())) {
            type = BoardType.SMES;
        }

        var state = world.getBlockState(pos);
        boolean ready = state.getBlock().getStateDefinition().getProperty("ready")
                instanceof BooleanProperty property && state.getValue(property);
        return forBoard(type, ready);
    }

    public static MachineFrameDisplayState forBoard(BoardType type, boolean ready) {
        return switch (type) {
            case SUBSTATION -> new MachineFrameDisplayState(
                    true, ready, "Подстанция", "x5 СВ кабель", "x5 ВВ кабель",
                    "x1 Батарея мал.", "x1 Конденсатор");
            case SMES -> new MachineFrameDisplayState(
                    true, ready, "СМЭС", "x1 Конденсатор", "x10 ВВ кабеля",
                    "x4 Батарея мал.", "");
            case NONE -> new MachineFrameDisplayState(
                    false, false, "ВСТАВЬТЕ ПЛАТУ", "", "", "", "");
        };
    }
}
