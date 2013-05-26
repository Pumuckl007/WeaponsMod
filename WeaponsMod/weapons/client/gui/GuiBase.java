package weapons.client.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

public class GuiBase extends GuiContainer{
	private Map<String, Integer[]> thetooltips = new HashMap<String, Integer[]>();
	private Map<String, String[]> thesubtooltips = new HashMap<String, String[]>();
	public GuiBase(Container par1Container)
	{
		super(par1Container);
	}
	public void addTooltip(String tooltip, int x, int y, int maxx, int maxy){
		Integer[] mouse = new Integer[4];
		mouse[0] = x;
		mouse[1] = y;
		mouse[2] = maxx;
		mouse[3] = maxy;
		thetooltips.put(tooltip, mouse);
	}
	public void addsubtooltip(String tooltip, String[] infotooltip){
		if(thetooltips.containsKey(tooltip) && infotooltip.length > 0){
			thesubtooltips.put(tooltip, infotooltip);
		}
	}
	public void updateToolTipText(String newtooltip, String oldtooltip){
		if(thetooltips.containsKey(oldtooltip)){
			Integer[] mouse = thetooltips.get(oldtooltip);
			thetooltips.remove(oldtooltip);
			thetooltips.put(newtooltip, mouse);
		}
	}
	public void updateSubToolTipText(String oldtooltip, String tooltip, String[] subtooltips){
		if(thetooltips.containsKey(tooltip) && subtooltips.length > 0 && thesubtooltips.containsKey(oldtooltip)){
			thesubtooltips.remove(oldtooltip);
			thesubtooltips.put(tooltip, subtooltips);
		}
	}
	public void updateToolTipBounds(String tooltip, int x, int y, int maxx, int maxy){
		Integer[] mouse = new Integer[4];
		mouse[0] = x;
		mouse[1] = y;
		mouse[2] = maxx;
		mouse[3] = maxy;
		thetooltips.remove(tooltip);
		thetooltips.put(tooltip, mouse);
	}
	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		int xoff = (width - xSize) / 2;
		int yoff = (height - ySize) / 2;
		mouseX -= xoff;
		mouseY -= yoff;
		int left = this.guiLeft;
		int top = this.guiTop;

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) left, (float) top, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.disableStandardItemLighting();

		InventoryPlayer playerInv = this.mc.thePlayer.inventory;

		if (playerInv.getItemStack() == null) {
			if (isMouseOver(mouseX, mouseY)) {
				String[] tips;
				String tip = getTip(mouseX, mouseY);
				if(thesubtooltips.containsKey(tip)){
					tips = new String[thesubtooltips.get(tip).length + 1];
					tips[0] = tip;
					int tipcount = 0;
					for(String addingtip : thesubtooltips.get(tip)){
						tipcount ++;
						tips[tipcount] = addingtip;
					}
					drawToolTips(tips, mouseX, mouseY);
				}
				else{
					tips = new String[1];
					tips[0] = tip;
					drawToolTips(tips, mouseX, mouseY);
				}
			}
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	private boolean isMouseOver(int x, int y){
		for (String key : thetooltips.keySet()) {
			Integer[] mousepoints = thetooltips.get(key);
			int startX = mousepoints[0];
			int startY = mousepoints[1];
			int endX = mousepoints[2];
			int endY = mousepoints[3];
			int xlenght = endX - startX;
			int ylenght = endY - startY;
			for(int i = 0; i < xlenght; i++){
				for(int k = 0; k < ylenght; k++){
					if(x == (startX + i) && y == (startY + k)){
						return true;
					}
				}
			}
		}
		return false;
	}
	private String getTip(int x, int y){
		for (String key : thetooltips.keySet()) {
			Integer[] mousepoints = thetooltips.get(key);
			int startX = mousepoints[0];
			int startY = mousepoints[1];
			int endX = mousepoints[2];
			int endY = mousepoints[3];
			int xlenght = endX - startX;
			int ylenght = endY - startY;
			for(int i = 0; i < xlenght; i++){
				for(int k = 0; k < ylenght; k++){
					if(x == (startX + i) && y == (startY + k)){
						return key;
					}
				}
			}
		}
		return "";
	}
	private void drawToolTips(String[] toolTips, int mouseX, int mouseY) {
		mouseX += 2;
		mouseY -= 2;
		int x = 0;
		int iteration = 0;
		for (String tip : toolTips){
			iteration ++;
			if(this.fontRenderer.getStringWidth(tip) > x){
				x = this.fontRenderer.getStringWidth(tip);
			}
		}
		int var15 = -267386864;
		int var16 = 1347420415;
		@SuppressWarnings("unused")
		int var17 = (var16 & 16711422) >> 1 | var16 & -16777216;
		this.drawGradientRect(mouseX - 1, mouseY - 5, mouseX + x + 9, mouseY + (toolTips.length * 12) - 1, var15, var15);
		this.drawGradientRect(mouseX + x + 7, mouseY - 5, mouseX + x + 8, mouseY + (toolTips.length * 12) - 1, var16, var16);
		this.drawGradientRect(mouseX, mouseY - 5, mouseX + 1, mouseY + (toolTips.length * 12) - 1, var16, var16);
		this.drawGradientRect(mouseX + 1, mouseY - 5, mouseX + x + 7, mouseY - 4, var16, var16);
		this.drawGradientRect(mouseX + 1, mouseY + (toolTips.length * 12) - 2, mouseX + x + 7, mouseY + (toolTips.length * 12) - 1, var16, var16);
		this.drawGradientRect(mouseX, mouseY + (toolTips.length * 12) - 1, mouseX + x + 8, mouseY + (toolTips.length * 12), var15, var15);
		this.drawGradientRect(mouseX, mouseY - 6, mouseX + x + 8, mouseY - 5, var15, var15);
		iteration = 0;
		for (String tip : toolTips){
			iteration ++;
			if(iteration < 2){
				this.fontRenderer.drawStringWithShadow(tip, mouseX + 5, mouseY, -1);
			}
			else{
				this.fontRenderer.drawStringWithShadow(tip, mouseX + 5, mouseY + (iteration * 6), -1);
			}
		}
	}
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j){}
}
