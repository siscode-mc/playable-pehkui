package org.siscode.playablePehkui.platform.fabric;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class KeyMappingUtil {
    public static void pphkRegisterKeymapping_fabric(KeyMapping keymapping) {
        KeyBindingHelper.registerKeyBinding(keymapping);
    }
}
