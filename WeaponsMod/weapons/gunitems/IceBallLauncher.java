package weapons.gunitems;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import weapons.ModItem;
import weapons.Weapons;
import weapons.bullets.EnityIceBall;
import weapons.client.RenderTick;
import weapons.server.SaveIceBalls;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IceBallLauncher extends ModItem {

	protected Random rand;
	public boolean fireing;
	private Map<String, Integer> iceballs = null;
	private String username = "";

	public IceBallLauncher(int par1)
	{
		super(par1);
	}
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{
		if(SaveIceBalls.hasLoaded){
			System.out.println("load");
			iceballs = Weapons.iceBalls;
			username = player.username;
			if(iceballs.get(username) !=null){
				System.out.println("username");
				int fuelint = iceballs.get(username);
				if (player.capabilities.isCreativeMode || dosePlayerHaveFule(fuelint))
				{
					System.out.println("has fule");

					if (!par2World.isRemote)
					{
						System.out.println("world ! remot");
						if(updateFule(username, -1)){
							EnityIceBall var8 = new EnityIceBall(par2World, player);
							par2World.spawnEntityInWorld(var8);
							System.out.println("entityspawned");
						}
						else{
							System.out.println("error while subtrating fule");
						}
					}
					else{
						RenderTick.playerRecoil += 1;
					}
				}
				else{
					if (player.inventory.hasItem(Item.bucketLava.itemID) && !par2World.isRemote){
						player.inventory.consumeInventoryItem(Item.bucketWater.itemID);
						ItemStack bucket = new ItemStack(Item.bucketEmpty);
						player.inventory.addItemStackToInventory(bucket);
						if(updateFule(player.username, 10)){

						}
						else{
							System.out.println("error while adding fule");
						}
					}

				}
			}
			else{
				if (player.inventory.hasItem(Item.bucketLava.itemID) && !par2World.isRemote){
					player.inventory.consumeInventoryItem(Item.bucketWater.itemID);
					ItemStack bucket = new ItemStack(Item.bucketEmpty);
					player.inventory.addItemStackToInventory(bucket);
					if(updateFule(player.username, 10)){

					}
					else{
						System.out.println("error while adding fule");
					}
				}

			}
			Weapons.fTFuel = iceballs;
		}
		ArrowNockEvent event = new ArrowNockEvent(player, par1ItemStack);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return event.result;
		}

		return par1ItemStack;



	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if(SaveIceBalls.hasLoaded){
			iceballs = Weapons.iceBalls;
		}
		//		EntityPlayer p = null;
		//		if(par3Entity instanceof EntityPlayer){
		//			p = (EntityPlayer) par3Entity;
		//		}
		//		if(p != null){
		//		p.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		//		}

	}

	private boolean updateFule(String username, int value)
	{
		if(iceballs.get(username) != null){
			int fuelLevel = iceballs.get(username);
			fuelLevel += value;
			iceballs.put(username, fuelLevel);
			return true;
		}
		else{
			iceballs.put(username, value);
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
		if(iceballs != null){
			if(iceballs.get(username) != null){
				fuelLevel = iceballs.get(username);
			}

			if(fuelLevel <= 2){
				color = EnumChatFormatting.DARK_RED;
			}
			else if(fuelLevel <= 400 && fuelLevel > 2){
				color = EnumChatFormatting.GOLD;
			}
			else if(fuelLevel <= 600 && fuelLevel > 4){
				color = EnumChatFormatting.YELLOW;
			}
			else if(fuelLevel <= 800 && fuelLevel > 6){
				color = EnumChatFormatting.GREEN;
			}
			else if(fuelLevel <= 1000 && fuelLevel > 8){
				color = EnumChatFormatting.DARK_GREEN;
			}
			if(iceballs.get(par2EntityPlayer.username) != null && iceballs.isEmpty() != true){
				par3List.add(color + "\u00A7o" + "IceBalls: " + Integer.toString(fuelLevel) + "/10");
			}
			else{
				par3List.add(color + "\u00A7o" + "IceBalls Are Unloaded Right Click To Load");
			}
		}
		else{
			color = EnumChatFormatting.AQUA;
			par3List.add(color + "\u00A7o" + "IceBalls Are Not Supported In Multiplayer Yet!");
		}
	}



}
