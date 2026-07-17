package org.siscode.playablePehkui.mixin;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BuiltInRegistries.class)
public interface BuiltInRegistriesMixin {
    @Invoker("registerSimple")
    static <T> Registry<T> pphk$registerSimple(ResourceKey<? extends Registry<T>> key, BuiltInRegistries.RegistryBootstrap<T> bootstrap) {
        throw new UnsupportedOperationException();
    }
}
