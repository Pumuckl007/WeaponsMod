package weapons.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import weapons.tileentity.TileEntityDeath;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDeathCubein extends ModelBase {

    private IModelCustom model;
//    float rotX = 0;
//    float rotY = 0;
//    float rotZ = 0;

    public ModelDeathCubein() {

        model = AdvancedModelLoader.loadModel("/mods/weapons/models/deathcubein.obj");
    }

    public void render() {

        model.renderAll();
    }

    public void render(TileEntityDeath tile, double x, double y, double z) {

        GL11.glPushMatrix();
//        float rotmult = (tile.timetokill / 10) + 1;
//        rotX -= 0.1;
//        rotY += 0.02;
//        rotZ -= 0.065;
//        GL11.glRotatef(rotX * rotmult, 1, 0, 0);
//        GL11.glRotatef(rotY * rotmult, 1, 0, 0);
//        GL11.glRotatef(rotZ * rotmult, 1, 0, 0);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/deathcubein.png");

        // Render
        this.render();

        GL11.glPopMatrix();
    }

}
