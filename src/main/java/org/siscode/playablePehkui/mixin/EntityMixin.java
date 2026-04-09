package org.siscode.playablePehkui.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.siscode.playablePehkui.mixininterface.Slowable;
import org.siscode.playablePehkui.util.PehkuiUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleTypes;

@Mixin(Entity.class)
public class EntityMixin implements Slowable {
    @Shadow
    protected Vec3 stuckSpeedMultiplier;
    @Unique double pphk$slowdownPower = 0;

    @Override
    public void ppkh$addSlowdownContribution(float basePower) {
        this.pphk$slowdownPower += basePower;
    }

    @Override
    public void pphk$applySlowdownToStuckSpeedMultiplier() {
        double slowdownBase = 1/Math.E;
        if (pphk$slowdownPower < 1.0E-7D) { return; }
        double entityScale = PehkuiUtil.GetEntityScale(((Entity) (Object) this), ScaleTypes.BASE);
        double scaledPower = pphk$slowdownPower / Math.pow(entityScale, 3);
        double slowdownRatio = Math.pow(slowdownBase, scaledPower);
        if (this.stuckSpeedMultiplier.lengthSqr() <= 1.0E-7D) {
            stuckSpeedMultiplier = new Vec3(slowdownRatio, slowdownRatio, slowdownRatio);
        } else {
            stuckSpeedMultiplier = new Vec3(
                    Math.min(stuckSpeedMultiplier.x, slowdownRatio),
                    Math.min(stuckSpeedMultiplier.y, slowdownRatio),
                    Math.min(stuckSpeedMultiplier.z, slowdownRatio)
            );
        }

        pphk$slowdownPower = 0;
    }

    @Inject(method = "move", at = @At("HEAD"))
    public void pphk$applySlowdownBeforeMoving(MoverType pType, Vec3 pPos, CallbackInfo ci) {
        this.pphk$applySlowdownToStuckSpeedMultiplier();
    }
}
