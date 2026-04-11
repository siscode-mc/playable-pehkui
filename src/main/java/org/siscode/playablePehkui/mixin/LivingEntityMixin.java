package org.siscode.playablePehkui.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.siscode.playablePehkui.config.ServerConfig;
import org.siscode.playablePehkui.movement.ScaleSensitiveClimbables;
import org.siscode.playablePehkui.platform.facade.PlayablePehkui;
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
        AABB corrected_bbox = new AABB(
                bb.minX - bb.getXsize()/2, bb.minY, bb.minZ-bb.getZsize()/2,
                bb.maxX + bb.getXsize()/2, bb.minY+bb.getYsize()/2, bb.maxZ+bb.getZsize()/2
                //                             ^^^^^^
                // Only check for collisions within the "feet" area
        );
        BlockCollisions<BlockPos> collisionChecker = new BlockCollisions<>(
                this.level(), null /* it will infer the wrong bbox */, corrected_bbox,
                false, (pos, _voxelshape) -> pos
        );

        while (collisionChecker.hasNext()) {
            BlockPos pos = collisionChecker.next();
            var state = this.level().getBlockState(pos);
            if (ScaleSensitiveClimbables.REGISTERED_CLIMBABLES.containsKey(state.getBlock())) {
                var climbable_range = ScaleSensitiveClimbables.REGISTERED_CLIMBABLES.get(state.getBlock()).apply(state);
                if (climbable_range.minScale() <= scale && climbable_range.maxScale() >= scale) {
                    this.lastClimbablePos = Optional.of(pos);
                    return true;
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
        var climbingSpeed = (actualSpeed * climbability.speedModifier()) + DEFAULT_BASE_GRAVITY;
        var allowJumpAtStartOfClimb = SERVER_CONFIG.allowJumpAtStartOfClimb;
        if (allowJumpAtStartOfClimb) {
            return Math.max(this.getDeltaMovement().y, climbingSpeed);
        } else {
            return climbingSpeed;
        }
    }

    @WrapMethod(method="Lnet/minecraft/world/entity/LivingEntity;handleOnClimbable(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;")
    private Vec3 defaultClimbableHandlingIsDumbActually(Vec3 original_velocity, Operation<Vec3> original) {
        double scale = ScaleTypes.BASE.getScaleData(this).getScale();
        boolean useBetterClimbingEverywhere = SERVER_CONFIG.useBetterClimbingCheckEverywhere;
        if (scale < 1 || useBetterClimbingEverywhere) {
            if (this.onGround()) { return original_velocity; }
        }
        return original.call(original_velocity);
    }
}
