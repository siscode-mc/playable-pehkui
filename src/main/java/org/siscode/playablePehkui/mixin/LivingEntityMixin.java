package org.siscode.playablePehkui.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.siscode.playablePehkui.config.ServerConfig;
import org.siscode.playablePehkui.movement.ScaleSensitiveClimbables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Optional;

import static net.minecraft.world.entity.LivingEntity.DEFAULT_BASE_GRAVITY;
import static org.siscode.playablePehkui.platform.facade.PlayablePehkui.SERVER_CONFIG;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    private Optional<BlockPos> lastClimbablePos;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(method = "onClimbable()Z", at = @At("RETURN"))
    public boolean onScaleSensitiveClimbable(boolean original) {
        if (original) { return original; }

        double scale = ScaleTypes.BASE.getScaleData(this).getScale();
        var use_scale_sensitive_climbing = SERVER_CONFIG.useScaleSensitiveClimbing;
        if (scale >= 1.0 || !use_scale_sensitive_climbing) {
            return false;
        }

        AABB bb = this.getBoundingBox();
        int mX = Mth.floor(bb.minX-bb.getXsize()/2);  // Add half the bb size again so that it counts if we're "near enough" to a climbable block
        int mY = Mth.floor(bb.minY);
        int mZ = Mth.floor(bb.minZ - bb.getZsize()/2);

        for (int y2 = mY; y2 <= bb.minY; y2++) {
            for (int x2 = mX; x2 < bb.maxX + bb.getXsize()/2; x2++) {
                for (int z2 = mZ; z2 < bb.maxZ + bb.getZsize()/2; z2++) {

                    BlockPos tmp = new BlockPos(x2, y2, z2);
                    var state = this.level().getBlockState(tmp);
                    if (ScaleSensitiveClimbables.REGISTERED_CLIMBABLES.containsKey(state.getBlock())) {
                        var climbable_range = ScaleSensitiveClimbables.REGISTERED_CLIMBABLES.get(state.getBlock()).apply(state);
                        if (climbable_range.minScale() <= scale && climbable_range.maxScale() >= scale) {
                            this.lastClimbablePos = Optional.of(tmp);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @ModifyExpressionValue(method="handleRelativeFrictionAndCalculateMovement",at= @At(value="CONSTANT", args="doubleValue=0.2D"))
    public double variableClimbingSpeed(double original) {
        LivingEntity self = ((LivingEntity)(Object)this);
        var climbing = self.getLastClimbablePos();
        if (climbing.isEmpty()) { return original; }

        BlockState climbingBlock = self.level().getBlockState(climbing.get());
        if (!ScaleSensitiveClimbables.REGISTERED_CLIMBABLES.containsKey(climbingBlock.getBlock())) { return original; }
        var climbability = ScaleSensitiveClimbables.REGISTERED_CLIMBABLES.get(climbingBlock.getBlock()).apply(climbingBlock);

        var actualSpeed = original - DEFAULT_BASE_GRAVITY;  // TODO: Handle entities with other gravities
        return (actualSpeed * climbability.speedModifier()) + DEFAULT_BASE_GRAVITY;
    }
}
