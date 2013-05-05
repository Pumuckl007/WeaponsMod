package weapons.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class ModelDummyPlayer extends ModelBiped{

	float armoffx = 0;
	ModelDummyPlayer(){
		super();
	}
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

        if (this.isChild)
        {
            float f6 = 2.0F;
            GL11.glPushMatrix();
            GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
            GL11.glTranslatef(0.0F, 16.0F * par7, 0.0F);
            this.bipedHead.render(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
            this.bipedHeadwear.render(par7);
            GL11.glPopMatrix();
        }
        else
        {
    		
        	GL11.glPushMatrix();
            this.bipedRightArm.rotateAngleX = 180;
            this.bipedLeftArm.rotateAngleX = 180;
            this.bipedRightArm.rotateAngleY = 4;
            this.bipedLeftArm.rotateAngleY = -4;
            this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
            this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
            
            this.bipedHead.rotateAngleY = 0;
            this.bipedHead.rotateAngleX = 0;
            this.bipedBody.rotateAngleY = 0;
            this.bipedBody.rotateAngleX = 0;
            this.bipedHead.render(par7 / 3F);
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
            this.bipedHeadwear.render(par7);
            GL11.glPopMatrix();
        }
    }
	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
		
        this.bipedHead.rotateAngleY = 0;
        this.bipedHead.rotateAngleX = 0;
        this.bipedBody.rotateAngleY = 0;
        this.bipedBody.rotateAngleX = 0;
        this.bipedRightArm.rotateAngleX = 180 + armoffx;
        this.bipedLeftArm.rotateAngleX = 180 - armoffx;
        this.bipedRightArm.rotateAngleY = 4 + armoffx;
        this.bipedLeftArm.rotateAngleY = 4 - armoffx;
        this.bipedRightLeg.rotateAngleX = 0;
        this.bipedLeftLeg.rotateAngleX = 0;
        this.bipedRightLeg.rotateAngleY = 0;
        this.bipedLeftLeg.rotateAngleY = 0;

        

        

        if (this.isSneak)
        {
            this.bipedBody.rotateAngleX = 0.5F;
            this.bipedRightArm.rotateAngleX += 0.4F;
            this.bipedLeftArm.rotateAngleX += 0.4F;
            this.bipedRightLeg.rotationPointZ = 4.0F;
            this.bipedLeftLeg.rotationPointZ = 4.0F;
            this.bipedRightLeg.rotationPointY = 9.0F;
            this.bipedLeftLeg.rotationPointY = 9.0F;
            this.bipedHead.rotationPointY = 1.0F;
            this.bipedHeadwear.rotationPointY = 1.0F;
        }
        else
        {
            this.bipedBody.rotateAngleX = 0.0F;
            this.bipedRightLeg.rotationPointZ = 0.1F;
            this.bipedLeftLeg.rotationPointZ = 0.1F;
            this.bipedRightLeg.rotationPointY = 12.0F;
            this.bipedLeftLeg.rotationPointY = 12.0F;
            this.bipedHead.rotationPointY = 0.0F;
            this.bipedHeadwear.rotationPointY = 0.0F;
        }


        if (this.aimedBow)
        {
           
        }
    }
}
