
package net.mcreator.ssc.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mcreator.ssc.entity.PosterEntity;
import net.mcreator.ssc.init.Ssc14ModEntities;
import net.mcreator.ssc.init.Ssc14ModItems;

import java.util.Map;
import java.util.HashMap;

public class Posters_InstallProcedure {
    private static final Map<net.minecraft.world.item.Item, String> POSTER_MAP = new HashMap<>();

    static {
        POSTER_MAP.put(Ssc14ModItems.POSTER_RANDOM_ANYTHING.get(), "poster_random_anything");
        POSTER_MAP.put(Ssc14ModItems.POSTER_ANOMALY.get(), "poster_anomaly");
        POSTER_MAP.put(Ssc14ModItems.POSTER_ARMORY.get(), "poster_armory");
        POSTER_MAP.put(Ssc14ModItems.POSTER_ATMOS.get(), "poster_atmos");
        POSTER_MAP.put(Ssc14ModItems.POSTER_BAR.get(), "poster_bar");
        POSTER_MAP.put(Ssc14ModItems.POSTER_BRIDGE.get(), "poster_bridge");
        POSTER_MAP.put(Ssc14ModItems.POSTER_CARGO.get(), "poster_cargo");
        POSTER_MAP.put(Ssc14ModItems.POSTER_CHAPEL.get(), "poster_chapel");
        POSTER_MAP.put(Ssc14ModItems.POSTER_CHEM.get(), "poster_chem");
        POSTER_MAP.put(Ssc14ModItems.POSTER_COMMANDER.get(), "poster_commander");
        POSTER_MAP.put(Ssc14ModItems.POSTER_DEATHSPOSAL.get(), "poster_deathsposal");
        POSTER_MAP.put(Ssc14ModItems.POSTER_DETECTIVE.get(), "poster_detective");
        POSTER_MAP.put(Ssc14ModItems.POSTER_DRAMA.get(), "poster_drama");
        POSTER_MAP.put(Ssc14ModItems.POSTER_ENG.get(), "poster_eng");
        POSTER_MAP.put(Ssc14ModItems.POSTER_EVA.get(), "poster_eva");
        POSTER_MAP.put(Ssc14ModItems.POSTER_GRAVI.get(), "poster_gravi");
        POSTER_MAP.put(Ssc14ModItems.POSTER_HYDRO.get(), "poster_hydro");
        POSTER_MAP.put(Ssc14ModItems.POSTER_JANITOR.get(), "poster_janitor");
        POSTER_MAP.put(Ssc14ModItems.POSTER_KITCHEN.get(), "poster_kitchen");
        POSTER_MAP.put(Ssc14ModItems.POSTER_LAW.get(), "poster_law");
        POSTER_MAP.put(Ssc14ModItems.POSTER_MAIL.get(), "poster_mail");
        POSTER_MAP.put(Ssc14ModItems.POSTER_MEDBAY.get(), "poster_medbay");
        POSTER_MAP.put(Ssc14ModItems.POSTER_MEDIUM_SECURE.get(), "poster_medium_secure");
        POSTER_MAP.put(Ssc14ModItems.POSTER_PODS.get(), "poster_pods");
        POSTER_MAP.put(Ssc14ModItems.POSTER_PRISON.get(), "poster_prison");
        POSTER_MAP.put(Ssc14ModItems.POSTER_RND.get(), "poster_rnd");
        POSTER_MAP.put(Ssc14ModItems.POSTER_SALVAGE.get(), "poster_salvage");
        POSTER_MAP.put(Ssc14ModItems.POSTER_SCI.get(), "poster_sci");
        POSTER_MAP.put(Ssc14ModItems.POSTER_SECURITY.get(), "poster_security");
        POSTER_MAP.put(Ssc14ModItems.POSTER_SPACE.get(), "poster_space");
        POSTER_MAP.put(Ssc14ModItems.POSTER_SURGERY.get(), "poster_surgery");
        POSTER_MAP.put(Ssc14ModItems.POSTER_TELECOMS.get(), "poster_telecoms");
        POSTER_MAP.put(Ssc14ModItems.POSTER_VAULT.get(), "poster_vault");
        POSTER_MAP.put(Ssc14ModItems.POSTER_VIROLOGY.get(), "poster_virology");
        POSTER_MAP.put(Ssc14ModItems.POSTER_XENOARCH.get(), "poster_xenoarch");
    }

