package se.itssimple.echolocation;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class SoundWave {
    public final Vec3 position;
    public float currentRadius;
    public final float maxRadius;
    public int lifetime;
    public final Vector4f color;

    public SoundWave(Vec3 position, float maxRadius, int lifetime, Vector4f color) {
        this.position = position;
        this.currentRadius = 0;
        this.maxRadius = maxRadius;
        this.lifetime = lifetime;
        this.color = color;
    }
}
