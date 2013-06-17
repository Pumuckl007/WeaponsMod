package weapons.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import weapons.ModBlock;
import weapons.Weapons;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRandom extends ModBlock{
	@SideOnly(Side.CLIENT)
	private Icon top;
	@SideOnly(Side.CLIENT)
	private Icon side;
	public BlockRandom(int id)
	{
		super(id, Material.iron);
		this.setUnlocalizedName("Random");
		this.setCreativeTab(Weapons.weaponsTab);
		this.setHardness(1F);
		GameRegistry.registerBlock(this, "Random");
		LanguageRegistry.addName(this,"Random");
		this.setTickRandomly(true);
	}
	@Override
	public boolean renderAsNormalBlock() {

		return true;
	}
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
	{
		if(par1 == 0 || par1 == 1){
			return top;
		}

		return side;
	}
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		world.spawnParticle("largeexplode", x,  y,  z, -4, 0, 0);
		for(int i = 0; i < 6000; i++){
			float offX = 0 + (float)(Math.random() * ((6 - 0) + 1));
			float offY = 0 + (float)(Math.random() * ((8 - 0) + 1));
			float offZ = 0 + (float)(Math.random() * ((6 - 0) + 1)); 
			world.spawnParticle("tilecrack_" + world.getBlockId(x, y-1, z) + "_" + world.getBlockMetadata(x, y-1, z), x - 3 + offX,  y - 4 + offY,  z - 3 + offZ, 0, 1, 0);

		}
		super.breakBlock(world, x, y, z, id, meta);
		world.createExplosion(null, x, y, z, 10, true);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z,
			Entity entity)
	{
		if(entity instanceof EntityLiving){
			int pot = 1 + (int)(Math.random() * ((20 - 1) + 1));
			int potdur = 1 + (int)(Math.random() * ((1000 - 1) + 1));
			int potAmp = 1 + (int)(Math.random() * ((4 - 1) + 1));
			EntityLiving entityLiving = (EntityLiving) entity;
			entityLiving.addPotionEffect(new PotionEffect(pot, potdur, potAmp));
		}
		for (int j20 = 0; j20 < 500; ++j20)
		{
			Random rand = new Random();
			world.spawnParticle("tilecrack_" + 89 + "_" + world.getBlockMetadata(x, y, z), x + (double)rand.nextFloat(), z + (double)rand.nextFloat(), z + (double)rand.nextFloat(),0, 1.5D,  0);					
		}
	}
	@Override
	public void onFallenUpon(World world, int x, int y, int z,
			Entity entity, float par6)
	{
		entity.addVelocity(0, 10, 0);
		for (int j20 = 0; j20 < 500; ++j20)
		{
			Random rand = new Random();
			world.spawnParticle("tilecrack_" + 10 + "_" + world.getBlockMetadata(x, y, z), x + (double)rand.nextFloat(), z + (double)rand.nextFloat(), z + (double)rand.nextFloat(),0, 1.5D,  0);					
		}
	}
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		for(int i = 0; i < 20; i++){
			world.spawnParticle("tilecrack_" + 119 + "_" + world.getBlockMetadata(x, y-1, z), x,  y,  z, 0, 1, 0);
		}
		if(random.nextInt(200) == 0){
			EntityLightningBolt entity = new EntityLightningBolt(world, x, y, z);
			world.addWeatherEffect(entity);
		}
		super.updateTick(world, x, y, z, random);
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

		if (player.isSneaking()){
			super.breakBlock(world, x, y, z, this.blockID, world.getBlockMetadata(x, y, z));
			return false;
		}
		else {
			player.setVelocity(0, 1, 0);
			for(int i = 0; i < 2000; i++){
				float offX = 0 + (float)(Math.random() * ((6 - 0) + 1));
				float offY = 0 + (float)(Math.random() * ((4 - 0) + 1));
				float offZ = 0 + (float)(Math.random() * ((6 - 0) + 1)); 
				world.spawnParticle("tilecrack_" + world.getBlockId(x, y, z) + "_" + world.getBlockMetadata(x, y, z), x - 3 + offX,  y - 2 + offY,  z - 3 + offZ, 0, 1, 0);

			}
			int pot = 1 + (int)(Math.random() * ((20 - 1) + 1));
			int potdur = 1 + (int)(Math.random() * ((1000 - 1) + 1));
			int potAmp = 1 + (int)(Math.random() * ((4 - 1) + 1));
			player.addPotionEffect(new PotionEffect(pot, potdur, potAmp));
			return true;
		}
	}
	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister iconRegister) {
		String id = this.getUnlocalizedName();
		id = id.replace("tile.", "");
		this.blockIcon = iconRegister.registerIcon("weapons:" + id);
		top = iconRegister.registerIcon("weapons:" + id + "top");
		side = iconRegister.registerIcon("weapons:" + id);
		System.out.println("block icon registered to: " + id);
	}

}
