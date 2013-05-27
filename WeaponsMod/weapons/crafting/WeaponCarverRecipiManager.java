package weapons.crafting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import weapons.Weapons;


public class WeaponCarverRecipiManager {

//	private Map<Integer[], ItemStack> recipies = new HashMap<Integer[], ItemStack>();
	private Map<List<Integer>, ItemStack> recipies = new HashMap<List<Integer>, ItemStack>();
	ItemStack[] inventory;
	Integer[] needed;
	ItemStack output;
	public WeaponCarverRecipiManager(ItemStack[] inventory){
		Integer[] slots = new Integer[9];
		for(int i = 0; i < 9; i++){
			slots[i] = i;
		}
		WeaponCarverRecipiManagerInit(inventory, slots);
	}
	
	public WeaponCarverRecipiManager(ItemStack[] inventory, Integer[] slots){
		WeaponCarverRecipiManagerInit(inventory, slots);
	}
	
	public void WeaponCarverRecipiManagerInit(ItemStack[] inventory, Integer[] slots) {
		this.inventory = new ItemStack[9];
		needed = new Integer[9];
		this.inventory[0] = inventory[slots[0]];
		this.inventory[1] = inventory[slots[1]];
		this.inventory[2] = inventory[slots[2]];
		this.inventory[3] = inventory[slots[3]];
		this.inventory[4] = inventory[slots[4]];
		this.inventory[5] = inventory[slots[5]];
		this.inventory[6] = inventory[slots[6]];
		this.inventory[7] = inventory[slots[7]];
		this.inventory[8] = inventory[slots[8]];
		initRecipies();
	}
	
	private void initRecipies(){
		addRecipe(1,1,1,1,1,1,1,1,1, new ItemStack(Weapons.pisol1));
//		addRecipe(0,0,0,7,0,7,7,7,7, new ItemStack(Weapons.flameThrower));
	}
	
	public void addRecipe(int slot1, int slot2, int slot3, int slot4, int slot5, int slot6, int slot7, int slot8, int slot9, ItemStack item){
		needed[0] = slot1;
		needed[1] = slot2;
		needed[2] = slot3;
		needed[3] = slot4;
		needed[4] = slot5;
		needed[5] = slot6;
		needed[6] = slot7;
		needed[7] = slot8;
		needed[8] = slot9;
		List<Integer> slots = Arrays.asList(needed);
		recipies.put(slots, item);
	}
	
	public ItemStack output(){
		Integer[] ids = new Integer[9];
		for(int i = 0; i < 9; i++){
			if(inventory[i] != null){
			ids[i] = inventory[i].itemID;
			}
			else{
				ids[i] = 0;
			}
		}
		List<Integer> slots = Arrays.asList(ids);
		return recipies.get(slots);
	}
	
	public boolean doStacksCreateRecipy(){
		Integer[] ids = new Integer[9];
		for(int i = 0; i < 9; i++){
			if(inventory[i] != null){
				ids[i] = inventory[i].itemID;
			}
			else{
				ids[i] = 0;
			}
		}
		List<Integer> slots = Arrays.asList(ids);
		System.out.println(recipies);
		return recipies.containsKey(slots);
	}
	
	public void updateInventory(ItemStack[] inventory, Integer[] slots){
		this.inventory[0] = inventory[slots[0]];
		this.inventory[1] = inventory[slots[1]];
		this.inventory[2] = inventory[slots[2]];
		this.inventory[3] = inventory[slots[3]];
		this.inventory[4] = inventory[slots[4]];
		this.inventory[5] = inventory[slots[5]];
		this.inventory[6] = inventory[slots[6]];
		this.inventory[7] = inventory[slots[7]];
		this.inventory[8] = inventory[slots[8]];
	}
	public void updateInventorySimp(ItemStack[] inventory){
		for(int i = 0; i < 9; i++){
			this.inventory[i] = inventory[i];
		}
	}
}
