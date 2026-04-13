package org.siscode.playablePehkui.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {
    @Accessor("key")
    InputConstants.Key pphk$currentKey();

    @Accessor("CATEGORY_SORT_ORDER")
    static Map<String, Integer> pphk$getCategoryMap() {
        throw new UnsupportedOperationException();
    }
}
