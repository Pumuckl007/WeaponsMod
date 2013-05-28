package weapons.client.renderblocks;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import weapons.client.models.blocks.ModelPowerStorage;
import weapons.tileentity.TileEntityPowerStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderPowerStorage extends TileEntitySpecialRenderer {

	private ModelPowerStorage model = new ModelPowerStorage();

	public TileEntityRenderPowerStorage(){
	}
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		GL11.glPushMatrix();
		model.render((TileEntityPowerStorage) tileEntity, x, y, z);
		GL11.glPopMatrix();
	}

}
