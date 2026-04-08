package org.siscode.playablePehkui.platform.fabric;

import net.fabricmc.api.ModInitializer;
import org.siscode.playablePehkui.PehkuiPlugin;

public class PlayablePehkui implements ModInitializer {
    @Override
    public void onInitialize() {
        PehkuiPlugin.initialize();
    }
}
