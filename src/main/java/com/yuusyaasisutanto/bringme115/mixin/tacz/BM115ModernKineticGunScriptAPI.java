package com.yuusyaasisutanto.bringme115.mixin.tacz;

import com.tacz.guns.api.util.LuaNbtAccessor;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ModernKineticGunScriptAPI.class)
public abstract class BM115ModernKineticGunScriptAPI {
/*    @Shadow public abstract LuaNbtAccessor getNbt();

    //次回はここの「銃弾スポーン時に倍率を設定させる」ところから
    @ModifyArg(method = "lambda$shootOnce$2",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), remap = false)
    public Entity setBM115DamageModifiersToEntity(Entity original){
            original.setB
        return original;

    }
*/
}
