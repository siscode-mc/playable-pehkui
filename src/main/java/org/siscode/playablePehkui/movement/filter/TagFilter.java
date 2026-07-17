package org.siscode.playablePehkui.movement.filter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TagFilter implements BlockStateFilter {
    private TagKey<Block> tag;

    public static Codec<TagFilter> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(TagKey.codec(Registries.BLOCK).fieldOf("tag").forGetter(TagFilter::getTag)).apply(instance, TagFilter::new));

    public TagFilter (TagKey<Block> tag) {
        this.tag = tag;
    }

    @Override
    public boolean match(BlockState state) {
        return state.is(tag);
    }

    @Override
    public BlockStateFilterType<?> getType() {
        return BlockStateFilterType.TAG_FILTER_TYPE;
    }

    public TagKey<Block> getTag() {
        return tag;
    }
}
