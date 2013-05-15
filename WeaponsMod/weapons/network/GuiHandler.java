package weapons.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import weapons.client.gui.GuiWeaponCarver;
import weapons.container.ContainerWeaponCarver;
import weapons.tileentity.TileEntityWeaponCarver;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * Gui handler for this mod. Mainly just takes an ID according to what was
 * passed to player.OpenGUI, and opens the corresponding GUI.
 */
public class GuiHandler implements IGuiHandler {
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
}
