package weapons.client.renderblocks;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import weapons.client.RenderTick;
import weapons.client.models.blocks.ModelSSFloat;
import weapons.client.models.blocks.ModelSSbase;
import weapons.client.rendering.RenderUtils;
import weapons.tileentity.TileEntitySicurityStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderSS extends TileEntitySpecialRenderer {

	private ModelSSbase model = new ModelSSbase();
	private ModelSSFloat modeltop = new ModelSSFloat();
	private final RenderItem customRenderItem;
	private double alphabackup;

	public TileEntityRenderSS(){
		customRenderItem = new RenderItem() {

			@Override
			public boolean shouldBob() {

				return false;
			};
		};
		customRenderItem.setRenderManager(RenderManager.instance);
	}
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		alphabackup = RenderTick.alpha;
		TileEntitySicurityStorage tile = (TileEntitySicurityStorage) tileEntity;
		if(!tile.ison){
			RenderTick.alpha = RenderTick.alpha/4;
		}
		GL11.glPushMatrix();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		model.render((TileEntitySicurityStorage) tileEntity, x, y, z);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		double thehight = Math.cos((RenderTick.hight[0]%90));
		renderbottombeam(x,y,z,thehight);
		double thehight2 = Math.cos((RenderTick.hight[1]%90));
		RenderUtils.renderBeam(x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, x + 0.75, y + 1.25 + (thehight2/10), z + 0.75, RenderTick.r, RenderTick.g + 40, RenderTick.b + 40, RenderTick.alpha, RenderTick.radius * 0.5F);
		RenderUtils.renderBeam(x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, x + 0.75, y + 1.25 + (thehight2/10), z + 0.25, RenderTick.r, RenderTick.g + 40, RenderTick.b + 40, RenderTick.alpha, RenderTick.radius * 0.5F);
		RenderUtils.renderBeam(x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, x + 0.25, y + 1.25 + (thehight2/10), z + 0.75, RenderTick.r, RenderTick.g + 40, RenderTick.b + 40, RenderTick.alpha, RenderTick.radius * 0.5F);
		RenderUtils.renderBeam(x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, x + 0.25, y + 1.25 + (thehight2/10), z + 0.25, RenderTick.r, RenderTick.g + 40, RenderTick.b + 40, RenderTick.alpha, RenderTick.radius * 0.5F);

		RenderUtils.renderSphere(x + 0.75, y + 1.25 + (thehight2/10), z + 0.75, RenderTick.r, RenderTick.g + 40, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderSphere(x + 0.75, y + 1.25 + (thehight2/10), z + 0.25, RenderTick.r, RenderTick.g + 40, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderSphere(x + 0.25, y + 1.25 + (thehight2/10), z + 0.75, RenderTick.r, RenderTick.g + 40, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderSphere(x + 0.25, y + 1.25 + (thehight2/10), z + 0.25, RenderTick.r, RenderTick.g + 40, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		GL11.glEnable(GL11.GL_CULL_FACE);
		RenderUtils.renderSphere(x + 0.5F,  y + 0.75F + (thehight / 20),  z + 0.5F, RenderTick.r, RenderTick.g + 40, RenderTick.b + 40, 255, RenderTick.radius + 0.03F);
		RenderUtils.renderSphere(x + 0.5F,  y + 0.75F + (thehight / 20),  z + 0.5F, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha + 30, 0.1F);
		RenderUtils.renderSphere(x + 0.5F,  y + 0.75F + (thehight / 20),  z + 0.5F, RenderTick.r, RenderTick.g + 245, RenderTick.b + 16, 50, 0.11F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslated(0,  0.50 + (thehight2/10), 0);
		GL11.glTranslated(x, y, z);
		RenderUtils.color(255, 255, 255, 255);
		renderItem((TileEntitySicurityStorage)tileEntity);
		GL11.glTranslated(-x, -y, -z);
		RenderUtils.color(RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha + 30);
		modeltop.render((TileEntitySicurityStorage) tileEntity, x, y, z);
		GL11.glPopMatrix();
		RenderTick.alpha = alphabackup;
	}
	private void renderbottombeam( double x, double y, double z, double thehight){
		RenderUtils.renderBeam(x + 0.500000000001, y + 0.125, z + 0.500000000001, x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, RenderTick.r - 40, RenderTick.g + 245 - 40, RenderTick.b + 16 - 40, 200, RenderTick.radius - 0.01F);
		RenderUtils.renderBeam(x + 0.445, y + 0.215, z + 0.445, x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderBeam(x + 0.445, y + 0.215, z + 0.555, x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderBeam(x + 0.555, y + 0.215, z + 0.445, x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderBeam(x + 0.555, y + 0.215, z + 0.555, x + 0.5, y + 0.75 + (thehight / 20), z + 0.5, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha, RenderTick.radius);

		RenderUtils.renderSphere(x + 0.445, y + 0.215, z + 0.445, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderSphere(x + 0.445, y + 0.215, z + 0.555, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderSphere(x + 0.555, y + 0.215, z + 0.445, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderSphere(x + 0.555, y + 0.215, z + 0.555, RenderTick.r, RenderTick.g, RenderTick.b, RenderTick.alpha, RenderTick.radius);
		RenderUtils.renderSphere(x + 0.500000000001, y + 0.125, z + 0.500000000001, RenderTick.r - 40, RenderTick.g + 245 - 40, RenderTick.b + 16 - 40, 200, RenderTick.radius - 0.01F);
	}
	private void renderItem(TileEntitySicurityStorage tile){
		GL11.glPushMatrix();

		GL11.glTranslated(0.5, 0.85, 0.5);
		GL11.glScaled(0.37, 0.37, 0.37);
		for(int i = 0; i < 32; i++){
			if (tile.getStackInSlot(i) != null) {

				float scaleFactor = getGhostItemScaleFactor(tile.getStackInSlot(i));
				float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
				EntityItem ghostEntityItem = new EntityItem(tile.worldObj);
				ghostEntityItem.hoverStart = 0;
				ghostEntityItem.setEntityItemStack(tile.getStackInSlot(i));
				GL11.glPushMatrix();
				GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
				if(i < 20){
					GL11.glRotatef(i * 18F, 0, 1, 0);
					GL11.glTranslated(0.5, 0.5, 0.5);
				}
				else if(i < 26){
					GL11.glRotatef(i * 60F, 0, 1, 0);
					GL11.glRotatef(35, 0, 0, 1);
					GL11.glTranslated(0.35, 0.7, 0.35);
				}
				else{
					GL11.glRotatef(i * 60F, 0, 1, 0);
					GL11.glRotatef(-35, 0, 0, 1);
					GL11.glTranslated(0.35, 0.35, 0.35);
				}
				GL11.glRotatef(rotationAngle, 0, 1, 0);
				customRenderItem.doRenderItem(ghostEntityItem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix();
			}
		}
		GL11.glPopMatrix();
	}
	private float getGhostItemScaleFactor(ItemStack itemStack) {

		float scaleFactor = 1.0F;

		if (itemStack != null) {
			if (itemStack.getItem() instanceof ItemBlock) {
				switch (customRenderItem.getMiniBlockCount(itemStack)) {
					case 1:
						return 0.90F;
					case 2:
						return 0.90F;
					case 3:
						return 0.90F;
					case 4:
						return 0.90F;
					case 5:
						return 0.80F;
					default:
						return 0.90F;
				}
			}
			else {
				switch (customRenderItem.getMiniItemCount(itemStack)) {
					case 1:
						return 0.65F;
					case 2:
						return 0.65F;
					case 3:
						return 0.65F;
					case 4:
						return 0.65F;
					default:
						return 0.65F;
				}
			}
		}

		return scaleFactor;
	}
}


