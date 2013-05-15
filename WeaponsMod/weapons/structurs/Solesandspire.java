package weapons.structurs;

import net.minecraft.world.World;

public class Solesandspire {

	public static void generate(World world, int x, int y, int z, int blockid, int thehight, int size){
		if(y < thehight){
			int i = 0;
			int hight = thehight - y;
			if(hight > 0){
				while(i < hight){
					int with = (int) ((float)size - (float)i / (float)((float)hight / 20F));
					int xoff = 0;
					while(xoff < with){
						int zoff = 0;
						while(zoff < with){
							if(i == 0 && xoff == 0 && zoff == 0){}
							else{
								world.setBlock(x + xoff, y + i, z + zoff, blockid);
								world.setBlock(x - xoff, y + i, z - zoff, blockid);
								world.setBlock(x + xoff, y + i, z - zoff, blockid);
								world.setBlock(x - xoff, y + i, z + zoff, blockid);
							}
							world.spawnParticle("largeexplode", x + xoff, y + i, z + zoff, -200, 0, 0);
							world.spawnParticle("largesmoke", x + xoff, y + i, z + zoff, 0, 0, 0);
							zoff ++;
						}
						xoff ++;
					}
					i ++;
				}
				world.setBlock(x, y + hight + 1, z, 10);
			}
		}
	}
}
