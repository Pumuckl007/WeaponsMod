package weapons.armor;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import weapons.client.models.armor.RenderJetPack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJetPack extends ItemArmor
implements
ISpecialArmor {

	int tid = 0;
	public static boolean isflying = false;
	public static double flyingspeed = 2;
	private static boolean toggle = false;
	private static boolean togglecooldown = true;
	private static boolean togglecooldown2 = true;
	private static boolean toggle2 = false;
	private static boolean togglecooldown3 = true;
	private static boolean toggle3 = false;
	private static int jumpcooldown = -1;
	private static boolean togglecooldown4;
	private static boolean toggle4;
	/**
	 * @param id
	 * @param renderIndex
	 * @param armorType   0 = head; 1 = torso; 2 = legs; 3 = feet
	 */
	public ItemJetPack(int id, int renderIndex, int armorType) {
		super(id, EnumArmorMaterial.IRON, renderIndex, armorType);
		setMaxStackSize(1);
		tid = id;
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, int layer) {

		return "/mods/weapons/textures/models/JETPACK.png";

	}



	@Override
	@SideOnly(Side.CLIENT)
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		if(world.isRemote){
			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				toggle = true;
			}
			else{
				toggle = false;
				togglecooldown = true;
			}
			if(toggle && togglecooldown){
				togglecooldown = false;
				if(ItemJetPack.isflying){
					ItemJetPack.isflying = false;
					player.addChatMessage(EnumChatFormatting.DARK_RED + "Jet Pack is now Disabled.");
				}
				else{
					ItemJetPack.isflying = true;
					player.addChatMessage(EnumChatFormatting.DARK_GREEN + "Jet Pack is now Enabled.");
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				toggle2 = true;
			}
			else{
				toggle2 = false;
				togglecooldown2 = true;
			}
			if(toggle2 && togglecooldown2){
				togglecooldown2 = false;
				if(ItemJetPack.flyingspeed < 5){
					ItemJetPack.flyingspeed += 1;
				}
				else if(flyingspeed < 10){
					flyingspeed = 10;
				}
				else{
					flyingspeed = 1;
				}
				player.addChatMessage(EnumChatFormatting.DARK_GREEN + "Throtle set to " + ItemJetPack.flyingspeed + ".");
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_CAPITAL)) {
				toggle3 = true;
			}
			else{
				toggle3 = false;
				togglecooldown3 = true;
			}
			if(toggle3 && togglecooldown3){
				togglecooldown3 = false;
				ItemJetPack.jumpcooldown = 200;
			}
			if(ItemJetPack.jumpcooldown != -1){
				if(ItemJetPack.jumpcooldown <= 0){
					player.addChatMessage(EnumChatFormatting.DARK_GREEN + "Warp jumping " + Math.pow(50, ItemJetPack.flyingspeed) + " blocks!");
					ItemJetPack.jumpcooldown = -1;
					if(ItemJetPack.isflying){
						double volocityX;
						double volocityY = 0;
						double volocityZ = 0;
						volocityX = Math.cos(Math.toRadians(player.rotationPitch)) * Math.sin(Math.toRadians(player.rotationYawHead) + Math.PI) * 50;
						volocityZ = Math.cos(Math.toRadians(player.rotationPitch)) * Math.cos(Math.toRadians(player.rotationYawHead)) * 50;
						volocityY = Math.sin(Math.toRadians(player.rotationPitch) + Math.PI) * 50;
						volocityX = Math.pow(volocityX, ItemJetPack.flyingspeed);
						volocityY = Math.pow(volocityY, ItemJetPack.flyingspeed);
						volocityZ = Math.pow(volocityZ, ItemJetPack.flyingspeed);
						double startX = player.posX + volocityX;
						double startY = player.posY + volocityY + 1;
						double startZ = player.posZ + volocityZ;
						player.setPositionAndUpdate(startX, startY, startZ);
						player.fallDistance = 0;
					}
				}
				else{
					int time = (ItemJetPack.jumpcooldown / 20) + 1;
					player.addChatMessage(EnumChatFormatting.GOLD + "Warp jumping in " + time + "! Press X to cancel!");
					ItemJetPack.jumpcooldown -= 1;
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
				toggle4 = true;
			}
			else{
				toggle4 = false;
				togglecooldown4 = true;
			}
			if(toggle4 && togglecooldown4){
				togglecooldown4 = false;
				ItemJetPack.jumpcooldown = -1;
				player.addChatMessage(EnumChatFormatting.DARK_GREEN + "Warp jump canceled!");
			}
			super.onArmorTickUpdate(world, player, itemStack);
			if(ItemJetPack.isflying){
				updateflying(player);
			}
		}
		if(ItemJetPack.isflying){
			PotionEffect potion = new PotionEffect(14, 2);
			player.addPotionEffect(potion);
		}

	}
	public void updateflying(EntityPlayer player){
		float volocityX;
		float volocityY = 0;
		float volocityZ = 0;
		float offX = 0.95F + (float)(Math.random() * ((1.05 - 0.95) + 1));
		float offY = 0.95F + (float)(Math.random() * ((1.05 - 0.95) + 1));
		float offZ = 0.95F + (float)(Math.random() * ((1.05 - 0.95) + 1)); 
		volocityX = (float) Math.cos(Math.toRadians(player.rotationPitch)) * (float) Math.sin(Math.toRadians(player.rotationYawHead) + Math.PI) * offX;
		volocityZ = (float) Math.cos(Math.toRadians(player.rotationPitch)) * (float) Math.cos(Math.toRadians(player.rotationYawHead)) * offZ;
		volocityY = (float) Math.sin(Math.toRadians(player.rotationPitch) + Math.PI) * offY;
		double startX = player.posX + volocityX;
		double startY = player.posY + volocityY + 1;
		double startZ = player.posZ + volocityZ;
		startX = startX * ItemJetPack.flyingspeed /2;
		startY = startY * ItemJetPack.flyingspeed/2;
		startZ = startZ * ItemJetPack.flyingspeed/2;
		player.posX += startX;
		player.posY += startY;
		player.posZ += startZ;
		player.motionX = volocityX / 2 * ItemJetPack.flyingspeed;
		player.motionY = volocityY / 2 * ItemJetPack.flyingspeed;
		player.motionZ = volocityZ / 2 * ItemJetPack.flyingspeed;
		player.fallDistance = 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLiving entityLiving, ItemStack itemstack, int armorSlot) {
		RenderJetPack model;
		model = RenderJetPack.getInstance();
		model.bipedBody.showModel = armorSlot == 1;
		model.bipedRightArm.showModel = armorSlot == 1;
		model.bipedLeftArm.showModel = armorSlot == 1;
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
		itemIcon = iconRegister.registerIcon("weapons:" + id);
		System.out.println("Item icon registed to: " + id);
	}

}
