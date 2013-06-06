package weapons.speacalitems;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import weapons.ModItem;
import weapons.Weapons;
import weapons.entity.EntityFlare;

public class ItemFlare extends ModItem{

	public ItemFlare(int itemId)
	{
		super(itemId);
		this.setUnlocalizedName("weaponsFlare");
		this.setCreativeTab(Weapons.weaponsTab);
		this.maxStackSize = 1;
		LanguageRegistry.addName(this,"Flare Shooter");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{
		if(player.isSneaking()){
			return item;
		}
		EntityFlare entity = new EntityFlare(world, player, 1000);
		world.spawnEntityInWorld(entity);
		world.playSoundAtEntity(player, "fireworks.launch", 3.0F, 1.0F);
		return item;
	}
	
}
