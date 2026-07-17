package org.siscode.playablePehkui.movement.filter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class AllFilter implements BlockStateFilter {
    private List<BlockStateFilter> children = new ArrayList<>();
    public static Codec<AllFilter> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(BlockStateFilter.CODEC.listOf().fieldOf("children").forGetter(AllFilter::getChildren)).apply(instance,AllFilter::new));

    public AllFilter(List<BlockStateFilter> children) {
        this.children = children;
    }

    @Override
    public boolean match(BlockState state) {
        return children.stream().allMatch(x -> x.match(state));
    }

    @Override
    public BlockStateFilterType<?> getType() {
        return BlockStateFilterType.ALL_FILTER_TYPE;
    }

    public List<BlockStateFilter> getChildren() {
        return children;
    }
}
