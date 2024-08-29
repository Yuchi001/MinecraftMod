package net.yuhi.better_progression.block.blockdata;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum ERequireTier {
    NONE(null),
    STONE(BlockTags.NEEDS_STONE_TOOL),
    IRON(BlockTags.NEEDS_IRON_TOOL),
    DIAMOND(BlockTags.NEEDS_DIAMOND_TOOL);
    
    private final TagKey<Block> tag;
    
    public TagKey<Block> getTag() {
        return tag;
    }
    
    ERequireTier(TagKey<Block> tag) {
        this.tag = tag;
    }
}
