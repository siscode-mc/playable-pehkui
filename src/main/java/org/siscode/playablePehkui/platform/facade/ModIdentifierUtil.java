package org.siscode.playablePehkui.platform.facade;

import net.minecraft.resources.ResourceLocation;

public class ModIdentifierUtil {
    public static ResourceLocation pphkResource(String resourcePath) {
        return org.siscode.playablePehkui.platform.forgelike.ModIdentifierUtil.pphkResource_forgelike(resourcePath);
    }
}
