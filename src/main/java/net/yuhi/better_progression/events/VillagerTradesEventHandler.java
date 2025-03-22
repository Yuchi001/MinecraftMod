package net.yuhi.better_progression.events;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.item.utils.ItemsUtilsMethods;

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
        
        

        if(event.getType() == VillagerProfession.FARMER) {
            var tierOne = new VillagerTradeCreator(1, trades);
            tierOne.buyCoinTrade(Items.WHEAT, 32);
            tierOne.buyCoinTrade(Items.CARROT, 24);
            tierOne.buyCoinTrade(Items.POTATO, 24);
            tierOne.buyCoinTrade(Items.MELON_SEEDS, 24);
            tierOne.buyCoinTrade(Items.APPLE, 64);

            tierOne.sellCoinTrade(Items.BREAD, 8);
            tierOne.sellCoinTrade(Items.APPLE, 32);
            tierOne.sellCoinTrade(Items.CARROT, 16);
            tierOne.sellCoinTrade(Items.POTATO, 16);
            tierOne.sellCoinTrade(Items.MELON_SEEDS, 16);

            var tierTwo = new VillagerTradeCreator(2, trades);
            tierTwo.buyCoinTrade(Items.PUMPKIN, 16);
            tierTwo.buyCoinTrade(Items.MELON, 16);
            tierTwo.buyCoinTrade(Items.BONE_MEAL, 32);
            tierTwo.buyCoinTrade(Items.OAK_FENCE, 32);

            tierTwo.sellCoinTrade(Items.PUMPKIN, 8);
            tierTwo.sellCoinTrade(Items.MELON, 8);
            tierTwo.sellCoinTrade(Items.BONE_MEAL, 16);
            tierTwo.sellCoinTrade(Items.OAK_FENCE, 16);
            
            var tierThree = new VillagerTradeCreator(3, trades);
            tierThree.buyCoinTrade(Items.HONEYCOMB, 8);
            tierThree.buyCoinTrade(Blocks.COMPOSTER.asItem(), 8);

            tierThree.sellCoinTrade(Items.COCOA_BEANS, 1, 3);
            tierThree.sellCoinTrade(Items.DARK_OAK_SAPLING, 4);
            tierThree.sellCoinTrade(Items.COOKIE, 18);
            tierThree.sellCoinTrade(Items.PUMPKIN_PIE, 4);
            
            var tierFour = new VillagerTradeCreator(4, trades);
            tierFour.buyCoinTrade(Items.GOLDEN_CARROT, 4);
            
            tierFour.sellCoinTrade(Items.CAKE, 1);
            tierFour.sellCoinTrade(Items.BEETROOT_SOUP, 4);
            tierFour.sellCoinTrade(Items.SUSPICIOUS_STEW, 4);
            
            var tierFive = new VillagerTradeCreator(5, trades);
            tierFive.sellEmeraldTrade(Items.GLISTERING_MELON_SLICE, 9);
            tierFive.sellEmeraldTrade(Items.GOLDEN_CARROT, 16);
        }

        if(event.getType() == VillagerProfession.ARMORER) {
            var tierOne = new VillagerTradeCreator(1, trades);
            tierOne.buyCoinTrade(Items.COAL, 16);
            tierOne.buyCoinTrade(Items.COPPER_INGOT, 8);
            tierOne.buyCoinTrade(Items.IRON_INGOT, 8);
            
            tierOne.sellCoinTrade(Items.IRON_HELMET, 1, 12);
            tierOne.sellCoinTrade(Items.IRON_CHESTPLATE, 1, 18);
            tierOne.sellCoinTrade(Items.IRON_LEGGINGS, 1, 15);
            tierOne.sellCoinTrade(Items.IRON_HELMET, 1, 12);

            tierOne.sellCoinTrade(Items.CHAINMAIL_HELMET, 1, 9);
            tierOne.sellCoinTrade(Items.CHAINMAIL_CHESTPLATE, 1, 15);
            tierOne.sellCoinTrade(Items.CHAINMAIL_LEGGINGS, 1, 12);
            tierOne.sellCoinTrade(Items.CHAINMAIL_BOOTS, 1, 9);

            tierOne.sellCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.ChainmailHelmet, EMaterialType.COPPER), 1, 9);
            tierOne.sellCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.ChainmailChestplate, EMaterialType.COPPER), 1, 15);
            tierOne.sellCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.ChainmailLeggings, EMaterialType.COPPER), 1, 12);
            tierOne.sellCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.ChainmailBoots, EMaterialType.COPPER), 1, 9);

            tierOne.sellCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.Helmet, EMaterialType.COPPER), 1, 12);
            tierOne.sellCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.Chestplate, EMaterialType.COPPER), 1, 18);
            tierOne.sellCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.Leggings, EMaterialType.COPPER), 1, 15);
            tierOne.sellCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.Boots, EMaterialType.COPPER), 1, 12);
        
            var tierTwo = new VillagerTradeCreator(2, trades);
            tierTwo.buyCoinTrade(Items.LAVA_BUCKET, 1, 3);
            
            tierTwo.sellCoinTrade(Items.BELL, 1, 6);
            
            var tierThree = new VillagerTradeCreator(3, trades);
            tierThree.buyCoinTrade(Items.DIAMOND, 1, 6);
            
            var tierFour = new VillagerTradeCreator(4, trades);
            tierFour.buyEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.Ingot, EMaterialType.STEEL), 2);
            tierFour.buyEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.Ingot, EMaterialType.BRONZE), 4);
            
            var tierFive = new VillagerTradeCreator(5, trades);
            tierFive.sellEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.Helmet, EMaterialType.BRONZE), 1, 12);
            tierFive.sellEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.Chestplate, EMaterialType.BRONZE), 1, 18);
            tierFive.sellEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.Leggings, EMaterialType.BRONZE), 1, 15);
            tierFive.sellEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.Boots, EMaterialType.BRONZE), 1, 12);
            tierFive.sellEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.ChainmailHelmet, EMaterialType.BRONZE), 1, 12);
            tierFive.sellEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.ChainmailChestplate, EMaterialType.BRONZE), 1, 18);
            tierFive.sellEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.ChainmailLeggings, EMaterialType.BRONZE), 1, 15);
            tierFive.sellEmeraldTrade(ItemsUtilsMethods.getItem(EItemCategory.ChainmailBoots, EMaterialType.BRONZE), 1, 12);
            tierFive.sellEmeraldTrade(Items.IRON_HELMET, 1, 12);
            tierFive.sellEmeraldTrade(Items.IRON_CHESTPLATE, 1, 18);
            tierFive.sellEmeraldTrade(Items.IRON_LEGGINGS, 1, 15);
            tierFive.sellEmeraldTrade(Items.IRON_HELMET, 1, 12);
            tierFive.sellEmeraldTrade(Items.CHAINMAIL_HELMET, 1, 9);
            tierFive.sellEmeraldTrade(Items.CHAINMAIL_CHESTPLATE, 1, 15);
            tierFive.sellEmeraldTrade(Items.CHAINMAIL_LEGGINGS, 1, 12);
            tierFive.sellEmeraldTrade(Items.CHAINMAIL_BOOTS, 1, 9);
        }

        if (event.getType() == VillagerProfession.BUTCHER) {
            var tierOne = new VillagerTradeCreator(1, trades);
            tierOne.buyCoinTrade(Items.CHICKEN, 8);
            tierOne.buyCoinTrade(Items.BEEF, 9);
            tierOne.buyCoinTrade(Items.RABBIT);
            
            tierOne.sellCoinTrade(Items.RABBIT_STEW, 3);
            
            var tierTwo = new VillagerTradeCreator(2, trades);
            tierTwo.buyCoinTrade(Items.COAL, 16);
            
            tierTwo.sellCoinTrade(Items.COOKED_PORKCHOP, 4);
            tierTwo.sellCoinTrade(Items.COOKED_CHICKEN, 6);
            
            var tierThree = new VillagerTradeCreator(3, trades);
            tierThree.buyCoinTrade(Items.PORKCHOP, 10);
            tierThree.buyCoinTrade(Items.MUTTON, 12);

            tierThree.sellCoinTrade(Items.BEEF, 5);
            tierThree.sellCoinTrade(Items.COOKED_MUTTON, 6);
            
            var tierFour = new VillagerTradeCreator(4, trades);
            tierFour.buyCoinTrade(Items.DRIED_KELP_BLOCK, 10);
            
            var tierFive = new VillagerTradeCreator(5, trades);
            tierFive.buyCoinTrade(Items.SWEET_BERRIES, 10);
            tierFive.buyCoinTrade(Items.GLOW_BERRIES, 1);
        }

        if (event.getType() == VillagerProfession.CLERIC) {
            var tierOne = new VillagerTradeCreator(1, trades);
            tierOne.buyCoinTrade(Items.ROTTEN_FLESH, 8);
            tierOne.buyCoinTrade(Items.BONE, 8);
            
            var tierTwo = new VillagerTradeCreator(2, trades);
            tierTwo.buyCoinTrade(Items.GOLD_INGOT, 2);
            tierTwo.buyCoinTrade(ItemsUtilsMethods.getItem(EItemCategory.Ingot, EMaterialType.TIN), 2);
        
            tierTwo.sellCoinTrade(Items.LAPIS_LAZULI, 3);
            
            var tierThree = new VillagerTradeCreator(3, trades);
            tierThree.buyCoinTrade(Items.RABBIT_FOOT);
            
            tierThree.sellCoinTrade(Items.GLOWSTONE_DUST);
            
            var tierFour = new VillagerTradeCreator(4, trades);
            tierFour.buyCoinTrade(Items.SCUTE, 1, 3);
            tierFour.buyCoinTrade(Items.GLASS_BOTTLE, 9);
            tierFour.buyCoinTrade(Items.GLASS_BOTTLE, 9);
            
            tierFour.sellCoinTrade(Items.ENDER_PEARL);
            
            var tierFive = new VillagerTradeCreator(5, trades);
            tierFive.buyCoinTrade(Items.NETHER_WART, 2);
            
            tierFive.sellEmeraldTrade(Items.EXPERIENCE_BOTTLE, 9);
        }
        
        //TODO: CARTOGRAPHER
        //TODO: FISHERMAN
        //TODO: FLETCHER
        //TODO: WEAPONSMITH
        //TODO: LEATHERWORKER
        //TODO: SHEPHERD
        //TODO: LIBRARIAN
        //TODO: TOOLSMITH
        //TODO: MASON
    }
    
    private static class VillagerTradeCreator {
        private final List<VillagerTrades. ItemListing> trades;
        private final int tier;
        
        private static final int maxUsesBase = 50;
        private static final int xp = 5;
        private static final float priceMultiplier = 0.01f;
        
        public VillagerTradeCreator(int tier, Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {
            this.trades = trades.get(tier);
            this.tier = tier;
        }
        
        public void buyCoinTrade(Item item, int count) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_COIN.get()),
                    new ItemStack(item, count),
                    maxUsesBase / tier, xp * tier, priceMultiplier * tier)));
        }

        public void sellCoinTrade(Item item, int count) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(item, count),
                    new ItemStack(ModItems.EMERALD_COIN.get()),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void buyCoinTrade(Item item, int count, int coinCount) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_COIN.get(), coinCount),
                    new ItemStack(item, count),
                    maxUsesBase / tier, xp * tier, priceMultiplier * tier)));
        }

        public void sellCoinTrade(Item item, int count, int coinCount) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(item, count),
                    new ItemStack(ModItems.EMERALD_COIN.get(), coinCount),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void buyCoinTrade(Item item) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(ModItems.EMERALD_COIN.get()),
                    new ItemStack(item),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void sellCoinTrade(Item item) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(item),
                    new ItemStack(ModItems.EMERALD_COIN.get()),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void buyEmeraldTrade(Item item, int count) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD),
                    new ItemStack(item, count),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void sellEmeraldTrade(Item item, int count) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(item, count),
                    new ItemStack(Items.EMERALD),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void buyEmeraldTrade(Item item, int count, int emeraldCount) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, emeraldCount),
                    new ItemStack(item, count),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void sellEmeraldTrade(Item item, int count, int emeraldCount) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(item, count),
                    new ItemStack(Items.EMERALD, emeraldCount),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void buyEmeraldTrade(Item item) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD),
                    new ItemStack(item),
                    maxUsesBase, xp, priceMultiplier)));
        }

        public void sellEmeraldTrade(Item item) {
            trades.add(((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(item),
                    new ItemStack(Items.EMERALD),
                    maxUsesBase, xp, priceMultiplier)));
        }
    }
}
