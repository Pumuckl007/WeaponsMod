package weapons.client.models.blocks;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import weapons.tileentity.TileEntityProjetor;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelProjetor extends ModelBase {

	private IModelCustom model;
	private IModelCustom model2;
	private double rotation = 0;
	private double hight = 0;

	public ModelProjetor() {

		model = AdvancedModelLoader.loadModel("/mods/weapons/models/projettor.obj");
		model2 = AdvancedModelLoader.loadModel("/mods/weapons/models/projettorbase.obj");
	}

	public void render(boolean part) {

		if(part){
			model2.renderAll();
			return;
		}
		model.renderAll();
	}

	public void render(TileEntityProjetor tile, double x, double y, double z, boolean part, String texture, float tick) {

		GL11.glPushMatrix();
		// Bind texture
		if(!part){
			double thehight = Math.cos((hight%90));
			GL11.glTranslated(0, (thehight / 20) + 0.1, 0);
			GL11.glRotatef((float)rotation, 0, 1, 0);
			rotation += 0.12634;
			hight += 0.0056543356;
		}
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/" + texture + ".png");

		// Render
		this.render(part);

		GL11.glPopMatrix();
	}

}
