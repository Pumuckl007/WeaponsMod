package weapons.client.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import weapons.Weapons;
import weapons.inventory.ContainerSicurityStorage;
import weapons.tileentity.TileEntitySicurityStorage;
import weapons.utils.Color;
import weapons.utils.CommonUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSicurityStorage extends GuiBase {

	private TileEntitySicurityStorage tile;
	ItemStack item = new ItemStack(Block.tnt);
	private String tooltipcolor = Color.WHITE.toString();
	private String oldtooltip = tooltipcolor + "Power Level: ";

	public GuiSicurityStorage(InventoryPlayer inventoryPlayer, TileEntitySicurityStorage sicurityStorage) {

		super(new ContainerSicurityStorage(inventoryPlayer, sicurityStorage));
		tile = sicurityStorage;
		xSize = 194;
		ySize = 166;
		this.addTooltip(tooltipcolor + "Power Level: ", 158, 48, 167, 81);
		String[] tips = new String[1];
		tips[0] = "";
		this.addsubtooltip(tooltipcolor + "Power Level: ", tips);
	}
	public void drawStack() {
		GL11.glPushMatrix();
		if(tile.getStackInSlot(32) == null){
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glPushMatrix();
			int cornerX = (width - xSize) / 2;
			int cornerY = (height - ySize) / 2;
			itemRenderer.zLevel = 200F;
			itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, item, cornerX + 154, cornerY + 23);
			itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, item, cornerX + 154, cornerY + 23);
			itemRenderer.zLevel = 0.0F;
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		//        fontRenderer.drawString(tile.isInvNameLocalized() ? tile.getInvName() : StatCollector.translateToLocal(tile.getInvName()), 8, 6, 4210752);
		//        fontRenderer.drawString(StatCollector.translateToLocal("Sicurity Storage"), 44, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
		drawStack();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/mods/weapons/textures/gui/GuiSS.png");
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		int power = (int)(tile.getPowerLevel()/31.25);
		String[] tips = new String[1];
		double numofitems;
		if(power == 0){
			numofitems = 0;
		}
		else{
			numofitems = getNumberofItems(tile.inventory)/40960D;
		}
		String num = CommonUtils.doubletostring(numofitems, 5);
		tips[0] = tooltipcolor + num + " " + Weapons.unitName +"/Tick";
		String addingpower = (new Integer(tile.getPowerLevel())).toString();
		this.updateToolTipText(tooltipcolor + "Power Level: " + addingpower, oldtooltip);
		this.updateSubToolTipText(oldtooltip, tooltipcolor + "Power Level: " + addingpower , tips);
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		this.drawTexturedModalRect(xStart + 157, yStart + 47 + 32 - power, 225, 32 - power, 8, power + 2);
		oldtooltip = tooltipcolor + "Power Level: " + addingpower;

	}
	private int getNumberofItems(ItemStack[] invetnory){
		int numitems = 0;
		for(int i = 0; i < 32; i++){
			if(invetnory[i] != null){
				numitems += invetnory[i].stackSize;
			}
		}
		return numitems;
	}
}
