package net.yuhi.better_progression.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.item.custom.MobEssenceItem;
import net.yuhi.better_progression.menu.custom.EssenceSpawnerMenu;
import net.yuhi.better_progression.mixin.accessor.BaseSpawnerAccessor;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EssenceSpawnerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {
    private int spawnerCharges = 0;
    private boolean hasMob = false;
    public final static int MAX_CHARGES = 3600;
    public final static int ESSENCE_CHARGE_VALUE = 360;
    private float getChargeProgress() {
        return (float)spawnerCharges / (float)ESSENCE_CHARGE_VALUE;
    }
    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    
    private final EssenceSpawner spawner = new EssenceSpawner();

    public EssenceSpawnerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ESSENCE_SPAWNER.get(), pPos, pBlockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu.better_progression.essence_spawner");
    }

    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int ignored) {
            return EssenceSpawnerBlockEntity.this.spawnerCharges;
        }

        public void set(int ignored, int value) {
            EssenceSpawnerBlockEntity.this.spawnerCharges = value;
        }

        public int getCount() {
            return 1;
        }
    };


    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new EssenceSpawnerMenu(pContainerId, pInventory, this, this.dataAccess);
    }
    
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.spawner.load(this.level, this.worldPosition, pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
        this.spawnerCharges = pTag.getInt("Charge");
        this.hasMob = pTag.getBoolean("HasMob");
        this.dataAccess.set(0, this.spawnerCharges);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        this.spawner.save(pTag);
        pTag.putInt("Charge", this.spawnerCharges);
        pTag.putBoolean("HasMob", this.hasMob);
        ContainerHelper.saveAllItems(pTag, this.items);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }
    
    public boolean hasMob() {
        return hasMob;
    }

    public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, EssenceSpawnerBlockEntity pBlockEntity) {
        pBlockEntity.spawner.clientTick(pLevel, pPos, pBlockEntity);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, EssenceSpawnerBlockEntity pBlockEntity) {
        if (pBlockEntity.spawnerCharges > 0) {
            pBlockEntity.spawner.serverTick((ServerLevel) pLevel, pPos);
            pBlockEntity.spawnerCharges -= 1;
            pBlockEntity.dataAccess.set(0, pBlockEntity.spawnerCharges);
            pLevel.sendBlockUpdated(pPos, pState, pState, 3);
        }
        pBlockEntity.loadChargeFromItems();
    }

    private void loadChargeFromItems() {
        for (int i = 0; i < this.items.size(); i++) {
            ItemStack stack = this.items.get(i);

            if (!stack.isEmpty() && stack.getItem() instanceof MobEssenceItem essenceItem) {
                int chargeValue = essenceItem.getStacks();
                int chargeToAdd = chargeValue * ESSENCE_CHARGE_VALUE;

                if (this.spawnerCharges + chargeToAdd <= MAX_CHARGES) {
                    int stacks = 0;
                    for (var j = 0; j < stack.getCount(); j++) {
                        if (this.spawnerCharges + chargeToAdd <= MAX_CHARGES) {
                            this.spawnerCharges += chargeToAdd;
                            stacks += 1;
                        }
                        else break;
                    }
                    this.dataAccess.set(0, spawnerCharges);
                    this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
                    stack.shrink(stacks);
                    break;
                } else if (this.spawnerCharges < MAX_CHARGES) {
                    int rest = MAX_CHARGES - this.spawnerCharges;
                    if (rest >= chargeToAdd) {
                        this.spawnerCharges = MAX_CHARGES;
                        this.dataAccess.set(0, spawnerCharges);
                        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
                        stack.shrink(1);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = this.saveWithoutMetadata();
        compoundtag.remove("SpawnPotentials");
        return compoundtag;
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        return this.spawner.onEventTriggered(this.level, pId) ? true : super.triggerEvent(pId, pType);
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public void setEntityId(EntityType<?> pType, RandomSource pRandom) {
        this.hasMob = true;
        this.spawner.setEntityId(pType, this.level, pRandom, this.worldPosition);
    }
    
    public BaseSpawner getSpawner() {
        return this.spawner;
    }

    public int @NotNull [] getSlotsForFace(Direction pSide) {
        return new int[]{ 0,1,2,3,4 };
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    @Override
    public @NotNull ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        if (pIndex >= 0 && pIndex < this.items.size()) {
            this.items.set(pIndex, pStack);
            if (pStack.getCount() > this.getMaxStackSize()) {
                pStack.setCount(this.getMaxStackSize());
            }
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return hasMob && Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return pStack.is(ModTags.Items.ESSENCE_ITEM);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
    
    @Override
    public void fillStackedContents(StackedContents pHelper) {
        for(ItemStack itemstack : this.items) {
            pHelper.accountStack(itemstack);
        }
    }

    public class EssenceSpawner extends BaseSpawner {
        public void broadcastEvent(Level pLevel, @NotNull BlockPos pPos, int p_155769_) {
            pLevel.blockEvent(pPos, Blocks.SPAWNER, p_155769_, 0);
        }

        public void setNextSpawnData(@Nullable Level p_155771_, BlockPos p_155772_, SpawnData p_155773_) {
            super.setNextSpawnData(p_155771_, p_155772_, p_155773_);
            if (p_155771_ != null) {
                BlockState blockstate = p_155771_.getBlockState(p_155772_);
                p_155771_.sendBlockUpdated(p_155772_, blockstate, blockstate, 4);
            }
        }

        public void clientTick(Level pLevel, BlockPos pPos, EssenceSpawnerBlockEntity pBlockEntity) {
            if (pBlockEntity.spawnerCharges > 0) clientTick(pLevel, pPos);
            else {
                var accessor = (BaseSpawnerAccessor)(Object)this;
                accessor.setOSpin(accessor.getSpin());
            }
        }

        public net.minecraft.world.level.block.entity.@NotNull BlockEntity getSpawnerBlockEntity(){ return EssenceSpawnerBlockEntity.this; }
    }
}
