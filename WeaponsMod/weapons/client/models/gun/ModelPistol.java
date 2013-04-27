package weapons.client.models.gun;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelPistol extends ModelBase {

    private IModelCustom modelTable;

    public ModelPistol() {

        modelTable = AdvancedModelLoader.loadModel("/mods/weapons/models/pistol.obj");
    }

    public void render() {

        modelTable.renderAll();
    }



   
}
