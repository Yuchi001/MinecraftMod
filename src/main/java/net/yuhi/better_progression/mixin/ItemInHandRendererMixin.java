package net.yuhi.better_progression.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.yuhi.better_progression.item.custom.SpearItem;
import net.yuhi.better_progression.item.interfaces.TwoHandedItem;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    public void renderItem(LivingEntity pEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pSeed, CallbackInfo ci ) {
        if (isThrowingSpear(pEntity, pItemStack) && !pDisplayContext.firstPerson()) {
            pPoseStack.pushPose();
            pPoseStack.translate(0.0, 0.0, 0.0);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
        
        if(!pLeftHand) return;
        
        if(pEntity.getMainHandItem().getItem() instanceof TwoHandedItem) ci.cancel();
    }

    @Inject(method = "renderItem", at = @At("RETURN"))
    public void renderItemPost(LivingEntity pEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pSeed, CallbackInfo ci) {
        if (isThrowingSpear(pEntity, pItemStack) && !pDisplayContext.firstPerson()) {
            pPoseStack.popPose();
        }
    }

    private boolean isThrowingSpear(LivingEntity entity, ItemStack stack) {
        return stack.getItem() instanceof SpearItem && entity.isUsingItem() && entity.getUseItem() == stack;
    }
}