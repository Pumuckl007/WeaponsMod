package weapons.client;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import weapons.armor.ItemInfoHelmet;
import weapons.client.gui.InfoHelmet;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class RenderTick implements ITickHandler{

	public static float playerRecoil = 0;
	public static double rasYaw = 0;
	public static double rasPitch = 0;
	public static double raHight = 0;
	private float antiRecoil = 0;
	protected static InfoHelmet info;
	public static Double[] hight;
	public static double r = 255;
	public static double g = 5;
	public static double b = 234;
	public static double alpha = 100;
	public static float radius = 0.02F;
	public static boolean radiusmax = false;
	public static boolean rmax = false;
	public static boolean gmax = false;
	public static boolean bmax = false;
	public static boolean amax = false;
	private int init = -1;
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if(init != 1){
			init = 1;
			hight = new Double[2];
			hight[0] = (double) 0;
			hight[1] = (double) 0;
		}
		updategunrecoil();
		updateSS();
		updateRobot();
	}

	private void updateRobot(){
		rasYaw += 0.014;
		rasPitch += 0.0178;
		raHight += 0.006782;
	}
	private void updateSS(){
		hight[0] += 0.0056543356;
		hight[1] += 0.0074522415;
		if(radius > 0.025){
			radiusmax = true;
		}
		if(radius < 0.015){
			radiusmax = false;
		}
		if(radiusmax){
			radius -= 0.0003;
		}
		else{
			radius += 0.0003;
		}
		
		if(r > 255){
			rmax = true;
		}
		if(r < 250){
			rmax = false;
		}
		if(rmax){
			r -= 0.001;
		}
		else{
			r += 0.001;
		}
		alpha = ((radius - 0.02) * 4000) + 100;
	}

	private void updategunrecoil()
	{
		if(ClientProxy.minecraft.thePlayer != null){
			if (playerRecoil > 0)
				playerRecoil *= 0.95F;
			ClientProxy.minecraft.thePlayer.rotationPitch -= playerRecoil;
			antiRecoil += playerRecoil;

			ClientProxy.minecraft.thePlayer.rotationPitch += antiRecoil * 0.2F;
			antiRecoil *= 0.8F;
		}


	}
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		if(player != null && isHelmetOn(player)){
			if (info == null) {
				info = new InfoHelmet();
			}
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution screen = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			double left = screen.getScaledWidth() - 20;
			double top = screen.getScaledHeight() / 2.0 - 16;
			info.draw(left, top, player);
		}
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isHelmetOn(EntityPlayer player) {
		List<ItemStack> helmet = new ArrayList(1);
		ItemStack equipped = itemsEquipped(player);
			if (equipped != null && equipped.getItem() instanceof ItemInfoHelmet) {
				helmet.add(equipped);
			}
		return helmet.size() > 0;
	}

	public static ItemStack itemsEquipped(EntityPlayer player) {
		ItemStack equipped = player.inventory.armorInventory[3];
		return equipped;
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.RENDER, TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
