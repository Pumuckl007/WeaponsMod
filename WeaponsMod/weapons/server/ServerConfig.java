package weapons.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import weapons.utils.CommonUtils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ServerConfig {

    public static File worldSaveFile;
    public static File worldSaveDir;
    public static NBTTagCompound worldCompound;
    private static MinecraftServer server;
	
	public static void load(World world)
    {
        if(MinecraftServer.getServer() == server)
            return;
        
        System.out.println("Loading WeaponsMod");
        server = MinecraftServer.getServer();
        
        loadSavedConfig(world);
    }

	private static void loadSavedConfig(World world)
	{
		try
        {
            worldSaveDir = CommonUtils.getWorldBaseSaveLocation(world);
            worldSaveFile = new File(worldSaveDir, "Weapons.dat");
            if(!worldSaveFile.getParentFile().exists())
                worldSaveFile.getParentFile().mkdirs();
            if(!worldSaveFile.exists())
                worldSaveFile.createNewFile();
            
            if(worldSaveFile.length() == 0)
                worldCompound = new NBTTagCompound();
            else
            {
                DataInputStream din = new DataInputStream(new FileInputStream(worldSaveFile));
                worldCompound = (NBTTagCompound) NBTBase.readNamedTag(din);
                din.close();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
		
	}
	public static void setPropertyDisabled(int dim, String name, boolean disable)
    {
        getDimCompound(dim).setBoolean("disabled"+name, disable);
        saveWorldCompound();
    }
	private static void saveWorldCompound()
    {
        try
        {
            DataOutputStream dout = new DataOutputStream(new FileOutputStream(worldSaveFile));
            NBTBase.writeNamedTag(worldCompound, dout);
            dout.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
	 private static NBTTagCompound getDimCompound(int dim)
	    {
	        if(!worldCompound.hasKey("dim"+dim))
	            worldCompound.setCompoundTag("dim"+dim, new NBTTagCompound());
	        return worldCompound.getCompoundTag("dim"+dim);
	    }
}
