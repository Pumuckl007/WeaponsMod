package weapons.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelInfo extends ModelBase {

    private IModelCustom modelInfo;

    public ModelInfo() {

        modelInfo = AdvancedModelLoader.loadModel("/mods/weapons/models/info.obj");
    }

    public void render() {

        modelInfo.renderAll();
    }



   
}
