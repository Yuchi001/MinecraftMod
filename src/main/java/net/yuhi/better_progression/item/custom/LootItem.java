package net.yuhi.better_progression.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.yuhi.better_progression.item.enums.ELootItemDropProps;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class LootItem<T extends LivingEntity> extends LayerableSwordItem {
    private final Class<T> entityClass;

    public LootItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, Class<T> pEntityClass) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.entityClass = pEntityClass;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        var res = super.hurtEnemy(stack, target, attacker);

        var health = target.getHealth();
        
        if (!entityClass.isInstance(target) || target.level.isClientSide && health <= 0.0f) return res;

        ServerLevel serverLevel = (ServerLevel) target.level;
        ResourceLocation lootTableLocation = target.getLootTable();
        LootTable lootTable = serverLevel.getServer().getLootTables().get(lootTableLocation);
        LootContext.Builder lootContextBuilder = (new LootContext.Builder(serverLevel))
                .withParameter(LootContextParams.THIS_ENTITY, target)
                .withParameter(LootContextParams.ORIGIN, target.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, attacker.damageSources().generic());
        LootContext lootContext = lootContextBuilder.create(LootContextParamSets.ENTITY);

        var drops = lootTable.getRandomItems(lootContext);
        for (ItemStack drop : drops) {
            var baseCount = drop.getCount();
            var lootingEnchantmentValue = getEnchantmentLevel(stack, Enchantments.MOB_LOOTING);
            drop.setCount(getCount(baseCount) + getCount(lootingEnchantmentValue));
        }

        for (var drop : drops) {
            target.spawnAtLocation(drop);
        }

        return res;
    }

    private int getCount(int defaultCount){
        class DropTuple {
            public final int count;
            public final float weight;
            DropTuple(int count, float weight) {
                this.count = count;
                this.weight = weight;
            }
        }

        var dropList = new ArrayList<DropTuple>();
        var maxCount = ELootItemDropProps.getTierDrop(getTier());
        var weightSum = 0.0f;
        var baseWeight = 100.0f;
        for (var i = 0; i <= maxCount; i++) {
            dropList.add(new DropTuple(i, baseWeight));
            weightSum += baseWeight;
            baseWeight /= 1.5f;
        }

        dropList.sort((o1, o2) -> Float.compare(o1.weight, o2.weight));

        var randomNum = ThreadLocalRandom.current().nextFloat(0.0f, weightSum + 1);
        for (var dropItem : dropList) {
            if (randomNum <= dropItem.weight) return dropItem.count + defaultCount;
            randomNum -= dropItem.weight;
        }

        return defaultCount;
    }
}
