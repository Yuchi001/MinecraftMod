package net.yuhi.better_progression.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.yuhi.better_progression.BetterProgression;

import java.security.PublicKey;

public class ModTags {
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(BetterProgression.MOD_ID, name));
        }
    }
    
    public static class Items {
        public static TagKey<Item> TINNABLE_TAG = tag("tinnable");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(BetterProgression.MOD_ID, name));
        }
    }
}
