package weapons.client.models.armor;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelJetPackFire extends ModelBase {

    private IModelCustom modeljetpack;

    public ModelJetPackFire() {

        modeljetpack = AdvancedModelLoader.loadModel("/mods/weapons/models/jetpackfire.obj");
    }

    public void render() {

    	GL11.glPushMatrix();
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/jetpackfire.png");
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        modeljetpack.renderAll();
        GL11.glPopMatrix();
    }



   
}
