package weapons.client.models.bullet;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelIceBall extends ModelBase {

    private IModelCustom modelBullet;

    public ModelIceBall() {

        modelBullet = AdvancedModelLoader.loadModel("/mods/weapons/models/iceball.obj");
    }

    public void render() {

        modelBullet.renderAll();
    }



   
}
