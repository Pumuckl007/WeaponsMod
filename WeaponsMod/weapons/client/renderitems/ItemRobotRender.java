package weapons.client.renderitems;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import weapons.client.models.ModelRobot;
import weapons.client.rendering.RenderUtils;
import weapons.utils.EnumRobot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ItemRobotRender extends Render implements IItemRenderer {

	private ModelRobot model;
	public ItemRobotRender() {

		model = new ModelRobot();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		
		return RenderUtils.shouldRender3d();
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
				render(0F, 0F, 0F, 0F, 0F, 0F, 0.50F, item);
				return;
			}
			case EQUIPPED: {
				render(1.4F, -1F, 0.05F, -7F, -75F, -7F, 0.25F, item);
				return;
			}
			case INVENTORY: {
				render(-2.5F, -1F, 0.0F, 0, 10F, 0F, 0.35F, item);
				return;
			}
			case EQUIPPED_FIRST_PERSON:{
				render(1F, 0F, 1.25F, 0F, 0, 13F, 0.50F, item);
				return;
			}
			default:
				return;
		}

	}

	private void render(float x, float y, float z, float rotx, float roty, float rotz, float scale, ItemStack item) {

		GL11.glPushMatrix();

		// Scale, Translate, Rotate
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(rotx, 1, 0, 0);
		GL11.glRotatef(roty, 0, 1, 0);
		GL11.glRotatef(rotz, 0, 0, 1);
		model.render(EnumRobot.R_BBase);
		model.render(EnumRobot.R_BEye);
		model.render(EnumRobot.R_BThrust);
		model.render(EnumRobot.R_BTurnbottom);
		model.render(EnumRobot.R_LArm);
		model.render(EnumRobot.R_LZap);
		model.render(EnumRobot.R_RArm);
		model.render(EnumRobot.R_RZap);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1)
	{
		System.out.println("There was an eror: " + entity);

	}
}
