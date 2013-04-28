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
import weapons.bullets.EntityBullet;
import weapons.client.ClientTick;

public class Pistol extends ModItem {

    protected Random rand;
    
	public Pistol(int par1)
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

        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Weapons.bullet1.itemID))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
            par3EntityPlayer.inventory.consumeInventoryItem(Weapons.bullet1.itemID);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.explode", 1.0F, 1.0F / (10F) + 5);
            if (!par2World.isRemote)
            {
                EntityBullet var8 = new EntityBullet(par2World, par3EntityPlayer, 42 * 2.0F, 10);
                par2World.spawnEntityInWorld(var8);
                ClientTick.playerRecoil += 10;
            }
            else{
            	ClientTick.playerRecoil += 10;
            }
        }
        
        return par1ItemStack;
        
        
        
    }
	@Override
	public int getItemEnchantability()
	{
		return 0;
	}
	
	
	
}
