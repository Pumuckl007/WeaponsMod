package weapons.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import weapons.Weapons;
import weapons.tileentity.TileEntitySicurityStorage;


public class BlockSicurityStorage extends BlockContainer {


	private Random rand = new Random();

	public BlockSicurityStorage(int id) {

		super(id, Material.wood);
		this.setUnlocalizedName("sicurityStorage");
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.9F, 0.9375F);
		this.setCreativeTab(Weapons.weaponsTab);
		this.setLightValue(1);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {

		return new TileEntitySicurityStorage();
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

		if (player.isSneaking()){
			TileEntitySicurityStorage tile = (TileEntitySicurityStorage) world.getBlockTileEntity(x, y, z);
			ItemStack stack = tile.getStackInSlot(32);
			if(stack != null){
				if(stack.itemID == Block.tnt.blockID){
					int size = stack.stackSize;
					world.createExplosion(player, x, y, z, 2*size, true);
				}
			}
			else if (!tile.ison){
				player.openGui(Weapons.instance, Weapons.guiSicurityStorage, world, x, y, z);
			}
			else{
				if(player.getHealth() > 1){
					player.attackEntityFrom(DamageSource.generic, 1);
				}
				else{
					player.addPotionEffect(new PotionEffect(15, 400));
					player.addPotionEffect(new PotionEffect(9, 400));
				}
			}
			return true;
		}
		else {
			if (!world.isRemote) {
				TileEntitySicurityStorage tile = (TileEntitySicurityStorage) world.getBlockTileEntity(x, y, z);
				if(tile != null){
					if(tile.getOwner().equals("")){
						tile.setOwner(player.username);
						player.openGui(Weapons.instance, Weapons.guiSicurityStorage, world, x, y, z);
					}
					else if (tile != null && tile.getOwner().equals(player.username)) {
						player.openGui(Weapons.instance, Weapons.guiSicurityStorage, world, x, y, z);
					}
					else if (!tile.ison){
						player.openGui(Weapons.instance, Weapons.guiSicurityStorage, world, x, y, z);
					}
					else{
						ItemStack stack = tile.getStackInSlot(32);
						if(stack != null){
							if(stack.itemID == Block.tnt.blockID){
								int size = stack.stackSize;
								tile.setInventorySlotContents(32, null);
								world.createExplosion(player, x, y, z, 2*size, true);
							}
						}
						else{
							if(player.getHealth() > 1){
								player.attackEntityFrom(DamageSource.generic, 1);
							}
							else{
								player.addPotionEffect(new PotionEffect(15, 400));
								player.addPotionEffect(new PotionEffect(9, 400));
							}
						}
					}
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
