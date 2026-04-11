package org.siscode.playablePehkui.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;

/*
 * Config file that would only be read by the server.
 * Since we don't have client-sided configs this is the only config made yet.
 */
public class ServerConfig extends WrappedConfig {

    /**
     * If beegs can pass through leaves.
     *
     * @since initial
     */
    @Comment("Big players will be able to pass through leaf blocks, making traversing forests much easier.")
    @Comment("They will also be slowed down for each leaf block they collide with, so denser forests")
    @Comment("will slow them down more. Said slowdown can be configured with the leavesSlowdown setting.")
    public boolean passThroughLeaves = true;

    /**
     * By how much each leaf block would slow the beeg down.
     *
     * @since initial
     *
     * @see org.siscode.playablePehkui.mixin.LeavesBlockMixin
     */
    @Comment("How much each leaf block will contribute to the slowdown.")
    @Comment("Setting it to zero disable it.")
    @FloatRange(min=0.0, max=10.0)
    public double leavesSlowdown = 1.0;
}
