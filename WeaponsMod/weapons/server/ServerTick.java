package weapons.server;

import java.util.EnumSet;

import net.minecraft.world.World;
import weapons.Weapons;
import weapons.utils.CommonUtils;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTick implements ITickHandler {

	private int savecooldown = 0;
	private SaveFTStats ft;
	private SaveIceBalls iceballs;
	public World world;
	@SuppressWarnings("unused")
	private int playercheck = 0;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub
		if(type.contains(TickType.WORLDLOAD))
		{
			ServerConfig.load((World)tickData[0]);
			world = (World)tickData[0];
			ft = new SaveFTStats(CommonUtils.getWorldBaseSaveLocation(((World)tickData[0])));
			iceballs = new SaveIceBalls(CommonUtils.getWorldBaseSaveLocation(((World)tickData[0])));
			Weapons.fTFuel = ft.getFuelLevel();
			Weapons.iceBalls = iceballs.getFuelLevel();
		}
		if(type.contains(TickType.WORLD))
		{
			if(savecooldown <= 0){
				savecooldown = 300;
				ft.setFuelLevel(Weapons.fTFuel);
				ft.save();
				iceballs.setFuelLevel(Weapons.fTFuel);
				iceballs.save();
			}
			else{
				savecooldown--;
			}
		}
//		if(type.contains(TickType.WORLD))
//		{
//			if(playercheck <= 0){
//				playercheck = 5;
//				String players = MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString();
//				List<String> player = Arrays.asList(players.split(","));
//				for (String s : player){
//					EntityPlayer playere = world.;
//					if(playere != null){
//						System.out.println(playere.username);
//						if(playere.inventory != null){
//							ItemStack[] armor = playere.inventory.armorInventory;
//							if(armor[2].itemID == Weapons.jetPack.itemID){
//								PotionEffect potion = new PotionEffect(14, 2);
//								playere.addPotionEffect(potion);
//							}
//							else{
//								System.out.println(armor[1].itemID);
//								System.out.println(armor[2].itemID);
//								System.out.println(armor[3].itemID);
//								System.out.println(armor[4].itemID);
//							}
//						}
//					}
//				}
//
//			}
//			else{
//				playercheck --;
//			}
//		}
	}







	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.WORLD, TickType.PLAYER, TickType.WORLDLOAD);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
