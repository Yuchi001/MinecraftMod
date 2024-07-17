package net.yuhi.better_progression.item.custom;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ClubItem extends AxeItem {
    private final TagKey<Block>[] mineableTags;

    public ClubItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Item.Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.mineableTags =
                new TagKey[]
                        {
                                BlockTags.MINEABLE_WITH_AXE,
                                BlockTags.MINEABLE_WITH_PICKAXE,
                                BlockTags.MINEABLE_WITH_SHOVEL
                        };
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        for (TagKey<Block> tag : mineableTags) {
            if (state.is(tag)) {
                return speed;
            }
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        for (TagKey<Block> tag : mineableTags) {
            if (state.is(tag)) {
                return true;
            }
        }
        return super.isCorrectToolForDrops(state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == ToolActions.PICKAXE_DIG || toolAction == ToolActions.AXE_DIG || super.canPerformAction(stack, toolAction);
    }
}
