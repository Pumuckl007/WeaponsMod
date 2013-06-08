package weapons.entity;

import java.util.Calendar;

import weapons.Weapons;
import weapons.power.PowerUtils;
import weapons.utils.EnumRobot;
import weapons.utils.WorldUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTwardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityRobot extends EntityGolem
{
	protected String owner;
	private int power = EnumRobot.StartPower.getValue();
	private int maxPower = EnumRobot.MaxPower.getValue();
	public ItemStack[] inventory;
	public EntityRobot(World par1World)
	{
		super(par1World);
		this.moveSpeed = 0.23F;
		this.getNavigator().setBreakDoors(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIBreakDoor(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
		this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
		this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
		this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, false));
		this.setSize(1.4F, 2F);
		inventory = new ItemStack[EnumRobot.InventorySize.getValue()];
	}
	public int getPower(){
		return this.power;
	}
	protected int func_96121_ay()
	{
		return 40;
	}

	@Override
	public boolean canBePushed()
	{
		// TODO Auto-generated method stub
		return power > 0;
	}
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}
	public float getSpeedModifier()
	{
		return super.getSpeedModifier() * (this.isChild() ? 1.5F : 1.0F);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		this.getDataWatcher().addObject(13, Byte.valueOf((byte)0));
		this.getDataWatcher().addObject(14, Byte.valueOf((byte)0));
	}

	public int getMaxHealth()
	{
		return 200;
	}

	/**
	 * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	 */
	public int getTotalArmorValue()
	{
		int i = super.getTotalArmorValue() + 2;

		if (i > 20)
		{
			i = 20;
		}

		return i;
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	protected boolean isAIEnabled()
	{
		return true;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	public void onLivingUpdate()
	{
		for(int i = 0; i < 50; i ++){
			WorldUtils.spawnPaticleWithOffset(this.worldObj, "reddust", this.posX, this.posY + 0.4, this.posZ, 0, 255, 255, 0.4, 0.2, 0.4);
		}
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild())
		{
			float f = this.getBrightness(1.0F);

			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				boolean flag = true;
				ItemStack itemstack = this.getCurrentItemOrArmor(4);

				if (itemstack != null)
				{
					if (itemstack.isItemStackDamageable())
					{
						itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + this.rand.nextInt(2));

						if (itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage())
						{
							this.renderBrokenItemStack(itemstack);
							this.setCurrentItemOrArmor(4, (ItemStack)null);
						}
					}

					flag = false;
				}

				if (flag)
				{
					PowerUtils.addPower(this.power, 1, this.maxPower);
				}
			}
		}

		super.onLivingUpdate();
	}
	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{

		super.onUpdate();
	}

	public boolean attackEntityAsMob(Entity par1Entity)
	{
		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag && this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float)this.worldObj.difficultySetting * 0.3F)
		{
			par1Entity.setFire(2 * this.worldObj.difficultySetting);
		}

		return flag;
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	public int getAttackStrength(Entity par1Entity)
	{
		ItemStack itemstack = this.getHeldItem();
		float f = (float)(this.getMaxHealth() - this.getHealth()) / (float)this.getMaxHealth();
		int i = 3 + MathHelper.floor_float(f * 4.0F);

		if (itemstack != null)
		{
			i += itemstack.getDamageVsEntity(this);
		}

		return i;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		return "mob.enderdragon.growl";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.irongolem.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.irongolem.death";
	}

	/**
	 * Plays step sound at given x, y, z for the entity
	 */
	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	public int getDropItemId()
	{
		return Weapons.robot.itemID;
	}
	public void setOwner(String owner){
		this.owner = owner;
	}
	public String getOwner(){
		return this.owner;
	}
	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEFINED;
	}

	protected void dropRareDrop(int par1)
	{
		switch (this.rand.nextInt(3))
		{
			case 0:
				this.dropItem(Item.ingotIron.itemID, 1);
				break;
			case 1:
				this.dropItem(Item.carrot.itemID, 1);
				break;
			case 2:
				this.dropItem(Item.potato.itemID, 1);
		}
	}

	/**
	 * Makes entity wear random armor based on difficulty
	 */
	 protected void addRandomArmor()
	{
		super.addRandomArmor();

		if (this.rand.nextFloat() < (this.worldObj.difficultySetting == 3 ? 0.05F : 0.01F))
		{
			int i = this.rand.nextInt(3);

			if (i == 0)
			{
				this.setCurrentItemOrArmor(0, new ItemStack(Item.swordIron));
			}
			else
			{
				this.setCurrentItemOrArmor(0, new ItemStack(Item.shovelIron));
			}
		}
	}

	 /**
	  * (abstract) Protected helper method to write subclass entity data to NBT.
	  */
	 public void writeEntityToNBT(NBTTagCompound nbt)
	 {
		 super.writeEntityToNBT(nbt);
		 nbt.setString("Owner", owner);
		 nbt.setShort("Power", (short)power);
		 nbt.setShort("MaxPower", (short)maxPower);
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
	 }

	 /**
	  * (abstract) Protected helper method to read subclass entity data from NBT.
	  */
	 public void readEntityFromNBT(NBTTagCompound nbt)
	 {
		 super.readEntityFromNBT(nbt);
		 this.owner = nbt.getString("Owner");
		 this.power = (int)nbt.getShort("Power");
		 this.maxPower = (int)nbt.getShort("MaxPower");
		// Read in the ItemStacks in the inventory from NBT
			NBTTagList tagList = nbt.getTagList("Items");
			inventory = new ItemStack[EnumRobot.InventorySize.getValue()];
			for (int i = 0; i < tagList.tagCount(); ++i) {
				NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
				byte slot = tagCompound.getByte("Slot");
				if (slot >= 0 && slot < inventory.length) {
					inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
				}
			}
	 }

	 /**
	  * This method gets called when the entity kills another one.
	  */
	 public void onKillEntity(EntityLiving par1EntityLiving)
	 {
		 super.onKillEntity(par1EntityLiving);
	 }

	 /**
	  * Initialize this creature.
	  */
	 public void initCreature()
	 {
		 this.setCanPickUpLoot(this.rand.nextFloat() < pickUpLootProability[this.worldObj.difficultySetting]);

		 this.addRandomArmor();
		 this.func_82162_bC();

		 if (this.getCurrentItemOrArmor(4) == null)
		 {
			 Calendar calendar = this.worldObj.getCurrentDate();

			 if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			 {
				 this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Block.pumpkinLantern : Block.pumpkin));
				 this.equipmentDropChances[4] = 0.0F;
			 }
		 }
	 }

	 /**
	  * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
	  */
	 public boolean interact(EntityPlayer player)
	 {
		 player.getCurrentEquippedItem();

		 return false;
	 }

	 @SideOnly(Side.CLIENT)
	 public void handleHealthUpdate(byte par1)
	 {
		 for(int i = 0; i < 35; i ++){
			 WorldUtils.spawnPaticleWithOffset(this.worldObj, "angryVillager", this.posX, this.posY + 2, this.posZ, 0, 0, 0, 0.25, 0.25, 0.25);
		 }
		 if (par1 == 16)
		 {
			 this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
		 }
		 else
		 {
			 super.handleHealthUpdate(par1);
		 }
	 }

	 /**
	  * Determines if an entity can be despawned, used on idle far away entities
	  */
	 protected boolean canDespawn()
	 {
		 return false;
	 }
}
