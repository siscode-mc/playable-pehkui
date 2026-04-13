package org.siscode.playablePehkui.keymapping;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.gui.screens.TitleScreen;

import java.util.HashMap;

public class KeyHandler {
    private static HashMap<Integer, KeyMapping> ALL_KEYS = new HashMap<>();

    private static Reference2ObjectOpenHashMap<KeyMapping, KeyEvents.Keypress> BASIC_LISTENERS = new Reference2ObjectOpenHashMap<>();
    private static Reference2ObjectOpenHashMap<KeyMapping, KeyEvents.LevelKeypress> LEVEL_LISTENERS = new Reference2ObjectOpenHashMap<>();
    private static Reference2ObjectOpenHashMap<KeyMapping, KeyEvents.ScreenKeypress> SCREEN_LISTENERS = new Reference2ObjectOpenHashMap<>();
    private static Reference2ObjectOpenHashMap<KeyMapping, KeyEvents.TitleScreenKeypress> TITLESCREEN_LISTENERS = new Reference2ObjectOpenHashMap<>();

    public static void put(KeyMapping keyMapping) {
        ALL_KEYS.put(keyMapping.keycode, keyMapping);
    }

    public static void register(KeyMapping keyMapping, KeyEvents.Keypress event) {
        BASIC_LISTENERS.put(keyMapping, event);
    }

    public static void register(KeyMapping keyMapping, KeyEvents.LevelKeypress levelEvent) {
        LEVEL_LISTENERS.put(keyMapping, levelEvent);
    }

    public static void register(KeyMapping keyMapping, KeyEvents.ScreenKeypress screenEvent) {
        SCREEN_LISTENERS.put(keyMapping, screenEvent);
    }

    public static void register(KeyMapping keyMapping, KeyEvents.TitleScreenKeypress titleEvent) {
        TITLESCREEN_LISTENERS.putIfAbsent(keyMapping, titleEvent);
    }

    public static void notify(int keyCode, boolean isPressed) {
        if (ALL_KEYS.containsKey(keyCode)) {
            KeyMapping key = ALL_KEYS.get(keyCode);
            boolean shouldNotify = key.shouldNotify(isPressed);
            key.setPressed(isPressed);

            KeyEvents.Keypress baseListener = BASIC_LISTENERS.get(key);
            if (baseListener != null && shouldNotify) {
                baseListener.onKeypress();
            }
        }
    }

    public static void notify(int keyCode, boolean isPressed, KeyContext.LevelCtx ctx) {
        if (ALL_KEYS.containsKey(keyCode)) {
            KeyMapping key = ALL_KEYS.get(keyCode);
            boolean shouldNotify = key.shouldNotify(isPressed);
            key.setPressed(isPressed);

            if (shouldNotify) {
                KeyEvents.LevelKeypress levelListener = LEVEL_LISTENERS.get(key);
                if (levelListener != null) {
                    levelListener.onLevelKeypress(ctx);
                }

                KeyEvents.Keypress baseListener = BASIC_LISTENERS.get(key);
                if (baseListener != null) {
                    baseListener.onKeypress();
                }
            }
        }
    }

    public static void notify(int keyCode, boolean isPressed, KeyContext.ScreenCtx ctx) {
        if (ALL_KEYS.containsKey(keyCode)) {
            KeyMapping key = ALL_KEYS.get(keyCode);
            boolean shouldNotify = key.shouldNotify(isPressed);
            key.setPressed(isPressed);

            if (shouldNotify) {
                if (ctx.parent() instanceof TitleScreen) {
                    KeyEvents.TitleScreenKeypress titleListener = TITLESCREEN_LISTENERS.get(key);
                    if (titleListener != null) {
                        titleListener.onTitleScreeKeypress();
                    }
                } else  {
                    KeyEvents.ScreenKeypress screenListener = SCREEN_LISTENERS.get(key);
                    if (screenListener != null) {
                        screenListener.onScreenKeyPress(ctx);
                    }
                }

                KeyEvents.Keypress baseListener = BASIC_LISTENERS.get(key);
                if (baseListener != null) {
                    baseListener.onKeypress();
                }
            }
        }
    }

    public static void updateKeycode(int from, int into) {
        if (ALL_KEYS.containsKey(from)) {
            KeyMapping value = ALL_KEYS.remove(from);
            ALL_KEYS.put(into, value);
        }
    }


    public static void registerVanillaKeyMappings() {
        for (var mapping : ALL_KEYS.values()) {
            if (mapping.showInConfigScreen) {
                new net.minecraft.client.KeyMapping(mapping.translationKey, mapping.keycode, mapping.category);
            }
        }
    }
}
