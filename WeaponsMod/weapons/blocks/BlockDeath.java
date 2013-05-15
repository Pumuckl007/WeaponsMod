package weapons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import weapons.Weapons;
import weapons.tileentity.TileEntityDeath;


public class BlockDeath extends BlockContainer {

	TileEntityDeath tile;
	
    public BlockDeath(int id) {

        super(id, Material.rock);
        this.setUnlocalizedName("weaponDeath");
        this.setCreativeTab(Weapons.weaponsTab);
        this.setHardness(5F);
        this.setBlockBounds(0.3F, 0, 0.3F, 0.7F, 1.5F, 0.7F);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {

        return new TileEntityDeath();
    }

    @Override
    public boolean renderAsNormalBlock() {

        return false;
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public int getRenderType() {

        return -1;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {

        super.breakBlock(world, x, y, z, id, meta);
    }
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i1 = par1World.getBlockMetadata(par2, par3, par4);

        if ((i1 & 8) == 0)
        {
                boolean flag1 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);

                if ((flag1 || par5 > 0 && Block.blocksList[par5].canProvidePower()) && par5 != this.blockID)
                {
                    this.onPowered(par1World, par2, par3, par4, flag1);
                }
            }
    }

	private void onPowered(World par1World, int par2, int par3, int par4, boolean flag1)
	{
		TileEntityDeath tile = (TileEntityDeath)par1World.getBlockTileEntity(par2, par3, par4);
		tile.isTicking = true;
	}


}
