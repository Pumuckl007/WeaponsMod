package weapons.structurs;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.world.World;

public class EntityGenerator {

	public static void generate(World world, int x, int y, int z){
		int i = 0;
		if(100 > 0){
			while(i < 100){
				int xoff = 0;
				while(xoff < 100){
					int zoff = 0;
					while(zoff < 100){
						int rand = 0 + (int)(Math.random() * ((40000 - 0) + 1));
						if(rand == 100 || rand == 200 || rand == 300 || rand == 400){
							EntityGhast entity = new EntityGhast(world);
							if(rand == 100){
								entity.posX = x + xoff;
								entity.posY = y + i;
								entity.posZ = z + zoff;
							}
							if(rand == 200){
								entity.posX = x - xoff;
								entity.posY = y + i;
								entity.posZ = z + zoff;
							}
							if(rand == 300){
								entity.posX = x + xoff;
								entity.posY = y + i;
								entity.posZ = z - zoff;
							}
							if(rand == 400){
								entity.posX = x - xoff;
								entity.posY = y + i;
								entity.posZ = z - zoff;
							}
							entity.heal(200);
							world.spawnEntityInWorld(entity);
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
