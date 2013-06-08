package weapons.gunitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import weapons.speacalitems.ItemChargable;
import weapons.utils.WorldUtils;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemPhaser extends ItemChargable{

	public ItemPhaser(int id)
	{
		super(id, 10000, false, false, EnumAction.bow);
		this.setUnlocalizedName("weaponsPhaser");
		this.maxStackSize = 1;
		LanguageRegistry.addName(this,"Phaser");
	}

	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		item.stackTagCompound.setBoolean("Fireing", false);
	}

	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
	{

		if(!item.hasTagCompound()){
			this.onCreated(item, world, player);
		}
		item.stackTagCompound.setBoolean("Fireing", true);
		player.setItemInUse(item, 7200);
		MovingObjectPosition mop = player.rayTrace(150, 1F);
		if(mop == null || (mop.typeOfHit == EnumMovingObjectType.ENTITY && mop.entityHit == null))
		{
			player.sendChatToPlayer("Can't fire phaser at nothing");
		}
		else if(mop.typeOfHit == EnumMovingObjectType.ENTITY)
		{
			for(int i = 0; i < 10; i ++){
				WorldUtils.spawnPaticleWithOffset(player.worldObj, "largeexplode",  mop.entityHit.posX + 0.5,  mop.entityHit.posY + 0.5, mop.entityHit.posZ + 0.5, 1, 0, 0, 1, 1, 1);	
			}
		}
		else
		{
			ItemStack tempStack = new ItemStack(world.getBlockId(mop.blockX, mop.blockY, mop.blockZ), 1, world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));
			if(tempStack.getItem() != null)
			{
				for(int i = 0; i < 10; i ++){
					WorldUtils.spawnPaticleWithOffset(player.worldObj, "largeexplode", mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 1, 0, 0, 1, 1, 1);	
				}
				world.createExplosion(null, mop.blockX, mop.blockY, mop.blockZ, 0, true);
				if(world.getBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ) != null){
					world.removeBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ);
				}
				world.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
			}
			else
			{
				for(int i = 0; i < 10; i ++){
					WorldUtils.spawnPaticleWithOffset(player.worldObj, "largeexplode", mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 1, 0, 0, 1, 1, 1);	
				}
			}
		}
		return super.onItemRightClick(item, world, player);



	}

	@Override
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count)
	{
		World world = player.worldObj;
		if(!world.isRemote){
			if(stack.stackTagCompound.getBoolean("Fireing")){
				MovingObjectPosition mop = player.rayTrace(150, 1F);
				if(mop == null || (mop.typeOfHit == EnumMovingObjectType.ENTITY && mop.entityHit == null))
				{
					player.sendChatToPlayer("Can't fire phaser at nothing");
				}
				else if(mop.typeOfHit == EnumMovingObjectType.ENTITY)
				{
					for(int i = 0; i < 10; i ++){
						WorldUtils.spawnPaticleWithOffset(player.worldObj, "largeexplode",  mop.entityHit.posX + 0.5,  mop.entityHit.posY + 0.5, mop.entityHit.posZ + 0.5, 1, 0, 0, 1, 1, 1);	
					}
				}
				else
				{
					ItemStack tempStack = new ItemStack(world.getBlockId(mop.blockX, mop.blockY, mop.blockZ), 1, world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ));
					if(tempStack.getItem() != null)
					{
						for(int i = 0; i < 10; i ++){
							WorldUtils.spawnPaticleWithOffset(player.worldObj, "largeexplode", mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 1, 0, 0, 1, 1, 1);	
						}
						world.createExplosion(null, mop.blockX, mop.blockY, mop.blockZ, 0, true);
						if(world.getBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ) != null){
							world.removeBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ);
						}
						world.setBlockToAir(mop.blockX, mop.blockY, mop.blockZ);
					}
					else
					{
						for(int i = 0; i < 10; i ++){
							WorldUtils.spawnPaticleWithOffset(player.worldObj, "largeexplode", mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 1, 0, 0, 1, 1, 1);	
						}
					}
				}
			}
		}
		stack.stackTagCompound.setBoolean("Fireing", false);
	}

}
