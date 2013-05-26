package weapons.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelSpaceShip extends ModelBase {

    private IModelCustom model;

    public ModelSpaceShip() {

        model = AdvancedModelLoader.loadModel("/mods/weapons/models/spaceship.obj");
    }

    public void render() {

        model.renderAll();
    }



   
}
