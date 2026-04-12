package org.siscode.playablePehkui.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.siscode.playablePehkui.building.BeegBuildingClient;

import java.util.ArrayList;

public class BoxRenderer {
    public static ArrayList<AABB> boxesToRender = new ArrayList<>();

    public static void renderBoxes(PoseStack poseStack, Camera camera) {
        for (var box : boxesToRender) {
            renderBox(box, poseStack, camera);
        }
//        var block = debug_getBigBox();
//        if (block != null) {
//            renderBox(block, poseStack, camera);
//        }
        boxesToRender.clear();
    }

    private static void renderBox(AABB box, PoseStack poseStack, Camera camera) {
        Minecraft mc = Minecraft.getInstance();

        Vec3 cameraPos = camera.getPosition();
        AABB cameraspaceBox = box.move(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        poseStack.pushPose();

        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(2.0f);

        VertexConsumer lines = mc.renderBuffers().bufferSource().getBuffer(RenderType.LINES);

        LevelRenderer.renderLineBox(
                poseStack,
                lines,
                cameraspaceBox,
                1f, 1f, 1f, 1f
        );

        mc.renderBuffers().bufferSource().endBatch(RenderType.LINES);

        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);

        poseStack.popPose();
    }

    private static AABB debug_getBlockBox() {
        Minecraft mc = Minecraft.getInstance();

        var hit = mc.hitResult;
        if (!(hit instanceof BlockHitResult)) { return null; }
        var block = (BlockHitResult) hit;

        return AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(block.getBlockPos()));
    }

    private static AABB debug_getBigBox() {
        return BeegBuildingClient.getCurrentSelection();
    }
}
