package org.siscode.playablePehkui.mixininterface;
/**
 * When something can be slowed down for any reason.
 *
 * @since initial
 * @author siscode-mc
 */
public interface Slowable {
    void ppkh$addSlowdownContribution(float basePower);
    void pphk$applySlowdownToStuckSpeedMultiplier();
}
