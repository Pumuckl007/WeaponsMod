package weapons.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import weapons.tileentity.TileEntitySicurityStorage;


public class ContainerSicurityStorage extends Container {

    private TileEntitySicurityStorage tile;

    private final int CHEST_INVENTORY_ROWS = 4;
    private final int CHEST_INVENTORY_COLUMNS = 8;

    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;

    public ContainerSicurityStorage(InventoryPlayer inventoryPlayer, TileEntitySicurityStorage tile) {

        this.tile = tile;

        tile.openChest();

        int index = 0;
        this.addSlotToContainer(new Slot(tile, 32, 154, 23));
        this.addSlotToContainer(new Slot(tile, 33, 172, 72));
        for (int chestRowIndex = 0; chestRowIndex < CHEST_INVENTORY_ROWS; ++chestRowIndex) {
            for (int chestColumnIndex = 0; chestColumnIndex < CHEST_INVENTORY_COLUMNS; ++chestColumnIndex) {
                this.addSlotToContainer(new Slot(tile, index, 5 + chestColumnIndex * 18, 5 + chestRowIndex * 18));
                index++;
            }
        }

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {

        return true;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onCraftGuiClosed(EntityPlayer entityPlayer) {

        super.onCraftGuiClosed(entityPlayer);
        tile.closeChest();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {

        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();

            if (slotIndex < CHEST_INVENTORY_ROWS * CHEST_INVENTORY_COLUMNS) {
                if (!this.mergeItemStack(itemStack, CHEST_INVENTORY_ROWS * CHEST_INVENTORY_COLUMNS, inventorySlots.size(), false))
                    return null;
            }
            else if (!this.mergeItemStack(itemStack, 0, CHEST_INVENTORY_ROWS * CHEST_INVENTORY_COLUMNS, false))
                return null;

            if (itemStack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }
}
