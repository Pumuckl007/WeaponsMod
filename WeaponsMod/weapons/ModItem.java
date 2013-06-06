package weapons;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModItem extends Item {
	private boolean allwaysUp = false;
	private EnumAction animation = EnumAction.none;
	public ModItem(int itemId){
		super(itemId);
	}
	public ModItem(int itemId, boolean allwaysUp, EnumAction animation){
		super(itemId);
		this.allwaysUp = allwaysUp;
		this.animation = animation;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return this.animation;
	}
	
	
	@Override
	public void onUpdate(ItemStack item, World par2World,
			Entity entity, int par4, boolean par5)
	{
		
		if(entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entity;
			if(this.allwaysUp){
//				player.setItemInUse(item, 1);
				player.limbSwing = 90;
			}
//			if(player.isEating() && !this.allwaysUp){
//				player.setEating(false);
//			}
		}
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		String id = this.getUnlocalizedName();
	         itemIcon = iconRegister.registerIcon("weapons:" + id);
	         System.out.println("Item icon registed to: " + id);
	}
	
}
