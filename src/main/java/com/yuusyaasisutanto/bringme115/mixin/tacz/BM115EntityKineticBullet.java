package com.yuusyaasisutanto.bringme115.mixin.tacz;

import com.tacz.guns.entity.EntityKineticBullet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityKineticBullet.class)
public class BM115EntityKineticBullet {

    @ModifyArg(method = "getDamage", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 0, remap = false)
    public float apply115InfusedDamage(float original){
        return original * 100F;
    }
}
