package weapons.client.models.gun;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelFlameThrower extends ModelBase {

    private IModelCustom modelFL;

    public ModelFlameThrower() {

        modelFL = AdvancedModelLoader.loadModel("/mods/weapons/models/flamethrower.obj");
    }

    public void render() {

    	GL11.glRotatef(-90, 0, 1, 0);
        modelFL.renderAll();
    }



   
}
