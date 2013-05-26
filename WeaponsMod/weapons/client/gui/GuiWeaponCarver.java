package weapons.client.gui;

import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import weapons.Weapons;
import weapons.inventory.ContainerWeaponCarver;
import weapons.tileentity.TileEntityWeaponCarver;
import weapons.utils.Color;
import weapons.utils.CommonUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWeaponCarver extends GuiBase {

	private TileEntityWeaponCarver tile;
	private String tooltipcolor = Color.WHITE.toString();
	private String oldtooltip = tooltipcolor + "Power Level: ";

	public GuiWeaponCarver(InventoryPlayer inventoryPlayer, TileEntityWeaponCarver tile) {

		super(new ContainerWeaponCarver(inventoryPlayer, tile));
		this.tile = tile;
		xSize = 176;
		ySize = 187;
		this.addTooltip(tooltipcolor + "Power Level: ", 158, 19, 167, 52);
		String[] tips = new String[1];
		tips[0] = "";
		this.addsubtooltip(tooltipcolor + "Power Level: ", tips);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/mods/weapons/textures/gui/GuiWeaponCarver.png");
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		int power = (int)(tile.getPower()/62.5);
		String[] tips = new String[1];
		double useingPower;
		if(power == 0){
			useingPower = 0;
		}
		else{
			if(tile.ison){
				useingPower = 1;
			}
			else{
				useingPower = 0;
			}
		}
		String num = CommonUtils.doubletostring(useingPower, 5);
		tips[0] = tooltipcolor + num + " " + Weapons.unitName +"/Tick";
		String addingpower = (new Integer(tile.getPower())).toString();
		this.updateToolTipText(tooltipcolor + "Power Level: " + addingpower, oldtooltip);
		this.updateSubToolTipText(oldtooltip, tooltipcolor + "Power Level: " + addingpower , tips);
		this.drawTexturedModalRect(xStart + 157, yStart + 18 + 32 - power, 225, 32 - power, 8, power + 2);
		int progress = this.tile.getProgress(24);
		this.drawTexturedModalRect(xStart + 79, yStart + 34, 255, 33, progress + 1, 16);
	}
}
