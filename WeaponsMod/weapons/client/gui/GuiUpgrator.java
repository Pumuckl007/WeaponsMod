package weapons.client.gui;

import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import weapons.inventory.ContainerUpgator;
import weapons.tileentity.TileEntityPowerStorage;
import weapons.tileentity.TileEntityUpgator;
import weapons.utils.Color;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiUpgrator extends GuiBase {

	private TileEntityUpgator tile;
	private String tooltipcolor = Color.WHITE.toString();
	private String oldtooltip = tooltipcolor + "Power Level: ";

	public GuiUpgrator(InventoryPlayer inventoryPlayer, TileEntityUpgator tile) {

		super(new ContainerUpgator(inventoryPlayer, tile));
		this.tile = tile;
		xSize = 176;
		ySize = 187;
		this.addTooltip(tooltipcolor + "Power Level: ", 83, 25, 93, 58);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

		if(tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord) instanceof TileEntityPowerStorage){
			tile = (TileEntityUpgator)tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/mods/weapons/textures/gui/GuiUpgradtor.png");
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		int power = (int)(((double)tile.getPower()/31.25D));
		String addingpower = (new Integer(tile.getPower())).toString();
		this.updateToolTipText(tooltipcolor + "Power Level: " + addingpower, oldtooltip);
		this.drawTexturedModalRect(xStart + 82, yStart + 25 + 32 - power, 225, 32 - power, 9, power + 2);
	}
}
