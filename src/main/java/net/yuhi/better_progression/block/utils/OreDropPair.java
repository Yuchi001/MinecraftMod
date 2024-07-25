package net.yuhi.better_progression.block.utils;

import net.minecraft.world.item.Item;

public class OreDropPair {
    public final Item item;
    public final int weight;

    public OreDropPair(Item item, int weight) {
        this.item = item;
        this.weight = weight;
    }
}
