package net.yuhi.better_progression.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.yuhi.better_progression.BetterProgression;

public class ModTags {
    public static class Blocks {
        public static TagKey<Block> ORE_TAG = tag("ore");
        public static TagKey<Block> NO_DROP = tag("no_drop");
        
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(BetterProgression.MOD_ID, name));
        }
    }
    
    public static class Items {
        public static TagKey<Item> LAYERABLE_TAG = tag("layerable");
        public static TagKey<Item> HEAVY_ARMOR_TAG = tag("armor");
        public static TagKey<Item> NETHERITE_ARMOR_TAG = tag("netherite_armor");
        public static TagKey<Item> ENDERITE_ARMOR_TAG = tag("enderite_armor");
        
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(BetterProgression.MOD_ID, name));
        }
    }
}
