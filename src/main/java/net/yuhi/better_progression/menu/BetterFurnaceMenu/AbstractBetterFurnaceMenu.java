package net.yuhi.better_progression.menu.BetterFurnaceMenu;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class AbstractBetterFurnaceMenu extends RecipeBookMenu<Container> {
    public static final int INGREDIENT_SLOT_1 = 0;
    public static final int INGREDIENT_SLOT_2 = 1;
    public static final int FUEL_SLOT = 2;
    public static final int RESULT_SLOT = 3;
    public static final int SLOT_COUNT = 4;
    public static final int DATA_COUNT = 4;
    private static final int INV_SLOT_START = 5;
    private static final int INV_SLOT_END = 31;
    private static final int USE_ROW_SLOT_START = 31;
    private static final int USE_ROW_SLOT_END = 40;
    private final Container container;
    private final ContainerData data;
    protected final Level level;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookType recipeBookType;

    protected AbstractBetterFurnaceMenu(MenuType<?> pMenuType, RecipeType<? extends AbstractCookingRecipe> pRecipeType, RecipeBookType pRecipeBookType, int pContainerId, Inventory pPlayerInventory) {
        this(pMenuType, pRecipeType, pRecipeBookType, pContainerId, pPlayerInventory, new SimpleContainer(SLOT_COUNT), new SimpleContainerData(DATA_COUNT));
    }

    protected AbstractBetterFurnaceMenu(MenuType<?> pMenuType, RecipeType<? extends AbstractCookingRecipe> pRecipeType, RecipeBookType pRecipeBookType, int pContainerId, Inventory pPlayerInventory, Container pContainer, ContainerData pData) {
        super(pMenuType, pContainerId);
        this.recipeType = pRecipeType;
        this.recipeBookType = pRecipeBookType;
        checkContainerSize(pContainer, 3);
        checkContainerDataCount(pData, 4);
        this.container = pContainer;
        this.data = pData;
        this.level = pPlayerInventory.player.level;
        this.addSlot(new Slot(pContainer, INGREDIENT_SLOT_1, 44, 15));
        this.addSlot(new Slot(pContainer, INGREDIENT_SLOT_2, 68, 15));
        this.addSlot(new BetterFurnaceFuelSlot(this, pContainer, FUEL_SLOT, 56, 53));
        this.addSlot(new FurnaceResultSlot(pPlayerInventory.player, pContainer, RESULT_SLOT, 116, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlots(pData);
    }

    public void fillCraftSlotsStackedContents(StackedContents pItemHelper) {
        if (this.container instanceof StackedContentsCompatible) {
            ((StackedContentsCompatible)this.container).fillStackedContents(pItemHelper);
        }

    }

    public void clearCraftingContent() {
        this.getSlot(INGREDIENT_SLOT_1).set(ItemStack.EMPTY);
        this.getSlot(INGREDIENT_SLOT_2).set(ItemStack.EMPTY);
        this.getSlot(RESULT_SLOT).set(ItemStack.EMPTY);
    }

    public boolean recipeMatches(Recipe<? super Container> pRecipe) {
        return pRecipe.matches(this.container, this.level);
    }

    public int getResultSlotIndex() {
        return RESULT_SLOT;
    }

    public int getGridWidth() {
        return 1;
    }

    public int getGridHeight() {
        return 1;
    }

    public int getSize() {
        return 3;
    }
    
    public boolean stillValid(Player pPlayer) {
        return this.container.stillValid(pPlayer);
    }

    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == RESULT_SLOT) {
                if (!this.moveItemStackTo(itemstack1, SLOT_COUNT, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex != FUEL_SLOT && pIndex != INGREDIENT_SLOT_1) {
                if (this.canSmelt(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, INGREDIENT_SLOT_1, FUEL_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, FUEL_SLOT, RESULT_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex > RESULT_SLOT && pIndex < INV_SLOT_START) {
                    if (!this.moveItemStackTo(itemstack1, INV_SLOT_END, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= INV_SLOT_END && pIndex < USE_ROW_SLOT_END && !this.moveItemStackTo(itemstack1, SLOT_COUNT, INV_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, SLOT_COUNT, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }

    protected boolean canSmelt(ItemStack pStack) {
        return this.level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>)this.recipeType, new SimpleContainer(pStack), this.level).isPresent();
    }

    protected boolean isFuel(ItemStack pStack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(pStack, this.recipeType) > 0;
    }

    public int getBurnProgress() {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    public int getLitProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    public RecipeBookType getRecipeBookType() {
        return this.recipeBookType;
    }

    public boolean shouldMoveToInventory(int pSlotIndex) {
        return pSlotIndex != FUEL_SLOT;
    }
}
