package weapons.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import weapons.power.PowerTransfer;


public class TileEntityPowerBase extends TileBase {


	protected int power = 0;
	protected int MAXPOWER = 10000;
	private boolean sucksPower;

	public TileEntityPowerBase(boolean sucksPower) {
		this.sucksPower = sucksPower;
	}
	public boolean hasPower(){
		return power > 0;
	}
	public boolean canRecivePower(){
		return power < MAXPOWER;
	}

	public int getMaxPower(){
		return MAXPOWER;
	}
	public boolean changePowerTransferState(){
		sucksPower = !sucksPower;
		return !sucksPower;
	}

	public boolean getPowerTransferState(){
		return sucksPower;
	}

	public boolean addPower(int power){
		if((this.power + power) <=MAXPOWER){
			this.power += power;
			this.worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, 6, power);
			return true;
		}
		return false;
	}

	public boolean subtractPower(int power){
		if((this.power - power) >= 0){
			this.power -= power;
			this.worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, 6, power);
			return true;
		}
		return false;
	}

	public int getPower(){
		return power;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {

		super.readFromNBT(nbtTagCompound);
		if (nbtTagCompound.hasKey("Power")) {
			power = (nbtTagCompound.getInteger("Power"));
		}
		if (nbtTagCompound.hasKey("PowerState")) {
			sucksPower = nbtTagCompound.getBoolean("PowerState");
		}
	}
	@Override
	public boolean receiveClientEvent(int eventID, int secondvalue) {
		switch(eventID){
			case(6):{
				this.power = secondvalue;
				break;
			}
			default:{
				return super.receiveClientEvent(eventID, secondvalue);
			}
		}
		return true;
	}
	@Override
	public void updateEntity()
	{
		if(this.sucksPower){
			boolean loop = true;
			TileEntity tile = null;
			int loopcount = 0;
			while(loop){
				switch(loopcount){
					case(0):{
						if(this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) != null){
							if(this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) instanceof TileEntityPowerBase){
								TileEntityPowerBase intile = (TileEntityPowerBase) this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
								if(intile.getPower() > 0 && !intile.getPowerTransferState()){
									tile = this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
									loop = false;
								}
							}
						}
						break;
					}
					case(1):{
						if(this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) != null){
							if(this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) instanceof TileEntityPowerBase){
								TileEntityPowerBase intile = (TileEntityPowerBase) this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
								if(intile.getPower() > 0 && !intile.getPowerTransferState()){
									tile = this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
									loop = false;
								}
							}
						}
						break;
					}
					case(2):{
						if(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) != null){
							if(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) instanceof TileEntityPowerBase){
								TileEntityPowerBase intile = (TileEntityPowerBase) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
								if(intile.getPower() > 0 && !intile.getPowerTransferState()){
									tile = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
									loop = false;
								}
							}
						}
						break;
					}
					case(3):{
						if(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) != null){
							if(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) instanceof TileEntityPowerBase){
								TileEntityPowerBase intile = (TileEntityPowerBase) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
								if(intile.getPower() > 0 && !intile.getPowerTransferState()){
									tile = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
									loop = false;
								}
							}
						}
						break;
					}
					default:{
						loop = false;
						break;
					}
				}
				loopcount++;
			}
			if(tile instanceof TileEntityPowerBase){
				PowerTransfer transfer = new PowerTransfer(this, (TileEntityPowerBase) tile);
				transfer.transferAllPowerToSink();
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {

		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setInteger("Power", this.getPower());
		nbtTagCompound.setBoolean("PowerState", sucksPower);

	}
}
