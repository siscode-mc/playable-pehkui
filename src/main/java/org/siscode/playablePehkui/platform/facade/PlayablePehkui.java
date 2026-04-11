package org.siscode.playablePehkui.platform.facade;

import org.siscode.playablePehkui.config.ServerConfig;
import org.siscode.playablePehkui.movement.ScaleSensitiveClimbables;

public class PlayablePehkui {
    public static ServerConfig SERVER_CONFIG = ServerConfig.createToml(ModPathUtil.pphkGetModConfigFolder(), "", "playable-pehkui-common", ServerConfig.class);

    public static void onInitialize() {
        ScaleSensitiveClimbables.initialize();
    }
}
