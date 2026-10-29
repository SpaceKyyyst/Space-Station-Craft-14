package net.ssc14.content.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.ssc14.content.item.access.AccessConfigInventoryCapability;
import net.ssc14.content.item.access.IDCardPassangerInventoryCapability;
import net.ssc14.content.item.communication.HeadsetBaseInventoryCapability;
import net.ssc14.content.item.communication.HeadsetBrigmedicInventoryCapability;
import net.ssc14.content.item.communication.HeadsetCargoInventoryCapability;
import net.ssc14.content.item.communication.HeadsetCentcomInventoryCapability;
import net.ssc14.content.item.communication.HeadsetCommandInventoryCapability;
import net.ssc14.content.item.communication.HeadsetEngineeringInventoryCapability;
import net.ssc14.content.item.communication.HeadsetMedicalInventoryCapability;
import net.ssc14.content.item.communication.HeadsetMedicalScienceInventoryCapability;
import net.ssc14.content.item.communication.HeadsetMiningInventoryCapability;
import net.ssc14.content.item.communication.HeadsetRoboticsInventoryCapability;
import net.ssc14.content.item.communication.HeadsetScienceInventoryCapability;
import net.ssc14.content.item.communication.HeadsetSecurityInventoryCapability;
import net.ssc14.content.item.communication.HeadsetServiceInventoryCapability;
import net.ssc14.content.item.communication.HeadsetSyndicateInventoryCapability;
import net.ssc14.content.item.container.Backpack2InventoryCapability;
import net.ssc14.content.item.container.EggCartonInventoryCapability;
import net.ssc14.content.item.decal.DECAL1spawnerInventoryCapability;
import net.ssc14.content.item.decal.DECAL2spawnerInventoryCapability;
import net.ssc14.content.item.decal.DECAL3spawnerInventoryCapability;
import net.ssc14.content.item.decal.DECAL4spawnerInventoryCapability;
import net.ssc14.content.item.decal.DECAL5spawnerInventoryCapability;
import net.ssc14.content.item.decal.DECAL6spawnerInventoryCapability;
import net.ssc14.content.item.pda.PDApassangerInventoryCapability;

/** Canonical NeoForge capability registration for item containers. */
public final class Ssc14ItemCapabilities {
    private Ssc14ItemCapabilities() {
    }

    public static void register(RegisterCapabilitiesEvent event) {
        register(event, Ssc14Items.ACCESS_CONFIG, AccessConfigInventoryCapability::new);
        register(event, Ssc14Items.DECAL_1SPAWNER, DECAL1spawnerInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_BASE, HeadsetBaseInventoryCapability::new);
        register(event, Ssc14Items.PD_APASSANGER, PDApassangerInventoryCapability::new);
        register(event, Ssc14Items.EGG_CARTON, EggCartonInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_BRIGMEDIC, HeadsetBrigmedicInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_CARGO, HeadsetCargoInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_CENTCOM, HeadsetCentcomInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_COMMAND, HeadsetCommandInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_ENGINEERING, HeadsetEngineeringInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_MEDICAL, HeadsetMedicalInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_MEDICAL_SCIENCE, HeadsetMedicalScienceInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_MINING, HeadsetMiningInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_ROBOTICS, HeadsetRoboticsInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_SCIENCE, HeadsetScienceInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_SECURITY, HeadsetSecurityInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_SERVICE, HeadsetServiceInventoryCapability::new);
        register(event, Ssc14Items.HEADSET_SYNDICATE, HeadsetSyndicateInventoryCapability::new);
        register(event, Ssc14Items.DECAL_2SPAWNER, DECAL2spawnerInventoryCapability::new);
        register(event, Ssc14Items.BACKPACK_2, Backpack2InventoryCapability::new);
        register(event, Ssc14Items.DECAL_3SPAWNER, DECAL3spawnerInventoryCapability::new);
        register(event, Ssc14Items.DECAL_4SPAWNER, DECAL4spawnerInventoryCapability::new);
        register(event, Ssc14Items.DECAL_5SPAWNER, DECAL5spawnerInventoryCapability::new);
        register(event, Ssc14Items.DECAL_6SPAWNER, DECAL6spawnerInventoryCapability::new);
    }

    private static <C extends ResourceHandler<ItemResource>> void register(RegisterCapabilitiesEvent event,
                                      net.neoforged.neoforge.registries.DeferredItem<Item> item,
                                      java.util.function.Function<ItemAccess, C> factory) {
        event.registerItem(Capabilities.Item.ITEM,
                (stack, access) -> factory.apply(access), item.get());
    }
}
