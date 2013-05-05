package weapons.client.models.gun;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelIceBallLauncher extends ModelBase {

    private IModelCustom modelTable;

    public ModelIceBallLauncher() {

        modelTable = AdvancedModelLoader.loadModel("/mods/weapons/models/iceballlauncher.obj");
    }

    public void render() {

        modelTable.renderAll();
    }



   
}
