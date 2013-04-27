package weapons.client.models.gun;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelRocketLauncher extends ModelBase {

    private IModelCustom modelTable;

    public ModelRocketLauncher() {

        modelTable = AdvancedModelLoader.loadModel("/mods/weapons/models/rocketlauncher.obj");
    }

    public void render() {

    	GL11.glScalef(6, 6, 6);
        modelTable.renderAll();
    }



   
}
