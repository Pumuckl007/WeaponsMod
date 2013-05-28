package weapons.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import weapons.Weapons;


public class TileEntityProjetor extends TileBase {


	public int size = 1;
	public int timealive = 0;
	public int instancetimealive = 0;
	public boolean active;
	private int sendcooldown;

	public TileEntityProjetor() {

	}

	/**
	 * Called when a client event is received with the event number and
	 * argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int eventID, int secondvalue) {
		switch(eventID){
			case(1):{
				if(secondvalue == 1){
					this.active = true;
				}
				else{
					this.active = false;
				}
				return true;
			}

			default:{
				return super.receiveClientEvent(eventID, secondvalue);
			}
		}

	}
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {

		super.readFromNBT(nbtTagCompound);
		if (nbtTagCompound.hasKey("Size")) {
			size = nbtTagCompound.getInteger("Size");
		}
		if (nbtTagCompound.hasKey("TimeAlive")) {
			timealive = nbtTagCompound.getInteger("TimeAlive");
		}
		if (nbtTagCompound.hasKey("Active")) {
			active = nbtTagCompound.getBoolean("Active");
		}

	}


	public float getsize(){
		return (float)this.size;
	}
	@Override
	public void updateEntity()
	{
		if (++sendcooldown % 20 == 0) {
			if(!this.worldObj.isRemote){
				if(active){
					worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 1, 1);
				}
				else{
					worldObj.addBlockEvent(xCoord, yCoord, zCoord, Weapons.sicurityStorage.blockID, 1, 0);
				}
				
			}
		}
		timealive ++;
		instancetimealive ++;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {

		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setInteger("Size", size);
		nbtTagCompound.setInteger("TimeAlive", timealive);
		nbtTagCompound.setBoolean("Active", active);
	}
}
