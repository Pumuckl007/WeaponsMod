package weapons.client;

import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class RenderTick implements ITickHandler{
	
	public static float playerRecoil = 0;
	private float antiRecoil = 0;
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		updategunrecoil();

	}


	private void updategunrecoil()
	{
		if(ClientProxy.minecraft.thePlayer != null){
		if (playerRecoil > 0)
			playerRecoil *= 0.95F;
		ClientProxy.minecraft.thePlayer.rotationPitch -= playerRecoil;
		antiRecoil += playerRecoil;

		ClientProxy.minecraft.thePlayer.rotationPitch += antiRecoil * 0.2F;
		antiRecoil *= 0.8F;
		}

		
	}
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.RENDER, TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
