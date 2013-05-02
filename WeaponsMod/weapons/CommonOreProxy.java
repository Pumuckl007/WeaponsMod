package weapons;

import weapons.server.ServerTick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;


public class CommonOreProxy implements IGuiHandler
{


	public void load()
	{
		
	}
	public void serverInit(){
		TickRegistry.registerTickHandler(new ServerTick(), Side.SERVER);
	}




	public void loadSound()
	{

	}


	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {



	return null;
}

@Override
public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {



	return null;
}

public void sayClient(String message){

}
public void sayServer(String message){
	System.out.println(message);
}
}