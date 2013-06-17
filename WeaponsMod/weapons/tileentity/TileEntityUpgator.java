package weapons.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import weapons.Weapons;
import weapons.upgrads.ItemUpgrade;
import weapons.upgrads.UpgradeUtils;

public class TileEntityUpgator extends TileEntityPowerBase implements IInventory {

	public ItemStack[] inventory;
	private final int INVENTORY_SIZE = 3;

	public static final int ITEMSTACK = 0;
	public static final int UPGRADE = 1;
	public static final int FUEL = 2;

	private int slotid;
	private int itemid;
	private int itemdamage;

	public TileEntityUpgator() {

		super(true);
		inventory = new ItemStack[INVENTORY_SIZE];
		this.MAXPOWER = 1000;
	}

	@Override
	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return INVENTORY_SIZE;
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
	/**
	 * Called when a client event is received with the event number and
	 * argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int eventID, int secondvalue) {
		switch(eventID){
			case(2):{
				this.slotid = secondvalue;
				return true;
			}
			case(3):{
				this.itemid = secondvalue;
				return true;
			}
			case(4):{
				this.itemdamage = secondvalue;
				return true;
			}
			case(5):{
				ItemStack item = new ItemStack(this.itemid, secondvalue, this.itemdamage);
				this.inventory[this.slotid] = item;
				return true;
			}
			case(6):{
				power = (short)secondvalue;
				return true;
			}
			case(7):{
				this.inventory[secondvalue] = null;
				return true;
			}
			default:{
				return super.receiveClientEvent(eventID, secondvalue);
			}
		}

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

		return this.hasCustomName() ? this.getCustomName() : "container.Upgator";
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void openChest() {
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.upgator.blockID, 6, power);
	}

	@Override
	public void closeChest() {
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.upgator.blockID, 6, power);
	}
	private int getFuel(){
		int fuel;
		if(inventory[FUEL] != null){
			switch(inventory[FUEL].itemID){
				case(351):{
					if(inventory[FUEL].getItemDamage() == 4){
						fuel = 5;
						break;
					}
					else{
						fuel = 0;
						break;
					}
				}
				case(331):{
					fuel = 1;
					break;
				}
				case(152):{
					fuel = 9;
					break;
				}
				case(22):{
					fuel = 45;
					break;
				}
				default:{
					fuel = 0;
					break;
				}
			}
		}
		else{
			fuel = 0;
		}
		if((power + fuel) < this.getMaxPower()){
			return fuel;
		}
		else{
			return 0;
		}
	}
	public boolean hasPower(){
		return power > 0;
	}
	/**
	 * Returns an integer between 0 and the passed value representing how close the current item is to being completely
	 * crafted
	 */

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {

		super.readFromNBT(nbtTagCompound);
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
	public int getPower()
	{
		return this.power;
	}
	/**
	 * Allows the entity to update its state. Overridden in most subclasses,
	 * e.g. the mob spawner uses this to count ticks and creates a new spawn
	 * inside its implementation.
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();
		int addingfuel = getFuel();
		if(addingfuel > 0){
			if(inventory[FUEL].stackSize >= 1){
				this.power += addingfuel;
				inventory[FUEL].stackSize --;
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.upgator.blockID, 6, power);
				int stack = FUEL;
				int id = inventory[stack].itemID;
				int count = inventory[stack].stackSize;
				int damage = inventory[stack].getItemDamage();
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.upgator.blockID, 2, stack);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.upgator.blockID, 3, id);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.upgator.blockID, 4, damage);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.upgator.blockID, 5, count);
			}
			else{
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.upgator.blockID, 7, FUEL);
			}
		}
		if(inventory[TileEntityUpgator.ITEMSTACK] != null && inventory[TileEntityUpgator.UPGRADE] != null && this.power > 50){
			if(inventory[TileEntityUpgator.UPGRADE].getItem() instanceof ItemUpgrade){
				if(UpgradeUtils.isItemStackUpgrableWithItem(inventory[TileEntityUpgator.ITEMSTACK], inventory[TileEntityUpgator.UPGRADE].getItem())){
					ItemUpgrade up = (ItemUpgrade)inventory[TileEntityUpgator.UPGRADE].getItem();
					UpgradeUtils.putUpgradeOnStack(up, inventory[TileEntityUpgator.ITEMSTACK]);
					this.subtractPower(50);
				}
			}
		}
		float offX = 0;
		float offY = 0.05F;
		float offZ = 0;
		this.worldObj.spawnParticle("tilecrack_" + this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) + "_" + 10, this.xCoord + offX + 0.5,  this.yCoord + offY + 1,  this.zCoord + offZ  + 0.5, 0, 0.2, 0);
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
