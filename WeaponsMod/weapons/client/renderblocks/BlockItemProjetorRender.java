package weapons.client.renderblocks;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import weapons.client.models.blocks.ModelProjetor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockItemProjetorRender implements IItemRenderer {

	private ModelProjetor model;

    public BlockItemProjetorRender() {

    	model  = new ModelProjetor();
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
                renderModel(-0.9F, -1.4F, -0.8F, 0.85F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:{
            	renderModel(0.5F, 0.0F, 1.25F, 0.66F);
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

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/projettor.png");
        model.render(false);
     // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/projettorbase.png");
        model.render(true);

        GL11.glPopMatrix();
    }
}
