package org.siscode.playablePehkui.platform.fabric;

import net.fabricmc.api.ModInitializer;
public class PlayablePehkui implements ModInitializer {
    @Override
    public void onInitialize() {
        org.siscode.playablePehkui.platform.facade.PlayablePehkui.onInitialize();
    }
}
