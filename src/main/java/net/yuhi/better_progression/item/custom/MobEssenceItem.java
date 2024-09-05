package net.yuhi.better_progression.item.custom;

import net.minecraft.world.item.Item;

public class MobEssenceItem extends Item {
    private final int stacks;
    
    public MobEssenceItem(int stacks, Properties pProperties) {
        super(pProperties);
        this.stacks = stacks;
    }

    public int getStacks() {
        return stacks;
    }
}
