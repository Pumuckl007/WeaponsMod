package weapons.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import weapons.structurs.Death;
import weapons.structurs.EntityGenerator;
import weapons.structurs.Solesandspire;


public class TileEntityDeath extends TileBase {


	public short timetokill = 500;
	public short explosionlenght = 0;
	public boolean isTicking = false;

	public TileEntityDeath() {

	}


	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {

		super.readFromNBT(nbtTagCompound);
		if (nbtTagCompound.hasKey("killTime")) {
			timetokill = nbtTagCompound.getShort("killTime");
		}
		if (nbtTagCompound.hasKey("explosionLenght")) {
			explosionlenght = nbtTagCompound.getShort("explosionLenght");
		}
		if (nbtTagCompound.hasKey("isTicking")) {
			isTicking = nbtTagCompound.getBoolean("isTicking");
		}
	}


	public float gettickingtime(){
		return (float)this.timetokill;
	}
	@Override
	public void updateEntity()
	{
		if(this.isTicking && this.timetokill > 0){
			this.timetokill --;
		}
		else if(this.isTicking){
			Solesandspire.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 88, 240, 20);
			Solesandspire.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 89, 180, 11);
			Solesandspire.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 0, 160, 10);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 60, 87, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 2, 87, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 141, 32, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 142, 32, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 59, 32, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 8, 49, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 9, 49, 100, 100);
			Death.generate(this.worldObj, this.xCoord-1, this.yCoord, this.zCoord, 8, 49, 100, 100);
			Death.generate(this.worldObj, this.xCoord-1, this.yCoord, this.zCoord, 9, 49, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 18, 48, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 17, 4, 100, 100);
			Death.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 106, 0, 100, 100);
			EntityGenerator.generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, 0);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {

		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setShort("killTime", timetokill);
		nbtTagCompound.setShort("explosionLenght", explosionlenght);
		nbtTagCompound.setBoolean("isTicking", isTicking);

	}
}
