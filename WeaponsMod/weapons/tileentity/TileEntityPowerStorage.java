package weapons.tileentity;

import weapons.Weapons;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityPowerStorage extends TileEntityPowerBase implements IInventory{

	private final int INVENTORY_SIZE = 2;
	public static final int OUTPUT = 0;
	public static final int INPUT = 1;
	public ItemStack[] inventory;
	private int slotid;
	private int itemid;
	private int itemdamage;
	public TileEntityPowerStorage()
	{
		super(false);
		inventory = new ItemStack[INVENTORY_SIZE];
		this.MAXPOWER = 100000;
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

		return this.hasCustomName() ? this.getCustomName() : "PowerStorage";
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void openChest() {
		for(int i = 0; i < 2; i ++){
			if(inventory[i]!= null){
				int stack = i;
				int id = inventory[stack].itemID;
				int count = inventory[stack].stackSize;
				int damage = inventory[stack].getItemDamage();
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
			}
			else{
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID,7, i);
			}
		}
	}

	@Override
	public void closeChest() {
		for(int i = 0; i < 2; i ++){
			if(inventory[i]!= null){
				int stack = i;
				int id = inventory[stack].itemID;
				int count = inventory[stack].stackSize;
				int damage = inventory[stack].getItemDamage();
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
			}
			else{
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID,7, i);
			}
		}
	}
	private int getFuel(){
		int fuel;
		if(inventory[INPUT] != null){
			switch(inventory[INPUT].itemID){
				case(351):{
					if(inventory[INPUT].getItemDamage() == 4){
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
			case(7):{
				this.inventory[secondvalue] = null;
			}
			default:{
				return super.receiveClientEvent(eventID, secondvalue);
			}
		}

	}
	/**
	 * Allows the entity to update its state. Overridden in most subclasses,
	 * e.g. the mob spawner uses this to count ticks and creates a new spawn
	 * inside its implementation.
	 */
	@Override
	public void updateEntity(){
		int addingfuel = getFuel();
		if(addingfuel > 0){
			if(inventory[INPUT].stackSize >= 1){
				this.power += addingfuel;
				inventory[INPUT].stackSize --;
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 6, power);
				int stack = INPUT;
				int id = inventory[stack].itemID;
				int count = inventory[stack].stackSize;
				int damage = inventory[stack].getItemDamage();
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
			}
			else{
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 7, INPUT);
			}
		}
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID,6, power);
		super.updateEntity();
		for(int i = 0; i < 2; i ++){
			if(inventory[i]!= null){
				int stack = i;
				int id = inventory[stack].itemID;
				int count = inventory[stack].stackSize;
				int damage = inventory[stack].getItemDamage();
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
			}
			else{
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID,7, i);
			}
		}
	}
	@Override
	public boolean isInvNameLocalized() {

		return this.hasCustomName();
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {

		return true;
	}

}
