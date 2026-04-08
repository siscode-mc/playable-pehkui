package org.siscode.playablePehkui.platform.facade;

import org.siscode.playablePehkui.config.ServerConfig;

public class PlayablePehkui {
    public static ServerConfig SERVER_CONFIG = ServerConfig.createToml(ModPathUtil.pphkGetModConfigFolder(), "", "playable-pehkui-common", ServerConfig.class);
}
