package org.siscode.playablePehkui.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.siscode.playablePehkui.keymapping.KeyHandler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Options.class)
public class OptionsMixin {
    @Mutable
    @Shadow
    @Final
    public KeyMapping[] keyMappings;

    @Inject(method = "load()V", at = @At("HEAD"))
    private void pphk$addCustomOptions(CallbackInfo ci){
        List<KeyMapping> original = Lists.newArrayList(keyMappings);
        KeyHandler.registerVanillaKeyMappings(original);
        keyMappings = original.toArray(new KeyMapping[0]);
    }
}
