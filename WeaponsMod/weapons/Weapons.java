package weapons;



import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import weapons.armor.ItemInfoHelmet;
import weapons.armor.ItemJetBoots;
import weapons.armor.ItemJetPack;
import weapons.blocks.BlockDeath;
import weapons.blocks.BlockProjetor;
import weapons.blocks.BlockSicurityStorage;
import weapons.blocks.BlockWeaponCarver;
import weapons.bullets.EntityBullet;
import weapons.bullets.EntityRocket;
import weapons.bullets.ItemBullet;
import weapons.entity.EntitySpaceShip;
import weapons.entity.EntitySpeeder;
import weapons.events.EventShipControl;
import weapons.gunitems.FT;
import weapons.gunitems.IceBallLauncher;
import weapons.gunitems.Pistol;
import weapons.gunitems.RocketLancher;
import weapons.gunitems.ScarH;
import weapons.network.PacketHandler;
import weapons.speacalitems.ItemInfo;
import weapons.speacalitems.ItemSpaceShip;
import weapons.speacalitems.ItemSpeeder;
import weapons.utils.Color;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "weapons",
name = "Weapons Mod",
version = "@VERSION@ (build @BUILD_NUMBER@)",
dependencies = "required-after:Forge@[7.8.0.686,)",
certificateFingerprint = "@FINGERPRINT@")
@NetworkMod(
        channels = { "weapons" },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class)

public class Weapons
{
	@SidedProxy(clientSide = "weapons.client.ClientProxy", serverSide = "weapons.CommonProxy")
	public static CommonProxy proxy;
	@Instance("weapons")
	public static Weapons instance;
	public static int dimension = 2;
	public static int dimensionJCE = 3;
	public static WeaponsTab weaponsTab = new WeaponsTab("weaponstab");
	public static boolean inMCP = false;
	public static boolean explosions;

	public static String unitName = "Units";
	public static Map<String, Integer> fTFuel = new HashMap<String, Integer>();
	public static Map<String, Integer> iceBalls = new HashMap<String, Integer>();
	
	public static Block weaponCarver;
	public static Block death;
	public static Block projetor;
	public static Block sicurityStorage;

	public static Item pisol1;
	public static Item mGun1;
	public static Item rocketLancher1;
	public static Item flameThrower;
	public static Item iceBallLauncher;
	
	public static Item info;
	
	public static Item spaceship;
	public static Item speeder;

	public static Item bullet1;
	public static Item rocket1;
	
	public static Item jetPack;
	public static Item jetBoots;
	public static Item infoHelmet;


	public static String errorString = "";
	public static int errorStringTimer = 0;




	static int startEntityId = 300;
	public static int yourblockModelId;
	public static int yourblockModelId2;
	public static int yourblockModelId3;
	public static int yourblockModelId4;
	public static int startBlockID;
	public static int bulletid;
	
	public static int guiWeaponCraver = 0;
	public static int guiSicurityStorage = 1;


	@PreInit
	public void initConfig(FMLPreInitializationEvent fpe)
	{
		
		Configuration config = new Configuration(fpe.getSuggestedConfigurationFile());

		config.load();

		int firstBlockId = config.getBlock("StartBlockId", 3700).getInt();
		startBlockID = firstBlockId;

		int randomItemID = config.getItem("StartItemId", 15000).getInt();
		bulletid = randomItemID;

		int entityid = config.get("EntityId", "EntityId", 400).getInt();
		startEntityId = entityid;


		// Since this flag is a boolean, we can read it into the variable directly from the config.
		explosions = config.get(Configuration.CATEGORY_GENERAL, "Explosions", false).getBoolean(false);

		//Notice there is nothing that gets the value of this property so the expression results in a Property object.
		Property someProperty = config.get(Configuration.CATEGORY_GENERAL, "SomeConfigString", "nothing");

		// Here we add a comment to our new property.
		someProperty.comment = "This value can be read as a string!";

		// this could also be:
		// int someInt = someProperty.getInt();
		// boolean someBoolean = someProperty.getBoolean(true);

		config.save();
		weaponCarver = (new BlockWeaponCarver(startBlockID));
		death = (new BlockDeath(startBlockID + 1));
		projetor = (new BlockProjetor(startBlockID + 2));
		sicurityStorage = (new BlockSicurityStorage(startBlockID + 3));
		GameRegistry.registerBlock(weaponCarver, "WeaponCarver");
		GameRegistry.registerBlock(death, "Death");
		GameRegistry.registerBlock(projetor, "Projetor");
		GameRegistry.registerBlock(sicurityStorage, "SicurityStorage");
		proxy.serverInit();
		proxy.registerKeyBindingHandler();
	}

