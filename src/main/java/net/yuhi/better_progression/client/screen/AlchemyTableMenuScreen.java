package net.yuhi.better_progression.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.entity.AlchemyTableBlockEntity;
import net.yuhi.better_progression.menu.custom.AlchemyTableMenu;

@OnlyIn(Dist.CLIENT)
public class AlchemyTableMenuScreen extends AbstractContainerScreen<AlchemyTableMenu> {
    private final ResourceLocation texture;

    public AlchemyTableMenuScreen(AlchemyTableMenu menu, Inventory pPlayerInventory, Component pTitle) {
        super(menu, pPlayerInventory, pTitle);
        this.texture = new ResourceLocation(BetterProgression.MOD_ID, "textures/gui/alchemy_table.png");
    }

    @Override
    public void init() {
        super.init();
        //this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShaderTexture(0, this.texture);

        int i = this.leftPos;
        int j = this.topPos;

        blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        renderFuelSlot(pPoseStack);
        renderFuelStatus(pPoseStack);
        renderCookingProgress(pPoseStack);
    }
    
    private void renderFuelStatus(PoseStack pPoseStack) {
        var currentFuel = this.menu.getCurrentFuel();
        if (currentFuel == 0) return;

        int fuelStatusX = 176;
        int fuelStatusY = 19;

        int currentFuelStatus = this.menu.getCurrentFuelStatus();
        int maxFuelStatus = 5 * currentFuel;

        int defaultWidth = 15;
        int dynamicWidth = (int)(defaultWidth * ((float)currentFuelStatus / maxFuelStatus));

        blit(pPoseStack, this.leftPos + 27, this.topPos + 28, fuelStatusX, fuelStatusY, dynamicWidth, 4);
    }

    private void renderCookingProgress(PoseStack pPoseStack) {
        var currentFuel = this.menu.getCurrentFuel();
        if (currentFuel == 0) return;

        int cookingProgressX = 176;
        int cookingProgressY = 23;

        var cookingProgress = this.menu.getCookingProgress();

        int defaultHeight = 54;
        int dynamicHeight = (int) (defaultHeight * ((float)cookingProgress / AlchemyTableBlockEntity.COOKING_TIME));

        int renderY = this.topPos + 16 + (defaultHeight - dynamicHeight);
        int textureY = cookingProgressY + (defaultHeight - dynamicHeight);
        
        blit(pPoseStack, this.leftPos + 47, renderY, cookingProgressX, textureY, 20, dynamicHeight);
    }
    
    private void renderFuelSlot(PoseStack pPoseStack){
        int fuelIconX = 196;
        int fuelIconY = 67;

        fuelIconY = switch (this.menu.getCurrentFuel()) {
            case 1 -> 13;
            case 2 -> 31;
            case 3 -> 49;
            default -> fuelIconY;
        };

        blit(pPoseStack, this.leftPos + 7, this.topPos + 15, fuelIconX, fuelIconY, 18, 18);
    }
}
