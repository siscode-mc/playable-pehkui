package org.siscode.playablePehkui.platform.facade;

import net.minecraft.client.KeyMapping;

public class KeyMappingUtil {
    public static void registerKeyMapping(KeyMapping keyMapping) {
        org.siscode.playablePehkui.platform.fabric.KeyMappingUtil.pphkRegisterKeymapping_fabric(keyMapping);
    }
}
