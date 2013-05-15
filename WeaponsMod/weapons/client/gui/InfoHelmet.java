package weapons.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class InfoHelmet {
	protected float zLevel = 0.0F;
	private double offset = 0;

	public void draw(double xpos, double ypos, EntityClientPlayerMP player) {
		if(Minecraft.getMinecraft().currentScreen == null){
			offset += 0.03F;
			drawtop(xpos, ypos, player);
			drawbottom(xpos, ypos, player);
			drawArrow(5, 5, true);
			drawArrow(113, 5, false);
			int off = 0;
			while(off < 32){
				drawFissur((offset + (off * 4)) % 128, 0, true);
				drawFissur((offset + (off * 4)) % 128, 0, false);
				off ++;
			}
		}
	}


	public void drawtop(double xpos, double ypos, EntityClientPlayerMP player) {
		GL11.glColor4f(1.0F, 0.501960784F, 0.0F, 0.7F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/gui/InfoHelmTop.png");
		int with = FMLClientHandler.instance().getClient().displayWidth;
		int height = FMLClientHandler.instance().getClient().displayHeight;
		int xStart = with/50;
		int yStart = height/50;
		int xsize = 148;
		int ysize = 100;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xsize, ysize);
	}
	public void drawbottom(double xpos, double ypos, EntityClientPlayerMP player) {
		GL11.glColor4f(1.0F, 0.501960784F, 0.0F, 0.7F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/gui/InfoHelmTop.png");
		int with = FMLClientHandler.instance().getClient().displayWidth;
		int height = FMLClientHandler.instance().getClient().displayHeight;
		int xStart = with/50;
		int yStart = (23*height/50) - 100;
		int xsize = 148;
		int ysize = 100;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xsize, ysize);
	}
	public void drawFissur(double xpos, double ypos, boolean top) {
		GL11.glColor4f(0.8F, 0.4F, 0.0F, 0.5F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/gui/Fisur.png");
		int with = FMLClientHandler.instance().getClient().displayWidth;
		int height = FMLClientHandler.instance().getClient().displayHeight;
		int xStart;
		int yStart;
		if(top){
			xStart = (int)(with/50 + xpos);
			yStart = (int)(height/50 + ypos);
		}
		else{
			xStart = (int)(with/50+ xpos);
			yStart = (int)((23*height/50) - 100 + ypos);
		}
		int xsize = 1;
		int ysize = 100;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xsize, ysize);
	}
	public void drawArrow(double xpos, double ypos, boolean rotation){
		GL11.glColor4f(1.0F, 0.501960784F, 0.0F, 1F);
		if(rotation){
			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/gui/ArrowLeft.png");
		}
		else{
			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/gui/ArrowRight.png");
		}
		int with = FMLClientHandler.instance().getClient().displayWidth;
		int height = FMLClientHandler.instance().getClient().displayHeight;
		int xStart;
		int yStart;
		xStart = (int)(with/50+ xpos);
		yStart = (int)((23*height/50) - 100 + ypos);
		int xsize = 5;
		int ysize = 5;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xsize, ysize);
	}
	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
	 */
	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
		tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
		tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
		tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
		tessellator.draw();
	}
}
