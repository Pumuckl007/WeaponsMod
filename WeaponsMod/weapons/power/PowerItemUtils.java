package weapons.power;

import net.minecraft.item.ItemStack;

public class PowerItemUtils {
	public static boolean hasPower(ItemStack item){
		return item.stackTagCompound.getInteger("Power") > 0;
	}
	public static boolean canRecivePower(ItemStack item){
		return item.stackTagCompound.getInteger("Power") < item.stackTagCompound.getInteger("MaxPower");
	}

	public static int getMaxPower(ItemStack item){
		return item.stackTagCompound.getInteger("MaxPower");
	}

	public static boolean addPower(int power,ItemStack item){
		if((item.stackTagCompound.getInteger("Power") + power) <=item.stackTagCompound.getInteger("MaxPower")){
			item.stackTagCompound.setInteger("Power", item.stackTagCompound.getInteger("Power") + power);
			return true;
		}
		return false;
	}
	public static boolean subtractPower(int power,ItemStack item){
		if((item.stackTagCompound.getInteger("Power") - power) >= 0){
			item.stackTagCompound.setInteger("Power", item.stackTagCompound.getInteger("Power") - power);
			return true;
		}
		return false;
	}

	public static int getPower(ItemStack item){
		return item.stackTagCompound.getInteger("Power");
	}
}
