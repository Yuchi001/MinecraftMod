package net.yuhi.better_progression.events;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;

import java.util.List;

@Mod.EventBusSubscriber(modid = BetterProgression.MOD_ID)
public class VillagerTradesEventHandler {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        for (int i = 1; i <= 5; i++) {
            if (event.getTrades().containsKey(i)) {
                event.getTrades().get(i).clear();
            }
        }

        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

        if(event.getType() == VillagerProfession.LIBRARIAN) {
            trades.get(1).add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(Items.BOOK),
                    10, 8, 0.02f)));
        }
    }
}
