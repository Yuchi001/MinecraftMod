package net.yuhi.better_progression.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EndBrewingStandBlock extends BrewingStandBlock implements IBrewingStand {

    public EndBrewingStandBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getFuel() {
        return new ItemStack(Items.DRAGON_BREATH);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BrewingStandBlockEntity(pos, state);
    }
}
