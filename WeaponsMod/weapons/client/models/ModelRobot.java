package weapons.client.models;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import weapons.client.rendering.RenderUtils;
import weapons.utils.EnumRobot;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelRobot extends ModelBase {

    private IModelCustom modelBaseBase;
    private IModelCustom modelBaseBlock;
    private IModelCustom modelBaseEye;
    private IModelCustom modelBaseThrust;
    private IModelCustom modelBaseTurnbottom;
    private IModelCustom modelLeftArm;
    private IModelCustom modelLeftArmZap;
    private IModelCustom modelRightArm;
    private IModelCustom modelRightArmZap;

    public ModelRobot() {

    	modelBaseBase = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/base.obj");
    	modelBaseBlock = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/block.obj");
    	modelBaseEye = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/eye.obj");
    	modelBaseThrust = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/thrust.obj");
    	modelBaseTurnbottom = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/turnbottom.obj");
    	modelLeftArm = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/leftarm/arm.obj");
    	modelLeftArmZap = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/leftarm/armzap.obj");
    	modelRightArm = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/rightarm/arm.obj");
    	modelRightArmZap = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/rightarm/armzap.obj");
    }

    public void render(EnumRobot part, float yaw, float pitch) {
    	GL11.glPushMatrix();
    	GL11.glDisable(GL11.GL_BLEND);
    	switch(part){
    		case R_BBase:{
    			EnumRobot.R_BaseColor.color();
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
    			modelBaseBase.renderAll();
    			break;
    		}
    		case R_BBlock:{
    			GL11.glEnable(GL11.GL_BLEND);
    			GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    			RenderUtils.color(60, 60, 60, 200);
    			GL11.glRotatef(180, 0, 1, 0);
    			GL11.glRotatef(90, 1, 0, 0);
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
    			modelBaseBlock.renderAll();
    			GL11.glDisable(GL11.GL_BLEND);
    			break;
    		}
    		case R_BEye:{
    			RenderUtils.glowOn();
    			EnumRobot.R_BEye.color();
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
    			modelBaseEye.renderAll();
    			RenderUtils.glowOff();
    			break;
    		}
    		case R_BThrust:{
    			RenderUtils.glowOn();
    			EnumRobot.R_BThrust.color();
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
    			modelBaseThrust.renderAll();
    			RenderUtils.glowOff();
    			break;
    		}
    		case R_BTurnbottom:{
    			RenderUtils.color(255, 255, 255, 255);
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/robot/base/turnbottom.png");
    			modelBaseTurnbottom.renderAll();
    			break;
    		}
    		case R_LArm:{
    			EnumRobot.R_BaseColor.color();
    			GL11.glTranslated(0, 0, 0.89);
    			GL11.glRotatef(yaw, 1, 0, 0);
    			GL11.glRotatef(pitch, 0, 0, 1);
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
    			modelLeftArm.renderAll();
    			break;
    		}
    		case R_LZap:{
    			RenderUtils.glowOn();
    			EnumRobot.R_LZap.color();
    			GL11.glTranslated(0, 0, 0.89);
    			GL11.glRotatef(yaw, 1, 0, 0);
    			GL11.glRotatef(pitch, 0, 0, 1);
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
    			modelLeftArmZap.renderAll();
    			RenderUtils.glowOff();
    			break;
    		}
    		case R_RArm:{
    			EnumRobot.R_BaseColor.color();
    			GL11.glTranslated(0, 0, -0.89);
    			GL11.glRotatef(yaw, 1, 0, 0);
    			GL11.glRotatef(pitch, 0, 0, 1);
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
    			modelRightArm.renderAll();
    			break;
    		}
    		case R_RZap:{
    			RenderUtils.glowOn();
    			EnumRobot.R_RZap.color();
    			GL11.glTranslated(0, 0, -0.89);
    			GL11.glRotatef(yaw, 1, 0, 0);
    			GL11.glRotatef(pitch, 0, 0, 1);
    			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/weapons/textures/models/dynamiclyColor.png");
    			modelRightArmZap.renderAll();
    			RenderUtils.glowOff();
    			break;
    		}
    		default:{
    			RenderUtils.renderSphere(0, 0, 0, 255, 255, 0, 255, 1);
    			break;
    		}
    	}
    	GL11.glPopMatrix();
    }
    public void render(EnumRobot part) {
    	this.render(part, 0, 0);
    }



   
}
