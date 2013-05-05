package weapons.gunitems;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import weapons.ModItem;
import weapons.Weapons;
import weapons.client.RenderTick;
import weapons.server.SaveFTStats;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FT extends ModItem {

	protected Random rand;
	public boolean fireing;
	private int cooldown;
	private Map<String, Integer> fuel = null;
	private String username = "";

	public FT(int par1)
	{
		super(par1);
	}
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{


		ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return event.result;
		}
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		fireing = true;

		return par1ItemStack;



	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
//		EntityPlayer p = null;
//		if(par3Entity instanceof EntityPlayer){
//			p = (EntityPlayer) par3Entity;
//		}
//		if (Mouse.isButtonDown(2)) {
//			fireing = true;
//		}
//		if(p != null){
//		p.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
//		}
		if(SaveFTStats.hasLoaded){
			fuel = Weapons.fTFuel;
			if(fireing){
				if(par3Entity instanceof EntityPlayer){
					EntityPlayer player = (EntityPlayer) par3Entity;
					username = player.username;
					if(fuel.get(username) !=null){
						int fuelint = fuel.get(username);
						if (player.capabilities.isCreativeMode || dosePlayerHaveFule(fuelint))
						{
							if(cooldown <= 0){
								cooldown += 3;
								//						player.inventory.consumeInventoryItem(Weapons.bullet1.itemID);

								if (!par2World.isRemote)
								{
									if(updateFule(username, -1)){
										float volocityX;
										float volocityY = 0;
										float volocityZ = 0;
										float offX = 0.95F + (float)(Math.random() * ((1.05 - 0.95) + 1));
										float offY = 0.95F + (float)(Math.random() * ((1.05 - 0.95) + 1));
										float offZ = 0.95F + (float)(Math.random() * ((1.05 - 0.95) + 1)); 
										volocityX = (float) Math.cos(Math.toRadians(player.rotationPitch)) * (float) Math.sin(Math.toRadians(player.rotationYawHead) + Math.PI) * offX;
										volocityZ = (float) Math.cos(Math.toRadians(player.rotationPitch)) * (float) Math.cos(Math.toRadians(player.rotationYawHead)) * offZ;
										volocityY = (float) Math.sin(Math.toRadians(player.rotationPitch) + Math.PI) * offY;
										double startX = player.posX + volocityX;
										double startY = player.posY + volocityY + 1;
										double startZ = player.posZ + volocityZ;
										EntitySmallFireball var8 = new EntitySmallFireball(par2World, startX, startY, startZ, volocityX, volocityY, volocityZ);
										par2World.spawnEntityInWorld(var8);
										RenderTick.playerRecoil += 1;							}
									else{
										System.out.println("error while subtrating fule");
									}
								}
								else{
									par2World.playAuxSFXAtEntity((EntityPlayer)player, 1009, (int)par3Entity.posX, (int)par3Entity.posY, (int)par3Entity.posZ, 0);
									RenderTick.playerRecoil += 1;
								}
							}
							else{
								cooldown --;
							}
						}
						else{
							if (player.inventory.hasItem(Item.bucketLava.itemID) && !par2World.isRemote){
								player.inventory.consumeInventoryItem(Item.bucketLava.itemID);
								ItemStack bucket = new ItemStack(Item.bucketEmpty);
								player.inventory.addItemStackToInventory(bucket);
								if(updateFule(player.username, 1000)){

								}
								else{
									System.out.println("error while adding fule");
								}
							}

						}
						Weapons.fTFuel = fuel;
					}
					else{
						if (player.inventory.hasItem(Item.bucketLava.itemID) && !par2World.isRemote){
							player.inventory.consumeInventoryItem(Item.bucketLava.itemID);
							ItemStack bucket = new ItemStack(Item.bucketEmpty);
							player.inventory.addItemStackToInventory(bucket);
							if(updateFule(player.username, 1000)){

							}
							else{
								System.out.println("error while adding fule");
							}
						}
					}
				}
			}
		}
	}

	private boolean updateFule(String username, int value)
	{
		if(fuel.get(username) != null){
			int fuelLevel = fuel.get(username);
			fuelLevel += value;
			fuel.put(username, fuelLevel);
			return true;
		}
		else{
			fuel.put(username, value);
			return true;
		}
	}
	private boolean dosePlayerHaveFule(int fuel)
	{
		return fuel > 0;
	}
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		fireing = false;
	}
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 2147399999;
	}
	@Override
	public int getItemEnchantability()
	{
		return 0;
	}
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, @SuppressWarnings("rawtypes") List par3List, boolean par4)
	{
		EnumChatFormatting color = EnumChatFormatting.DARK_RED;
		int fuelLevel = 0;
		if(fuel != null){
		if(fuel.get(username) != null){
			fuelLevel = fuel.get(username);
		}
		
		if(fuelLevel <= 200){
			color = EnumChatFormatting.DARK_RED;
		}
		else if(fuelLevel <= 400 && fuelLevel > 200){
			color = EnumChatFormatting.GOLD;
		}
		else if(fuelLevel <= 600 && fuelLevel > 400){
			color = EnumChatFormatting.YELLOW;
		}
		else if(fuelLevel <= 800 && fuelLevel > 600){
			color = EnumChatFormatting.GREEN;
		}
		else if(fuelLevel <= 1000 && fuelLevel > 800){
			color = EnumChatFormatting.DARK_GREEN;
		}
		if(fuel.get(par2EntityPlayer.username) != null && fuel.isEmpty() != true){
			par3List.add(color + "\u00A7o" + "Fuel Level: " + Integer.toString(fuelLevel) + "/1000");
		}
		else{
			par3List.add(color + "\u00A7o" + "Fuel Is Unloaded Right Click To Load");
		}
		}
		else{
			color = EnumChatFormatting.AQUA;
			par3List.add(color + "\u00A7o" + "Fuel Is Not Supported In Multiplayer Yet!");
		}
	}



}
