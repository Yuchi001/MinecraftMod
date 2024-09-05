package net.yuhi.better_progression.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.entity.custom.ThrownWeapon;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class ThrownWeaponRenderer extends EntityRenderer<ThrownWeapon> {
    private final ItemRenderer itemRenderer;
    private final ItemModelShaper itemModelShaper;
    
    private Quaternionf rotation = null;

    public ThrownWeaponRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        itemRenderer = pContext.getItemRenderer();
        itemModelShaper = pContext.getItemRenderer().getItemModelShaper();
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownWeapon pEntity) {
        return new ResourceLocation(BetterProgression.MOD_ID, "textures/item/" + pEntity.getMaterialType() + "_dagger.png");
    }

    @Override
    public void render(ThrownWeapon pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        var pItemStack = pEntity.getItem();
        if (pItemStack.isEmpty()) return;
        
        pPoseStack.pushPose();

        if (pEntity.shouldRotate()) {
            float degrees = pEntity.getRotationAnimation(pPartialTicks);
            if(rotation == null) rotation = this.entityRenderDispatcher.cameraOrientation();
            
            pPoseStack.translate(0.F, 0.15F, 0.0F);
            
            float yaw = Mth.lerp(pEntity.tickCount, pEntity.yRotO, pEntity.getYRot());
            float pitch = Mth.lerp(pEntity.tickCount, pEntity.xRotO, pEntity.getXRot());

            pPoseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90F));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(-pitch - degrees - pEntity.getStartingAngle()));
        } else {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) - 135.0F));
        }

        var scale = pEntity.getScale();
        pPoseStack.scale(scale, scale, 0.88F);

        BakedModel bakedmodel = this.itemModelShaper.getItemModel(pItemStack);
        this.itemRenderer.render(pItemStack, ItemDisplayContext.FIXED, false, pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, bakedmodel);

        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}