package se.itssimple.echolocation;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.world.phys.Vec3;

public class ModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModCommon.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            SoundWaveManager.tick();
        });

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            var poseStack = context.matrixStack();

            Vec3 camera = context.camera().getPosition();
            poseStack.pushPose();
            poseStack.translate(-camera.x, -camera.y, -camera.z);

            SoundWaveManager.render(poseStack);

            poseStack.popPose();
        });
    }
}