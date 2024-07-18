package net.yuhi.better_progression.block.BetterFurnace;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.menu.BetterFurnaceMenu.BetterFurnaceMenu;
import net.yuhi.better_progression.recipe.ModRecipeType;
import org.jetbrains.annotations.NotNull;

public class BetterFurnaceBlockEntity extends AbstractBetterFurnaceBlockEntity {

    public BetterFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BETTER_FURNACE.get(), pPos, pBlockState, ModRecipeType.BetterSmeltingRecipeType.BETTER_SMELTING);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.furnace");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new BetterFurnaceMenu(pContainerId, pInventory, this, this.dataAccess);
    }
}
