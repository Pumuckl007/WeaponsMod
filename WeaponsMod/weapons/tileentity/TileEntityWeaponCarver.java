package weapons.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import weapons.crafting.WeaponCarver;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityWeaponCarver extends TileBase implements IInventory {

	public ItemStack[] inventory;

	private final int INVENTORY_SIZE = 11;

	public static final int CRAFT1 = 0;
	public static final int CRAFT2 = 1;
	public static final int CRAFT3 = 2;
	public static final int CRAFT4 = 3;
	public static final int CRAFT5 = 4;
	public static final int CRAFT6 = 5;
	public static final int CRAFT7 = 6;
	public static final int CRAFT8 = 7;
	public static final int CRAFT9 = 8;
	public static final int FUEL = 9;
	public static final int OUTPUT = 10;

	public int burnTime = 0;

	public int currentFuel = 0;

	public int currentWrokTime = 0;

	public TileEntityWeaponCarver() {

		inventory = new ItemStack[INVENTORY_SIZE];
	}

	@Override
	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return 11;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {

		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {

		ItemStack itemStack = getStackInSlot(slot);
		if (itemStack != null) {
			if (itemStack.stackSize <= amount) {
				setInventorySlotContents(slot, null);
			}
			else {
				itemStack = itemStack.splitStack(amount);
				if (itemStack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {

		ItemStack itemStack = getStackInSlot(slot);
		if (itemStack != null) {
			setInventorySlotContents(slot, null);
		}
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {

		inventory[slot] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {

		return this.hasCustomName() ? this.getCustomName() : "container.weaponcarver";
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}
	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * crafted
	 */
	public int getCookProgressScaled(int par1)
	{
		return this.currentWrokTime * par1 / 20000;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {

		super.readFromNBT(nbtTagCompound);
		this.burnTime = nbtTagCompound.getShort("BurnTime");
		this.currentFuel = nbtTagCompound.getShort("Fule");
		this.currentWrokTime = nbtTagCompound.getShort("WorkTime");
		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList("Items");
		inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {

		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setShort("BurnTime", (short)this.burnTime);
		nbtTagCompound.setShort("Fule", (short)this.currentFuel);
		nbtTagCompound.setShort("WorkTime", (short)this.currentWrokTime);
		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
			if (inventory[currentIndex] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag("Items", tagList);
	}

	@Override
	public boolean isInvNameLocalized() {

		return this.hasCustomName();
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {

		return true;
	}
	/**
	 * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
	 * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
	 */
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (this.burnTime == 0)
		{
			this.burnTime = 20;
		}

		return this.currentWrokTime * par1 / this.burnTime;
	}

	public boolean isWorking()
	{
		return this.currentWrokTime > 0;
	}
	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	public void updateEntity()
	{
		boolean flag = this.burnTime > 0;
		boolean flag1 = false;

		if (this.burnTime > 0)
		{
			--this.burnTime;
		}

		if (!this.worldObj.isRemote)
		{
			if (this.burnTime == 0 && this.canSmelt())
			{
				this.currentWrokTime = this.burnTime = getItemBurnTime(this.inventory[1]);

				if (this.burnTime > 0)
				{
					flag1 = true;
					int i = 0;
					while(i < 9){

						if (this.inventory[i] != null)
						{
							--this.inventory[i].stackSize;

							if (this.inventory[i].stackSize == 0)
							{
								this.inventory[i] = this.inventory[i].getItem().getContainerItemStack(inventory[i]);
							}
						}
						i ++;
					}
				}

			}

			if (this.isWorking() && this.canSmelt())
			{
				++this.currentWrokTime;

				if (this.currentWrokTime == 2000)
				{
					this.currentWrokTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}
			else
			{
				this.currentWrokTime = 0;
			}

			if (flag != this.burnTime > 0)
			{
				flag1 = true;
			}
		}

		if (flag1)
		{
			this.onInventoryChanged();
		}
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			WeaponCarver weaponCarver = WeaponCarver.getInstance();
			ItemStack itemstack = weaponCarver.findMatchingRecipe(this, worldObj);

			if (this.inventory[10] == null)
			{
				this.inventory[10] = itemstack.copy();
			}
			else if (this.inventory[10].isItemEqual(itemstack))
			{
				inventory[10].stackSize += itemstack.stackSize;
			}

			int i = 0;
			while(i < 9){
				if(this.inventory[i] != null){
					--this.inventory[i].stackSize;
				}
				i ++;
			}

			if (this.inventory[0].stackSize <= 0)
			{
				this.inventory[0] = null;
			}
		}
	}

	public static int getItemBurnTime(ItemStack par0ItemStack)
	{
		if (par0ItemStack == null)
		{
			return 0;
		}
		else
		{
			int i = par0ItemStack.getItem().itemID;
			Item item = par0ItemStack.getItem();

			if (par0ItemStack.getItem() instanceof ItemBlock && Block.blocksList[i] != null)
			{
				Block block = Block.blocksList[i];

				if (block == Block.woodSingleSlab)
				{
					return 150;
				}

				if (block.blockMaterial == Material.wood)
				{
					return 300;
				}
			}

			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
			if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) return 200;
			if (i == Item.stick.itemID) return 100;
			if (i == Item.coal.itemID) return 1600;
			if (i == Item.bucketLava.itemID) return 20000;
			if (i == Block.sapling.blockID) return 100;
			if (i == Item.blazeRod.itemID) return 2400;
			return GameRegistry.getFuelValue(par0ItemStack);
		}
	}
	private boolean canSmelt()
	{
		if (this.inventory[0] == null && this.inventory[1] == null && this.inventory[2] == null && this.inventory[3] == null && this.inventory[4] == null && this.inventory[5] == null && this.inventory[6] == null && this.inventory[7] == null && this.inventory[8] == null)
		{
			return false;
		}
		else
		{
			WeaponCarver weaponCarver = WeaponCarver.getInstance();
			ItemStack itemstack = weaponCarver.findMatchingRecipe(this, worldObj);
			if (itemstack == null) return false;
			if (this.inventory[9] == null) return true;
			if (!this.inventory[9].isItemEqual(itemstack)) return false;
			int result = inventory[9].stackSize + itemstack.stackSize;
			return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
		}
	}
	public ItemStack getStackInRowAndColumn(int par1, int par2)
    {
        if (par1 >= 0 && par1 < 3)
        {
            int k = par1 + par2 * 3;
            return this.getStackInSlot(k);
        }
        else
        {
            return null;
        }
    }
}
