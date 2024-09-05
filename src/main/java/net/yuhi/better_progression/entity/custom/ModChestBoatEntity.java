package net.yuhi.better_progression.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.yuhi.better_progression.entity.ModEntityTypes;
import net.yuhi.better_progression.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class ModChestBoatEntity extends ChestBoat {
    public ModChestBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public ModChestBoatEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntityTypes.MOD_CHEST_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public @NotNull Item getDropItem() {
        return switch (getModVariant()){
            case END_OAK -> ModItems.END_OAK_CHEST_BOAT.get();
        };
    }

    public void setVariant(ModBoatEntity.Type pVariant) {
        this.entityData.set(ModBoatEntity.DATA_ID_TYPE, pVariant.ordinal());
    }

    public ModBoatEntity.Type getModVariant() {
        return ModBoatEntity.Type.byId(this.entityData.get(ModBoatEntity.DATA_ID_TYPE));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ModBoatEntity.DATA_ID_TYPE, ModBoatEntity.Type.END_OAK.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if(pCompound.contains("Type", 8)) {
            this.setVariant(ModBoatEntity.Type.byName(pCompound.getString("Type")));
        }
    }
}
