package weapons.client.renderitems;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import weapons.client.models.ModelSpaceShip;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ItemSpaceShipRender extends Render implements IItemRenderer {

	private ModelSpaceShip testModel;

	public ItemSpaceShipRender() {

		testModel = new ModelSpaceShip();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {

		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

		if(type == ItemRenderType.ENTITY){
			return type == ItemRenderType.ENTITY;
		}
		else{
			return type == ItemRenderType.INVENTORY;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {


		switch (type) {
			case ENTITY: {
				render(1F, 0F, 0F, 0F, 0F, 0F, 0.50F);
				return;
			}
			case EQUIPPED: {
				render(1F, 0.0F, 1.25F, 0F, 90F, 0F, 0.40F);
				return;
			}
			case INVENTORY: {
				render(1.0F, -1F, 0.0F, 0, 10F, 0F, 0.20F);
				return;
			}
			case EQUIPPED_FIRST_PERSON:{
				render(3F, 0.5F, 1.25F, 0F, 0, -27.5F, 0.50F);
				return;
			}
			default:
				return;
		}

	}

	private void render(float x, float y, float z, float rotx, float roty, float rotz, float scale) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		// Scale, Translate, Rotate
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(rotx, 1, 0, 0);
		GL11.glRotatef(roty, 0, 1, 0);
		GL11.glRotatef(rotz, 0, 0, 1);



		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/spaceship.png");

		// Render
		testModel.render();

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1)
	{
		// TODO Auto-generated method stub

	}
}
