package weapons.client.renderitems;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import weapons.client.rendering.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ItemMobJarRender extends Render implements IItemRenderer {

	public ItemMobJarRender() {
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
				render(0F, 0F, 0F, 0F, 0F, 0F, 0.50F, item);
				return;
			}
			case EQUIPPED: {
				render(1.4F, -1F, 0.05F, -7F, -75F, -7F, 0.25F, item);
				return;
			}
			case INVENTORY: {
				render(0F, -2F, 0.0F, 0, 10F, 0F, 0.32F, item);
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
		if(item.hasTagCompound()){
			Entity entity = EntityList.createEntityByName(item.stackTagCompound.getString("WeaponTagExtraEntityName"), null);
			if(entity != null){
				entity.readFromNBT(item.stackTagCompound);
				if(!(entity instanceof EntityGhast) && x != 1){
					GL11.glScaled(3, 3, 3);
				}
				RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0);
			}
			else{
				RenderUtils.renderSphere(0, 0 + 1, 0, 255, 0, 0, 255, 1);
			}
		}
		else{
			RenderUtils.renderSphere(0, 0 + 1, 0, 255, 0, 0, 255, 1);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1)
	{
		// TODO Auto-generated method stub

	}
}
