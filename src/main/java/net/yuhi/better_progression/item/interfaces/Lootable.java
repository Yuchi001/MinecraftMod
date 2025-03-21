package net.yuhi.better_progression.item.interfaces;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
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
            var tier = getSwordItem().getTier();
            var maxCount = baseCount + modifyDropCount(tier);
            drop.setCount(getCount(baseCount, maxCount) + getCount(lootingEnchantmentValue, maxCount));
        }

        drops = modifyDrop(drops, target);
        for (var drop : drops) {
            target.spawnAtLocation(drop);
        }

        return true;
    }
    
    default ObjectArrayList<ItemStack> modifyDrop(ObjectArrayList<ItemStack> current, LivingEntity target) {
        return current;
    }
    
    default int modifyDropCount(Tier tier) {
        return 0;
    }
    
    SwordItem getSwordItem();
}
