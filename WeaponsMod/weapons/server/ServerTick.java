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

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub
		if(type.contains(TickType.WORLDLOAD))
		{
			ServerConfig.load((World)tickData[0]);
			ft = new SaveFTStats(CommonUtils.getWorldBaseSaveLocation(((World)tickData[0])));
			Weapons.fTFuel = ft.getFuelLevel();
		}
		if(type.contains(TickType.WORLD))
		{
			if(savecooldown <= 0){
				savecooldown = 300;
				ft.setFuelLevel(Weapons.fTFuel);
				ft.save();
			}
		}
		else{
			savecooldown--;
		}
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
