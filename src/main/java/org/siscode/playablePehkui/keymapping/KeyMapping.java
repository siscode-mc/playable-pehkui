package org.siscode.playablePehkui.keymapping;


/**
 * Represents a custom keymapping that's cross-platform and have a better API.
 */
public class KeyMapping {
    public static final String DEFAULT_CATEGORY = "key.playablepehkui";

    public int ticksSinceLastPress = Integer.MAX_VALUE;
    public boolean isPressed = false;
    public int keycode;
    public boolean showInConfigScreen;
    public String translationKey;
    public String category;

    public KeyMapping(String translationKey, int keycode, boolean showInConfigScreen) {
        this.translationKey = translationKey;
        this.category = DEFAULT_CATEGORY;
        this.keycode = keycode;
        this.showInConfigScreen = showInConfigScreen;

        KeyHandler.put(this);
    }

    public KeyMapping(String translationKey, String category, int keycode, boolean showInConfigScreen) {
        this.translationKey = translationKey;
        this.category = category;
        this.keycode = keycode;
        this.showInConfigScreen = showInConfigScreen;

        KeyHandler.put(this);
    }

    public void setPressed(boolean value) {
        this.isPressed = value;
        if (value) {
            this.ticksSinceLastPress = 0;
        }
    }

    public void tick() {
        if (ticksSinceLastPress != Integer.MAX_VALUE) {
            this.ticksSinceLastPress += 1;
        }
    }

    public boolean shouldNotify(boolean newState) {
        return !isPressed && newState;
    }

    public void onKeyPress(KeyEvents.Keypress event) {
        KeyHandler.register(this, event);
    }

    public void onScreenKeypress(KeyEvents.ScreenKeypress event) {
        KeyHandler.register(this, event);
    }

    public void onLevelKeypress(KeyEvents.LevelKeypress event) {
        KeyHandler.register(this, event);
    }

    public void onTitleScreenKeypress(KeyEvents.TitleScreenKeypress event) {
        KeyHandler.register(this, event);
    }
}
