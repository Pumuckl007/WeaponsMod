package weapons.upgrads;

import net.minecraft.item.Item;
import weapons.Weapons;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class Upgrads {
	public static Item TransBlockTeleporterUpgradeTire2;
	public static void load(FMLInitializationEvent fie)
	{
		TransBlockTeleporterUpgradeTire2 = (new ItemUpgrade(Weapons.upgradIds, Weapons.transBlockTelepoerter, "Teare", 2).setUnlocalizedName("tbtupgradet2"));
		Upgrads.initNams();
	}
	private static void initNams(){
		LanguageRegistry.addName(TransBlockTeleporterUpgradeTire2,"Trans Block Teleporter Upgrade Tire 2");
	}
}
