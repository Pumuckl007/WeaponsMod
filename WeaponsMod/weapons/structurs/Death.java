package weapons.structurs;

import net.minecraft.world.World;

public class Death {

	public static void generate(World world, int x, int y, int z,int blockidtorepace,int blockidtoplace, int with, int hight){
		int i = 0;
		if(hight > 0){
			while(i < hight){
				int xoff = 0;
				while(xoff < with){
					int zoff = 0;
					while(zoff < with){
						if(world.getBlockId(x + xoff, y + i, z + zoff) == blockidtorepace){
							world.setBlock(x + xoff, y + i, z + zoff, blockidtoplace);
						}
						if(world.getBlockId(x - xoff, y + i, z - zoff) == blockidtorepace){
							world.setBlock(x - xoff, y + i, z - zoff, blockidtoplace);
						}
						if(world.getBlockId(x - xoff, y + i, z + zoff) == blockidtorepace){
							world.setBlock(x - xoff, y + i, z + zoff, blockidtoplace);
						}
						if(world.getBlockId(x + xoff, y + i, z - zoff) == blockidtorepace){
							world.setBlock(x + xoff, y + i, z - zoff, blockidtoplace);
						}

						if(world.getBlockId(x + xoff, y - i, z + zoff) == blockidtorepace){
							world.setBlock(x + xoff, y - i, z + zoff, blockidtoplace);
						}
						if(world.getBlockId(x - xoff, y - i, z - zoff) == blockidtorepace){
							world.setBlock(x - xoff, y - i, z - zoff, blockidtoplace);
						}
						if(world.getBlockId(x - xoff, y - i, z + zoff) == blockidtorepace){
							world.setBlock(x - xoff, y - i, z + zoff, blockidtoplace);
						}
						if(world.getBlockId(x + xoff, y - i, z - zoff) == blockidtorepace){
							world.setBlock(x + xoff, y - i, z - zoff, blockidtoplace);
						}
						zoff ++;
					}
					xoff ++;
				}
				i ++;
			}
		}
	}

}
