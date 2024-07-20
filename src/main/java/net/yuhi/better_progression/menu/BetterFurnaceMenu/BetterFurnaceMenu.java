package net.yuhi.better_progression.menu.BetterFurnaceMenu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.yuhi.better_progression.menu.ModMenus;
import net.yuhi.better_progression.recipe.ModRecipeType;

public class BetterFurnaceMenu extends AbstractBetterFurnaceMenu {
    public BetterFurnaceMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf pBuffer) {
        super(ModMenus.FURNACE.get(), ModRecipeType.BetterSmeltingRecipeType.BETTER_SMELTING, RecipeBookType.FURNACE, pContainerId, pPlayerInventory);
    }

    public BetterFurnaceMenu(int pContainerId, Inventory pPlayerInventory, Container pFurnaceContainer, ContainerData pFurnaceData) {
        super(ModMenus.FURNACE.get(), ModRecipeType.BetterSmeltingRecipeType.BETTER_SMELTING, RecipeBookType.FURNACE, pContainerId, pPlayerInventory, pFurnaceContainer, pFurnaceData);
    }
}
