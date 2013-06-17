package weapons;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModItem extends Item {
	@SuppressWarnings("unused")
	private boolean allwaysUp = false;
	private EnumAction animation = EnumAction.none;
	public ModItem(int itemId){
		super(itemId);
		this.allwaysUp = false;
		this.setCreativeTab(Weapons.weaponsTab);
	}
	public ModItem(int itemId, boolean allwaysUp, EnumAction animation){
		this(itemId);
		this.allwaysUp = allwaysUp;
		this.animation = animation;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return this.animation;
	}


	@Override
	public void onCreated(ItemStack item, World par2World,
			EntityPlayer par3EntityPlayer)
	{
		if(!item.hasTagCompound()){
			item.setTagCompound(new NBTTagCompound("WeaponItem"));
		}
	}
//	@Override
//	public void onUpdate(ItemStack item, World par2World,
//			Entity entity, int par4, boolean par5)
//	{
//		if(!item.hasTagCompound() && entity instanceof EntityPlayer){
//			this.onCreated(item, par2World, (EntityPlayer)entity);
//		}
//		if(entity instanceof EntityPlayer){
//			EntityPlayer player = (EntityPlayer) entity;
//			if(this.allwaysUp){
//				player.setItemInUse(item, 1);
//			}
//			else{
//				player.stopUsingItem();
//			}
//		}
//	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		String id = this.getUnlocalizedName();
		id = id.replace("item.", "");
		itemIcon = iconRegister.registerIcon("weapons:" + id);
		System.out.println("Item icon registed to: " + id);
	}

}
