package weapons.client.models.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelInfoHelmet extends ModelBiped {
	protected static ModelInfoHelmet instance;
	private ModelinfoHelmet aModel = new ModelinfoHelmet();

	public static ModelInfoHelmet getInstance() {
		if (instance == null) {
			instance = new ModelInfoHelmet();
		}
		return instance;
	}

	public WavefrontObject wavefrontObj;

	public ModelInfoHelmet()
	{
		this(0.0F);
	}

	public ModelInfoHelmet(float par2)
	{
		this.wavefrontObj = (WavefrontObject) AdvancedModelLoader.loadModel("/mods/weapons/models/jetpack.obj");
		this.bipedBody = new ModelPartRender(this, wavefrontObj);
		this.bipedBody.setRotationPoint(0.0F, 0.0F + par2, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		try {
			EntityLiving entity = (EntityLiving) par1Entity;
			ItemStack stack = entity.getCurrentItemOrArmor(0);
			this.heldItemRight = (stack != null) ? 1 : 0;
			this.isSneak = entity.isSneaking();
			this.aimedBow = ((EntityPlayer) entity).getItemInUse() != null;
			// if (entity.)
		} catch (Exception e) {
		}
		GL11.glPushMatrix();
		if(par1Entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) par1Entity;
			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/infohelmet.png");
			GL11.glScalef(0.6F, 0.6F, 0.6F);
//			GL11.glRotatef(player.rotationPitch, 1, 0, 0);
			GL11.glRotatef((par5) -90, 0, 1, 0);
			GL11.glRotatef(-player.rotationPitch, 0, 0, 1);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glTranslatef(0, 0.5F, 0);
	        aModel.render();
		}
		GL11.glPopMatrix();
	}
}