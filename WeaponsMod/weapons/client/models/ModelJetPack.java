package weapons.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

import org.lwjgl.opengl.GL11;

import weapons.armor.ItemJetPack;

import cpw.mods.fml.client.FMLClientHandler;

public class ModelJetPack extends ModelBiped {
	protected static ModelJetPack instance;
	private ModeljetPack aModel = new ModeljetPack();
	private ModelJetPackRoter aModelRoter = new ModelJetPackRoter();
	private ModelJetPackFire aModelFire = new ModelJetPackFire();

	public static ModelJetPack getInstance() {
		if (instance == null) {
			instance = new ModelJetPack();
		}
		return instance;
	}

	public WavefrontObject jetPack;
	private float rotation;

	public ModelJetPack()
	{
		this(0.0F);
	}

	public ModelJetPack(float par2)
	{
		this.jetPack = (WavefrontObject) AdvancedModelLoader.loadModel("/mods/weapons/models/jetpack.obj");
		this.bipedBody = new ArmorRenderPart(this, jetPack);
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
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		GL11.glPushMatrix();
		EntityPlayer player = null;
		if(par1Entity instanceof EntityPlayer){
			player = (EntityPlayer)par1Entity;

			if(ItemJetPack.isflying){
				float speed = (float) ItemJetPack.flyingspeed;
				rotation += speed;
			}
			float pitch = player.rotationPitch + 90;
			GL11.glRotatef(par5, 0, 1, 0);
			GL11.glRotatef(pitch, 1, 0, 0);
		}
		if(ItemJetPack.isflying){
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(par1Entity.getTexture());
			ModelDummyPlayer model = new ModelDummyPlayer();
			GL11.glTranslatef(0, -2.5F, 0);
			model.render(par1Entity, par2, par3, par4, par5, par6, par7 * (float) 2.5);
		}
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/JETPACK.png");
		if(ItemJetPack.isflying){
			GL11.glTranslatef(0, 2.5F, 0);
			GL11.glRotatef(-90, 0, 1, 0);
			GL11.glRotatef(180, 0, 0, 1);
		}
		else{
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glRotatef(-90, 0, 0, 1);
			GL11.glRotatef(180, 1, 0, 0);
		}
		aModel.render();
		GL11.glTranslatef(0, 0, 1);
		GL11.glRotatef(rotation, 0, 1, 0);
		GL11.glScalef(0.1F, 0.1F, 0.1F);
		aModelRoter.render();
		GL11.glScalef(10F, 10F, 10F);
		GL11.glRotatef(-rotation, 0, 1, 0);
		GL11.glTranslatef(0, 0, -2);
		GL11.glRotatef(rotation, 0, 1, 0);
		GL11.glScalef(0.1F, 0.1F, 0.1F);
		aModelRoter.render();
		GL11.glScalef(10F, 10F, 10F);
		GL11.glRotatef(-rotation, 0, 1, 0);
		GL11.glTranslatef(0, 0, 1);
		if(ItemJetPack.isflying){
			GL11.glScalef(1, (float) ItemJetPack.flyingspeed / 2, 1);
			if(ItemJetPack.flyingspeed < 10){
			GL11.glTranslatef(0, (float)ItemJetPack.flyingspeed/10F, 0);
			}
			else{
				GL11.glTranslatef(0, (float)ItemJetPack.flyingspeed/15F, 0);
			}
			aModelFire.render();
		}
		GL11.glPopMatrix();
	}
}