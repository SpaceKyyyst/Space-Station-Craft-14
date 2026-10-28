package net.mcreator.ssc.item;

import net.minecraft.world.item.Item;
import net.ssc14.content.item.weapon.PistolMagazineItem;
import net.ssc14.content.item.weapon.PistolMagazineState;

/** @deprecated Use the canonical pistol-magazine item registration. */
@Deprecated(forRemoval = false)
public class MagazinePistol35emptyItem extends PistolMagazineItem {
    public MagazinePistol35emptyItem(Item.Properties properties) {
        super(properties, PistolMagazineState.EMPTY);
    }
}
