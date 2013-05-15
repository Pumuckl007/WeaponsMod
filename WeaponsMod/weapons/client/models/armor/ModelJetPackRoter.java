package weapons.client.models.armor;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelJetPackRoter extends ModelBase {

    private IModelCustom modeljetpack;

    public ModelJetPackRoter() {

        modeljetpack = AdvancedModelLoader.loadModel("/mods/weapons/models/jetpackroters.obj");
    }

    public void render() {

    	GL11.glPushMatrix();
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/jetpackroters.png");
        modeljetpack.renderAll();
        GL11.glPopMatrix();
    }



   
}
