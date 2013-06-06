package weapons.utils;

import java.io.File;
import java.text.DecimalFormat;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.ISaveHandler;
import cpw.mods.fml.common.FMLCommonHandler;

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
	public static String doubletostring(double a, int places){
		String string = "";
		String round = "";
		for(int i = 0; i <= places; i ++){
			if(i == 0){
				round = "#.";
			}
			else{
				round = round + "#";
			}
		}
		DecimalFormat twoDForm = new DecimalFormat(round);
		string = twoDForm.format(a);
		return string;
	}
	public static int booleanToInt(boolean bool){
		if(bool){
			return 1;
		}
		else{
			return 0;
		}
	}
}
