package weapons.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import weapons.tileentity.TileEntityUpgator;


public class ContainerUpgator extends Container {

    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    TileEntityUpgator tile;

    public ContainerUpgator(InventoryPlayer inventoryPlayer, TileEntityUpgator tile) {

    	this.tile = tile;
        this.addSlotToContainer(new SlotOne(tile, TileEntityUpgator.ITEMSTACK, 101, 6));
        this.addSlotToContainer(new SlotUpgrade(tile, TileEntityUpgator.UPGRADE, 58, 6));
        this.addSlotToContainer(new SlotFuel(tile, TileEntityUpgator.FUEL, 80, 62));

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

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return par1 >= 9 ? null : tile.inventory[par1];
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer var1) {

        return true;
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {

        return null;
    }
}
