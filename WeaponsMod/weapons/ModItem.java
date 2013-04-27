package weapons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ModItem extends Item {
	private int itemid;

	public ModItem(int par1){
		super(par1);
		itemid = par1;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		int id = itemid - Weapons.bulletid + 256;
	         itemIcon = iconRegister.registerIcon("weapons:" + id);
	         System.out.println("Item icon registed to: " + id);
	}
	
}
