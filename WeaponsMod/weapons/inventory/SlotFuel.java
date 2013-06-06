package weapons.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import weapons.power.Fuel;

public class SlotFuel extends Slot
{

    public SlotFuel(IInventory par1IInventory, int par3, int par4, int par5)
    {
        super(par1IInventory, par3, par4, par5);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack item)
    {
    	return Fuel.isItemFuelSource(item.getItem());
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return 64;
    }
}
