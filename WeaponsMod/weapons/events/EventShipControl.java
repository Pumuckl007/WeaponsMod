package weapons.events;

import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;

public class EventShipControl extends Event {

	public int presedkey;
	public String player;
	
	public EventShipControl(String theplayer, int key) {
        super();
        this.presedkey = key;
        player = theplayer;
    }
	public EventShipControl() {
        super();
        this.presedkey = 0;
        player = "";
    }
	@ForgeSubscribe
	public void start(EventShipControl event)
	{

	/*
	* Note this possibility to interrupt certain (not all) events
	*/
	if (event.isCancelable())
	{
	event.setCanceled(true);
	}

	}
}
