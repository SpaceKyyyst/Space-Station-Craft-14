package net.ssc14.content.item.communication;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/** Existing data-pack tags used by the station communication equipment. */
public final class Ssc14CommunicationTags {
    private static final String LEGACY_TAG_NAMESPACE = "ssc14";

    public static final TagKey<Item> HEADSETS = itemTag("headsets");
    public static final TagKey<Item> ENCRYPTION_KEYS = itemTag("encryption_keys");

    private Ssc14CommunicationTags() { }

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(LEGACY_TAG_NAMESPACE, path));
    }
}
