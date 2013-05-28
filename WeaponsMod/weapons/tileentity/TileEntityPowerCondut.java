package weapons.tileentity;

public class TileEntityPowerCondut extends TileEntityPowerBase {

	TileEntityPowerBase connectedTile;
	public TileEntityPowerCondut()
	{
		super(false);
	}
	@Override
	public boolean canRecivePower(){
		return false;
	}
	@Override
	public void updateEntity()
	{
		
	}
	
	

}
