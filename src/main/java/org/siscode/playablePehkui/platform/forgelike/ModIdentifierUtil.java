package org.siscode.playablePehkui.platform.forgelike;

import net.minecraft.resources.ResourceLocation;

public class ModIdentifierUtil {
    public static ResourceLocation pphkResource_forgelike(String resourcePath) {
        return ResourceLocation.fromNamespaceAndPath("playablepehkui", resourcePath);
    }
}
