package weapons.speacalitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import weapons.ModItem;
import weapons.entity.EntitySpeeder;

public class ItemSpeeder extends ModItem{


	public ItemSpeeder(int id)
	{
		super(id);
	}
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{

		EntitySpeeder entity = new EntitySpeeder(par2World, (double)((float)player.posX + 0.5F), (double)((float)player.posY + 1.0F), (double)((float)player.posZ + 0.5F));
		entity.rotationYaw = (float)(((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90);


		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(entity);
		}

		if (!player.capabilities.isCreativeMode)
		{
			--par1ItemStack.stackSize;
		}


		return par1ItemStack;
	}

}
