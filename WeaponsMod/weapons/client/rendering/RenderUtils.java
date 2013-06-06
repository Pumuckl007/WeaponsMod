package weapons.client.rendering;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

import cpw.mods.fml.client.FMLClientHandler;

import weapons.lib.Textures;

public class RenderUtils {

    private static int rotationAngle = 0;
    private static float lightmapLastX;
    private static float lightmapLastY;

    public static void renderRotatingBlockIntoGUI(FontRenderer fontRenderer, RenderEngine renderEngine, ItemStack stack, int x, int y, float zLevel, float scale) {

        RenderBlocks renderBlocks = new RenderBlocks();

        Block block = Block.blocksList[stack.itemID];
        renderEngine.bindTexture(Textures.VANILLA_BLOCK_TEXTURE_SHEET);
        GL11.glPushMatrix();
        GL11.glTranslatef(x - 2, y + 3, -3.0F + zLevel);
        GL11.glScalef(10.0F, 10.0F, 10.0F);
        GL11.glTranslatef(1.0F, 0.5F, 1.0F);
        GL11.glScalef(1.0F * scale, 1.0F * scale, -1.0F);
        GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(0F + 1 * rotationAngle, 0.0F, 1.0F, 0.0F);
        rotationAngle = (rotationAngle + 1) % 360;

        int var10 = Item.itemsList[stack.itemID].getColorFromItemStack(stack, 0);
        float var16 = (var10 >> 16 & 255) / 255.0F;
        float var12 = (var10 >> 8 & 255) / 255.0F;
        float var13 = (var10 & 255) / 255.0F;

        GL11.glColor4f(var16, var12, var13, 1.0F);

        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        renderBlocks.useInventoryTint = true;
        renderBlocks.renderBlockAsItem(block, stack.getItemDamage(), 1.0F);
        renderBlocks.useInventoryTint = true;
        GL11.glPopMatrix();
    }

    public static void renderItemIntoGUI(FontRenderer fontRenderer, RenderEngine renderEngine, ItemStack itemStack, int x, int y, float opacity, float scale) {

        Icon icon = itemStack.getIconIndex();
        GL11.glDisable(GL11.GL_LIGHTING);
        renderEngine.bindTexture(Textures.VANILLA_ITEM_TEXTURE_SHEET);
        int overlayColour = itemStack.getItem().getColorFromItemStack(itemStack, 0);
        float red = (overlayColour >> 16 & 255) / 255.0F;
        float green = (overlayColour >> 8 & 255) / 255.0F;
        float blue = (overlayColour & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, opacity);
        drawTexturedQuad(x, y, icon, 16 * scale, 16 * scale, -90);
        GL11.glEnable(GL11.GL_LIGHTING);

    }

    public static void drawTexturedQuad(int x, int y, Icon icon, float width, float height, double zLevel) {

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + width, y + height, zLevel, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, icon.getMinU(), icon.getMinV());
        tessellator.draw();
    }
    public static void drawGradientRect(float par1, int par2, float par3, int par4, int par5, int par6)
    {
        float f = (float)(par5 >> 24 & 255) / 255.0F;
        float f1 = (float)(par5 >> 16 & 255) / 255.0F;
        float f2 = (float)(par5 >> 8 & 255) / 255.0F;
        float f3 = (float)(par5 & 255) / 255.0F;
        float f4 = (float)(par6 >> 24 & 255) / 255.0F;
        float f5 = (float)(par6 >> 16 & 255) / 255.0F;
        float f6 = (float)(par6 >> 8 & 255) / 255.0F;
        float f7 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)par3, (double)par2, (double)100);
        tessellator.addVertex((double)par1, (double)par2, (double)100);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)par1, (double)par4, (double)100);
        tessellator.addVertex((double)par3, (double)par4, (double)100);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    public static void renderBeam(double x, double y, double z, double desierdx, double desierdy, double desierdz, double red, double green, double blue, double alpha, float radius){
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
		double lenght = Math.sqrt(Math.pow(x - desierdx, 2) + Math.pow(y - desierdy, 2) + Math.pow(z - desierdz, 2));
		double a = x - desierdx;
		double b = z - desierdz;
		double c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		double secondb = y - desierdy;
		double secondc = Math.sqrt(Math.pow(c, 2) + Math.pow(secondb, 2));
		double yaw = Math.asin(b/c);
		double pitch = Math.asin(secondb/secondc);
		RenderUtils.color(red, green, blue, alpha);
		GL11.glTranslated(x, y, z);
		if((x - desierdx) > 0 && (z - desierdz) > 0){
			GL11.glRotatef(-180, 0, 1, 0);
			GL11.glRotatef((float)Math.toDegrees(yaw), 0, 1, 0);
		}
		else if((x - desierdx) < 0 && (z - desierdz) > 0){
			GL11.glRotatef((float)Math.toDegrees(yaw) + 90, 0, 1, 0);
		}
		else if((x - desierdx) > 0 && (z - desierdz) < 0){
			GL11.glRotatef(-(float)Math.toDegrees(yaw) - 90, 0, 1, 0);
		}
		else{
			GL11.glRotatef(-(float)Math.toDegrees(yaw), 0, 1, 0);
		}
		if((y - desierdy) > 0){
			GL11.glRotatef(-90, 1, 0, 0);
		}
		GL11.glRotatef((float)Math.toDegrees(pitch), 1, 0, 0);
		Cylinder cyl = new Cylinder();
		cyl.draw(radius, radius, (float)lenght, 16, 1);
		GL11.glPopMatrix();
	}
    public static void color(double red, double green, double blue, double alpha){
		GL11.glColor4f((float)red/255F, (float)green/255F, (float)blue/255F, (float)alpha/255F);
	}
    public static void renderSphere(double x, double y, double z, double r, double g, double b, double alpha, float radius) {
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
		RenderUtils.color(r, g, b, alpha);
		GL11.glTranslated(x, y, z);
		Sphere s = new Sphere();
		s.draw(radius, 32, 16);
		GL11.glPopMatrix();
	}
    public static void glowOn() {
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        lightmapLastX = OpenGlHelper.lastBrightnessX;
        lightmapLastY = OpenGlHelper.lastBrightnessY;
        RenderHelper.disableStandardItemLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
    }

    public static void glowOff() {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapLastX, lightmapLastY);
        GL11.glPopAttrib();
    }
}
