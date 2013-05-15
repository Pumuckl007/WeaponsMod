package weapons.client.renderblocks;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import weapons.client.models.blocks.ModelDeathBall;
import weapons.client.models.blocks.ModelDeathCubein;
import weapons.client.models.blocks.ModelDeathCubeout;
import weapons.client.models.blocks.ModelDeathPentagon;
import weapons.tileentity.TileEntityDeath;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderDeath extends TileEntitySpecialRenderer {

	private ModelDeathCubein modelCubein = new ModelDeathCubein();
	private ModelDeathPentagon modelPentagon = new ModelDeathPentagon();
	private ModelDeathCubeout modelCubeout = new ModelDeathCubeout();
	private ModelDeathBall modelBall = new ModelDeathBall();
	private double hight;
	private boolean isincrementing;

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {

		GL11.glPushMatrix();
		GL11.glTranslated(0, (Math.cos(hight)/ 2)  - 0.25, 0);
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		modelCubein.render((TileEntityDeath) tileEntity, x, y, z);
        GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		modelPentagon.render((TileEntityDeath) tileEntity, x, y, z);
		modelCubeout.render((TileEntityDeath) tileEntity, x, y, z);
		modelBall.render((TileEntityDeath) tileEntity, x, y, z);
		GL11.glPopMatrix();
		if(isincrementing){
			if(hight > 45){
				isincrementing = false;
			}
			else{
				hight += 0.003231;
			}
		}
		else{
			if(hight < 25){
				isincrementing = true;
			}
			else{
				hight -= 0.004734;
			}
		}
	}

}
