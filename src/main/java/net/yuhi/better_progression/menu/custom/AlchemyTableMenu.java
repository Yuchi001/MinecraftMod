package net.yuhi.better_progression.menu.custom;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.yuhi.better_progression.menu.ModMenus;

public class AlchemyTableMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData containerData;
    
    public int getCurrentFuel() {
        return containerData.get(0);
    }

    public int getCurrentFuelStatus() {
        return containerData.get(1);
    }

    public int getCookingProgress() {
        return containerData.get(2);
    }
    
    public AlchemyTableMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, new SimpleContainer(5), new SimpleContainerData(3));
    }

    public AlchemyTableMenu(int id, Inventory playerInventory, Container container, ContainerData extraData)  {
        super(ModMenus.ALCHEMY_TABLE.get(), id);
        this.container = container;
        this.containerData = extraData;
        
        this.addSlot(new AlchemyTableMenuFuelSlot(container, this, 0, 8, 16));

        for (int i = 0; i < 2; ++i) {
            this.addSlot(new AlchemyTableMenuSlot(container, i + 1, 8 + i * 18, 36));
        }

        for (int i = 0; i < 2; ++i) {
            this.addSlot(new AlchemyTableMenuSlot(container, i + 3, 8 + i * 18, 54));
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
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            ItemStack copyStack = originalStack.copy();

            if (index >= 5) {
                if (isValidFuel(originalStack)) {
                    if (!this.moveItemStackTo(originalStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (this.moveItemStackTo(originalStack, 1, 5, false)) {
                    return copyStack;
                }
            }
            else {
                if (!this.moveItemStackTo(originalStack, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            return copyStack;
        }
        return ItemStack.EMPTY;
    }

    public boolean isValidFuel(ItemStack stack) {
        return switch (getCurrentFuel()) {
            case 1 -> stack.is(Items.GUNPOWDER);
            case 2 -> stack.is(Items.BLAZE_POWDER);
            case 3 -> stack.is(Items.DRAGON_BREATH);
            default -> false;
        };
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        for (Slot slot : this.slots) {
            slot.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.container.stillValid(pPlayer);
    }
    
    private static class AlchemyTableMenuSlot extends Slot {
        public AlchemyTableMenuSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return true;
        }
    }

    private static class AlchemyTableMenuFuelSlot extends Slot {
        private final AlchemyTableMenu menu;
        
        public AlchemyTableMenuFuelSlot(Container container, AlchemyTableMenu menu, int index, int x, int y) {
            super(container, index, x, y);
            this.menu = menu;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return menu.isValidFuel(stack);
        }

        @Override
        public boolean isActive() {
            return menu.getCurrentFuel() > 0 && super.isActive();
        }
    }
}
