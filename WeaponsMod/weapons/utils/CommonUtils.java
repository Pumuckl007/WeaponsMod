package weapons.utils;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.ISaveHandler;

public class CommonUtils {

	@SuppressWarnings("unused")
	private static File minecraftDir;
	
	public static File getWorldBaseSaveLocation(World world)
    {
        File savedir = getWorldSaveLocation(world);
        if(savedir == null)
        {
            return null;
        }
        else if(savedir.getName().contains("DIM"))
        {
            return savedir.getParentFile();
        }
        else
        {
            return savedir;
        }
    }

	private static File getWorldSaveLocation(World world)
    {
        try
        {
            ISaveHandler worldsaver = world.getSaveHandler();
            IChunkLoader loader = worldsaver.getChunkLoader(world.provider);
            if(loader instanceof AnvilChunkLoader)
            {
                return ((AnvilChunkLoader)loader).chunkSaveLocation;
            }
            return null;
        }
        catch(IllegalAccessError e)
        {
            return ((WorldServer)world).getChunkSaveLocation();
        }
        catch(Exception e)
        {
            FMLCommonHandler.instance().raiseException(e, "Weapons Mod", true);
            return null;
        }
    }
}
