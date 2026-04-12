package org.siscode.playablePehkui.building;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.siscode.playablePehkui.PehkuiPlugin;

public class BeegBuildingClient {
    public static @Nullable Vec3 lockedBigGrid = Vec3.ZERO;

    public static AABB getCurrentSelection() {
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) { return null; }
        var scale = Math.round(PehkuiPlugin.WORLD_INTERACTION_SCALE.getScaleData(mc.player).getScale());
        if (scale <= 1) { return null; }

        var hit = mc.hitResult;
        if (!(hit instanceof BlockHitResult blockHit)) { return null; }
        if (mc.level.getBlockState(blockHit.getBlockPos()).isAir()) { return null; }

        if (lockedBigGrid == null) {
            var direction = blockHit.getDirection();
            Vec3i center_of_face;
            if (scale%2 == 0) {  // even
                var loc = blockHit.getLocation();
                center_of_face = new Vec3i((int) Math.round(loc.x), (int) Math.round(loc.y), (int) Math.round(loc.z));
                var center_of_cube = center_of_face.offset(direction.getNormal().multiply(-scale/2));
                return AABB.ofSize(Vec3.atLowerCornerOf(center_of_cube), scale, scale, scale);
            } else {
                var pos = blockHit.getBlockPos();
                var center_of_block = new Vec3i(pos.getX(), pos.getY(), pos.getZ());
                var center_of_cube = Vec3.atCenterOf(center_of_block).add(Vec3.atLowerCornerOf(direction.getNormal().multiply(-scale/2)));
                return AABB.ofSize(center_of_cube, scale, scale, scale);
            }
        } else {
            var pos = blockHit.getLocation();
            var shifted = pos.add(lockedBigGrid.reverse()).add(Vec3.atLowerCornerOf(blockHit.getDirection().getNormal()).scale(-0.5));
            var big_pos = new Vec3(Mth.floor(shifted.x/scale), Mth.floor(shifted.y/scale), Mth.floor(shifted.z/scale));

            Vec3 block_bottom_left = big_pos.multiply(scale, scale, scale).add(lockedBigGrid);
            Vec3 middle_of_cube = block_bottom_left.add(scale/2.0, scale/2.0, scale/2.0);
            return AABB.ofSize(middle_of_cube, scale, scale, scale);
        }
    }
}
