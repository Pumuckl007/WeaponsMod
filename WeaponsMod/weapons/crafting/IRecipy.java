package weapons.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import weapons.tileentity.TileEntityWeaponCarver;

public interface IRecipy
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(TileEntityWeaponCarver inventorycrafting, World world);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(TileEntityWeaponCarver inventorycrafting);

    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();

    ItemStack getRecipeOutput();
}
