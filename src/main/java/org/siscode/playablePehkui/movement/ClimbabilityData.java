package org.siscode.playablePehkui.movement;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.siscode.playablePehkui.movement.filter.BlockStateFilter;

import java.util.Optional;

public record ClimbabilityData(Either<TagKey<Block>, BlockState> block, Optional<BlockStateFilter> filter, double minScale, double maxScale, double speedModifier) {
    public static final Codec<ClimbabilityData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.either(TagKey.hashedCodec(Registries.BLOCK), BlockState.CODEC).fieldOf("block").forGetter(ClimbabilityData::block),
                    BlockStateFilter.CODEC.optionalFieldOf("filter").forGetter(ClimbabilityData::filter),
                    Codec.DOUBLE.fieldOf("min_scale").forGetter(ClimbabilityData::minScale),
                    Codec.DOUBLE.fieldOf("max_scale").forGetter(ClimbabilityData::maxScale),
                    Codec.DOUBLE.fieldOf("speed_modifier").forGetter(ClimbabilityData::speedModifier)
                    ).apply(instance, ClimbabilityData::new));
}
