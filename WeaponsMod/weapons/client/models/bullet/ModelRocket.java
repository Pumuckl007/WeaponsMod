package weapons.client.models.bullet;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import weapons.bullets.EntityRocket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelRocket extends ModelBase {

    private IModelCustom modelBullet;

    public ModelRocket() {

        modelBullet = AdvancedModelLoader.loadModel("/mods/weapons/models/rocketlauncher_shell.obj");
    }

    public void render(EntityRocket par1EntityRocket, double cx, double cy, double cz, float par9) {
        GL11.glTranslatef((float)cx, (float)cy, (float)cz);
        GL11.glRotatef(par1EntityRocket.prevRotationYaw + (par1EntityRocket.rotationYaw - par1EntityRocket.prevRotationYaw) * par9, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(par1EntityRocket.prevRotationPitch + (par1EntityRocket.rotationPitch - par1EntityRocket.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
        modelBullet.renderAll();
    }
    public void render() {
    	GL11.glScalef(15, 15, 15);
        modelBullet.renderAll();
    }



   
}
