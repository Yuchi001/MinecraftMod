package net.yuhi.better_progression.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.custom.ThrownDagger;
import net.yuhi.better_progression.model_layer.ModModelLayers;

@OnlyIn(Dist.CLIENT)
public class DaggerRenderer extends ItemEntityRenderer {
    private final ItemRenderer itemRenderer;

    public DaggerRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ItemEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        var daggerEntity = (ThrownDagger)pEntity;
        ItemStack itemStack = daggerEntity.getPickupItem();
        pMatrixStack.pushPose();
        // Apply transformations and rendering logic here
        itemRenderer.renderStatic(itemStack, , pPackedLight, OverlayTexture.NO_OVERLAY, matrixStack, bufferSource, entity.getLevel(), 0);
        pMatrixStack.popPose();
    }
}