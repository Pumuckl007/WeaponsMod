package weapons.client.rendering.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import weapons.bullets.EntityRocket;
import weapons.client.models.bullet.ModelRocket;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRocket extends Render
{
    private ModelRocket aModel = new ModelRocket();

    public RenderRocket(float par1)
    {
    }

    public void doRenderLightningBlot(EntityRocket par1EntityBullet, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
		// Bind texture
        GL11.glScalef(0.1F, 0.1F, 0.1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/rocetlanch.png");
		aModel.render(par1EntityBullet, par2, par4, par6, par9);
		
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
        this.doRenderLightningBlot((EntityRocket)par1Entity, par2, par4, par6, par8, par9);
    }
}

