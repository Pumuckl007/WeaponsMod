package weapons.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelTransBlockTeleport extends ModelBase {

    private IModelCustom model;
    private IModelCustom model2;

    public ModelTransBlockTeleport() {

        model = AdvancedModelLoader.loadModel("/mods/weapons/models/tbt.obj");
        model2 = AdvancedModelLoader.loadModel("/mods/weapons/models/tbtglow.obj");
    }

    public void render() {

        model.renderAll();
    }
    public void renderGlow() {

        model2.renderAll();
    }



   
}
