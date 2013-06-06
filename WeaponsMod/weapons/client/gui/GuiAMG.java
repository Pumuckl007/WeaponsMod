package weapons.client.gui;

import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import weapons.inventory.ContainerAMG;
import weapons.tileentity.TileEntityAntiMaterGenerator;
import weapons.utils.Color;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAMG extends GuiBase {

	private TileEntityAntiMaterGenerator tile;
	private String tooltipcolor = Color.WHITE.toString();
	private String oldtooltip = tooltipcolor + "Power Level: ";

	public GuiAMG(InventoryPlayer inventoryPlayer, TileEntityAntiMaterGenerator tile) {

		super(new ContainerAMG(inventoryPlayer, tile));
		this.tile = tile;
		xSize = 176;
		ySize = 187;
		this.addTooltip(tooltipcolor + "Power Level: ", 112, 14, 170, 63);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

		if(tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord) instanceof TileEntityAntiMaterGenerator){
			tile = (TileEntityAntiMaterGenerator)tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/mods/weapons/textures/gui/GuiPowerStorage.png");
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		int power = (int)(((double)tile.getPower()* 48D)/(double)tile.getMaxPower());
		String addingpower = (new Integer(tile.getPower())).toString();
		this.updateToolTipText(tooltipcolor + "Power Level: " + addingpower, oldtooltip);
		this.drawTexturedModalRect(xStart + 112, yStart + 14 + 56 - power, 200, 56 - power, 56, power + 2);
		oldtooltip=tooltipcolor + "Power Level:" + addingpower;
	}
}
