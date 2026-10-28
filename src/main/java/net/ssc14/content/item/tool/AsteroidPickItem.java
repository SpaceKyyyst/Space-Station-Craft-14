package net.ssc14.content.item.tool;

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.ssc14.content.block.Ssc14Blocks;

/** Canonical asteroid pick with constant-time membership checks for mineable stone. */
public class AsteroidPickItem extends Item {
    private static final float ASTEROID_MINING_SPEED = 40.0F;
    private static final float DEFAULT_MINING_SPEED = 1.0F;

    public AsteroidPickItem(Properties properties) {
        super(properties.stacksTo(1).fireResistant()
                .attributes(MechanicalHandToolItem.attributes(MechanicalToolProfile.PICK))
                .enchantable(1));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return AsteroidStones.BLOCKS.contains(state.getBlock())
                ? ASTEROID_MINING_SPEED
                : DEFAULT_MINING_SPEED;
    }

    @Override
    public boolean mineBlock(
            ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        stack.hurtAndBreak(1, entity, entity.getUsedItemHand().asEquipmentSlot());
        return true;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, target, target.getUsedItemHand().asEquipmentSlot());
        MechanicalToolDamageService.apply(
                target.level(), target, MechanicalToolProfile.PICK);
    }

    private static final class AsteroidStones {
        private static final Set<Block> BLOCKS = Set.of(
                Ssc14Blocks.ASTEROID_STONE_BASE.get(),
                Ssc14Blocks.ASTEROID_STONE_IRON.get(),
                Ssc14Blocks.ASTEROID_STONE_QUARTZ.get(),
                Ssc14Blocks.ASTEROID_STONE_COAL.get(),
                Ssc14Blocks.ASTEROID_STONE_GOLD.get(),
                Ssc14Blocks.ASTEROID_STONE_PLASMA.get(),
                Ssc14Blocks.ASTEROID_STONE_SILVER.get(),
                Ssc14Blocks.ASTEROID_STONE_URAN.get(),
                Ssc14Blocks.ASTEROID_STONE_DIAMOND.get());

        private AsteroidStones() { }
    }
}
