package weapons.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import weapons.tileentity.TileEntityWeaponCarver;


public class ContainerWeaponCarver extends Container {

    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    TileEntityWeaponCarver tile;

    public int lastFuel = 0;

    public int lastWrokTime = 0;

    public ContainerWeaponCarver(InventoryPlayer inventoryPlayer, TileEntityWeaponCarver tile) {

    	this.tile = tile;
    	int carftX = 14;
    	int craftY = 19;
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT1, carftX, craftY));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT2, carftX, craftY + 18));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT3, carftX, craftY + 36));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT4, carftX + 18, craftY));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT5, carftX + 18, craftY + 18));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT6, carftX + 18, craftY + 36));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT7, carftX + 36, craftY));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT8, carftX + 36, craftY + 18));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT9, carftX + 36, craftY + 36));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.FUEL, 154, 58));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.OUTPUT, 118, 36));

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
