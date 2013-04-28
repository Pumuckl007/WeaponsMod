package weapons.client.models.gun;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelScarH extends ModelBase {

    private IModelCustom modelTable;

    public ModelScarH() {

        modelTable = AdvancedModelLoader.loadModel("/mods/weapons/models/scarh.obj");
    }

    public void render() {

        modelTable.renderAll();
    }



   
}
