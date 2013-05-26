package weapons.client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import weapons.events.EventShipControl;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTick implements ITickHandler {

	private int keyNum;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub
		keyNum = 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			keyNum = 1;
		}
		if(keyNum != 0){
//		System.out.println("keydown: " + keyNum);
		}
		if (keyNum == 1)
		{
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			EventShipControl event = new EventShipControl(player.username, keyNum);
			MinecraftForge.EVENT_BUS.post(event);
//			System.out.println("posed event: " + player.username);
		}

	}
	


		
	



	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.CLIENT, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
