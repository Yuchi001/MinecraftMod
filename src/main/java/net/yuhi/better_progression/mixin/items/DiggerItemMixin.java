package net.yuhi.better_progression.mixin.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.yuhi.better_progression.attribute.ModAttributes;
import net.yuhi.better_progression.enchantment.ModEnchantments;
import net.yuhi.better_progression.item.interfaces.ReachItem;
import net.yuhi.better_progression.mixin.accessor.DiggerItemAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

import static net.yuhi.better_progression.attribute.ModAttributes.ATTACK_REACH_UUID;

@Mixin(DiggerItem.class)
public class DiggerItemMixin {

    @Inject(method = "getDefaultAttributeModifiers", at = @At("HEAD"), cancellable = true)
    public void getAttributeModifiers(EquipmentSlot pSlot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        try {
            var item = (Item) (Object) this;

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

    @Inject(method = "mineBlock", at = @At("HEAD"), cancellable = true)
    public void mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) pEntityLiving;

        if (!pLevel.isClientSide && pStack.getItem() instanceof DiggerItem && EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.HAMMER.get(), pStack) > 0) {
            
            var diggerItem = (DiggerItem) pStack.getItem();
            int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.HAMMER.get(), pStack);
            int depth = Math.min(level, 3);

            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            var droppedBlock = false;
            for (int z = 0; z < depth; z++) {
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (pPos.getY() < player.getY()) mutablePos.set(pPos.offset(x, -z, y));
                        else if (pPos.getY() > player.getY() + 1.62) mutablePos.set(pPos.offset(x, z, y));
                        else if (player.getDirection().getAxis() == Direction.Axis.Z) mutablePos.set(pPos.offset(x, y, player.getDirection().getAxisDirection() == Direction.AxisDirection.POSITIVE ? z : -z));
                        else if (player.getDirection().getAxis() == Direction.Axis.X) mutablePos.set(pPos.offset(player.getDirection().getAxisDirection() == Direction.AxisDirection.POSITIVE ? z : -z, y, x));
                    
                        if(!diggerItem.isCorrectToolForDrops(player.getMainHandItem(), pLevel.getBlockState(mutablePos))) continue;
                        
                        pLevel.destroyBlock(mutablePos, !droppedBlock);
                        droppedBlock = true;
                    }
                }
            }
           
            pStack.hurtAndBreak(1, pEntityLiving, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
