package net.yuhi.better_progression.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.menu.BetterFurnaceMenu.AbstractBetterFurnaceMenu;
import net.yuhi.better_progression.recipe.BetterSmeltingRecipeBookComponent;

public class BetterFurnaceMenuScreen extends AbstractBetterFurnaceScreen<AbstractBetterFurnaceMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterProgression.MOD_ID, "textures/gui/better_furnace.png");

    public BetterFurnaceMenuScreen(AbstractBetterFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new BetterSmeltingRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE);
    }
}
