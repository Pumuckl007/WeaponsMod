package weapons.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import weapons.tileentity.TileEntityWeaponCarver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class ContainerWeaponCarver extends Container {

    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    TileEntityWeaponCarver tile;
    public int lastburnTime = 0;

    public int lastFuel = 0;

    public int lastWrokTime = 0;

    public ContainerWeaponCarver(InventoryPlayer inventoryPlayer, TileEntityWeaponCarver tile) {

    	this.tile = tile;
    	int carftX = 0;
    	int craftY = 18;
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT1, carftX, craftY));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT2, carftX, craftY + 18));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT3, carftX, craftY + 36));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT4, carftX + 18, craftY));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT5, carftX + 18, craftY + 18));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT6, carftX + 18, craftY + 36));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT7, carftX + 36, craftY));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT8, carftX + 36, craftY + 18));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.CRAFT9, carftX + 36, craftY + 36));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.FUEL, 156, 84));
        this.addSlotToContainer(new Slot(tile, TileEntityWeaponCarver.OUTPUT, 130, 36));

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 106 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 164));
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
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.currentWrokTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.tile.burnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.tile.currentFuel);
    }
    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastFuel != this.tile.currentFuel)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.currentFuel);
            }

            if (this.lastburnTime != this.tile.burnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tile.burnTime);
            }

            if (this.lastWrokTime != this.tile.currentWrokTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tile.currentWrokTime);
            }
        }

        this.lastFuel = this.tile.currentFuel;
        this.lastburnTime = this.tile.burnTime;
        this.lastWrokTime = this.tile.currentWrokTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.tile.currentFuel = par2;
        }

        if (par1 == 1)
        {
            this.tile.burnTime = par2;
        }

        if (par1 == 2)
        {
            this.tile.currentWrokTime = par2;
        }
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {

        return null;
    }
}
