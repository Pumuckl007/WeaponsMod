package weapons;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import weapons.client.gui.GuiPowerStorage;
import weapons.client.gui.GuiSicurityStorage;
import weapons.client.gui.GuiWeaponCarver;
import weapons.inventory.ContainerPowerStorage;
import weapons.inventory.ContainerSicurityStorage;
import weapons.inventory.ContainerWeaponCarver;
import weapons.server.ServerTick;
import weapons.tileentity.TileEntityAntiMaterGenerator;
import weapons.tileentity.TileEntityDeath;
import weapons.tileentity.TileEntityPowerBase;
import weapons.tileentity.TileEntityPowerStorage;
import weapons.tileentity.TileEntityProjetor;
import weapons.tileentity.TileEntitySicurityStorage;
import weapons.tileentity.TileEntityUpgator;
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
	public void spawnParticle(EntityFX particle){
		
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

		if (ID == Weapons.guiWeaponCraver){
			TileEntityWeaponCarver tileWeaponCarver = (TileEntityWeaponCarver) world.getBlockTileEntity(x, y, z);
            return new ContainerWeaponCarver(player.inventory, tileWeaponCarver);
        }
        if (ID == Weapons.guiSicurityStorage){
        	TileEntitySicurityStorage tile = (TileEntitySicurityStorage) world.getBlockTileEntity(x, y, z);
            return new ContainerSicurityStorage(player.inventory, tile);
        }
        if (ID == Weapons.guiPowerStorage){
        	TileEntityPowerStorage tile = (TileEntityPowerStorage) world.getBlockTileEntity(x, y, z);
            return new ContainerPowerStorage(player.inventory, tile);
        }
		return null;
	}
	@Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if (ID == Weapons.guiWeaponCraver){
            TileEntityWeaponCarver tileWeaponCarver = (TileEntityWeaponCarver) world.getBlockTileEntity(x, y, z);
            return new GuiWeaponCarver(player.inventory, tileWeaponCarver);
        }
        if (ID == Weapons.guiSicurityStorage){
        	TileEntitySicurityStorage tile = (TileEntitySicurityStorage) world.getBlockTileEntity(x, y, z);
            return new GuiSicurityStorage(player.inventory, tile);
        }
        if (ID == Weapons.guiPowerStorage){
        	TileEntityPowerStorage tile = (TileEntityPowerStorage) world.getBlockTileEntity(x, y, z);
            return new GuiPowerStorage(player.inventory, tile);
        }
        return null;
    }
	
	public void initTileEntities() {

		GameRegistry.registerTileEntity(TileEntityWeaponCarver.class, "weaponCarver");
		GameRegistry.registerTileEntity(TileEntityDeath.class, "death");
		GameRegistry.registerTileEntity(TileEntityProjetor.class, "projetor");
		GameRegistry.registerTileEntity(TileEntitySicurityStorage.class, "sicurityStorage");
		GameRegistry.registerTileEntity(TileEntityPowerBase.class, "powerBase");
		GameRegistry.registerTileEntity(TileEntityPowerStorage.class, "powerStorage");
		GameRegistry.registerTileEntity(TileEntityAntiMaterGenerator.class, "amg");
		GameRegistry.registerTileEntity(TileEntityUpgator.class, "upgrator");
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