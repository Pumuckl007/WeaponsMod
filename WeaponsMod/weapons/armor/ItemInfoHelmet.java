package weapons.armor;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import weapons.client.models.armor.RenderInfoHelmet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInfoHelmet extends ItemArmor
implements
ISpecialArmor {

	int tid = 0;
	int cooldown = 0;
	/**
	 * @param id
	 * @param renderIndex
	 * @param armorType   0 = head; 1 = torso; 2 = legs; 3 = feet
	 */
	public ItemInfoHelmet(int id, int renderIndex, int armorType) {
		super(id, EnumArmorMaterial.IRON, renderIndex, armorType);
		setMaxStackSize(1);
		tid = id;
	}
	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, int layer) {

		return "/mods/weapons/textures/items/infohelmet.png";

	}



	@Override
	@SideOnly(Side.CLIENT)
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{


	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLiving entityLiving, ItemStack itemstack, int armorSlot) {
		RenderInfoHelmet model;
		model = RenderInfoHelmet.getInstance();
		model.bipedHead.showModel = armorSlot == 0;
		if(entityLiving instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entityLiving;
			if(player.username.equalsIgnoreCase("deadmau5")){
				model.bipedEars.showModel = armorSlot == 0;
			}
			if(player.username.equalsIgnoreCase("Max_of_max")){
				model.bipedCloak.showModel = armorSlot == 0;
			}
		}
		return model;
	}


	/**
	 * Inherited from ISpecialArmor, allows significant customization of damage
	 * calculations.
	 */
	@Override
	public ISpecialArmor.ArmorProperties getProperties(EntityLiving player, ItemStack armor, DamageSource source, double damage, int slot) {
		// Order in which this armor is assessed for damage. Higher(?) priority
		// items take damage first, and if none spills over, the other items
		// take no damage.
		int priority = 1;
		double armorDouble;

		if (player instanceof EntityPlayer) {
			armorDouble = getArmorDouble((EntityPlayer) player, armor);
		} else {
			armorDouble = 2;
		}

		// How much of incoming damage is absorbed by this armor piece.
		// 1.0 = absorbs all damage
		// 0.5 = 50% damage to item, 50% damage carried over
		double absorbRatio = 0.04 * armorDouble;

		// Maximum damage absorbed by this piece. Actual damage to this item
		// will be clamped between (damage * absorbRatio) and (absorbMax). Note
		// that a player has 20 hp (1hp = 1 half-heart)
		int absorbMax = (int) armorDouble * 75; // Not sure why this is
		// necessary but oh well
		if (source.isUnblockable()) {
			absorbMax = 0;
			absorbRatio = 0;
		}
		return new ArmorProperties(priority, absorbRatio, absorbMax);
	}

	public static double clampDouble(double value, double min, double max) {
		if (value < min)
			return min;
		if (value > max)
			return max;
		return value;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return false;
	}


	/**
	 * Inherited from ISpecialArmor, allows us to customize the calculations for
	 * how much armor will display on the player's HUD.
	 */
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return (int) getArmorDouble(player, armor);
	}

	public double getArmorDouble(EntityPlayer player, ItemStack stack) {
		double totalArmor = 5;
		return totalArmor;
	}

	/**
	 * Inherited from ISpecialArmor, allows us to customize how the armor
	 * handles being damaged.
	 */
	@Override
	public void damageArmor(EntityLiving entity, ItemStack stack, DamageSource source, int damage, int slot) {
	}

	/**
	 * Adds information to the item's tooltip when 'getting' it.
	 *
	 * @param stack            The itemstack to get the tooltip for
	 * @param player           The player (client) viewing the tooltip
	 * @param currentTipList   A list of strings containing the existing tooltip. When
	 *                         passed, it will just contain the name of the item;
	 *                         enchantments and lore are
	 *                         appended afterwards.
	 * @param advancedToolTips Whether or not the player has 'advanced tooltips' turned on in
	 *                         their settings.
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List currentTipList, boolean advancedToolTips) {

	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		String id = this.getUnlocalizedName();
		id = id.replace("item.", "");
		this.itemIcon = iconRegister.registerIcon("weapons:" + id);
		System.out.println("Item icon registed to: " + id);
	}

}
