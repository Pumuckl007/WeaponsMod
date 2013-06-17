package weapons.client.renderitems;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import weapons.client.models.ModelTransBlockTeleport;
import weapons.client.rendering.RenderUtils;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ItemTBTRender extends Render implements IItemRenderer {

	private ModelTransBlockTeleport model;
	public ItemTBTRender() {

		model = new ModelTransBlockTeleport();
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



		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		if(item.hasTagCompound()){
			if(item.stackTagCompound.hasKey("Teare")){
				switch(item.stackTagCompound.getInteger("Teare")){
					case(1):{
						RenderUtils.color(85, 255, 0, 255);
						break;
					}
					case(2):{
						RenderUtils.color(0, 240, 252, 255);
						break;
					}
					case(3):{
						RenderUtils.color(240, 0, 252, 255);
						break;
					}
				}
			}
		}
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/tbtbaseglow.png");

		GL11.glPushMatrix();
		RenderUtils.glowOn();
		GL11.glScaled(1.001, 1.001, 1.001);
		model.render();
		RenderUtils.glowOff();
		GL11.glPopMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/tbt.png");
		// Render
		model.render();
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderUtils.glowOn();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/tbtglow.png");
		model.renderGlow();
		RenderUtils.glowOff();
		if(item.hasTagCompound()){
			if(item.stackTagCompound.hasKey("Teare")){
				switch(item.stackTagCompound.getInteger("Teare")){
					case(1):{
						this.renderExtra(85, 255, 0, item);
						break;
					}
					case(2):{
						this.renderExtra(0, 240, 252, item);
						break;
					}
					case(3):{
						this.renderExtra(240, 0, 252, item);
						break;
					}
				}
			}
		}
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1)
	{
		// TODO Auto-generated method stub

	}
	public void renderExtra(int r, int g, int b, ItemStack item){
		GL11.glPushMatrix();
		RenderUtils.glowOn();
		float radius = item.stackTagCompound.getInteger("Power")/200;
		if(radius > 1){
			radius = 1;
		}
		RenderUtils.renderSphere(4.1, 1, 0, r, g, b, 255, 0.05F * radius);
		RenderUtils.renderSphere(4.1, 1, 0, r, g, b, 100, 0.35F * radius);
		RenderUtils.glowOff();
		GL11.glPopMatrix();
	}
}
