package net.yuhi.better_progression.block.BetterBlastFurnace;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.yuhi.better_progression.block.BetterFurnace.AbstractBetterFurnaceBlockEntity;
import net.yuhi.better_progression.block.ModBlockEntities;
import net.yuhi.better_progression.menu.BetterBlastFurnaceMenu.BetterBlastFurnaceMenu;
import net.yuhi.better_progression.recipe.ModRecipeType;
import org.jetbrains.annotations.NotNull;

public class BetterBlastFurnaceBlockEntity extends AbstractBetterFurnaceBlockEntity {
    public BetterBlastFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BETTER_BLAST_FURNACE.get(), pPos, pBlockState, ModRecipeType.BetterBlastingRecipeType.BETTER_BLASTING);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.blast_furnace");
    }

    @Override
    protected int getBurnDuration(ItemStack pFuel) {
        return super.getBurnDuration(pFuel) / 2;
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
        return new BetterBlastFurnaceMenu(pId, pPlayer, this, this.dataAccess);
    }
}
