package org.siscode.playablePehkui.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.siscode.playablePehkui.platform.facade.PlayablePehkui;
import org.siscode.playablePehkui.util.PehkuiUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import virtuoel.pehkui.api.ScaleTypes;

@Mixin(BlockCollisions.class)
public class BlockCollisionsMixin<T>  {
    @WrapOperation(method = "computeNext", at = @At(value = "INVOKE", target="Lnet/minecraft/world/level/block/state/BlockState;getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    public VoxelShape pphk$leavesHaveNoColissionIfYoureBig(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext, Operation<VoxelShape> original) {
        if (!PlayablePehkui.SERVER_CONFIG.passThroughLeaves) {
            return original.call(blockState, blockGetter, blockPos, collisionContext);
        }
        
        if (!(collisionContext instanceof EntityCollisionContext)) { return original.call(blockState, blockGetter, blockPos, collisionContext); }
        Entity entity = ((EntityCollisionContext) collisionContext).getEntity();
        if (entity == null) { return original.call(blockState, blockGetter, blockPos, collisionContext); }
        double scale = PehkuiUtil.GetEntityScale(entity, ScaleTypes.BASE);
        if (scale >= 2) {
            if (blockState.is(BlockTags.LEAVES)) {
                return Shapes.empty();
            }
        }
        return original.call(blockState, blockGetter, blockPos, collisionContext);
    }
}
