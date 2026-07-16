package com.example.perfboost.mixin;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    // Chan moi particle duoc spawn qua ParticleEffect (lua, khoi, nuoc, bot khi, block break, v.v.)
    @Inject(
        method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void perfboost$cancelParticleByEffect(ParticleEffect parameters, double x, double y, double z,
                                                   double velocityX, double velocityY, double velocityZ,
                                                   CallbackInfo ci) {
        ci.cancel();
    }

    // Chan luon truong hop them Particle instance truc tiep (mot so hieu ung dac biet)
    @Inject(
        method = "addParticle(Lnet/minecraft/client/particle/Particle;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void perfboost$cancelParticleInstance(Particle particle, CallbackInfo ci) {
        ci.cancel();
    }
}
