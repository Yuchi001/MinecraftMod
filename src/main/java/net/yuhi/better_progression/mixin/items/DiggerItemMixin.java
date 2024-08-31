package net.yuhi.better_progression.mixin.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.attribute.ModAttributes;
import net.yuhi.better_progression.item.interfaces.ReachItem;
import net.yuhi.better_progression.mixin.accessor.DiggerItemAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

import static net.yuhi.better_progression.attribute.ModAttributes.ATTACK_REACH_UUID;

@Mixin(DiggerItem.class)
public class DiggerItemMixin {
    @Inject(method = "hurtEnemy", at = @At("HEAD"), cancellable = true)
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        System.out.println(stack.getDamageValue() + " = " + stack.getMaxDamage());
        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            System.out.println("dupa");
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "mineBlock", at = @At("HEAD"), cancellable = true)
    public void mineBlock(ItemStack stack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving, CallbackInfoReturnable<Boolean> cir) {
        System.out.println(stack.getDamageValue() + " = " + stack.getMaxDamage());
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            System.out.println("dupa");
            cir.setReturnValue(false);
        }
    }
    
    @Inject(method = "getDefaultAttributeModifiers", at = @At("HEAD"), cancellable = true)
    public void getAttributeModifiers(EquipmentSlot pSlot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        try {
            Item item = (Item) (Object) this;

            if(pSlot != EquipmentSlot.MAINHAND) {
                cir.setReturnValue(ImmutableMultimap.of());
                return;
            }

            var reachItem = item instanceof ReachItem ? (ReachItem) item : null;
            var hasReach = reachItem != null;
            Multimap<Attribute, AttributeModifier> existingModifiers = ((DiggerItemAccessor)(DiggerItem)item).getDefaultModifiers();
            
            if(!hasReach) {
                cir.setReturnValue(existingModifiers);
                return;
            }

            Multimap<Attribute, AttributeModifier> modifiedModifiers = HashMultimap.create();
            var reach_modifier = new AttributeModifier(ATTACK_REACH_UUID, ModAttributes.ATTACK_REACH_KEY, reachItem.getReach(new ItemStack(item)), AttributeModifier.Operation.ADDITION);
            modifiedModifiers.putAll(existingModifiers);
            modifiedModifiers.put(ModAttributes.ATTACK_REACH.get(), reach_modifier);
            cir.setReturnValue(modifiedModifiers);
        }
        catch (Exception ignore) {}
    }
}
