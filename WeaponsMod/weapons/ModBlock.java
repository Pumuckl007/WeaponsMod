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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		String id = this.getUnlocalizedName();
		id = id.replace("tile.", "");
		this.blockIcon = iconRegister.registerIcon("weapons:" + id);
		System.out.println("block icon registered to: " + id);
	}

}
