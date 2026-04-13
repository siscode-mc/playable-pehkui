package org.siscode.playablePehkui.keymapping;

public class KeyEvents {
    @FunctionalInterface
    public interface ScreenKeypress {
        void onScreenKeyPress(KeyContext.ScreenCtx context);
    }
    @FunctionalInterface
    public interface LevelKeypress {
        void onLevelKeypress(KeyContext.LevelCtx context);
    }
    @FunctionalInterface
    public interface TitleScreenKeypress {
        void onTitleScreeKeypress();
    }

    @FunctionalInterface
    public interface Keypress {
        /**
         * Contextual-less keypress.
         * Use this if the place where the keypress happens does not matter.
         */
        void onKeypress();
    }
}
