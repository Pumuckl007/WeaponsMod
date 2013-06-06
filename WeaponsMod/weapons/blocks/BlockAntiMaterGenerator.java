package weapons.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import weapons.Weapons;
import weapons.tileentity.TileEntityAntiMaterGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockAntiMaterGenerator extends BlockContainer{
	private Random rand = new Random();
	private int blockid;
	private int blockhight;
	public BlockAntiMaterGenerator(int id, int blockid)
	{
		super(id, Material.iron);
		this.setUnlocalizedName("weaponsAntiMaterGenerator");
//        this.setCreativeTab(Weapons.weaponsTab);
        this.setHardness(20F);
        this.blockid = (blockid%9) + 1;
        this.blockhight = -(((blockid - (blockid%9))/9) - 1);
        GameRegistry.registerBlock(this, "BlockAntiMaterGenerator" + blockid);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityAntiMaterGenerator();
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

        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

        if (player.isSneaking())
            return false;
        else {
            if (!world.isRemote) {
            	TileEntityAntiMaterGenerator tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity(x, y, z);

                if (tile != null) {
                	switch(blockid){
                		case(1):{
                			tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity(x - 1, y + blockhight, z - 1);
                            break;
                		}
                		case(2):{
                            tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity( x - 1, y + blockhight, z);
                            break;
                		}
                		case(3):{
                            tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity( x - 1, y + blockhight, z + 1);
                            break;
                		}
                		case(4):{
                            tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity( x, y + blockhight, z - 1);
                            break;
                		}
                		case(5):{
                            tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity( x, y + blockhight, z);
                            break;
                		}
                		case(6):{
                            tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity( x, y + blockhight, z + 1);
                            break;
                		}
                		case(7):{
                            tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity( x + 1, y + blockhight, z - 1);
                            break;
                		}
                		case(8):{
                            tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity( x + 1, y + blockhight, z);
                            break;
                		}
                		case(9):{
                            tile = (TileEntityAntiMaterGenerator) world.getBlockTileEntity( x + 1, y + blockhight, z + 1);
                            break;
                		}
                	}
                	if(tile != null)
                	player.openGui(Weapons.instance, Weapons.guiAntiMaterGenerator, tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
                }
            }

            return true;
        }
    }


    private void dropInventory(World world, int x, int y, int z) {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }

}
