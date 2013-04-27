package weapons.bullets;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBullet extends Entity implements IProjectile
{
	public int field_92012_e = 5;
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	private boolean inGround = false;
	public EntityLiving shootingEntity;
	private int ticksAlive;
	private int ticksInAir = 0;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	public int entityId;
	public double renderDistanceWeight;
	private int fire;
	public float directoinX = 0;
	public float directoinY = 0;
	public EntityBullet(World par1World)
	{
		super(par1World);
	}

	public boolean isInRangeToRenderDist(double par1)
	{
		double var3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		var3 *= 64.0D;
		return par1 < var3 * var3;
	}

	public EntityBullet(World par1World, EntityLiving par2EntityLiving, float par3, float x, float y)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.accelerationX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.accelerationZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.accelerationY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.5F, 1.0F);
		directoinX = x;
		directoinY = y;
	}

	@SideOnly(Side.CLIENT)
	public EntityBullet(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World);
		this.setSize(1.0F, 1.0F);
		this.setLocationAndAngles(par2, par4, par6, this.rotationYaw, this.rotationPitch);
		this.setPosition(par2, par4, par6);
		double var14 = (double)MathHelper.sqrt_double(par8 * par8 + par10 * par10 + par12 * par12);
		this.accelerationX = par8 / var14 * 0.1D;
		this.accelerationY = par10 / var14 * 0.1D;
		this.accelerationZ = par12 / var14 * 0.1D;
	}

	public EntityBullet(World par1World, EntityLiving par2EntityLiving, double par3, double par5, double par7)
	{
		super(par1World);
		this.shootingEntity = par2EntityLiving;
		this.setSize(1.0F, 1.0F);
		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY, par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = this.motionY = this.motionZ = 0.0D;
		par3 += this.rand.nextGaussian() * 0.4D;
		par5 += this.rand.nextGaussian() * 0.4D;
		par7 += this.rand.nextGaussian() * 0.4D;
		double var9 = (double)MathHelper.sqrt_double(par3 * par3 + par5 * par5 + par7 * par7);
		this.accelerationX = par3 / var9 * 0.1D;
		this.accelerationY = par5 / var9 * 0.1D;
		this.accelerationZ = par7 / var9 * 0.1D;

	}

	@Override
	public void onUpdate()
	{
		if (!this.worldObj.isRemote && (this.shootingEntity != null && this.shootingEntity.isDead || !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ)))
		{
			this.setDead();
		}
		else
		{
			this.worldObj.theProfiler.startSection("entityBaseTick");

			if (this.ridingEntity != null && this.ridingEntity.isDead)
			{
				this.ridingEntity = null;
			}

			this.prevDistanceWalkedModified = this.distanceWalkedModified;
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			int var2;

			if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer)
			{
				this.worldObj.theProfiler.startSection("portal");
				MinecraftServer var1 = ((WorldServer)this.worldObj).getMinecraftServer();
				var2 = this.getMaxInPortalTime();


				if (this.inPortal)
				{
					if (var1.getAllowNether())
					{
						//                        if (this.ridingEntity == null && this.field_82153_h++ >= var2)
						//                        {
						//                            this.field_82153_h = var2;
						//                            this.timeUntilPortal = this.getPortalCooldown();
						//                            byte var3;
						//
						//                            if (this.worldObj.provider.dimensionId == -1)
						//                            {
						//                                var3 = 0;
						//                            }
						//                            else
						//                            {
						//                                var3 = -1;
						//                            }
						//
						//                            this.travelToDimension(var3);
						//                        }
						//
						//                        this.inPortal = false;
						//                    }
					}
					else
					{
						//                    if (this.field_82153_h > 0)
						//                    {
						//                        this.field_82153_h -= 4;
						//                    }
						//
						//                    if (this.field_82153_h < 0)
						//                    {
						//                        this.field_82153_h = 0;
						//                    }
					}

					if (this.timeUntilPortal > 0)
					{
						--this.timeUntilPortal;
					}

					this.worldObj.theProfiler.endSection();
				}

				if (this.isSprinting() && !this.isInWater())
				{
					int var5 = MathHelper.floor_double(this.posX);
					var2 = MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.yOffset);
					int var6 = MathHelper.floor_double(this.posZ);
					int var4 = this.worldObj.getBlockId(var5, var2, var6);

					if (var4 > 0)
					{
						this.worldObj.spawnParticle("tilecrack_" + var4 + "_" + this.worldObj.getBlockMetadata(var5, var2, var6), this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, this.boundingBox.minY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D);
					}
				}

				this.handleWaterMovement();

				if (this.worldObj.isRemote)
				{
					this.fire = 0;
				}
				else if (this.fire > 0)
				{
					if (this.isImmuneToFire)
					{
						this.fire -= 4;

						if (this.fire < 0)
						{
							this.fire = 0;
						}
					}
					else
					{
						if (this.fire % 20 == 0)
						{
							this.attackEntityFrom(DamageSource.onFire, 1);
						}

						--this.fire;
					}
				}

				if (this.handleLavaMovement())
				{
					this.setOnFireFromLava();
					this.fallDistance *= 0.5F;
				}

				if (this.posY < -64.0D)
				{
					this.kill();
				}

				if (!this.worldObj.isRemote)
				{
					this.setFlag(0, this.fire > 0);
					this.setFlag(2, this.ridingEntity != null && ridingEntity.shouldRiderSit());
				}

				this.worldObj.theProfiler.endSection();
			}

			if (this.inGround)
			{
				int var1 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

				if (var1 == this.inTile)
				{
					++this.ticksAlive;

					if (this.ticksAlive == 600)
					{
						this.setDead();
					}

					return;
				}

				this.inGround = false;
				this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
				this.ticksAlive = 0;
				this.ticksInAir = 0;
			}
			else
			{
				++this.ticksInAir;
			}

			Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
			vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (movingobjectposition != null)
			{
				vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			@SuppressWarnings("rawtypes")
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;

			for (int j = 0; j < list.size(); ++j)
			{
				Entity entity1 = (Entity)list.get(j);

				if (entity1.canBeCollidedWith() && (!entity1.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25))
				{
					float f = 0.3F;
					AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f, (double)f, (double)f);
					MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

					if (movingobjectposition1 != null)
					{
						double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null)
			{
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null)
			{
				this.onImpact(movingobjectposition);
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) + 90.0F;

			for (this.rotationPitch = (float)(Math.atan2((double)var16, this.motionY) * 180.0D / Math.PI) - 90.0F; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			{
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float var17 = this.getMotionFactor();

			if (this.isInWater())
			{
				for (int var19 = 0; var19 < 4; ++var19)
				{
					float var18 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var18, this.posY - this.motionY * (double)var18, this.posZ - this.motionZ * (double)var18, this.motionX, this.motionY, this.motionZ);
				}

				var17 = 0.8F;
			}

			this.motionX += this.accelerationX;
			this.motionY += this.accelerationY;
			this.motionZ += this.accelerationZ;
			this.motionX *= (double)var17;
			this.motionY *= (double)var17;
			this.motionZ *= (double)var17;

			this.setPosition(this.posX, this.posY, this.posZ);
		}
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if (!this.worldObj.isRemote)
		{
			if (par1MovingObjectPosition.entityHit != null)
			{
				this.worldObj.playSoundAtEntity(par1MovingObjectPosition.entityHit, "random.explode", 1.0F, 1);	
				par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.func_92087_a(this), 10);
			}


			this.setDead();
		}
	}



	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short)this.xTile);
		par1NBTTagCompound.setShort("yTile", (short)this.yTile);
		par1NBTTagCompound.setShort("zTile", (short)this.zTile);
		par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
		par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
		par1NBTTagCompound.setTag("direction", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		this.xTile = par1NBTTagCompound.getShort("xTile");
		this.yTile = par1NBTTagCompound.getShort("yTile");
		this.zTile = par1NBTTagCompound.getShort("zTile");
		this.inTile = par1NBTTagCompound.getByte("inTile") & 255;
		this.inGround = par1NBTTagCompound.getByte("inGround") == 1;

		if (par1NBTTagCompound.hasKey("direction"))
		{
			NBTTagList var2 = par1NBTTagCompound.getTagList("direction");
			this.motionX = ((NBTTagDouble)var2.tagAt(0)).data;
			this.motionY = ((NBTTagDouble)var2.tagAt(1)).data;
			this.motionZ = ((NBTTagDouble)var2.tagAt(2)).data;
		}
		else
		{
			this.setDead();
		}
	}
	/**
	 * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	 */
	protected float getMotionFactor()
	{
		return 0.95F;
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */


	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	public boolean canBeCollidedWith()
	{
		return true;
	}

	public float getCollisionBorderSize()
	{
		return 1.0F;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			this.setBeenAttacked();

			if (par1DamageSource.getEntity() != null)
			{
				Vec3 var3 = par1DamageSource.getEntity().getLookVec();

				if (var3 != null)
				{
					this.motionX = var3.xCoord;
					this.motionY = var3.yCoord;
					this.motionZ = var3.zCoord;
					this.accelerationX = this.motionX * 0.1D;
					this.accelerationY = this.motionY * 0.1D;
					this.accelerationZ = this.motionZ * 0.1D;
				}

				if (par1DamageSource.getEntity() instanceof EntityLiving)
				{
					this.shootingEntity = (EntityLiving)par1DamageSource.getEntity();
				}

				return true;
			}
			else
			{
				return false;
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float par1)
	{
		return 1.0F;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void setThrowableHeading(double var1, double var3, double var5,
			float var7, float var8) {

	}



}
