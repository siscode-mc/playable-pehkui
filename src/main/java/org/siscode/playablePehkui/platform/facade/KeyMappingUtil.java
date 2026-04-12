package org.siscode.playablePehkui.platform.facade;

import net.minecraft.client.KeyMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class KeyMappingUtil {
    private static Map<KeyMapping, Runnable> keymappingsList = new HashMap<>();
    public static final String MOD_KEYMAPPING_CATEGORY = "key.playablepehkui";

    /**
     * Register a keyboard keybinding with the default mod category.<br>
     * Use <code>GLFW.KEY_</code> to get the correct keycode.<br>
     * Use <code>KeyMapping#isDown()</code> to check if a key was pressed.<br>
     * @param translationKey The translation key (or name) of the keybinding.
     * @param keyCode The key code to be used for this keybinding.
     * @param callback The callback that will be run once the keybinding is pressed.
     * @return The minecraft <code>KeyMapping</code> that was registered.
     *
     * @see KeyMapping
     * @see org.lwjgl.glfw.GLFW
     */
    public static KeyMapping registerKeybinding(String translationKey, int keyCode, Runnable callback) {
        KeyMapping keyMapping = new KeyMapping(translationKey, keyCode, MOD_KEYMAPPING_CATEGORY);
        keymappingsList.put(keyMapping, callback);
        return keyMapping;
    }


    /**
     * Allows for mod-loader specific implementations to register keybindings
     * on their own way, by consuming the internal keymapping list.
     * @param consumer The function that will be run for each keymapping.
     */
    public static void consumeKeyMappings(Consumer<KeyMapping> consumer) {
        keymappingsList.keySet().forEach(consumer);
    }

    public static void pollAllMappings() {
        keymappingsList.forEach( (k,r) -> {
            if (k.consumeClick()) {
                r.run();
            }
        });
    }
}
