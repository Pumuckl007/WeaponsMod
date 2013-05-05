package weapons.client.rendering.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import weapons.client.models.bullet.ModelIceBall;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIceBall extends Render
{

	private ModelIceBall aModel = new ModelIceBall();
    public RenderIceBall()
    {
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
    	GL11.glPushMatrix();
//    	GL11.glTranslatef((float)par2, (float)par4, (float)par6);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/iceball.png");
    	aModel.render();
    	GL11.glPopMatrix();
    }

}
