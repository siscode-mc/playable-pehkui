package org.siscode.playablePehkui.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.siscode.playablePehkui.mixininterface.Slowable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block {
    public LeavesBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(@NotNull BlockState thisstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
        double BASE_POWER = 1.0;
        AABB intersection = entity.getBoundingBox().intersect(AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos)));
        double volume = intersection.getXsize() * intersection.getYsize() * intersection.getZsize();
        ((Slowable)entity).ppkh$addSlowdownContribution((float) (BASE_POWER * volume));
    }
}
