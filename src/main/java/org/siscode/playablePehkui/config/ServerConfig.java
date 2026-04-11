package org.siscode.playablePehkui.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

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
}
