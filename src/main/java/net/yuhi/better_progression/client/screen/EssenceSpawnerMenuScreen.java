package net.yuhi.better_progression.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.entity.EssenceSpawnerBlockEntity;
import net.yuhi.better_progression.menu.custom.EssenceSpawnerMenu;

public class EssenceSpawnerMenuScreen extends AbstractContainerScreen<EssenceSpawnerMenu> {
    private final ResourceLocation texture;

    public EssenceSpawnerMenuScreen(EssenceSpawnerMenu menu, Inventory pPlayerInventory, Component pTitle) {
        super(menu, pPlayerInventory, pTitle);
        this.texture = new ResourceLocation(BetterProgression.MOD_ID, "textures/gui/essence_spawner.png");
    }

    @Override
    public void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShaderTexture(0, this.texture);
        
        int i = this.leftPos;
        int j = this.topPos;

        blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        float spawnerCharge = (float)this.menu.getSpawnerCharge() / (float)EssenceSpawnerBlockEntity.MAX_CHARGES;

        int maxBarWidth = 89;
        int barWidth = (int) (spawnerCharge * maxBarWidth);
        blit(pPoseStack, i + 42, j + 52, 0, 166, barWidth, 4);
    }
}