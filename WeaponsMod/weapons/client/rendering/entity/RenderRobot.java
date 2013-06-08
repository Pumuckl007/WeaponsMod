package weapons.client.rendering.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import weapons.client.ClientProxy;
import weapons.client.RenderTick;
import weapons.client.models.ModelRobot;
import weapons.client.rendering.RenderUtils;
import weapons.entity.EntityRobot;
import weapons.utils.EnumRobot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRobot extends Render
{
	protected ModelRobot model;

	public RenderRobot()
	{
		this.shadowSize = 1F;
		this.model = new ModelRobot();
	}

	/**
	 * The render method used in RenderBoat that renders the boat model.
	 */
	public void renderBoat(EntityRobot entity, double x, double y, double z, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y + 1.5F, (float)z);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glPushMatrix();
		GL11.glRotatef(-(float)entity.rotationYaw, 0, 1, 0);
		GL11.glRotatef((float)entity.rotationPitch, 0, 0, 1);
		this.model.render(EnumRobot.R_BBase);
		this.model.render(EnumRobot.R_BEye);
		this.model.render(EnumRobot.R_BThrust);
//		this.model.render(EnumRobot.R_BBlock);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glRotatef(-(float)entity.rotationYaw, 0, 1, 0);
		GL11.glRotatef(-(float)entity.rotationYawHead, 0, 1, 0);
		GL11.glRotatef((float)entity.rotationPitch, 0, 0, 1);
		this.model.render(EnumRobot.R_BTurnbottom);
		GL11.glPopMatrix();
		GL11.glRotatef(-(float)entity.rotationYaw, 0, 1, 0);
		float yaw = (float)((Math.cos((RenderTick.rasYaw%90)) * 2.5));
		float pitch = (float)((Math.cos((RenderTick.rasPitch%90)) * 2.5));
		double higth = ((Math.cos((RenderTick.raHight%90)) * 0.025));
		GL11.glTranslated(0, higth, 0);
		this.model.render(EnumRobot.R_LArm, yaw, pitch);
		this.model.render(EnumRobot.R_LZap, yaw, pitch);
		this.model.render(EnumRobot.R_RArm, -yaw, -pitch);
		this.model.render(EnumRobot.R_RZap, -yaw, -pitch);
		GL11.glPopMatrix();
		if(ClientProxy.minecraft.thePlayer != null){
			if(ClientProxy.minecraft.thePlayer.username.equals(entity.getOwner())){
				byte yoff = 0;
				RenderUtils.renderString(x, y + 2, z, "Heath" + entity.getHealth(), yoff);
			}
		}
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderBoat((EntityRobot)par1Entity, par2, par4, par6, par8, par9);
	}
}
