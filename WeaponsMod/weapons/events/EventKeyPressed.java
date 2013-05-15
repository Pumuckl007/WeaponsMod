package weapons.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.Event;

public class EventKeyPressed extends Event {

	public int presedkey;
	public EntityPlayer player;
	public ItemStack item;
	
	public EventKeyPressed(EntityPlayer theplayer, int key, ItemStack theitem) {
        super();
        this.presedkey = key;
        player = theplayer;
        item = theitem;
    }
}
