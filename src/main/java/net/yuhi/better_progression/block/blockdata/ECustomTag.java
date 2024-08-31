package net.yuhi.better_progression.block.blockdata;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.yuhi.better_progression.tag.ModTags;

public enum ECustomTag {
    NONE(null),
    LEAVES(BlockTags.LEAVES),
    FLOWERS(BlockTags.FLOWERS),
    LOGS(BlockTags.LOGS),
    LOGS_THAT_BURN(BlockTags.LOGS_THAT_BURN),
    ORE(ModTags.Blocks.ORE_TAG),
    WOODEN_FENCES(BlockTags.WOODEN_FENCES),
    FENCE_GATES(BlockTags.FENCE_GATES),
    NO_DROP(ModTags.Blocks.NO_DROP),
    RAIL(BlockTags.RAILS);
    
    private final TagKey<Block> tag;

    public TagKey<Block> getTag() {
        return this.tag;
    }
    
    ECustomTag(TagKey<Block> tag) {
        this.tag = tag;
    }
}
