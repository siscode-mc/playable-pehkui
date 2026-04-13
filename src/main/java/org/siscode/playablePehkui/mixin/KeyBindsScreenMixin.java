package org.siscode.playablePehkui.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import org.siscode.playablePehkui.keymapping.KeyHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyBindsScreen.class)
public class KeyBindsScreenMixin {
    @WrapOperation(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;setKey(Lnet/minecraft/client/KeyMapping;Lcom/mojang/blaze3d/platform/InputConstants$Key;)V"))
    public void pphk$updateScancode(Options instance, KeyMapping keyBinding, InputConstants.Key input, Operation<Void> original) {
        InputConstants.Key key = ((KeyMappingAccessor) keyBinding).pphk$currentKey();
        KeyHandler.updateKeycode(key.getValue(), input.getValue());
        original.call(instance, keyBinding, input);
    }
}
