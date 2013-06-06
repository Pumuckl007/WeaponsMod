package weapons.speacalitems;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class ItemChargable extends weapons.ModItem{

	protected int power = 0;
	protected int MAXPOWER = 10000;
	protected boolean canDischarge;

	public ItemChargable(int id, int maxpower) {
		super(id);
		MAXPOWER = maxpower;
		canDischarge = false;
	}
	public ItemChargable(int id, int maxpower, boolean canDischarge) {
		super(id);
		MAXPOWER = maxpower;
		this.canDischarge = canDischarge;
	}
	public ItemChargable(int id, int maxpower, boolean canDischarge, boolean isAllwaysUp, EnumAction animation) {
		super(id, isAllwaysUp, animation);
		MAXPOWER = maxpower;
		this.canDischarge = canDischarge;
	}
	

	@Override
	public void onCreated(ItemStack item, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(item.hasTagCompound()){
			NBTTagCompound tag = item.stackTagCompound;
			tag.setInteger("Power", 0);
			tag.setInteger("MaxPower", MAXPOWER);
		}
		else{
			item.setTagCompound(new NBTTagCompound("ItemChargable"));
			NBTTagCompound tag = item.stackTagCompound;
			tag.setInteger("Power", 0);
			tag.setInteger("MaxPower", MAXPOWER);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean par4)
	{
		if(item.hasTagCompound()){
			if(item.stackTagCompound.hasKey("Power")){
				list.add("Power: " + item.stackTagCompound.getInteger("Power"));
			}
		}
	}

}