    public static void execute(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) return;

        BlockPos pos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (player == null) return;
        if (clickedFace.getAxis().isVertical()) return; 

        BlockPos spawnPos = pos.relative(clickedFace);

        String currentPosterType = POSTER_MAP.get(itemstack.getItem());
        if (currentPosterType == null) return;

        // ВЫЧИСЛЕНИЕ ДРОБНОЙ ПОЗИЦИИ КЛИКА (от 0.0 до 1.0 внутри блока)
        Vec3 clickLoc = context.getClickLocation();
        double hitX = clickLoc.x - (double)pos.getX();
        double hitY = clickLoc.y - (double)pos.getY();
        double hitZ = clickLoc.z - (double)pos.getZ();

        // Переводим hitX и hitZ в строго положительные значения (на случай отрицательных координат мира)
        hitX = (hitX < 0) ? hitX + 1.0D : hitX;
        hitZ = (hitZ < 0) ? hitZ + 1.0D : hitZ;

        // Базовые координаты центра блока воздуха перед стеной
        double x = (double) spawnPos.getX() + 0.5D;
        double y = (double) spawnPos.getY();
        double z = (double) spawnPos.getZ() + 0.5D;

        // Смещение К СТЕНЕ по оси клика
        x -= (double) clickedFace.getStepX() * 0.465D;
        z -= (double) clickedFace.getStepZ() * 0.465D;

        // СИСТЕМА УМНОГО СДВИГА ПО ТРЁМ ВЕКТОРАМ (Шаг 0.5 блока)
        // Высота (Вектор Y) обрабатывается всегда, так как все стены вертикальные
        y += getSmartOffset(hitY);

        // Горизонтальные векторы обрабатываются в зависимости от направления стены
        if (clickedFace.getAxis() == Direction.Axis.Z) {
            // Кликнули по стене Север/Юг -> постер двигается влево/вправо по оси X
            x = (double) spawnPos.getX() + getSmartOffset(hitX);
        } else if (clickedFace.getAxis() == Direction.Axis.X) {
            // Кликнули по стене Восток/Запад -> постер двигается влево/вправо по оси Z
            z = (double) spawnPos.getZ() + getSmartOffset(hitZ);
        }

        PosterEntity poster = new PosterEntity(Ssc14ModEntities.POSTER.get(), level);
        poster.absSnapTo(x, y, z, 0.0F, 0.0F);
        poster.setFacingDirection(clickedFace);
        poster.setPosterType(currentPosterType);

        // ДИНАМИЧЕСКАЯ НАСТРОЙКА ХИТБОКСА (подстраивается под новые координаты смещения)
        double thickness = 0.03D; 
        double hWidth = 0.5D;     
        double hHeight = 1.0D;    

        double minX = x - (clickedFace.getAxis() == Direction.Axis.Z ? hWidth : thickness);
        double maxX = x + (clickedFace.getAxis() == Direction.Axis.Z ? hWidth : thickness);
        double minY = y;
        double maxY = y + hHeight;
        double minZ = z - (clickedFace.getAxis() == Direction.Axis.X ? hWidth : thickness);
        double maxZ = z + (clickedFace.getAxis() == Direction.Axis.X ? hWidth : thickness);

        poster.setBoundingBox(new AABB(minX, minY, minZ, maxX, maxY, maxZ));

        level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.PAINTING_PLACE, net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
        level.addFreshEntity(poster);

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
    }

    // Вспомогательный метод для округления долей клика по правилу 0.25 - 0.75
    private static double getSmartOffset(double hitValue) {
        if (hitValue < 0.25D) {
            return 0.0D; // Сдвиг к началу блока (координата 0)
        } else if (hitValue > 0.75D) {
            return 1.0D; // Сдвиг к концу блока (координата 1)
        } else {
            return 0.5D; // Строго центр блока
        }
    }
}
