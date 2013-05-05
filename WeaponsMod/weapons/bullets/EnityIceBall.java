package weapons.bullets;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EnityIceBall extends EntityThrowable
{
	private EntityLiving shootingentity;
	public EnityIceBall(World par1World)
	{
		super(par1World);
	}

	public EnityIceBall(World par1World, EntityLiving par2EntityLiving)
	{
		super(par1World, par2EntityLiving);
		shootingentity = par2EntityLiving;
	}

	public EnityIceBall(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if (this.isInWater())
        {
            if(this.worldObj.isRemote){
            	this.worldObj.setBlockMetadataWithNotify((int)this.posX, (int)this.posY, (int)this.posZ, 79, 0);
            }
			for (int j20 = 0; j20 < 300; ++j20)
			{

				int var4 = 79;
				if (var4 > 0)
				{
		            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("tilecrack_" + var4 + "_" + 0, this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, this.boundingBox.minY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D);
				}							
			}
			
        }
	}
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit != this.shootingentity)
		{
			byte b0 = 4;

			if (par1MovingObjectPosition.entityHit instanceof EntityBlaze)
			{
				b0 = 30;
			}

			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0);
		}

		int var4 = this.worldObj.getBlockId((int)this.posX, (int)this.posY, (int)this.posZ);
		for (int j20 = 0; j20 < 300; ++j20)
		{

			if (var4 > 0)
			{
				this.worldObj.spawnParticle("tilecrack_" + var4 + "_" + this.worldObj.getBlockMetadata((int)this.posX, (int)this.posY, (int)this.posZ), this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, this.boundingBox.minY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D);
			}							
		}
		this.worldObj.playSound(this.posX, this.posY, this.posZ, "random.glass", 4F, 40, false);


		if (!this.worldObj.isRemote)
		{
			if(var4 == 20 || var4 == 18){
				this.worldObj.setBlockMetadataWithNotify((int)this.posX, (int)this.posY, (int)this.posZ, 0, 0);
			}
			
			this.setDead();
		}
	}
}
