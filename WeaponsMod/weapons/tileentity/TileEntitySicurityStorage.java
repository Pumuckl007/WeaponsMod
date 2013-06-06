package weapons.tileentity;

import java.util.Random;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import weapons.Weapons;


public class TileEntitySicurityStorage extends TileBase implements IInventory {

	/** The current angle of the chest lid (between 0 and 1) */
	public float lidAngle;

	/** The angle of the chest lid last tick */
	public float prevLidAngle;

	/** The number of players currently using this chest */
	public int numUsingPlayers;

	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;
	private int slotid;
	private int itemid;
	private int itemdamage;
	private int power = 0;
	private int powerDrainedCooldown;
	protected Random rand;
	public boolean ison = true;

	public static final int INVENTORY_SIZE = 8 * 4;

	/**
	 * The ItemStacks that hold the items currently being used
	 */
	public ItemStack[] inventory;
	private ItemStack[] oldInventory;

	public TileEntitySicurityStorage() {

		super();
		inventory = new ItemStack[34];
		oldInventory = new ItemStack[34];
		rand = new Random();
	}

	@Override
	public int getSizeInventory() {

		return inventory.length;
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

		if (inventory[slot] != null) {
			ItemStack itemStack = inventory[slot];
			inventory[slot] = null;
			return itemStack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {

		inventory[slot] = itemStack;

		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {

		return this.hasCustomName() ? this.getCustomName() : "SicurityStorage";
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	/**
	 * Called when a client event is received with the event number and
	 * argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int eventID, int secondvalue) {
		switch(eventID){
			case(1):{
				this.numUsingPlayers = secondvalue;
				return true;
			}
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
			}
			default:{
				return super.receiveClientEvent(eventID, secondvalue);
			}
		}

	}

	@Override
	public void openChest() {

		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 1, numUsingPlayers);
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 6, power);
		for(int i = 0; i < 33; i ++){
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

		--numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 1, numUsingPlayers);
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 6, power);
		for(int i = 0; i < 33; i ++){
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
		if(inventory[33] != null){
			switch(inventory[33].itemID){
				case(351):{
					if(inventory[33].getItemDamage() == 4){
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
	/**
	 * Allows the entity to update its state. Overridden in most subclasses,
	 * e.g. the mob spawner uses this to count ticks and creates a new spawn
	 * inside its implementation.
	 */
	@Override
	public void updateEntity() {
		int addingfuel = getFuel();
		if(addingfuel > 0){
			if(inventory[33].stackSize >= 1){
				this.power += addingfuel;
				inventory[33].stackSize --;
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 6, power);
				int stack = 33;
				int id = inventory[stack].itemID;
				int count = inventory[stack].stackSize;
				int damage = inventory[stack].getItemDamage();
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 2, stack);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 3, id);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 4, damage);
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 5, count);
			}
			else{
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 7, 33);
			}
		}
		super.updateEntity();
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
			powerDrainedCooldown += this.getNumberofItems();
		}
		if (++ticksSinceSync % 20 == 0) {
			if(oldInventory == null){
				oldInventory = inventory;
				return;
			}
			if(!this.worldObj.isRemote){
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 1, numUsingPlayers);
				for(int i = 0; i < 33; i ++){
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
		ticksSinceSync ++;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {

		super.readFromNBT(nbtTagCompound);

		if (nbtTagCompound.hasKey("Power")) {
			power = nbtTagCompound.getInteger("Power");
		}
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

	public int getNumberofItems(){
		int numitems = 0;
		for(int i = 0; i < 32; i++){
			if(inventory[i] != null){
				numitems += inventory[i].stackSize;
			}
		}
		return numitems;
	}
	@Override
	public boolean isInvNameLocalized() {

		return this.hasCustomName();
	}

	@Override
	public boolean isStackValidForSlot(int side, ItemStack itemStack) {

		return true;
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(super.toString());

		stringBuilder.append("TileEntitySS Data - ");
		for (int i = 0; i < inventory.length; i++) {
			if (i != 0) {
				stringBuilder.append(", ");
			}

			if (inventory[i] != null) {
				stringBuilder.append(String.format("inventory[%d]: %s", i, inventory[i].toString()));
			}
			else {
				stringBuilder.append(String.format("inventory[%d]: empty", i));
			}
		}
		stringBuilder.append("\n");

		return stringBuilder.toString();
	}
	public int getPowerLevel(){
		return power;
	}
}
