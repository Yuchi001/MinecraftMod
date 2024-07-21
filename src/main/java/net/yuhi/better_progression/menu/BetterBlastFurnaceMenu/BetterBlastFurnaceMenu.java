package net.yuhi.better_progression.menu.BetterBlastFurnaceMenu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeBookType;
import net.yuhi.better_progression.menu.BetterFurnaceMenu.AbstractBetterFurnaceMenu;
import net.yuhi.better_progression.menu.ModMenus;
import net.yuhi.better_progression.recipe.ModRecipeType;

public class BetterBlastFurnaceMenu extends AbstractBetterFurnaceMenu {
    public BetterBlastFurnaceMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf pBuffer) {
        super(ModMenus.BLAST_FURNACE.get(), ModRecipeType.BetterBlastingRecipeType.BETTER_BLASTING, RecipeBookType.BLAST_FURNACE, pContainerId, pPlayerInventory);
    }

    public BetterBlastFurnaceMenu(int pContainerId, Inventory pPlayerInventory, Container pFurnaceContainer, ContainerData pFurnaceData) {
        super(ModMenus.BLAST_FURNACE.get(), ModRecipeType.BetterBlastingRecipeType.BETTER_BLASTING, RecipeBookType.BLAST_FURNACE, pContainerId, pPlayerInventory, pFurnaceContainer, pFurnaceData);
    }
}
