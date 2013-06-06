package weapons.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFlare extends Entity
{
	int life;
	int age;
	public EntityFlare(World world)
	{
		super(world);
	}
	public EntityFlare(World world, double x, double y, double z, int life){
		super(world);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.life = life;
	}
	public EntityFlare(World world, EntityPlayer player, int life){
		super(world);
		this.posX = player.posX;
		this.posY = player.posY;
		this.posZ = player.posZ;
		this.life = life;
	}
	@Override
	protected void entityInit()
	{
		
	}
	public int getLife(){
		return this.life;
	}
	@Override
	public void onEntityUpdate()
	{
//		super.onEntityUpdate();
		if(this.life > 0){
			this.motionY = 0.1;
			this.life --;
		}
		else{
			this.setDead();
		}
	}
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.life = nbt.getInteger("Life");
		this.age = nbt.getInteger("Age");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("Life", this.life);
		nbt.setInteger("Age", this.life);
		
	}
	
}
