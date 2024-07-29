package net.yuhi.better_progression.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.custom.ThrownDagger;
import net.yuhi.better_progression.model_layer.ModModelLayers;

@OnlyIn(Dist.CLIENT)
public class DaggerRenderer extends EntityRenderer<ThrownDagger> {
    private final DaggerModel model;

    public DaggerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new DaggerModel(pContext.bakeLayer(ModModelLayers.DAGGER_LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownDagger pEntity) {
        return new ResourceLocation(BetterProgression.MOD_ID, "textures/item/" + pEntity.getMaterialType() + "_dagger.png");
    }

    public void render(ThrownDagger pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(this.getTextureLocation(pEntity)), false, pEntity.isFoil());
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}