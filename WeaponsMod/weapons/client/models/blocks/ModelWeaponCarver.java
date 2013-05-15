package weapons.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import weapons.tileentity.TileEntityWeaponCarver;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWeaponCarver extends ModelBase {

    private IModelCustom model;

    public ModelWeaponCarver() {

        model = AdvancedModelLoader.loadModel("/mods/weapons/models/weaponcarver.obj");
    }

    public void render() {

        model.renderAll();
    }

    public void render(TileEntityWeaponCarver tile, double x, double y, double z) {

        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.5F, 0, 0.5F);
        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/WeaponCarver.png");

        // Render
        this.render();

        GL11.glPopMatrix();
    }

}
