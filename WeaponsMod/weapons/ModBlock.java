package weapons;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModBlock extends Block {
	public ModBlock(int par1, Material par3){
		super(par1,par3);
	}
	
	public void func_94581_a(IconRegister iconRegister)
	{
		
//		this.field_94336_cN = iconRegister.func_94245_a( + id);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		// TODO Auto-generated method stub
		String id = this.getUnlocalizedName();
		this.blockIcon = par1IconRegister.registerIcon("weapons:" + id);
		System.out.println("block icon registered to: " + id);
	}

}
