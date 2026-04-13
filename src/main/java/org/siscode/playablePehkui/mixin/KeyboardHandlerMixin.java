package org.siscode.playablePehkui.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.siscode.playablePehkui.keymapping.KeyContext;
import org.siscode.playablePehkui.keymapping.KeyHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "keyPress", at = @At(value = "HEAD"))
    private void pphk$handleKeypresses(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        // Action 0: KeyUp (release)
        // Action 1 or 2: Hell If I know, but they're both used for signifying a key was pressed down.
        if (minecraft.screen != null) {
            KeyHandler.notify(key, action != 0, new KeyContext.ScreenCtx(minecraft.screen));
        } else {
            if (minecraft.level != null && minecraft.player != null) {
                KeyHandler.notify(key, action != 0, new KeyContext.LevelCtx(minecraft.level, minecraft.player));
            } else {
                KeyHandler.notify(key, action != 0);
            }
        }
    }
}
