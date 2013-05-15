package weapons.client.models.armor;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModeljetPack extends ModelBase {

    private IModelCustom modeljetpack;

    public ModeljetPack() {

        modeljetpack = AdvancedModelLoader.loadModel("/mods/weapons/models/jetpack.obj");
    }

    public void render() {

        modeljetpack.renderAll();
    }



   
}
