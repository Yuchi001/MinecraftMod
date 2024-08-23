package net.yuhi.better_progression.item.interfaces;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getCount;

public interface Lootable<T extends LivingEntity> {
    Class<T> getEntityClass();

    default boolean lootEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker, boolean defaultRes) {
        if (!getEntityClass().isInstance(target) || target.level.isClientSide || target.getHealth() > 0.0f) {
            return defaultRes;
        }

        if (!(target.level instanceof ServerLevel serverLevel)) {
            return defaultRes;
        }

        ResourceLocation lootTableLocation = target.getLootTable();
        LootTable lootTable = serverLevel.getServer().getLootTables().get(lootTableLocation);
        LootContext.Builder lootContextBuilder = (new LootContext.Builder(serverLevel))
                .withParameter(LootContextParams.THIS_ENTITY, target)
                .withParameter(LootContextParams.ORIGIN, target.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, attacker.damageSources().generic());
        LootContext lootContext = lootContextBuilder.create(LootContextParamSets.ENTITY);

        var drops = lootTable.getRandomItems(lootContext);
        for (ItemStack drop : drops) {
            int baseCount = drop.getCount();
            int lootingEnchantmentValue = getSwordItem().getEnchantmentLevel(stack, Enchantments.MOB_LOOTING);
            drop.setCount(getCount(baseCount, getSwordItem()) + getCount(lootingEnchantmentValue, getSwordItem()));
        }

        for (var drop : drops) {
            target.spawnAtLocation(drop);
        }

        return true;
    }
    
    SwordItem getSwordItem();
}
