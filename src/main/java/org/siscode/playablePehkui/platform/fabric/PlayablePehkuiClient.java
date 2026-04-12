package org.siscode.playablePehkui.platform.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.siscode.playablePehkui.platform.facade.KeyMappingUtil;

public class PlayablePehkuiClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyMappingUtil.consumeKeyMappings(KeyBindingHelper::registerKeyBinding);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            KeyMappingUtil.pollAllMappings();
        });
    }
}
