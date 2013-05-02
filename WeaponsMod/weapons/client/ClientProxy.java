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
import weapons.client.renderitems.ItemFTRender;
import weapons.client.renderitems.ItemInfoRender;
import weapons.client.renderitems.ItemPistolRender;
import weapons.client.renderitems.ItemRocketRender;
import weapons.client.renderitems.ItemRocktlauncherRender;
import weapons.client.renderitems.ItemScarHRender;
import weapons.server.ServerTick;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends weapons.CommonOreProxy
{
	public static Minecraft minecraft = FMLClientHandler.instance().getClient();
	@SuppressWarnings({ })
	public void load()
	{
		TickRegistry.registerTickHandler(new RenderTick(), Side.CLIENT);
		TickRegistry.registerTickHandler(new ClientTick(), Side.CLIENT);
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid, new ItemPistolRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 50, new ItemScarHRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 100, new ItemRocktlauncherRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 125, new ItemFTRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 256, new ItemBulletRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 356, new ItemRocketRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 512, new ItemInfoRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet(0.1F));
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket(0.1F));
		
	}
	@Override
	public void serverInit(){
		TickRegistry.registerTickHandler(new ServerTick(), Side.SERVER);
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