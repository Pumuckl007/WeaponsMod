package weapons.power;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Fuel {
	public static Map<Item, Integer> useableItemFuel = new HashMap<Item, Integer>();
	public static Map<Block, Integer> useableBlockFuel = new HashMap<Block, Integer>();
	public static ArrayList<Item> fuelItems = new ArrayList<Item>();
	public static void init(){
		useableItemFuel.put(Item.redstone, 1);
		useableBlockFuel.put(Block.blockRedstone, 9);
		useableItemFuel.put(Item.dyePowder, 5);
		useableBlockFuel.put(Block.blockLapis, 45);
	}
	public static void addUseableFuel(Item item, int integer){
		useableItemFuel.put(item, integer);
	}
	public static void fuelItem(Item item){
		fuelItems.add(item);
	}
	public static boolean isItemFuelSource(Item item){
		if(useableItemFuel.containsKey(item)){
			return true;
		}
		else if(fuelItems.contains(item)){
			return true;
		}
		else{
			return false;
		}
	}
}
