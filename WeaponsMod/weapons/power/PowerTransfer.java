package weapons.power;

import weapons.tileentity.TileEntityPowerBase;

public class PowerTransfer {

	private TileEntityPowerBase sink;
	private TileEntityPowerBase source;
	public PowerTransfer(TileEntityPowerBase sink, TileEntityPowerBase source){
		this.sink = sink;
		this.source = source;
	}
	public boolean transfer(int power){
		return transfer(power, 0);
	}
	public boolean transferAllPowerToSink(){
		if((source.getPower() + sink.getPower()) >= sink.getMaxPower()){
			transfer(sink.getMaxPower() - sink.getPower());
			return true;
		}
		else if(source.getPower() > 0 && source.getPower() + sink.getPower() < sink.getMaxPower()){
			return this.transfer(source.getPower());
		}
		return false;
	}
	public boolean transfer(int power, double decay){
		if(power > 0 && decay >= 0 && power <= source.getPower() && decay <1 && power < (sink.getMaxPower() - sink.getPower())){
			sink.addPower((int)(power * (1-decay)));
			source.subtractPower(power);
			return true;
		}
		return false;
	}
	public TileEntityPowerBase getPowerSink(){
		return sink;
	}
	public TileEntityPowerBase getPowerProvider(){
		return source;
	}
	
}
