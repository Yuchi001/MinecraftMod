package net.yuhi.better_progression.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.yuhi.better_progression.item.TierKnifeMaxDrop;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class KnifeItem extends SwordItem implements TinedItem {
    public KnifeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        var res = super.hurtEnemy(stack, target, attacker);

        var health = target.getHealth();
        if (target instanceof Animal animal && !target.level.isClientSide && health <= 0.0f) {
            ServerLevel serverLevel = (ServerLevel) animal.level;
            ResourceLocation lootTableLocation = animal.getLootTable();
            LootTable lootTable = serverLevel.getServer().getLootTables().get(lootTableLocation);
            LootContext.Builder lootContextBuilder = (new LootContext.Builder(serverLevel))
                    .withParameter(LootContextParams.THIS_ENTITY, animal)
                    .withParameter(LootContextParams.ORIGIN, animal.position())
                    .withParameter(LootContextParams.DAMAGE_SOURCE, attacker.damageSources().generic());
            LootContext lootContext = lootContextBuilder.create(LootContextParamSets.ENTITY);

            List<ItemStack> drops = lootTable.getRandomItems(lootContext);
            for (ItemStack drop : drops) {
                var baseCount = drop.getCount();
                drop.setCount(getCount(baseCount));
            }
            //cow.spawnAtLocation(new ItemStack(Items.LEATHER, 1));
            for (var drop : drops) {
                animal.spawnAtLocation(drop);
            }
        }

        return res;
    }

    private int getCount(int defaultCount){
        class DropTuple {
            public int count;
            public float weight;
            DropTuple(int count, float weight) {
                this.count = count;
                this.weight = weight;
            }
        }

        var dropList = new ArrayList<DropTuple>();
        var maxCount = TierKnifeMaxDrop.getTierDrop(getTier());
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

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pToolTipComponents, @NotNull TooltipFlag pIsAdvanced) {
        var components = TinedItem.getTinedDescription(pStack);
        if (components != null) pToolTipComponents.addAll(components);
        super.appendHoverText(pStack, pLevel, pToolTipComponents, pIsAdvanced);
    }
}
