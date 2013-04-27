package weapons.vector;

public class ThrustDirectonFlying {

	float x = 0;
	float y = 0;
	float z = 0;
	float directX = 0;
	float directY = 0;
	float directForward = 0;
	public ThrustDirectonFlying(){
		
	}
	
	public void setXYZ(float cx, float cy, float cz){
		x = cx;
		y = cy;
		z = cz;
	}
	public void setDirectionAndThrust(float leftright, float updown, float thrust){
		directX = leftright;
		directY = updown;
		directForward = thrust;
	}
	public void update(){
		
	}
	
}
