package weapons.client.models.armor;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelJetBoot extends ModelBase {

    private IModelCustom modeljetbootleft;
    private IModelCustom modeljetbootright;

    public ModelJetBoot() {

        modeljetbootleft = AdvancedModelLoader.loadModel("/mods/weapons/models/jetbootsleft.obj");
        modeljetbootright = AdvancedModelLoader.loadModel("/mods/weapons/models/jetbootsright.obj");
    }

    public void renderleft() {

        modeljetbootleft.renderAll();
    }
    public void renderright() {

        modeljetbootright.renderAll();
    }
    public void render(){
    	modeljetbootleft.renderAll();
    	modeljetbootright.renderAll();
    }



   
}