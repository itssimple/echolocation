package se.itssimple.echolocation;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import se.itssimple.echolocation.data.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SoundWaveManager {
    private static final List<SoundWave> activeWaves = new CopyOnWriteArrayList<>();

    public static void addWave(SoundWave wave)
    {
        Constants.LOG.info("Wave at {} with color {} added", wave.position, wave.color);
        activeWaves.add(wave);
    }

    public static void tick() {
        List<SoundWave> toRemove = new ArrayList<>();
        for(SoundWave wave : activeWaves)
        {
            wave.lifetime--;
            wave.currentRadius = wave.maxRadius * (1.0f - (float)wave.lifetime / 60.0f);

            if(wave.lifetime <= 0) {
                toRemove.add(wave);
            }
        }

        if(!toRemove.isEmpty()) {
            activeWaves.removeAll(toRemove);
        }
    }

    public static void render(PoseStack poseStack) {
        if(activeWaves.isEmpty()) return;

        Tesselator tesselator = Tesselator.getInstance();
        var bufferBuilder = tesselator.getBuilder();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.enableDepthTest();

        RenderSystem.enablePolygonOffset();
        RenderSystem.polygonOffset(-1.0f, -1.0f);

        for(SoundWave wave : activeWaves)
        {
            poseStack.pushPose();
            poseStack.translate(wave.position.x(), wave.position.y() + 0.05f, wave.position.z());
            Matrix4f matrix = poseStack.last().pose();

            drawCircle(bufferBuilder, matrix, wave);

            poseStack.popPose();
        }

        RenderSystem.disablePolygonOffset();
        RenderSystem.polygonOffset(0f,0f);
        RenderSystem.disableBlend();
    }

    private static void drawCircle(BufferBuilder bufferBuilder, Matrix4f matrix, SoundWave wave) {
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

        float alpha = Math.min(1.0f, wave.lifetime / 20.0f) * wave.color.w();

        int segments = 20;
        for(int i = 0; i <= segments; i++) {
            double angle = (i / (double)segments) * 2.0 * Math.PI;
            float xO = (float)(Math.cos(angle) * wave.currentRadius);
            float zO = (float)(Math.sin(angle) * wave.currentRadius);

            float thickness = wave.maxRadius * 0.05f;
            float xI = (float)(Math.cos(angle) * (wave.currentRadius - thickness));
            float zI = (float)(Math.sin(angle) * (wave.currentRadius - thickness));

            bufferBuilder.vertex(matrix, xO, 0, zO).color(wave.color.x(), wave.color.y(), wave.color.z(), alpha).endVertex();
            bufferBuilder.vertex(matrix, xI, 0, zI).color(wave.color.x(), wave.color.y(), wave.color.z(), alpha).endVertex();
        }

        Tesselator.getInstance().end();
    }
}
