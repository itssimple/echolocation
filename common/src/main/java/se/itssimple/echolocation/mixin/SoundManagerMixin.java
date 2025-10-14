package se.itssimple.echolocation.mixin;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.itssimple.echolocation.SoundWave;
import se.itssimple.echolocation.SoundWaveManager;

@Mixin(SoundManager.class)
public class SoundManagerMixin {

    @Inject(method = "play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V", at = @At("HEAD"))
    private void onPlaySound(SoundInstance sound, CallbackInfo ci) {
        if (sound == null || sound.isRelative()) return;

        Vec3 position = new Vec3(sound.getX(), sound.getY(), sound.getZ());

        float volume = (sound.getSound() != null ? sound.getVolume() : 2.0f);

        float radius = 1.0f * volume;
        int lifetime = 60;

        Vector4f color = new Vector4f(0.3f, 0.8f, 1.0f, 0.5f);

        SoundWave wave = new SoundWave(position, radius, lifetime, color);
        SoundWaveManager.addWave(wave);
    }
}
