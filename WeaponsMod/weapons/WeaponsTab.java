package weapons;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class WeaponsTab extends CreativeTabs{

	public WeaponsTab(String label) {
	    super(label);
	}
	public ItemStack getIconItemStack() {
		ItemStack item = new ItemStack(Weapons.weaponCarver);
	    return item;
	}
	
}
