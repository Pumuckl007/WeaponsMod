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

    public void render(EntityBullet par1EntityBullet, double cx, double cy, double cz, float x, float y, float z) {
        GL11.glTranslatef((float)cx, (float)cy, (float)cz);
        GL11.glRotatef(x, 1, 0, 0);
        GL11.glRotatef(y, 0, 1, 0);
        GL11.glRotatef(z, 0, 0, 1);
        modelBullet.renderAll();
    }
    public void render() {

        modelBullet.renderAll();
    }



   
}
