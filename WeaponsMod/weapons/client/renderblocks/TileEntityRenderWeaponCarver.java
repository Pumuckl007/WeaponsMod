package weapons.client.renderblocks;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import weapons.client.models.blocks.ModelWeaponCarver;
import weapons.client.rendering.RenderUtils;
import weapons.tileentity.TileEntityWeaponCarver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderWeaponCarver extends TileEntitySpecialRenderer {

	private ModelWeaponCarver model = new ModelWeaponCarver();
	private final RenderItem customRenderItem;

	public TileEntityRenderWeaponCarver(){
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
		GL11.glPushMatrix();
		model.render((TileEntityWeaponCarver) tileEntity, x, y, z);
		RenderUtils.color(255, 255, 255, 255);
		GL11.glTranslated(x, y, z);
		renderItem((TileEntityWeaponCarver)tileEntity);
		GL11.glPopMatrix();
	}
	private void renderItem(TileEntityWeaponCarver tile){
		GL11.glPushMatrix();

		GL11.glTranslated(0.5, 1.25, 0.5);
		GL11.glScaled(0.37, 0.37, 0.37);
		for(int i = 0; i < 9; i++){
			if (tile.getStackInSlot(i) != null) {

				float scaleFactor = getGhostItemScaleFactor(tile.getStackInSlot(i));
				float rotationAngle = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
				EntityItem ghostEntityItem = new EntityItem(tile.worldObj);
				ghostEntityItem.hoverStart = 0;
				ghostEntityItem.setEntityItemStack(tile.getStackInSlot(i));
				GL11.glPushMatrix();
				GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
				switch(i){
					case(0):{
						GL11.glTranslated(0.75, 0, 0.75);
						break;
					}
					case(1):{
						GL11.glTranslated(0, 0, 0.75);
						break;
					}
					case(2):{
						GL11.glTranslated(-0.75, 0, 0.75);
						break;
					}
					case(3):{
						GL11.glTranslated(0.75, 0, 0);
						break;
					}
					case(4):{
						break;
					}
					case(5):{
						GL11.glTranslated(-0.75, 0, 0);
						break;
					}
					case(6):{
						GL11.glTranslated(0.75, 0, -0.75);
						break;
					}
					case(7):{
						GL11.glTranslated(0, 0, -0.75);
						break;
					}
					case(8):{
						GL11.glTranslated(-0.75, 0, -0.75);
						break;
					}
					default:{
						break;
					}
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
