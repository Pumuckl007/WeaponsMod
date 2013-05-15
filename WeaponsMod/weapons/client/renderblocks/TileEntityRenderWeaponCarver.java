package weapons.client.renderblocks;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import weapons.client.models.blocks.ModelWeaponCarver;
import weapons.tileentity.TileEntityWeaponCarver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderWeaponCarver extends TileEntitySpecialRenderer {

	private ModelWeaponCarver model = new ModelWeaponCarver();
//	private RenderItemDummy itemRender = new RenderItemDummy();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {

		model.render((TileEntityWeaponCarver) tileEntity, x, y, z);
		//Put on hold
//		TileEntityWeaponCarver tile = (TileEntityWeaponCarver) tileEntity;
//		ItemStack itemstack = null;
//		if(tile.inventory[0] != null){
//			int itemid = tile.inventory[0].itemID;
//			Item itemitem = new Item(itemid);
//			itemstack = new ItemStack(itemitem);
//		}
//		if(itemstack != null){
//			Icon icon1 = itemstack.getIconIndex();
//			EntityItem item = new EntityItem(tile.worldObj, x, y, z, itemstack);
////			itemRender.
//			itemRender.render(item, icon1, 1, (float) x, (float)y + 1.2F, (float)z, 1);
//		}
	}

}
