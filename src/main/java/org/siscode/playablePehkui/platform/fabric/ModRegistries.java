package org.siscode.playablePehkui.platform.fabric;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.siscode.playablePehkui.movement.filter.BlockStateFilterType;

import static net.minecraft.resources.ResourceKey.createRegistryKey;


public class ModRegistries {
    public static ResourceKey<Registry<BlockStateFilterType<?>>> BLOCK_STATE_FILTER_TYPE_KEY = createRegistryKey(ModIdentifierUtil.pphkResource_fabric("block_state_filter_type"));
    public static Registry<BlockStateFilterType<?>> BLOCK_STATE_FILTER_TYPE = FabricRegistryBuilder.createSimple(BLOCK_STATE_FILTER_TYPE_KEY).buildAndRegister();
}
