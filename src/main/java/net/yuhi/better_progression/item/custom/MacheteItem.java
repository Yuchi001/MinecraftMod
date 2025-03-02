package net.yuhi.better_progression.item.custom;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.yuhi.better_progression.item.enums.EMacheteItemDropProps;
import net.yuhi.better_progression.item.interfaces.Lootable;
import net.yuhi.better_progression.item.utils.ItemsUtilsMethods;
import org.jetbrains.annotations.NotNull;

public class MacheteItem extends SwordItem implements Lootable<Monster> {
    public MacheteItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public Class<Monster> getEntityClass() {
        return Monster.class;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        return lootEnemy(pStack, pTarget, pAttacker, super.hurtEnemy(pStack, pTarget, pAttacker));
    }

    @Override
    public boolean lootEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker, boolean defaultRes) {
        return Lootable.super.lootEnemy(pStack, pTarget, pAttacker, defaultRes);
    }
    
    @Override
    public SwordItem getSwordItem() {
        return this;
    }

    @Override
    public ObjectArrayList<ItemStack> modifyDrop(ObjectArrayList<ItemStack> current, LivingEntity target) {
        if (!(target instanceof WitherSkeleton) || !EMacheteItemDropProps.dropSkull(getTier())) return current;
        current.add(new ItemStack(Items.WITHER_SKELETON_SKULL));
        return current;
    }
}
