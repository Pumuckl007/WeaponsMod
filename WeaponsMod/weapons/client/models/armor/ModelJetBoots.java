package weapons.client.models.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelJetBoots extends ModelBiped {
	protected static ModelJetBoots instance;
	private ModelJetBoot aModel = new ModelJetBoot();

	public static ModelJetBoots getInstance() {
		if (instance == null) {
			instance = new ModelJetBoots();
		}
		return instance;
	}

	public WavefrontObject jetPack;

	public ModelJetBoots()
	{
		this(0.0F);
	}

	public ModelJetBoots(float par2)
	{
		this.jetPack = (WavefrontObject) AdvancedModelLoader.loadModel("/mods/weapons/models/jetpack.obj");
		this.bipedBody = new JetBootsRenderPart(this, jetPack);
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
			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/jetboots.png");
			GL11.glScalef(0.6F, 0.6F, 0.6F);
			GL11.glTranslatef(0, 2.5F, 0);
			GL11.glRotatef(-90, 0, 1, 0);
			GL11.glRotatef(180, 1, 0, 0);
			par3 = par3 * 52.325581395F;
	        GL11.glRotatef(MathHelper.cos(par2 * 0.6662F) * 1.4F * par3, 0,0,1);
	        aModel.renderright();
	        GL11.glRotatef(-(MathHelper.cos(par2 * 0.6662F) * 1.4F * par3), 0,0,1);
	        GL11.glRotatef(MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3, 0,0,1);
	        aModel.renderleft();
		}
		GL11.glPopMatrix();
	}
}