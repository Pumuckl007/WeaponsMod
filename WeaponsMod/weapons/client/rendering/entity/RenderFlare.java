package weapons.client.rendering.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import weapons.client.rendering.RenderUtils;
import weapons.entity.EntityFlare;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFlare extends Render
{

	public RenderFlare()
	{
		this.shadowSize = 0.5F;
	}

	public void render(EntityFlare entity, double x, double y, double z, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderUtils.glowOn();
		if(entity.getLife() < 101){
			RenderUtils.renderSphere(x, y, z, 255, 225, 0, 75, ((float)entity.getLife() / 4F) / 100F);
			RenderUtils.renderSphere(x, y, z, 255, 225, 0, 75, ((float)entity.getLife() / 2F) / 100F);
			RenderUtils.renderSphere(x, y, z, 255, 225, 0, 75, ((float)entity.getLife() - (float)entity.getLife() / 4F) / 100F);
		}
		else{
			RenderUtils.renderSphere(x, y, z, 255, 225, 0, 75, 0.25F);
			RenderUtils.renderSphere(x, y, z, 255, 225, 0, 75, 0.50F);
			RenderUtils.renderSphere(x, y, z, 255, 225, 0, 75, 0.75F);
		}
		RenderUtils.glowOff();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.render((EntityFlare)par1Entity, par2, par4, par6, par8, par9);
	}
}
