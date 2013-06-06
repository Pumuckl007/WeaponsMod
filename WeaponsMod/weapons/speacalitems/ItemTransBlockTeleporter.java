package weapons.speacalitems;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import weapons.Weapons;
import weapons.power.PowerItemUtils;
import weapons.utils.Color;

public class ItemTransBlockTeleporter extends ItemChargable{

	public ItemTransBlockTeleporter(int par1)
	{
		super(par1, 2000, false, true, EnumAction.bow);
		this.setCreativeTab(Weapons.weaponsTab);
		this.setUnlocalizedName("TransBlockTelepoerter");
		this.maxStackSize = 1;
		LanguageRegistry.addName(this,"Trans Block Teleporter");
	}

	public int getTeare(ItemStack item){
		return item.stackTagCompound.getInteger("Teare");
	}
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		if(item.hasTagCompound()){
			if(player.isSneaking()){
				PowerItemUtils.addPower(1, item);
				player.swingItem();
			}
			else{
				if(item.stackTagCompound.hasKey("Teare") && item.stackTagCompound.hasKey("Power")){
					float accel = 0;
					int power = 0;
					switch(item.stackTagCompound.getInteger("Teare")){
						case(1):{
							accel = 2.5F;
							power = 100;
							break;
						}
						case(2):{
							accel = 7;
							power = 200;
							break;
						}
						case(3):{
							accel = 20;
							power = 250;
							break;
						}
						default:{
							player.addChatMessage("The NBT Data of the item was modifide incorectly!");
							if(item.stackTagCompound.hasKey("Teare")){
								player.addChatMessage("NBT Data: " + item.stackTagCompound.getInteger("Teare") + " ,Item: TransBlockTeleport");
							}
							else{
								player.addChatMessage("NBT Data: 0 ,Item: TransBlockTeleport");	
							}
							player.addChatMessage("This is a bug pleas report it to the mod author.");
						}
					}
					if(PowerItemUtils.subtractPower(power, item) && accel > 0){
						float volocityX;
						float volocityY;
						float volocityZ;
						volocityX = (float) Math.cos(Math.toRadians(player.rotationPitch)) * (float) Math.sin(Math.toRadians(player.rotationYawHead) + Math.PI) * accel;
						volocityZ = (float) Math.cos(Math.toRadians(player.rotationPitch)) * (float) Math.cos(Math.toRadians(player.rotationYawHead)) * accel;
						volocityY = (float) Math.sin(Math.toRadians(player.rotationPitch) + Math.PI) * accel;
						player.noClip = true;
						player.moveEntity(volocityX, volocityY, volocityZ);
						player.noClip = false;
						//						player.playSound("mob.wither.idle", 1, 1);
						world.playAuxSFXAtEntity(player, 1014, (int)player.posX, (int)player.posY, (int)player.posZ, 0);
					}
				}
			}
		}
		else{
			this.onCreated(item, world, player);
			return this.onItemRightClick(item, world, player);
		}
		return item;
	}

	@Override
	public void onUpdate(ItemStack item, World par2World,
			Entity par3Entity, int par4, boolean par5)
	{
		super.onUpdate(item, par2World, par3Entity, par4, par5);
		if(!item.hasTagCompound()){
			if(par3Entity instanceof EntityPlayer){
				this.onCreated(item, par2World, (EntityPlayer)par3Entity);
			}
		}
	}

	@Override
	public void onCreated(ItemStack item, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onCreated(item, par2World, par3EntityPlayer);
		NBTTagCompound tag = item.stackTagCompound;
		if(!tag.hasKey("Teare")){
			tag.setInteger("Teare", 1);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean par4)
	{
		if(item.hasTagCompound()){
			if(item.stackTagCompound.hasKey("Teare")){
				switch(item.stackTagCompound.getInteger("Teare")){
					case(1):{
						list.add(Color.DARK_GREEN +"Tier One");
						break;
					}
					case(2):{
						list.add(Color.AQUA +"Tier Two");
						break;
					}
					case(3):{
						list.add(Color.DARK_PURPLE +"Tier Three");
						break;
					}
				}
			}
		}
		super.addInformation(item, player, list, par4);
	}


}
