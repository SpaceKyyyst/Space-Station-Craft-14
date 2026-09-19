
package net.mcreator.ssc.procedures;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.mcreator.ssc.init.Ssc14ModItems;

public class PDA_generateProcedure {
	
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		
        if (false == itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("generate", false)) {
            {
                final String _tagName = "generate";
                final boolean _tagValue = true;
                CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
            }
            
            // ИСПРАВЛЕНО: Безопасное получение инвентаря предмета по стандартам NeoForge 26.1.2
            var rawCap = itemstack.getCapability(Capabilities.Item.ITEM, null);
            IItemHandler itemHandler = rawCap == null ? null : IItemHandler.of(rawCap);
            
            if (itemHandler != null) {
                ItemStack _setstack = new ItemStack(Ssc14ModItems.PEN.get()).copy();
                _setstack.setCount(1);
                // В NeoForge 26.x во встроенных инвентарях используем прямую вставку или замену в слоте
                if (itemHandler instanceof net.neoforged.neoforge.items.IItemHandlerModifiable modifiable) {
                    modifiable.setStackInSlot(1, _setstack);
                }
            }
            
			if (Ssc14ModItems.PD_APASSANGER.get() == itemstack.getItem()) {
				if (itemHandler != null) {
					ItemStack _setstack = new ItemStack(Ssc14ModItems.ID_CARD_PASSANGER.get()).copy();
					_setstack.setCount(1);
					if (itemHandler instanceof net.neoforged.neoforge.items.IItemHandlerModifiable modifiable) {
                        modifiable.setStackInSlot(0, _setstack);
                    }
				}
			}
        }

        if (!itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBooleanOr("flashlight", false)) {
            cleanupLight(world, entity, itemstack);
            return;
        }
        
        if (entity == null || world.isClientSide() || !entity.isAlive()) return;
        if (!(entity instanceof LivingEntity living)) return;

        BlockPos currentPos = living.blockPosition();
        var nbt = entity.getPersistentData();
        String key = "ssc14_light_pos_";

        BlockPos lastPos = readLightPos(itemstack, key);
        if (lastPos == null && nbt.contains(key + "x")) {
            pos_x_block: {
                var optX = nbt.getInt(key + "x");
                var optY = nbt.getInt(key + "y");
                var optZ = nbt.getInt(key + "z");
                if (optX.isEmpty() || optY.isEmpty() || optZ.isEmpty()) break pos_x_block;
                lastPos = new BlockPos(optX.get(), optY.get(), optZ.get());
            }
        }

        if (lastPos != null && lastPos.equals(currentPos)) {
            writeLightPosToItem(itemstack, currentPos, key);
            return;
        }

        safeRemoveLight(world, lastPos);

        var currentState = world.getBlockState(currentPos);
        if (currentState.isAir() || currentState.is(Blocks.LIGHT)) {
            BlockState lightState = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 3);
            world.setBlock(currentPos, lightState, 3);
        }

        nbt.putInt(key + "x", currentPos.getX());
        nbt.putInt(key + "y", currentPos.getY());
        nbt.putInt(key + "z", currentPos.getZ());
        writeLightPosToItem(itemstack, currentPos, key);
    }
    
    private static void cleanupLight(LevelAccessor world, Entity entity, ItemStack itemstack) {
        String key = "ssc14_light_pos_";
        var nbt = entity.getPersistentData();
        
        BlockPos pos = readLightPos(itemstack, key);
        if (pos == null && nbt.contains(key + "x")) {
            pos_cleanup_block: {
                var optX = nbt.getInt(key + "x");
                var optY = nbt.getInt(key + "y");
                var optZ = nbt.getInt(key + "z");
                if (optX.isEmpty() || optY.isEmpty() || optZ.isEmpty()) break pos_cleanup_block;
                pos = new BlockPos(optX.get(), optY.get(), optZ.get());
            }
        }
        
        safeRemoveLight(world, pos);
        
        nbt.remove(key + "x");
        nbt.remove(key + "y");
        nbt.remove(key + "z");
        clearLightPosInItem(itemstack, key);
    }
    
    private static BlockPos readLightPos(ItemStack stack, String key) {
        var nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (!nbt.contains(key + "x")) return null;
        var optX = nbt.getInt(key + "x");
        var optY = nbt.getInt(key + "y");
        var optZ = nbt.getInt(key + "z");
        if (optX.isEmpty() || optY.isEmpty() || optZ.isEmpty()) return null;
        return new BlockPos(optX.get(), optY.get(), optZ.get());
    }
    
    private static void writeLightPosToItem(ItemStack stack, BlockPos pos, String key) {
        var nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        nbt.putInt(key + "x", pos.getX());
        nbt.putInt(key + "y", pos.getY());
        nbt.putInt(key + "z", pos.getZ());
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }
    
    private static void clearLightPosInItem(ItemStack stack, String key) {
        var nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        nbt.remove(key + "x");
        nbt.remove(key + "y");
        nbt.remove(key + "z");
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }
    
    private static void safeRemoveLight(LevelAccessor world, BlockPos pos) {
        if (pos == null) return;
        if (!world.hasChunk(pos.getX() >> 4, pos.getZ() >> 4)) return;
        if (world.getBlockState(pos).is(Blocks.LIGHT)) {
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }
}
