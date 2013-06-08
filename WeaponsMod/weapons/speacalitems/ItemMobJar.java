package weapons.speacalitems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import weapons.ModItem;
import weapons.Weapons;
import weapons.utils.WorldUtils;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemMobJar extends ModItem{

	public ItemMobJar(int itemId)
	{
		super(itemId);
		this.setUnlocalizedName("weaponsMobJar");
		this.setCreativeTab(Weapons.weaponsTab);
		this.maxStackSize = 1;
		LanguageRegistry.addName(this,"Monster Jar");
	}

	@Override
	public void onCreated(ItemStack item, World par2World,
			EntityPlayer par3EntityPlayer)
	{
		super.onCreated(item, par2World, par3EntityPlayer);
		item.stackTagCompound.setBoolean("WeaponTagExtraHasEntity", false);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity entity)
	{
		if(!stack.stackTagCompound.getBoolean("WeaponTagExtraHasEntity")){
			if(entity instanceof EntityLiving){
				stack.stackTagCompound.setBoolean("WeaponTagExtraHasEntity", true);
				entity.writeToNBT(stack.stackTagCompound);
				stack.stackTagCompound.setString("WeaponTagExtraEntityName", entity.getEntityName());
				for(int i = 0; i < 250; i ++){
					WorldUtils.spawnPaticleWithOffset(entity.worldObj, "happyVillager", entity.posX, entity.posY + 1, entity.posZ, 0, 1, 0, 0.5, 2, 0.5);	
				}
				for(int i = 0; i < 10; i ++){
					WorldUtils.spawnPaticleWithOffset(entity.worldObj, "largeexplode", entity.posX, entity.posY + 1, entity.posZ, 1, 0, 0, 0.5, 2, 0.5);	
				}
				entity.setDead();
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y,
			int z, int par7, float par8, float par9, float par10)
	{
		if(!world.isRemote){
			if(item.stackTagCompound.getBoolean("WeaponTagExtraHasEntity")){
				Entity entity = EntityList.createEntityByName(item.stackTagCompound.getString("WeaponTagExtraEntityName"), world);
				if(entity != null){
					entity.worldObj = world;
					entity.readFromNBT(item.stackTagCompound);
					world.spawnEntityInWorld(entity);
					entity.setLocationAndAngles(x, y + 1, z, entity.rotationYaw, entity.rotationPitch);
					for(int i = 0; i < 250; i ++){
						WorldUtils.spawnPaticleWithOffset(entity.worldObj, "happyVillager", entity.posX, entity.posY + 1, entity.posZ, 0, 1, 0, 0.5, 2, 0.5);	
					}
					for(int i = 0; i < 10; i ++){
						WorldUtils.spawnPaticleWithOffset(entity.worldObj, "largeexplode", entity.posX, entity.posY + 1, entity.posZ, 1, 0, 0, 0.5, 2, 0.5);	
					}
					if(player.capabilities.isCreativeMode && !player.isSneaking()){
						return true;
					}
					item.stackTagCompound = new NBTTagCompound("WeaponItem");
					item.stackTagCompound.setBoolean("WeaponTagExtraHasEntity", false);
					return true;
				}
			}
		}
		else{
			if(item.stackTagCompound.getBoolean("WeaponTagExtraHasEntity")){
				Entity entity = EntityList.createEntityByName(item.stackTagCompound.getString("WeaponTagExtraEntityName"), world);
				if(entity != null){
					entity.worldObj = world;
					entity.readFromNBT(item.stackTagCompound);
					entity.posX = x;
					entity.posY = y + 1;
					entity.posZ = z;
					entity.worldObj.spawnEntityInWorld(entity);
					for(int i = 0; i < 250; i ++){
						WorldUtils.spawnPaticleWithOffset(entity.worldObj, "happyVillager", entity.posX, entity.posY + 1, entity.posZ, 0, 1, 0, 0.5, 2, 0.5);	
					}
					for(int i = 0; i < 10; i ++){
						WorldUtils.spawnPaticleWithOffset(entity.worldObj, "largeexplode", entity.posX, entity.posY + 1, entity.posZ, 1, 0, 0, 0.5, 2, 0.5);	
					}
					if(player.capabilities.isCreativeMode && !player.isSneaking()){
						return true;
					}
					item.stackTagCompound = new NBTTagCompound("WeaponItem");
					item.stackTagCompound.setBoolean("WeaponTagExtraHasEntity", false);
					return true;
				}
			}
		}
		return false;
	}



}
