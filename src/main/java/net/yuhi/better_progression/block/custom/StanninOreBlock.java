package net.yuhi.better_progression.block.custom;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.yuhi.better_progression.block.utils.OreDropPair;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public class StanninOreBlock extends DropExperienceBlock {
    public StanninOreBlock(IntProvider pXpRange) {
        super(BlockBehaviour.Properties.copy(Blocks.STONE)
                .requiresCorrectToolForDrops()
                .strength(3.0F, 3.0F), pXpRange);
    }
    
    private final int maxDropCount = 3;

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        var drops = new ArrayList<ItemStack>();
        if (pBuilder.getParameter(LootContextParams.TOOL).getAllEnchantments().containsKey(Enchantments.SILK_TOUCH)) {
            drops.add(new ItemStack(this)); // Zwraca blok sam w sobie
            return drops;
        }
        
        var fortuneValue = pBuilder.getParameter(LootContextParams.TOOL).getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
        var updatedMaxDrop = getMaxDropCount(maxDropCount, 1) + getMaxDropCount(fortuneValue, 0);
        
        for (var i = 0; i < updatedMaxDrop; i++) {
            var item = getDrop();
            if(item == null) continue;;
            
            if (drops.stream().noneMatch(d -> d.getItem() == item)) {
                drops.add(new ItemStack(item));
                continue;
            }
            
            var stackOptional = drops.stream().filter(filterItem -> filterItem.is(item)).findFirst();
            if(stackOptional.isEmpty()) continue;
            var stack = stackOptional.get();
            stack.setCount(stack.getCount() + 1);
            var newStack = stack.copy();
            drops.remove(stack);
            drops.add(newStack);
        }
        
        return drops;
    }

    private int getMaxDropCount(int tempMaxDrop, int defaultValue) {
        var dropsInt = new ArrayList<Integer>(List.of(100));
        var weightSum = 0;
        for (int i = tempMaxDrop; i > 0; i--) {
            var last = dropsInt.get(dropsInt.size() - 1);
            var newWeight = (int)(last / 2f);
            dropsInt.add(newWeight);
            weightSum += newWeight;
        }

        dropsInt.sort(Float::compare);
        var randomNum = ThreadLocalRandom.current().nextFloat(0.0f, weightSum + 1);

        for (var dropItem : dropsInt) {
            var count = dropsInt.indexOf(dropItem);
            if (randomNum <= dropItem) return count == 0 ? defaultValue : count;
            randomNum -= dropItem;
        }

        return tempMaxDrop;
    }
    
    private Item getDrop() {
        var drops = new ArrayList<>(List.of(
                new OreDropPair(getItem(EItemCategory.RawMaterial, EMaterialType.TIN), 70),
                new OreDropPair(Items.RAW_COPPER, 20),
                new OreDropPair(Items.RAW_IRON, 10)
        ));
        drops.sort((o1, o2) -> Float.compare(o1.weight, o2.weight));
        
        var weightSum = drops.stream().mapToInt(item -> item.weight).sum();
        var randomNum = ThreadLocalRandom.current().nextFloat(0.0f, weightSum + 1);

        for (var dropItem : drops) {
            if (randomNum <= dropItem.weight) return dropItem.item;
            randomNum -= dropItem.weight;
        }

        return null;
    }
}
