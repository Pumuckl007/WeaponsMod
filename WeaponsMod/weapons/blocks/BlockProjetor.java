package weapons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import weapons.Weapons;
import weapons.tileentity.TileEntityProjetor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockProjetor extends BlockContainer{

	public BlockProjetor(int id)
	{
		super(id, Material.rock);
        this.setUnlocalizedName("weaponProjetor");
        this.setCreativeTab(Weapons.weaponsTab);
        this.setHardness(5F);
        this.setBlockBounds(0F, 0, 0F, 1F, 1.5F, 1F);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityProjetor();
	}
	@Override
    public boolean renderAsNormalBlock() {

        return false;
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType()
    {
         return weapons.client.ClientProxy.projetorRenderId;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {

        super.breakBlock(world, x, y, z, id, meta);
    }
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
		TileEntityProjetor tile = (TileEntityProjetor)par1World.getBlockTileEntity(par2, par3, par4);
		tile.active = flag1;
	}
}
