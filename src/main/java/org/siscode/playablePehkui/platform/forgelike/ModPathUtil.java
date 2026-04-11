package org.siscode.playablePehkui.platform.forgelike;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ModPathUtil {
    public static Path pphkGetModConfigFolder_forgelike() {
        return FMLPaths.CONFIGDIR.get();
    }
}
