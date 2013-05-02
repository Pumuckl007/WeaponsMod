package weapons.gunitems;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import weapons.ModItem;
import weapons.Weapons;
import weapons.bullets.EntityBullet;
import weapons.client.RenderTick;

public class ScarH extends ModItem {

	protected Random rand;
	public boolean fireing;
	private int cooldown;

	public ScarH(int par1)
	{
		super(par1);
		// TODO Auto-generated constructor stub
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
		if(fireing){
			if(par3Entity instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer) par3Entity;
				if (player.capabilities.isCreativeMode || player.inventory.hasItem(Weapons.bullet1.itemID))
				{
					if(cooldown <= 0){
						cooldown += 2;
						player.inventory.consumeInventoryItem(Weapons.bullet1.itemID);
						par2World.playSoundAtEntity(player, "random.explode", 1.0F, 1.0F / (10F) + 5);
						if (!par2World.isRemote)
						{
							EntityBullet var8 = new EntityBullet(par2World, player, 42 * 2.0F, 5);
							par2World.spawnEntityInWorld(var8);
							RenderTick.playerRecoil += 2;
						}
						else{
							RenderTick.playerRecoil += 2;
						}
					}
					else{
						cooldown --;
					}
				}
			}
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		fireing = false;
	}
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		// TODO Auto-generated method stub
		return 7200;
	}
	@Override
	public int getItemEnchantability()
	{
		return 0;
	}



}
