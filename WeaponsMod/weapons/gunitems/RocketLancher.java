package weapons.gunitems;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import weapons.ModItem;
import weapons.Weapons;
import weapons.bullets.EntityRocket;
import weapons.client.RenderTick;

public class RocketLancher extends ModItem {

    protected Random rand;
    
	public RocketLancher(int par1)
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

        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Weapons.rocket1.itemID))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
            par3EntityPlayer.inventory.consumeInventoryItem(Weapons.rocket1.itemID);
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.click", 1.0F, 2);
        return par1ItemStack;
        
        
        
    }
	
	
	
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		EntityRocket var8 = new EntityRocket(par2World, par3EntityPlayer, 42 * 2.0F);
        par2World.playSoundAtEntity(par3EntityPlayer, "tile.piston.in", 1.0F, 5);
        
        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(var8);
            RenderTick.playerRecoil += 15;
        }
        else{
        	RenderTick.playerRecoil += 15;
        }
		
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
