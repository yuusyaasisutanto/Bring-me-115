package com.yuusyaasisutanto.bringme115.content.testbomb;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BM115TestItemBombCapability implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<BM115TestItemBombCapability> TOKEN = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<BM115TestItemBombCapability> capability = LazyOptional.of(() -> this);
    private ResourceKey<Level> dimension = null;
    private BlockPos pos = null;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        /*
         if文の代替、三項演算子。以下のif文と同じ動作をする
        if (cap == TOKEN) {
            return this.capability.cast();
        } else {
            return LazyOptional.empty();
        }
         */
        return cap == TOKEN ? this.capability.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (this.dimension != null && this.pos != null){
            tag.putString("Dimension", this.dimension.location().toString());
            tag.putLong("Pos", this.pos.asLong());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Dimension") && nbt.contains("Pos")){
            this.dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(nbt.getString("Dimension")));
            this.pos = BlockPos.of(nbt.getLong("Pos"));
        } else {
            this.dimension = null;
            this.pos = null;
        }

    }
}
