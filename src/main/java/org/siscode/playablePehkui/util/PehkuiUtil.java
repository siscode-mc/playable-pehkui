package org.siscode.playablePehkui.util;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

public class PehkuiUtil {
    /**
     * @param entity The entity of which to check the scale
     * @param type The ScaleType to be used on this check which, not limited to Pehkui's own scale types
     * @return The scale of this entity
     *
     * @see ScaleTypes
     * @see ScaleType
     *
     * @since initial
     */
    public static float GetEntityScale(@NotNull Entity entity, @NotNull ScaleType type) {
        return type.getScaleData(entity).getScale();
    }
}
