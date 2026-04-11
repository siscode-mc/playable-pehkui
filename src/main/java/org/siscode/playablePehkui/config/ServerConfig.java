package org.siscode.playablePehkui.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;

/*
 * Config file that would only be read by the server.
 * Since we don't have client-sided configs this is the only config made yet.
 */
public class ServerConfig extends WrappedConfig {
    @Comment("Changes how pehkui's movement speed is calculated.")
    @Comment("Under default linear scaling, being small feels too slow and being big feels too fast.")
    public boolean useMotionRescale = true;
    @Comment("Changes how pehkui's reach is calculated.")
    @Comment("Under default linear scaling, being small quickly becomes unplayable.")
    public boolean useReachRescale = true;
    @Comment("Changes how pehkui's third person camera position is calculated.")
    @Comment("This makes it so the camera is more zoomed out if you're small and zooms in when you're big")
    @Comment("giving third person view a better sense of scale.")
    public boolean useThirdPersonRescale = true;

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
    /**
     * If beegs can pass through leaves.
     *
     * @since initial
     */
    @Comment("Tinies will be able to climb certain blocks. The exact blocks that are climbable depends")
    @Comment("on the scale of the tiny, with grass and cobblestone being climbable at larger scales (1/2-1/3),")
    @Comment("planks, bricks, and so on being climbable at medium scales (1/4), and dirt being climbable at")
    @Comment("very small scales (1/8 or less)")
    public boolean useScaleSensitiveClimbing = true;

    /**
     * Whether to disable the velocity-capping behaviour that causes 45-strafes while in climbable blocks
     * globally, instead of just for scale-sensitive climbables
     *
     * @since 0.1.0
     */
    @Comment("Removes the vanilla speed capping while within a climbable block but still on the ground.")
    @Comment("Doesn't modify behaviour once no longer on the ground. This makes 45-strafes impossible but")
    @Comment("feels more ergonomic. This is always true for tinies on scale-sensitive climbables.")
    public boolean useBetterClimbingCheckEverywhere = false;

    /**
     * Whether to allow jumping at the start of a climb for scale-sensitive climbing. Simply do not remove vertical
     * velocity that is greater than the climbing velocity.
     *
     * @since 0.1.0
     */
    @Comment("Allows tinies to jump before climbing blocks, by preventing their vertical velocity from being capped.")
    @Comment("Without this, it especially becomes more painful to climb single blocks at intermediate scales (1/4-1/2)")
    public boolean allowJumpAtStartOfClimb = true;
}
