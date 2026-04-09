package org.siscode.playablePehkui.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class ModPathUtil {
    public static Path pphkGetModConfigFolder_fabric() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
