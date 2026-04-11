package org.siscode.playablePehkui.platform.fabric;

import net.fabricmc.api.ModInitializer;
import org.siscode.playablePehkui.PehkuiPlugin;
import org.siscode.playablePehkui.movement.ScaleSensitiveClimbables;

public class PlayablePehkui implements ModInitializer {
    @Override
    public void onInitialize() {
        PehkuiPlugin.initialize();
        ScaleSensitiveClimbables.initialize();

    }
}
