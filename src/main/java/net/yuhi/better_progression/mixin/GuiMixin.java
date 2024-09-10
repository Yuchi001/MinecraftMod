package net.yuhi.better_progression.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;

import static org.apache.logging.log4j.core.tools.picocli.CommandLine.Help.Ansi.Style.blink;

@Mixin(Gui.class)
public class GuiMixin {

    /*private void invokeRenderHeart(PoseStack poseStack, Object heartType, int x, int y, int vOffset, boolean renderHighlight, boolean halfHeart) {
        try {
            Method renderHeartMethod = Gui.class.getDeclaredMethod("renderHeart", PoseStack.class, heartType.getClass(), int.class, int.class, int.class, boolean.class, boolean.class);
            renderHeartMethod.setAccessible(true);
            renderHeartMethod.invoke(Minecraft.getInstance().gui, poseStack, heartType, x, y, vOffset, renderHighlight, halfHeart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "renderHearts", at = @At("HEAD"), cancellable = true)
    private void injectRenderHearts(PoseStack pPoseStack, Player pPlayer, int pX, int pY, int pHeight, int pOffsetHeartIndex, float pMaxHealth, int pCurrentHealth, int pDisplayHealth, int pAbsorptionAmount, boolean pRenderHighlight, CallbackInfo ci) {
        int health = (int)Math.ceil(pPlayer.getHealth());
        int maxHealth = (int)Math.ceil(pPlayer.getMaxHealth());
        boolean oddHealth = health % 2 == 1;

        for (int i = 0; i < maxHealth / 2; i++) {
            int xPos = pX + i * 8;
            Object heartType = getHeartType(i == maxHealth / 2 - 1 && oddHealth);

            invokeRenderHeart(pPoseStack, heartType, xPos, pY, 0, pRenderHighlight, oddHealth && i == maxHealth / 2 - 1);
        }

        // Anulujemy oryginalne wywołanie renderowania serc
        ci.cancel();
    }

    private Object getHeartType(boolean halfHeart) {
        try {
            Class<?> heartTypeClass = Class.forName("net.minecraft.client.gui.Gui$HeartType");
            if (halfHeart) {
                return Enum.valueOf((Class<Enum>) heartTypeClass, "HALF");
            } else {
                return Enum.valueOf((Class<Enum>) heartTypeClass, "FULL");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/
}
