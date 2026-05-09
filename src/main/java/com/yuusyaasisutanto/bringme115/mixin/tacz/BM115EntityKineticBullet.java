package com.yuusyaasisutanto.bringme115.mixin.tacz;

import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.resource.pojo.data.gun.BulletData;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.yuusyaasisutanto.bringme115.content.pap.BM115PaPLevelToDamage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityKineticBullet.class)
public abstract class BM115EntityKineticBullet {


    /*

*/
    @Unique
    private float bring_Me_115_code$bm115PapDamageModifier = 1.0f;

    @Unique
    public int getBm115PapDamageModifier(ItemStack targetGunItem){
        CompoundTag nbt = targetGunItem.getTag();
        if(nbt != null && nbt.contains("BM115Modify")){
            CompoundTag BM115Modify = nbt.getCompound("BM115Modify");
            return BM115Modify.getInt("PaPlvl");
        }
        // PAPレベル非適用時は0を返す
        return 0;
    }

    @Inject(
            method = "Lcom/tacz/guns/entity/EntityKineticBullet;<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;ZLcom/tacz/guns/resource/pojo/data/gun/GunData;Lcom/tacz/guns/resource/pojo/data/gun/BulletData;)V",
            at = @At(value = "TAIL"), remap = false)
    public void setBm115PapLevelToEntityKineticBullet(EntityType type, Level worldIn, LivingEntity throwerIn, ItemStack gunItem, ResourceLocation ammoId, ResourceLocation gunId, ResourceLocation gunDisplayId, boolean isTracerAmmo, GunData gunData, BulletData bulletData, CallbackInfo ci){
        this.bring_Me_115_code$bm115PapDamageModifier = BM115PaPLevelToDamage.getPackAPunchedDamage(getBm115PapDamageModifier(gunItem));
        //                bring_Me_115_code$bm115PapDamageModifier = setBm115PapDamageModifier(getBm115PapDamageModifier(gunItem));
    }


    // 1.1.8においてgetDamageメソッドにてMath.maxは2回呼びだされる為、結果として想定してた値を2乗した数値が倍数として入ってしまう
    // ordinalでReturnに使われる方を指定、成功
    @ModifyArg(method = "getDamage", at = @At(value = "INVOKE", ordinal = 1, target = "Ljava/lang/Math;max(FF)F"), index = 0, remap = false)
    public float apply115InfusedDamage(float original){
    //    final Logger logger = (Logger) LogManager.getLogger();
    //    logger.info("PaP multiplied! - bringme115 log");

        return original * this.bring_Me_115_code$bm115PapDamageModifier;
    }
}
