package net.yuhi.better_progression.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.block.custom.IBrewingStand;
import net.yuhi.better_progression.menu.custom.AlchemyTableMenu;
import net.yuhi.better_progression.mixin.BrewingStandBlockMixin;
import net.yuhi.better_progression.mixin.accessor.BrewingStandBlockEntityAccessor;
import net.yuhi.better_progression.recipe.AbstractBetterCookingRecipe;
import net.yuhi.better_progression.recipe.AlchemyTableRecipe;
import net.yuhi.better_progression.recipe.BetterBlastingRecipe;
import net.yuhi.better_progression.recipe.ModRecipeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AlchemyTableBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {
    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private final RecipeType<? extends AlchemyTableRecipe> recipeType;
    private final RecipeManager.CachedCheck<Container, ? extends BetterBlastingRecipe> customRecipeCheck;
    private int currentFuelType = 0;
    private int[] fuelPercentage = new int[3];
    private int cookingProgress = 0;
    
    public AlchemyTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALCHEMY_TABLE.get(), pPos, pBlockState);
        this.recipeType = ModRecipeType.ALCHEMY.get();
        this.customRecipeCheck = RecipeManager.createCheck((RecipeType)(this.recipeType));
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return pSide == Direction.UP ? new int[]{0} : new int[]{1, 2, 3, 4};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return pIndex == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu.better_progression.alchemy_table");
    }

    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> AlchemyTableBlockEntity.this.currentFuelType;
                case 1 -> AlchemyTableBlockEntity.this.currentFuelType == 0 ? 0 : AlchemyTableBlockEntity.this.fuelPercentage[AlchemyTableBlockEntity.this.currentFuelType - 1];
                case 2 -> AlchemyTableBlockEntity.this.cookingProgress;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    AlchemyTableBlockEntity.this.currentFuelType = value;
                    break;
                case 1:
                    var percentageIndex = AlchemyTableBlockEntity.this.currentFuelType;
                    if (percentageIndex == 0) break;
                    AlchemyTableBlockEntity.this.fuelPercentage[percentageIndex - 1] = value;
                    break;
                case 2:
                    AlchemyTableBlockEntity.this.cookingProgress = value;
                    break;
            }
        }

        public int getCount() {
            return 3;
        }
    };
    
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        if (level != null) {
            var pos = new BlockPos(this.worldPosition.getX(), this.worldPosition.getY() + 1, this.worldPosition.getZ());
            setNeighborChanged(this.level.getBlockState(pos), pos);
        }
        return new AlchemyTableMenu(pContainerId, pInventory, this, this.dataAccess);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
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
    public void setItem(int pSlot, ItemStack pStack) {
        if (pSlot >= 0 && pSlot < this.items.size()) {
            this.items.set(pSlot, pStack);
            if (pStack.getCount() > this.getMaxStackSize()) {
                pStack.setCount(this.getMaxStackSize());
            }
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        if (pIndex > 0) return true;

        return switch (this.dataAccess.get(0)) {
            case 1 -> pStack.is(Items.GUNPOWDER);
            case 2 -> pStack.is(Items.BLAZE_POWDER);
            case 3 -> pStack.is(Items.DRAGON_BREATH);
            default -> false;
        };
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

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
        this.currentFuelType = pTag.getInt("CurrentFuel");
        this.fuelPercentage = pTag.getIntArray("FuelPercentage");
        this.cookingProgress = pTag.getInt("CookingProgress");
        this.dataAccess.set(0, this.currentFuelType);
        
        var percentageIndex = currentFuelType;
        this.dataAccess.set(1, percentageIndex == 0 ? 0 : this.fuelPercentage[percentageIndex - 1]);
        this.dataAccess.set(2, this.cookingProgress);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.items);
        pTag.putInt("CurrentFuel", this.currentFuelType);
        pTag.putIntArray("FuelPercentage", this.fuelPercentage);
        pTag.putInt("CookingProgress", this.cookingProgress);
    }
    
    public void setNeighborChanged(BlockState blockState, BlockPos pPos) {
        var fuel = 0;
        if (blockState.getBlock() instanceof IBrewingStand brewingStand) {
            var fuelType = brewingStand.getFuel();
            if (fuelType.is(Items.GUNPOWDER)) fuel = 1;
            if (fuelType.is(Items.BLAZE_POWDER)) fuel = 2;
            if (fuelType.is(Items.DRAGON_BREATH)) fuel = 3;
        }
        dataAccess.set(0, fuel);
        
        if (items.get(0).isEmpty() || this.level == null || canPlaceItem(0, items.get(0))) return;
        
        var item = items.get(0);
        this.level.addFreshEntity(new ItemEntity(this.level, pPos.getX() + 0.5f, pPos.getY() + 0.5f, pPos.getZ() + 0.5f, item.copy()));
        item.setCount(0);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AlchemyTableBlockEntity entity) { }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlchemyTableBlockEntity entity) {
        if (entity.level == null) return;
        
        var fuelSlotItemStack = entity.items.get(0);
        var currentFuelPercentage = entity.dataAccess.get(1);
        if (currentFuelPercentage <= 0 && !fuelSlotItemStack.isEmpty()) {
            var maxFuelValue = 5 * entity.dataAccess.get(0);
            entity.dataAccess.set(1, maxFuelValue);
            fuelSlotItemStack.shrink(1);
            setChanged(level, pos, state);
        }
        
        if (currentFuelPercentage <= 0) return;
        
        var emptySlots = 0;
        for(var slot : entity.items) if (!slot.isEmpty()) emptySlots++;
        if (emptySlots == 4) return;
        
        var recipe = entity.customRecipeCheck.getRecipeFor(entity, level).orElse(null);
        if (recipe == null) return;

        var canUseRecipe = canUseInRecipe(level.registryAccess(), recipe, entity);
        if (!canUseRecipe) return;
        
        ++entity.cookingProgress;
        if (entity.cookingProgress >= 100) {
            entity.cookingProgress = 0;
            cookPotions(entity, level, recipe, entity.level.registryAccess());
            level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }
    
    private static void cookPotions(AlchemyTableBlockEntity entity, Level level, @javax.annotation.Nullable Recipe<?> recipe, RegistryAccess access) {
        var brewingStand = GetBrewingStand(level, entity.getBlockPos());
        if (brewingStand == null) return;
        
        var result = ((Recipe<WorldlyContainer>)recipe).assemble(entity, access);
        
        var accessor = (BrewingStandBlockEntityAccessor)brewingStand;
        var items = accessor.getItems();

        Map<Integer, ItemStack> resultMap = new HashMap<>();
        resultMap.put(2, ItemStack.EMPTY);
        resultMap.put(3, ItemStack.EMPTY);
        resultMap.put(4, ItemStack.EMPTY);
        
        for (var i = 0; i < 3; ++i) {
            if (items.get(i).isEmpty()) continue;
            
            resultMap.put(i,  result.copy());
        }
        
        List<ItemStack> resultList = new ArrayList<>(items);
        for(var key : resultMap.keySet()) {
            resultList.set(key, resultMap.get(key));
        }
        NonNullList<ItemStack> nonNullList = NonNullList.create();
        nonNullList.addAll(resultList);
        
        accessor.setItems(nonNullList);
        
        for (var item : entity.items) {
            item.shrink(1);
        }

        var brewingStandAccessor = (BrewingStandBlockEntityAccessor)brewingStand;
        boolean[] aboolean = getPotionBits(entity.items);
        
        brewingStandAccessor.setLastPotionCount(aboolean);
        BlockState blockstate = brewingStand.getBlockState();

        for(int i = 0; i < BrewingStandBlock.HAS_BOTTLE.length; ++i) {
            blockstate = blockstate.setValue(BrewingStandBlock.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
        }

        level.setBlock(brewingStand.getBlockPos(), blockstate, 2);
    }

    private static boolean[] getPotionBits(NonNullList<ItemStack> items){
        boolean[] aboolean = new boolean[3];

        for(int i = 0; i < 3; ++i) {
            if (!items.get(i).isEmpty()) {
                aboolean[i] = true;
            }
        }

        return aboolean;
    }

    private static boolean canUseInRecipe(RegistryAccess pRegistryAccess, @javax.annotation.Nullable Recipe<?> pRecipe, AlchemyTableBlockEntity entity) {
        var itemstack = ((Recipe<WorldlyContainer>) pRecipe).assemble(entity, pRegistryAccess);
        if (itemstack.isEmpty()) return false;
        
        var brewingStand = GetBrewingStand(entity.level, entity.getBlockPos());
        if (brewingStand == null) return false;
        
        var accessor = (BrewingStandBlockEntityAccessor)brewingStand;
        var items = accessor.getItems();
        
        var bottles = 0;
        for (var i = 0; i < 3; i++) {
            var item = items.get(i);
            if (!item.isEmpty()) continue;
            if (!item.is(Items.POTION)) continue;
            
            if (PotionUtils.getPotion(item) != Potions.WATER) return false;
            bottles++;
        }
        
        return bottles > 0;
    }
    
    private static BrewingStandBlockEntity GetBrewingStand(Level level, BlockPos blockPos){
        if (level == null) return null;
        
        var brewingStandPos = new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
        var blockEntity = level.getBlockEntity(brewingStandPos);
        if (blockEntity instanceof BrewingStandBlockEntity entity) return entity;
        return null;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
