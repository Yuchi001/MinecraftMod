package net.yuhi.better_progression.block.blockdata;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum EMineableWith {
    PICKAXE(BlockTags.MINEABLE_WITH_PICKAXE),
    AXE(BlockTags.MINEABLE_WITH_AXE),
    HOE(BlockTags.MINEABLE_WITH_HOE),
    SHOVEL(BlockTags.MINEABLE_WITH_SHOVEL),
    NONE(null);
    
    private final TagKey<Block> tag;
    
    public TagKey<Block> getTag() {
        return tag;
    }
    
    EMineableWith(TagKey<Block> tag) {
        this.tag = tag;
    }
}