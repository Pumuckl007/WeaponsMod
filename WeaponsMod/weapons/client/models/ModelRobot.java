package weapons.client.models;

import weapons.utils.EnumRobot;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelRobot extends ModelBase {

    private IModelCustom modelBaseBase;
    private IModelCustom modelBaseBlock;
    private IModelCustom modelBaseEye;
    private IModelCustom modelBaseThrust;
    private IModelCustom modelBaseTurnbottom;

    public ModelRobot() {

    	modelBaseBase = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/base.obj");
    	modelBaseBlock = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/block.obj");
    	modelBaseEye = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/eye.obj");
    	modelBaseThrust = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/thrust.obj");
    	modelBaseTurnbottom = AdvancedModelLoader.loadModel("/mods/weapons/models/robot/base/turnbottom.obj");
    }

    public void render(EnumRobot part) {
    	switch(part){
    		case R_BBase:{
    			modelBaseBase.renderAll();
    			break;
    		}
    		case R_BBlock:{
    			modelBaseBlock.renderAll();
    			break;
    		}
    		case R_BEye:{
    			modelBaseEye.renderAll();
    			break;
    		}
    		case R_BThrust:{
    			modelBaseThrust.renderAll();
    			break;
    		}
    		case R_BTurnbottom:{
    			modelBaseTurnbottom.renderAll();
    			break;
    		}
    		default:{
    			break;
    		}
    	}
    }



   
}
