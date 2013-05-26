package weapons.client.renderblocks;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import weapons.client.models.blocks.ModelProjetor;
import weapons.tileentity.TileEntityProjetor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderProjetor extends TileEntitySpecialRenderer {

	private ModelProjetor model = new ModelProjetor();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, 0, 0.5F);
		GL11.glScalef(0.89F, 0.89F, 0.89F);
		TileEntityProjetor tile = (TileEntityProjetor) tileEntity;
		model.render(tile, x, y, z, true, "projettorbase", tick);
		model.render(tile, x, y, z, false, "projettor", tick);
		GL11.glPopMatrix();
		Long time = tile.worldObj.getWorldTime()%24000;
		//				(tile.xCoord, tile.yCoord - 1, tile.zCoord);
		double thectime = time - (time%1000);
		Integer houre =(int) thectime;
		double intemediattime = (time - (1000 * houre))/(1000D/60D);
		Integer minut = (int)intemediattime;
		Integer actualhoure = (houre + 6) % 24;
		@SuppressWarnings("unused")
		String thetime;
		if(minut < 10){
			thetime = actualhoure.toString() + ":0" + minut.toString();
		}
		else{
			thetime = actualhoure.toString() + ":" + minut.toString();
		}
		byte off = 20;
//		GL11.glPushMatrix();
//		renderString(x,y,z, thetime,off);
//		GL11.glPopMatrix();
		off = 10;
		Integer posX = tile.xCoord;
		Integer posY = tile.yCoord;
		Integer posZ = tile.zCoord;
		GL11.glPushMatrix();
		renderString(x,y,z, "posZ: " + posZ.toString(), off);
		GL11.glPopMatrix();
		off = 0;
		GL11.glPushMatrix();
		renderString(x,y,z, "posY: " + posY.toString(), off);
		GL11.glPopMatrix();
		off = -10;
		GL11.glPushMatrix();
		renderString(x,y,z, "posX: " + posX.toString(), off);

		GL11.glPopMatrix();
	}
	public FontRenderer getFontRendererFromRenderManager()
	{
		return FMLClientHandler.instance().getClient().fontRenderer;
	}
	public void renderString(double x, double y, double z, String string, byte offsetY){
		GL11.glTranslated(0.5, 0, 0.5);
		RenderManager renderManager = RenderManager.instance;
		FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
		float f = 1.6F;
		float f1 = 0.016666668F * f;
		GL11.glTranslatef((float)x + 0.0F, (float)y + 1.6F + 0.5F, (float)z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f1, -f1, f1);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.instance;
		byte b0 = offsetY;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		tessellator.startDrawingQuads();
		int j = fontrenderer.getStringWidth(string) / 2;
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tessellator.addVertex((double)(-j - 1), (double)(-1 + b0), 0.0D);
		tessellator.addVertex((double)(-j - 1), (double)(8 + b0), 0.0D);
		tessellator.addVertex((double)(j + 1), (double)(8 + b0), 0.0D);
		tessellator.addVertex((double)(j + 1), (double)(-1 + b0), 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		fontrenderer.drawString(string, -fontrenderer.getStringWidth(string) / 2, b0, 553648127);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		fontrenderer.drawString(string, -fontrenderer.getStringWidth(string) / 2, b0, -1);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
