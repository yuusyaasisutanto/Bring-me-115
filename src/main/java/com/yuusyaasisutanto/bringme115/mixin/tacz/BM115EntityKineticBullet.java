package com.yuusyaasisutanto.bringme115.mixin.tacz;

import com.tacz.guns.entity.EntityKineticBullet;
import com.yuusyaasisutanto.bringme115.content.pap.BM115LevelToDamage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityKineticBullet.class)
public class BM115EntityKineticBullet {
/*

*/
    @Unique
    private float bring_Me_115_code$bm115PapDamageModifier = 1.0f;

    @Unique
    public void setBm115PapDamageModifier(int paplvl) {
        this.bring_Me_115_code$bm115PapDamageModifier = BM115LevelToDamage.getPackAPunchedDamage(paplvl);
    }

    @Unique
    public float getBm115PapDamageModifier(ItemStack targetGunItem){
        CompoundTag nbt = targetGunItem.getTag();
        if(nbt != null && nbt.contains("BM115Modify")){
            CompoundTag BM115Modify = nbt.getCompound("BM115Modify");
            return BM115Modify.getInt("PaPLvL");
        }
        // PAPレベル非適用時は0を返す
        return 0;
    }

    @ModifyArg(method = "getDamage", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"), index = 0, remap = false)
    public float apply115InfusedDamage(float original){
        return original * this.bring_Me_115_code$bm115PapDamageModifier;
    }
}
