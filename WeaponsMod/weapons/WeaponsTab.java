package weapons;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class WeaponsTab extends CreativeTabs{

	public WeaponsTab(String label) {
	    super(label);
	}
	public ItemStack getIconItemStack() {
	    return new ItemStack(Weapons.pisol1);
	}
	
}
