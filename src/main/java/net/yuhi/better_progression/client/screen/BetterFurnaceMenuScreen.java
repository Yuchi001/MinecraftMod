package net.yuhi.better_progression.client.screen;

import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.menu.BetterFurnaceMenu.BetterFurnaceMenu;

public class BetterFurnaceMenuScreen extends AbstractBetterFurnaceScreen<BetterFurnaceMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterProgression.MOD_ID, "textures/gui/better_furnace.png");

    public BetterFurnaceMenuScreen(BetterFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new SmeltingRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE);
    }
}
