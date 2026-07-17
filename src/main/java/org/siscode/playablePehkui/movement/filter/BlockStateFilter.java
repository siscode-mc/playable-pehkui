package org.siscode.playablePehkui.movement.filter;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import org.siscode.playablePehkui.platform.facade.ModRegistries;

public interface BlockStateFilter {
    Codec<BlockStateFilter> CODEC = ModRegistries.BLOCK_STATE_FILTER_TYPE.byNameCodec().dispatch(BlockStateFilter::getType, BlockStateFilterType::codec);
    boolean match(BlockState state);
    BlockStateFilterType<?> getType();
}
