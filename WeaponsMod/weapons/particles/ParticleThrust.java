package weapons.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

public class ParticleThrust extends EntityFX
{


	public ParticleThrust(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) 
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		this.particleMaxAge = 200;
		this.worldObj = par1World;
		this.setParticleTextureIndex(0);
		this.noClip = true;
	}
	public String getTexture()
	{
		return "null";
	}
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		Tessellator tessellator1 = new Tessellator();
		tessellator1.startDrawingQuads();
		tessellator1.setBrightness(getBrightnessForRender(par2));
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("null");

		float f4 = 0.1F * particleScale;
		float f5 = (float)((prevPosX + (posX - prevPosX) * (double)par2) - interpPosX);
		float f6 = (float)((prevPosY + (posY - prevPosY) * (double)par2) - interpPosY);
		float f7 = (float)((prevPosZ + (posZ - prevPosZ) * (double)par2) - interpPosZ);
		tessellator1.setColorOpaque_F(0, 0.6F, 1);
		tessellator1.addVertexWithUV(f5 - par3 * f4 - par6 * f4, f6 - par4 * f4, f7 - par5 * f4 - par7 * f4, 0, 0);
		tessellator1.addVertexWithUV((f5 - par3 * f4) + par6 * f4, f6 + par4 * f4, (f7 - par5 * f4) + par7 * f4, 0, 0);
		tessellator1.addVertexWithUV(f5 + par3 * f4 + par6 * f4, f6 + par4 * f4, f7 + par5 * f4 + par7 * f4, 0, 0);
		tessellator1.addVertexWithUV((f5 + par3 * f4) - par6 * f4, f6 - par4 * f4, (f7 + par5 * f4) - par7 * f4, 0, 0);

		tessellator1.draw();

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setDead();
		}
		this.motionY = 0;
	}
}