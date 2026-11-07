package org.siscode.playablePehkui.platform.facade;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.siscode.playablePehkui.mixin.BuiltInRegistriesMixin;
import org.siscode.playablePehkui.movement.filter.BlockStateFilterType;

import static org.siscode.playablePehkui.platform.facade.ModIdentifierUtil.pphkResource;


public class ModRegistries {
    public static Registry<BlockStateFilterType<?>> BLOCK_STATE_FILTER_TYPE = org.siscode.playablePehkui.platform.fabric.ModRegistries.BLOCK_STATE_FILTER_TYPE;

    public static void initialize() {
        BlockStateFilterType.init();
    }
}
