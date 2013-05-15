package weapons.client.renderblocks;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import weapons.client.models.blocks.ModelDeathBall;
import weapons.client.models.blocks.ModelDeathCubein;
import weapons.client.models.blocks.ModelDeathCubeout;
import weapons.client.models.blocks.ModelDeathPentagon;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockItemDeathRender implements IItemRenderer {

	private ModelDeathCubein modelCubein;
	private ModelDeathPentagon modelPentagon;
	private ModelDeathCubeout modelCubeout;
	private ModelDeathBall modelBall;

    public BlockItemDeathRender() {

    	modelCubein  = new ModelDeathCubein();
    	modelPentagon = new ModelDeathPentagon();
    	modelCubeout = new ModelDeathCubeout();
    	modelBall = new ModelDeathBall();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {

        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        switch (type) {
            case ENTITY: {
                renderModel(0, 0.0F, 0.5F, 0.8F);
                return;
            }
            case EQUIPPED: {
                renderModel(0.5F, 0.0F, 1.25F, 0.66F);
                return;
            }
            case INVENTORY: {
                renderModel(-1, -1.7F, -0.8F, 0.85F);
                return;
            }
            default:
                return;
        }
    }

    private void renderModel(float x, float y, float z, float scale) {

        GL11.glPushMatrix();

        // Scale, Translate, Rotate
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x, y, z);
        GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/deathcubein.png");

        // Render
        modelCubein.render();
        
     // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/deathpentagon.png");

        // Render
        modelPentagon.render();
        
     // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/deathcubeout.png");

        // Render
        modelCubeout.render();
        
     // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/deathball.png");

        // Render
        modelBall.render();

        GL11.glPopMatrix();
    }
}
