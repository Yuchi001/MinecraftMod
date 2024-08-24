package net.yuhi.better_progression.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.utils.ItemsUtilsMethods;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class ThrownWeapon extends AbstractArrow {
    private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(ThrownWeapon.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownWeapon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> ID_ITEM = SynchedEntityData.defineId(ThrownWeapon.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> ID_STARTING_ANGLE = SynchedEntityData.defineId(ThrownWeapon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ID_SHOULD_ROTATE = SynchedEntityData.defineId(ThrownWeapon.class, EntityDataSerializers.BOOLEAN);
    
    private float clientSideRotation = 0;
    private boolean counterClockwiseBounce = true;
    public float prevRotationYaw;
    public float rotationYaw;
    private boolean dealtDamage;
    public int clientSideReturnWeaponTickCount;
    private boolean shouldRotate = false;
    private EMaterialType materialType;
    private ItemStack thrownItem = new ItemStack(ItemsUtilsMethods.getItem(EItemCategory.Dagger, EMaterialType.DIAMOND));
    
    public ThrownWeapon(EntityType<? extends ThrownWeapon> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownWeapon(Level pLevel, LivingEntity pShooter, ItemStack pStack, EMaterialType pMaterialType, boolean shouldRotate) {
        super(ModEntityTypes.THROWN_WEAPON.get(), pShooter, pLevel);
        this.thrownItem = pStack.copy();
        this.shouldRotate = shouldRotate;
        this.materialType = pMaterialType;
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(pStack));
        this.entityData.set(ID_FOIL, pStack.hasFoil());
        this.entityData.set(ID_ITEM, pStack.copy());
        this.entityData.set(ID_STARTING_ANGLE, new Random(0).nextInt(100 - 1));
        this.entityData.set(ID_SHOULD_ROTATE, this.shouldRotate);
    }
    
    public EMaterialType getMaterialType() {
        return materialType;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_LOYALTY, (byte)0);
        this.entityData.define(ID_FOIL, false);
        this.entityData.define(ID_ITEM, ItemStack.EMPTY);
        this.entityData.define(ID_STARTING_ANGLE, new Random(0).nextInt(100 - 1));
        this.entityData.define(ID_SHOULD_ROTATE, false);
    }
    
    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        int i = this.entityData.get(ID_LOYALTY);
        if (i > 0 && (this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.level.isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double)i, this.getZ());
                if (this.level.isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * (double)i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnWeaponTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.clientSideReturnWeaponTickCount;
            }
        }

        if (!this.inGround) {
            this.rotationYaw += 50.0F;
            if (this.rotationYaw >= 360.0F) {
                this.rotationYaw -= 360.0F;
            }
        }

        this.prevRotationYaw = this.rotationYaw;

        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        return this.thrownItem.copy();
    }
    
    public ItemStack getItem() {
        return this.entityData.get(ID_ITEM);
    }
    
    public int getStartingAngle() {
        return this.entityData.get(ID_STARTING_ANGLE);
    }

    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    /**
     * Gets the EntityHitResult representing the entity hit
     */
    @Nullable
    @Override
    protected EntityHitResult findHitEntity(@NotNull Vec3 pStartVec, @NotNull Vec3 pEndVec) {
        return this.dealtDamage ? null : super.findHitEntity(pStartVec, pEndVec);
    }
    
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        counterClockwiseBounce = !counterClockwiseBounce;
        
        Entity entity = pResult.getEntity();
        float f = 8.0F;
        if (entity instanceof LivingEntity livingentity) {
            f += EnchantmentHelper.getDamageBonus(this.thrownItem, livingentity.getMobType());
        }
        
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, (Entity)(entity1 == null ? this : entity1));
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
                }
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;

        this.playSound(soundevent, f1, 1.0F);
    }


    @Override
    protected boolean tryPickup(@NotNull Player pPlayer) {
        return super.tryPickup(pPlayer) || this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
    public void playerTouch(@NotNull Player pEntity) {
        if (this.ownedBy(pEntity) || this.getOwner() == null) {
            super.playerTouch(pEntity);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.dealtDamage = pCompound.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, (byte)EnchantmentHelper.getLoyalty(this.thrownItem));
        if (pCompound.contains("ThrowableItem", 10)) {
            this.thrownItem = ItemStack.of(pCompound.getCompound("ThrowableItem"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("DealtDamage", this.dealtDamage);
        if (!this.thrownItem.isEmpty()) {
            pCompound.put("ThrowableItem", this.thrownItem.save(new CompoundTag()));
        }
    }

    @Override
    public void tickDespawn() {
        int i = this.entityData.get(ID_LOYALTY);
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
            super.tickDespawn();
        }

    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRotationAnimation(float partialTicks) {
        if(!this.inGround) {
            clientSideRotation = (this.counterClockwiseBounce? 1:-1)*(this.tickCount + partialTicks)*50F;
        }
        return this.clientSideRotation;
    }
    
    @OnlyIn(Dist.CLIENT)
    public boolean shouldRotate() {
        return this.entityData.get(ID_SHOULD_ROTATE);
    }
}
