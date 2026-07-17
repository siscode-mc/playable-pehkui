package org.siscode.playablePehkui.movement.filter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class AnyFilter implements BlockStateFilter {
    private List<BlockStateFilter> children;
    public static Codec<AnyFilter> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(BlockStateFilter.CODEC.listOf().fieldOf("children").forGetter(AnyFilter::getChildren)).apply(instance, AnyFilter::new));

    public AnyFilter(List<BlockStateFilter> children) {
        this.children = children;
    }

    @Override
    public boolean match(BlockState state) {
        return children.stream().anyMatch(x -> x.match(state));
    }

    @Override
    public BlockStateFilterType<?> getType() {
        return BlockStateFilterType.ANY_FILTER_TYPE;
    }

    public List<BlockStateFilter> getChildren() {
        return children;
    }
}
