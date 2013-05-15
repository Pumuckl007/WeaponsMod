package weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import weapons.client.gui.GuiWeaponCarver;
import weapons.container.ContainerWeaponCarver;
import weapons.server.ServerTick;
import weapons.tileentity.TileEntityDeath;
import weapons.tileentity.TileEntityWeaponCarver;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;


public class CommonProxy implements IGuiHandler
{


	public void load()
	{

	}
	public void serverInit(){
		TickRegistry.registerTickHandler(new ServerTick(), Side.SERVER);
	}


	public void handleTileEntityPacket(int x, int y, int z, ForgeDirection orientation, short state, String player, String customName) {

    }

	public void loadSound()
	{

	}
	public void sendRequestEventPacket(byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data) {

    }

	public boolean isClient(){
		return false;
	}
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){

		if (ID == 0){
            TileEntityWeaponCarver tileWeaponCarver = (TileEntityWeaponCarver) world.getBlockTileEntity(x, y, z);
            return new ContainerWeaponCarver(player.inventory, tileWeaponCarver);
            }

		return null;
	}
	@Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == 0){
            TileEntityWeaponCarver tileWeaponCarver = (TileEntityWeaponCarver) world.getBlockTileEntity(x, y, z);
            return new GuiWeaponCarver(player.inventory, tileWeaponCarver);
        }

        return null;
    }
	
	public void initTileEntities() {

		GameRegistry.registerTileEntity(TileEntityWeaponCarver.class, "weaponCarver");
		GameRegistry.registerTileEntity(TileEntityDeath.class, "death");
	}

	public void sayClient(String message){

	}
	public void sayServer(String message){
		System.out.println(message);
	}
	public void registerKeyBindingHandler()
	{
		// TODO Auto-generated method stub
		
	}
}