	public static weapons.network.GuiHandler guiHandler = new weapons.network.GuiHandler();

	@SuppressWarnings("static-access")
	@Init

	public void load(FMLInitializationEvent fie)
	{

        NetworkRegistry.instance().registerGuiHandler(instance, guiHandler);
        MinecraftForge.EVENT_BUS.register(new EventShipControl("" , 0));
		this.addAchievementLocalizations();



		int gunid = bulletid - 256;
		int specialid = bulletid + 256;
		
		

		pisol1 = (new Pistol(gunid).setUnlocalizedName("1"));
		mGun1 = (new ScarH(gunid + 50).setUnlocalizedName("5"));
		rocketLancher1 = (new RocketLancher(gunid + 100).setUnlocalizedName("2"));
		flameThrower = (new FT(gunid + 125).setUnlocalizedName("6"));
		info = (new ItemInfo(specialid).setUnlocalizedName("7"));
		iceBallLauncher = (new IceBallLauncher(gunid + 80).setUnlocalizedName("8"));

		bullet1 = (new ItemBullet(bulletid).setUnlocalizedName("3"));

		rocket1 = (new ItemBullet(bulletid + 100).setUnlocalizedName("4"));
		jetPack = (new ItemJetPack(specialid + 41, 0, 1).setUnlocalizedName("9"));
		jetBoots = (new ItemJetBoots(specialid + 43, 0, 3).setUnlocalizedName("10"));
		infoHelmet = (new ItemInfoHelmet(specialid + 40, 0, 0).setUnlocalizedName("11"));
		
		spaceship = (new ItemSpaceShip(specialid + 1).setUnlocalizedName("12"));
		speeder = (new ItemSpeeder(specialid + 2).setUnlocalizedName("13"));

		pisol1.setCreativeTab(weaponsTab);
		mGun1.setCreativeTab(weaponsTab);
		iceBallLauncher.setCreativeTab(weaponsTab);
		rocketLancher1.setCreativeTab(weaponsTab);
		flameThrower.setCreativeTab(weaponsTab);
		bullet1.setCreativeTab(weaponsTab);
		rocket1.setCreativeTab(weaponsTab);
		info.setCreativeTab(weaponsTab);
		jetPack.setCreativeTab(weaponsTab);
		jetBoots.setCreativeTab(weaponsTab);
		infoHelmet.setCreativeTab(weaponsTab);
		spaceship.setCreativeTab(weaponsTab);
		speeder.setCreativeTab(weaponsTab);
		
		proxy.initTileEntities();
		
		registeringBlocks();
		itemNames();
		recipes();
		smelting();
		blockNames();
		biomes();
		otherNames();
		addAchievementLocalizations();



		//Minecraft Forge Preload Textures

		EntityRegistry.registerModEntity(EntityBullet.class, "bullet", this.getUniqueEntityId(), this, 80, 3, true);
		LanguageRegistry.instance().addStringLocalization("entity.MoreOres.bullet.name", "bullet");
		EntityRegistry.registerModEntity(EntityRocket.class, "rocket", this.getUniqueEntityId(), this, 80, 3, true);
		LanguageRegistry.instance().addStringLocalization("entity.MoreOres.rocket.name", "rocket");
		EntityRegistry.registerModEntity(EntityRocket.class, "iceball", this.getUniqueEntityId(), this, 80, 3, true);
		LanguageRegistry.instance().addStringLocalization("entity.MoreOres.iceball.name", "iceball");
		EntityRegistry.registerModEntity(EntitySpaceShip.class, "spaceship", this.getUniqueEntityId(), this, 80, 3, true);
		LanguageRegistry.instance().addStringLocalization("entity.MoreOres.spaceship.name", "spaceship");
		EntityRegistry.registerModEntity(EntitySpeeder.class, "speeder", this.getUniqueEntityId(), this, 80, 3, true);
		LanguageRegistry.instance().addStringLocalization("entity.MoreOres.speeder.name", "speeder");

		proxy.load();
		proxy.loadSound();


	}
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		//		GameRegistry.registerCraftingHandler(craftHandler);	



	}





	public static int getUniqueEntityId() 
	{
		do 
		{
			startEntityId++;
		} 
		while (EntityList.getStringFromID(startEntityId) != null);

		return startEntityId;
	}

	@SuppressWarnings("unchecked")
	public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor) 
	{
		int id = getUniqueEntityId();
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}






	//	public static CraftingHandler craftHandler = new CraftingHandler();

	public static boolean bombsEnabled = true;

	public static boolean bulletsEnabled = false;

	public static boolean vehiclesNeedFuel = true;

	public static boolean canBreakGlass = true;




	private void addAchievementLocalizations()
	{

	}




	public void registeringBlocks()
	{
	}

	public void blockNames()
	{
		LanguageRegistry.addName(weaponCarver,"Weapon Carver");
		LanguageRegistry.addName(death,Color.DARK_RED + "Death");
		LanguageRegistry.addName(projetor,"Block Projetor");
	}

	public void itemNames()
	{
		LanguageRegistry.addName(pisol1, "RPD Police Beretta");
		LanguageRegistry.addName(bullet1, "Ammo");
		LanguageRegistry.addName(rocketLancher1, "RPG");
		LanguageRegistry.addName(flameThrower, "Flame Thrower");
		LanguageRegistry.addName(rocket1, "RPG Ammo");
		LanguageRegistry.addName(mGun1, "Scar H");
		LanguageRegistry.addName(info, "Hand Held Information Reader");
		LanguageRegistry.addName(iceBallLauncher,"Ice Ball Launcher");
		LanguageRegistry.addName(jetPack,"JetPack");
		LanguageRegistry.addName(jetBoots,"Jet Boots");
		LanguageRegistry.addName(infoHelmet,"Information Helmet");
		LanguageRegistry.addName(spaceship,Color.DARK_AQUA+ "\u00a7lSpace Ship");
		LanguageRegistry.addName(speeder,Color.DARK_AQUA+ "\u00a7lSpeeder");
	}

	public void otherNames(){
		LanguageRegistry.instance().addStringLocalization("itemGroup.weaponstab", "en_US", "Weapons Mod");
	}

	public void recipes()
	{


		GameRegistry.addRecipe(new ItemStack(Weapons.bullet1, 32), 
				" x ", 
				"xwx", 
				"yzy", 
				'w', Item.gunpowder,
				'x', Item.ingotIron,
				'y',Item.blazePowder,
				'z', Item.flint);
		GameRegistry.addRecipe(new ItemStack(Weapons.rocket1, 3), 
				" a ", 
				"xwx", 
				"yzy", 
				'w', Block.tnt,
				'x', Item.ingotIron,
				'y',Item.blazePowder,
				'z', Item.fireballCharge,
				'a', Block.blockIron);

	}

	public void smelting()
	{
		//		GameRegistry.addSmelting(MoreOres.InfernalOre.blockID, new ItemStack(MoreOres.InfernalIngot), 2.0f);
	}
	public void biomes()
	{
	}
	public static void logQuietly(String s)
	{
	}

	public static void logLoudly(String s)
	{
		errorString = s;
		errorStringTimer = 100;
		System.out.println("SERIOUS PROBLEM!");
		log(s);
	}

	/** Logger. */
	public static void log(Object arg0)
	{
		System.out.println("WeaponsMod : " + arg0);
	}
}