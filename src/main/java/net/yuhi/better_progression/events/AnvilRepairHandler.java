package net.yuhi.better_progression.events;

import net.minecraft.world.item.*;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnvilRepairHandler {

    @SubscribeEvent
    public static void onAnvilRepair(AnvilUpdateEvent event) {
        ItemStack leftItem = event.getLeft();
        ItemStack rightItem = event.getRight();

        if (leftItem.isDamageableItem() && leftItem.getDamageValue() > 0) {
            if (isValidRepairMaterial(leftItem, rightItem)) {
                int materialCount = event.getMaterialCost();
                float repairPercentage = 0.05f;
                int totalRepairAmount = Math.round(leftItem.getMaxDamage() * repairPercentage * materialCount);

                int currentDamage = leftItem.getDamageValue();
                int newDamage = Math.max(0, currentDamage - totalRepairAmount);

                ItemStack repairedItem = leftItem.copy();
                repairedItem.setDamageValue(newDamage);

                int repairCost = materialCount;
                event.setCost(repairCost);

                event.setOutput(repairedItem);
            }
        }
    }

    private static boolean isValidRepairMaterial(ItemStack leftItem, ItemStack rightItem) {
        Item item = leftItem.getItem();

        if (item instanceof TieredItem) {
            Tier tier = ((TieredItem) item).getTier();
            if (tier == Tiers.WOOD && rightItem.getItem() == Items.OAK_PLANKS) return true;
            if (tier == Tiers.STONE && rightItem.getItem() == Items.COBBLESTONE) return true;
            if (tier == Tiers.IRON && rightItem.getItem() == Items.IRON_INGOT) return true;
            if (tier == Tiers.DIAMOND && rightItem.getItem() == Items.DIAMOND) return true;
            if (tier == Tiers.NETHERITE && rightItem.getItem() == Items.NETHERITE_INGOT) return true;
        }
        
        return false;
    }
}