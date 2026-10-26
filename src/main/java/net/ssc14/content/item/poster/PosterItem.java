package net.ssc14.content.item.poster;

import java.util.Objects;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

/** Canonical item representation of one poster variant. */
public class PosterItem extends Item {
    private final String posterType;

    public PosterItem(Properties properties, String posterType) {
        super(properties.stacksTo(1));
        this.posterType = Objects.requireNonNull(posterType, "posterType");
    }

    public String posterType() {
        return posterType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        PosterPlacementService.place(context, posterType);
        return InteractionResult.SUCCESS;
    }
}
