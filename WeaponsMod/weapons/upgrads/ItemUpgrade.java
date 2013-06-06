package weapons.upgrads;

import net.minecraft.item.Item;
import weapons.ModItem;
import weapons.Weapons;

public class ItemUpgrade extends ModItem{

	public Item itemToUpgrade;
	public String name;
	protected int num = 0;
	public ItemUpgrade(int itemId, Item item, String name)
	{
		super(itemId);
		this.itemToUpgrade = item;
		this.name = name;
		UpgradeUtils.itemsUpgrads.put(item, name);
		UpgradeUtils.items.put(this, item);
		this.setCreativeTab(Weapons.weaponsTab);
	}
	public ItemUpgrade(int itemId, Item item, String name, int num)
	{
		super(itemId);
		this.itemToUpgrade = item;
		this.name = name;
		UpgradeUtils.itemsUpgrads.put(item, name);
		UpgradeUtils.items.put(this, item);
		UpgradeUtils.speacals.add(this);
		this.num = num;
		this.setCreativeTab(Weapons.weaponsTab);
	}
	public int getSpeacalInt(){
		return num;
	}
}
