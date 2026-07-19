package org.siscode.playablePehkui.movement.filter;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import org.siscode.playablePehkui.platform.facade.ModRegistries;

public interface BlockStateFilterType<T extends BlockStateFilter> {
    BlockStateFilterType<NotFilter> NOT_FILTER_TYPE = register("playable_pehkui:not", NotFilter.CODEC);
    BlockStateFilterType<AnyFilter> ANY_FILTER_TYPE = register("playable_pehkui:any", AnyFilter.CODEC);
    BlockStateFilterType<AllFilter> ALL_FILTER_TYPE = register("playable_pehkui:all", AllFilter.CODEC);
    BlockStateFilterType<OrientationFilter> ORIENTATION_FILTER_TYPE = register("playable_pehkui:orientation", OrientationFilter.CODEC);
    BlockStateFilterType<TagFilter> TAG_FILTER_TYPE = register("playable_pehkui:matches_tag", TagFilter.CODEC);

    Codec<T> codec();

    static <T extends BlockStateFilter> BlockStateFilterType<T> register(String name, Codec<T> codec) {
        return Registry.register(ModRegistries.BLOCK_STATE_FILTER_TYPE, name, () -> codec);
    }
}
