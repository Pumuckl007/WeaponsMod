package weapons.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import weapons.Weapons;
import weapons.bullets.EntityBullet;
import weapons.bullets.EntityRocket;
import weapons.client.rendering.entity.RenderBullet;
import weapons.client.rendering.entity.RenderRocket;
import weapons.client.renderitems.ItemBulletRender;
import weapons.client.renderitems.ItemPistolRender;
import weapons.client.renderitems.ItemRocketRender;
import weapons.client.renderitems.ItemRocktlauncherRender;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends weapons.CommonOreProxy
{
	public static Minecraft minecraft = FMLClientHandler.instance().getClient();
	@SuppressWarnings({ })
	public void load()
	{

        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid, new ItemPistolRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 100, new ItemRocktlauncherRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 256, new ItemBulletRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 356, new ItemRocketRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet(0.1F));
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket(0.1F));
		
	}




	

	@Override
	public void loadSound()
	{
	}

	public boolean isThePlayer(EntityPlayer player)
	{
		return player == FMLClientHandler.instance().getClient().thePlayer;
	}



	public void sayClient(String message){
		System.out.println(message);
	}
	public void sayServer(String message){

	}
}