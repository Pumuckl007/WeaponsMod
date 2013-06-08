package weapons.utils;

import net.minecraft.world.World;

public class WorldUtils {
	public static void spawnPaticleWithOffset(World world, String par1Str, double par2, double par4, double par6, double par8, double par10, double par12, double rangeX, double rangeY, double rangeZ){
		float offX = 0 + (float)(Math.random() * ((rangeX - 0) + 1));
		float offY = 0 + (float)(Math.random() * ((rangeY - 0) + 1));
		float offZ = 0 + (float)(Math.random() * ((rangeZ - 0) + 1)); 
		world.spawnParticle(par1Str, par2 + offX - (rangeX/2), par4 + offY - (rangeY/2), par6 + offZ - (rangeZ/2), par8, par10, par12);
	}
}
