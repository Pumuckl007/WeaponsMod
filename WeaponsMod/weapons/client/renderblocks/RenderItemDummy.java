package weapons.client.renderblocks;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class RenderItemDummy extends RenderItem{

	public void render(EntityItem par1EntityItem, Icon par2Icon, int par3, float par4, float par5, float par6, float par7)
	{
		Tessellator tessellator = Tessellator.instance;

		if (par2Icon == null)
		{
			par2Icon = FMLClientHandler.instance().getClient().renderEngine.getMissingIcon(par1EntityItem.getEntityItem().getItemSpriteNumber());
		}

		float f4 = par2Icon.getMinU();
		float f5 = par2Icon.getMaxU();
		float f6 = par2Icon.getMinV();
		float f7 = par2Icon.getMaxV();
		float f9 = 0.5F;
		float f10 = 0.25F;
		float f11;

		GL11.glPushMatrix();

		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

		float f12 = 0.0625F;
		f11 = 0.021875F;
		ItemStack itemstack = par1EntityItem.getEntityItem();
		byte b0 = getMiniItemCount(itemstack);

		GL11.glTranslatef(-f9, -f10, -((f12 + f11) * (float)b0 / 2.0F));

		for (int k = 0; k < b0; ++k)
		{

			GL11.glTranslatef(0f, 0f, f12 + f11);

			GL11.glColor4f(par5, par6, par7, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, f5, f6, f4, f7, par2Icon.getSheetWidth(), par2Icon.getSheetHeight(), f12);

			if (itemstack != null && itemstack.hasEffect())
			{
				GL11.glDepthFunc(GL11.GL_EQUAL);
				GL11.glDisable(GL11.GL_LIGHTING);
				FMLClientHandler.instance().getClient().renderEngine.bindTexture("%blur%/misc/glint.png");
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
				float f13 = 0.76F;
				GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glPushMatrix();
				float f14 = 0.125F;
				GL11.glScalef(f14, f14, f14);
				float f15 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
				GL11.glTranslatef(f15, 0.0F, 0.0F);
				GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glScalef(f14, f14, f14);
				f15 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
				GL11.glTranslatef(-f15, 0.0F, 0.0F);
				GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
				ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
				GL11.glPopMatrix();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}
		}

		GL11.glPopMatrix();
	}

}
