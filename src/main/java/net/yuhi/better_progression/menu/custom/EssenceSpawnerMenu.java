package net.yuhi.better_progression.menu.custom;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.yuhi.better_progression.menu.ModMenus;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.NotNull;

public class EssenceSpawnerMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData containerData;

    public EssenceSpawnerMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, new SimpleContainer(5), new SimpleContainerData(1));
    }

    public EssenceSpawnerMenu(int id, Inventory playerInventory, Container container, ContainerData extraData) {
        super(ModMenus.ESSENCE_SPAWNER.get(), id);
        this.container = container;
        this.containerData = extraData;
        
        for (int i = 0; i < 5; ++i) {
            this.addSlot(new EssenceItemSlot(container, i, 43 + i * 18, 24));
        }
        
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
        
        this.addDataSlots(extraData);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (pIndex < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(stackInSlot, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (stackInSlot.is(ModTags.Items.ESSENCE_ITEM)) {
                    if (!this.moveItemStackTo(stackInSlot, 0, this.container.getContainerSize(), false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }

    public int getSpawnerCharge() {
        return this.containerData.get(0);
    }

    private static class EssenceItemSlot extends Slot {
        public EssenceItemSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.is(ModTags.Items.ESSENCE_ITEM);
        }
    }
}
