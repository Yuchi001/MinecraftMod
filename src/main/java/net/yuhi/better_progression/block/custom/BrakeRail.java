package net.yuhi.better_progression.block.custom;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BrakeRail extends RailBlock {
    public BrakeRail() {
        super(BlockBehaviour.Properties.copy(Blocks.RAIL));
    }
}
