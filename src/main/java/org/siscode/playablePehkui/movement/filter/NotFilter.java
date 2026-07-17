package org.siscode.playablePehkui.movement.filter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;

public class NotFilter implements BlockStateFilter {
    private BlockStateFilter children;

    public static Codec<NotFilter> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(BlockStateFilter.CODEC.fieldOf("children").forGetter(NotFilter::getChildren)).apply(instance, NotFilter::new));

    public NotFilter(BlockStateFilter children) {
        this.children = children;
    }

    public BlockStateFilter getChildren() {
        return this.children;
    }

    @Override
    public boolean match(BlockState state) {
        return !children.match(state);
    }


    @Override
    public BlockStateFilterType<?> getType() {
        return BlockStateFilterType.NOT_FILTER_TYPE;
    }
}
