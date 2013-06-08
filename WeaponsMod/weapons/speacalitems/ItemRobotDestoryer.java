package weapons.speacalitems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import weapons.ModItem;
import weapons.Weapons;
import weapons.entity.EntityRobot;
import weapons.utils.WorldUtils;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemRobotDestoryer extends ModItem{

	public ItemRobotDestoryer(int itemId)
	{
		super(itemId);
		this.setUnlocalizedName("weaponsRobotDestroyer");
		this.setCreativeTab(Weapons.weaponsTab);
		this.maxStackSize = 1;
		LanguageRegistry.addName(this,"Robot Destroyer");
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity entity)
	{
		if(entity instanceof EntityRobot){
			EntityRobot robot = (EntityRobot) entity;
			boolean destroy = false;
			if(robot.getOwner() == null){
				destroy = true;
			}
			else{
				if(robot.getOwner().equals(player.username)){
					destroy = true;
				}
			}
			if(destroy){
				robot.dropItem(robot.getDropItemId(), 1);
				robot.setDead();
				for(int i = 0; i < 250; i ++){
					WorldUtils.spawnPaticleWithOffset(robot.worldObj, "happyVillager", robot.posX, robot.posY + 1, robot.posZ, 0, 1, 0, 0.5, 2, 0.5);	
				}
//				for(int i = 0; i < 500; i ++){
//					WorldUtils.spawnPaticleWithOffset(robot.worldObj, "happyVillager", robot.posX, robot.posY + 6, robot.posZ, 0, 1, 0, 0.1, 10, 0.1);	
//				}
//				for(int i = 0; i < 100; i ++){
//					WorldUtils.spawnPaticleWithOffset(robot.worldObj, "largeexplode", robot.posX, robot.posY + 6, robot.posZ, 0, 0, 0, 0.1, 10, 0.1);	
//				}
				for(int i = 0; i < 10; i ++){
					WorldUtils.spawnPaticleWithOffset(robot.worldObj, "largeexplode", robot.posX, robot.posY + 1, robot.posZ, 1, 0, 0, 0.5, 2, 0.5);	
				}
				return true;
			}
		}
	return false;
}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world,
			EntityPlayer player)
	{
		Entity entity = null;
		player.swingItem();
		MovingObjectPosition mop = player.rayTrace(100, 1F);
		if(mop != null){
			if(mop.typeOfHit == EnumMovingObjectType.ENTITY){
				if(mop.entityHit != null){
					entity = mop.entityHit;
				}
			}
		}
		if(entity instanceof EntityRobot){
			EntityRobot robot = (EntityRobot) entity;
			boolean destroy = false;
			if(robot.getOwner() == null){
				destroy = true;
			}
			else{
				if(robot.getOwner().equals(player.username)){
					destroy = true;
				}
			}
			if(destroy){
				robot.dropItem(robot.getDropItemId(), 1);
				robot.setDead();
				for(int i = 0; i < 250; i ++){
					WorldUtils.spawnPaticleWithOffset(robot.worldObj, "happyVillager", robot.posX, robot.posY + 1, robot.posZ, 0, 1, 0, 0.5, 2, 0.5);	
				}
				for(int i = 0; i < 10; i ++){
					WorldUtils.spawnPaticleWithOffset(robot.worldObj, "largeexplode", robot.posX, robot.posY + 1, robot.posZ, 1, 0, 0, 0.5, 2, 0.5);	
				}
			}
		}
		return item;
	}



}
