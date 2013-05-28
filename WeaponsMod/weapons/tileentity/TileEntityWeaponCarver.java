package weapons.tileentity;

import weapons.Weapons;
import weapons.crafting.WeaponCarverRecipiManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityWeaponCarver extends TileEntityPowerBase implements IInventory {

	public ItemStack[] inventory;
	private ItemStack[] oldInventory;

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

	private WeaponCarverRecipiManager recipies;
	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;
	private int slotid;
	private int itemid;
	private int itemdamage;
	private int powerDrainedCooldown;
	private ItemStack product;
	public boolean ison = false;

	public int currentWrokTime = 0;

	public TileEntityWeaponCarver() {

		super(true);
		inventory = new ItemStack[INVENTORY_SIZE];
		oldInventory = new ItemStack[INVENTORY_SIZE];
		recipies = new WeaponCarverRecipiManager(inventory);
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
				power = secondvalue;
				return true;
			}
			case(7):{
				this.inventory[secondvalue] = null;
				return true;
			}
			case(8):{
				this.currentWrokTime = secondvalue;
				return true;
			}
			case(9):{
				if(secondvalue == 1){
					this.ison = true;
				}
				else{
					this.ison = false;
				}
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

		return this.hasCustomName() ? this.getCustomName() : "container.weaponcarver";
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void openChest() {
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 6, power);
		for(int i = 0; i < this.INVENTORY_SIZE - 1; i ++){
			if(inventory[i]!= null){
				if(oldInventory[i] == null){
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
					if(inventory[i].equals(oldInventory[i])){
						int stack = i;
						int id = inventory[stack].itemID;
						int count = inventory[stack].stackSize;
						int damage = inventory[stack].getItemDamage();
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
					}
				}
			}
		}
		oldInventory = inventory;
	}

	@Override
	public void closeChest() {
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 6, power);
		for(int i = 0; i < this.INVENTORY_SIZE - 1; i ++){
			if(inventory[i]!= null){
				if(oldInventory[i] == null){
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
					if(inventory[i].equals(oldInventory[i])){
						int stack = i;
						int id = inventory[stack].itemID;
						int count = inventory[stack].stackSize;
						int damage = inventory[stack].getItemDamage();
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
					}
				}
			}
		}
		oldInventory = inventory;
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
		if((power + fuel) < 2000){
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
	public int getProgress(int par1)
	{
		return this.currentWrokTime * par1 / 20000;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {

		super.readFromNBT(nbtTagCompound);
		power = nbtTagCompound.getInteger("Power");
		currentWrokTime = nbtTagCompound.getInteger("WorkTime");
		ison = nbtTagCompound.getBoolean("IsWorking");
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
		nbtTagCompound.setInteger("Power", power);
		nbtTagCompound.setInteger("WorkTime", currentWrokTime);
		nbtTagCompound.setBoolean("IsWorking", ison);
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

	public boolean isWorking()
	{
		return this.currentWrokTime > 0;
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
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 6, power);
				int stack = FUEL;
				int id = inventory[stack].itemID;
				int count = inventory[stack].stackSize;
				int damage = inventory[stack].getItemDamage();
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
			}
			else{
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 7, FUEL);
			}
		}

		if(this.hasPower() && !this.ison){
			recipies.updateInventorySimp(inventory);
			if(recipies.doStacksCreateRecipy()){
				this.ison = true;
				this.product = recipies.output();
			}
		}
		if(this.product != null && inventory[OUTPUT] == null){
			recipies.updateInventorySimp(inventory);
			if(this.currentWrokTime > 99){
				if(recipies.doStacksCreateRecipy()){
					if(recipies.output() == this.product){
						for(int i = 0; i < 9; i++){
							if(inventory[i].stackSize > 1){
								inventory[i].stackSize --;
								int id = inventory[i].itemID;
								int count = inventory[i].stackSize;
								int damage = inventory[i].getItemDamage();
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, i);
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
							}
							else{
								inventory[i] = null;
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 7, i);
							}
						}
						inventory[OUTPUT] = this.product;
						this.product = null;
						this.ison = false;
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 9, 0);
					}
					else{
						this.currentWrokTime = 0;
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 8, 0);
						this.product = recipies.output();
					}
				}
				else{
					this.currentWrokTime = 0;
					worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 8, 0);
					this.ison = false;
					worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 9, 0);
				}
			}
			else{
				if(recipies.doStacksCreateRecipy()){
					if(recipies.output() == this.product){
						this.currentWrokTime ++;
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 8, this.currentWrokTime);
					}
					else{
						this.currentWrokTime = 0;
						worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 8, 0);
						this.product = recipies.output();
					}
				}
				else{
					this.currentWrokTime = 0;
					worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 8, 0);
					this.ison = false;
					worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 9, 0);
				}
			}
		}
		else if(this.product != null){
			this.currentWrokTime = 0;
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 8, 0);
		}

		if(powerDrainedCooldown > 40960){
			powerDrainedCooldown = 0;
			if(power > 0){
				power --;
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 6, power);
			}
			else{
				ison = false;
			}
		}
		else{
			if(this.ison){
				powerDrainedCooldown += 2048;
			}
		}
		if (++ticksSinceSync % 20 == 0) {
			if(oldInventory == null){
				oldInventory = inventory;
				return;
			}
			if(!this.worldObj.isRemote){
				for(int i = 0; i < this.INVENTORY_SIZE - 1; i ++){
					if(inventory[i]!= null){
						if(oldInventory[i] == null){
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
							if(inventory[i] != oldInventory[i]){
								int stack = i;
								int id = inventory[stack].itemID;
								int count = inventory[stack].stackSize;
								int damage = inventory[stack].getItemDamage();
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
								worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
							}
						}
					}
				}
				oldInventory = inventory;
			}
		}
		else{
			ticksSinceSync ++;
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
