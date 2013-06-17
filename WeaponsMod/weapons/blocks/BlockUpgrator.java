package weapons.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import weapons.Weapons;
import weapons.client.rendering.RenderUtils;
import weapons.tileentity.TileEntityUpgator;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockUpgrator extends BlockContainer{
	private Random rand = new Random();
	@SideOnly(Side.CLIENT)
	private Icon icon;
	@SideOnly(Side.CLIENT)
	private Icon iconacid;
	public BlockUpgrator(int id)
	{
		super(id, Material.iron);
		this.setUnlocalizedName("weaponUpgator");
        this.setCreativeTab(Weapons.weaponsTab);
        this.setHardness(1F);
        GameRegistry.registerBlock(this, "Upgator");
        LanguageRegistry.addName(this,"Upgator");
	}
	
	@Override
	public Icon getIcon(int par1, int par2)
	{
		if(par2 == 10){
			return this.iconacid;
		}
		if(par1 == 0 || par1 == 1){
			return this.icon;
		}
		return this.blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityUpgator();
	}
	@Override
    public boolean renderAsNormalBlock() {

        return RenderUtils.shouldRender3d();
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
	public void registerIcons(IconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon("weapons:Upgrator");
		this.icon = iconRegister.registerIcon("weapons:Upgratortop");
		this.iconacid = iconRegister.registerIcon("weapons:Upgratoracid");
	}

	@Override
    public int getRenderType() {

    	if(RenderUtils.shouldRender3d()){
    		return -1;
    	}
        return super.getRenderType();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {

        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
    }

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

        if (player.isSneaking()){
            return false;
        }
        else {
            if (!world.isRemote) {
            	TileEntityUpgator tile = (TileEntityUpgator) world.getBlockTileEntity(x, y, z);

                if (tile != null) {
                    player.openGui(Weapons.instance, Weapons.guiUpgator, world, x, y, z);
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
