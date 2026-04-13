package org.siscode.playablePehkui.keymapping;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;

public class KeyContext {
    public record ScreenCtx(Screen parent) {

    }

    public record LevelCtx(Level level, LocalPlayer player) {

    }
}
