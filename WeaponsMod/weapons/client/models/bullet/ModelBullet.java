package weapons.client.models.bullet;

import org.lwjgl.opengl.GL11;

import weapons.bullets.EntityBullet;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelBullet extends ModelBase {

    private IModelCustom modelBullet;

    public ModelBullet() {

        modelBullet = AdvancedModelLoader.loadModel("/mods/weapons/models/bullet.obj");
    }

    public void render(EntityBullet par1EntityBullet, double cx, double cy, double cz, float par9) {
        GL11.glTranslatef((float)cx, (float)cy, (float)cz);
        GL11.glRotatef(90, 1, 0, 0);
        GL11.glRotatef(par1EntityBullet.prevRotationYaw + (par1EntityBullet.rotationYaw - par1EntityBullet.prevRotationYaw) * par9, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(par1EntityBullet.prevRotationPitch + (par1EntityBullet.rotationPitch - par1EntityBullet.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
        modelBullet.renderAll();
    }
    public void render() {

        modelBullet.renderAll();
    }



   
}
