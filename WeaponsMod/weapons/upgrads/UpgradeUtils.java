package weapons.upgrads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UpgradeUtils {
	public static Map<Item, String> itemsUpgrads = new HashMap<Item, String>();
	public static Map<Item, Item> items = new HashMap<Item, Item>();
	public static ArrayList<Item> speacals = new ArrayList<Item>();
	public static ItemStack putUpgradeOnStack(ItemUpgrade upGradeItem, ItemStack item){
		if(items.containsKey(upGradeItem)){
			Item upGrade = items.get(upGradeItem);
			if(itemsUpgrads.containsKey(upGrade)){
				if(item.hasTagCompound()){
					if(!item.stackTagCompound.hasKey(itemsUpgrads.get(upGrade))){
						if(speacals.contains(upGrade)){
							item.stackTagCompound.setInteger(itemsUpgrads.get(upGrade), upGradeItem.getSpeacalInt());
						}
						item.stackTagCompound.setBoolean(itemsUpgrads.get(upGrade), true);
					}
				}
				else{
					item.setTagCompound(new NBTTagCompound(item.getItem().getUnlocalizedName()));
					item = UpgradeUtils.putUpgradeOnStack(upGradeItem, item);
				}
			}
		}
		return item;
	}
	public static boolean isItemStackUpgrableWithItem(ItemStack itemToUpgrade, Item itemToUpgradeWith){
		if(items.containsKey(itemToUpgradeWith)){
			Item upGrade = items.get(itemToUpgradeWith);
			if(itemsUpgrads.containsKey(upGrade)){
				if(itemToUpgrade.hasTagCompound()){
					if(!itemToUpgrade.stackTagCompound.hasKey(itemsUpgrads.get(upGrade))){
						return true;
					}
				}
				else{
					itemToUpgrade.setTagCompound(new NBTTagCompound(itemToUpgrade.getItem().getUnlocalizedName()));
					return true;
				}
			}
		}
		return false;
	}
}
