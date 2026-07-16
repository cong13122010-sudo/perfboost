package com.example.perfboost.mixin;

import com.example.perfboost.PerfBoostConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobAiThrottleMixin {

    @Unique
    private int perfboost$tickCounter = 0;

    // Chi chan tickMovement (AI: goal selector, pathfinding, look control...)
    // KHONG chan Entity#tick / LivingEntity#tick nen vat ly (roi, va cham) van hoat dong binh thuong.
    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void perfboost$throttleAiTick(CallbackInfo ci) {
        MobEntity self = (MobEntity) (Object) this;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (self.getWorld() != client.player.getWorld()) return;

        double distSq = self.squaredDistanceTo(client.player);
        boolean inView = perfboost$isRoughlyInView(self, client);
        int interval = PerfBoostConfig.calculateTickInterval(distSq, inView);

        if (interval <= 1) return;

        perfboost$tickCounter++;
        if (perfboost$tickCounter % interval != 0) {
            ci.cancel();
        }
    }

    @Unique
    private boolean perfboost$isRoughlyInView(MobEntity entity, MinecraftClient client) {
        Vec3d forward = client.player.getRotationVec(1.0F);
        Vec3d toEntity = entity.getPos().subtract(client.player.getEyePos());
        double lengthSq = toEntity.lengthSquared();
        if (lengthSq < 1.0E-4) return true;
        Vec3d dir = toEntity.normalize();
        double dot = forward.dotProduct(dir);
        return dot >= PerfBoostConfig.FOV_DOT_THRESHOLD;
    }
}
