package net.yuhi.better_progression.block.utils;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.yuhi.better_progression.BetterProgression;

public class ModWoodTypes {
    public static final WoodType END_OAK = WoodType.register(new WoodType(BetterProgression.MOD_ID + ":end_oak", BlockSetType.OAK));
}
