package weapons.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;
import weapons.Weapons;
import weapons.bullets.EnityIceBall;
import weapons.bullets.EntityBullet;
import weapons.bullets.EntityRocket;
import weapons.client.renderblocks.BlockItemDeathRender;
import weapons.client.renderblocks.BlockItemWeaponCarverRender;
import weapons.client.renderblocks.TileEntityRenderDeath;
import weapons.client.renderblocks.TileEntityRenderWeaponCarver;
import weapons.client.rendering.entity.RenderBullet;
import weapons.client.rendering.entity.RenderIceBall;
import weapons.client.rendering.entity.RenderRocket;
import weapons.client.renderitems.ItemBulletRender;
import weapons.client.renderitems.ItemFTRender;
import weapons.client.renderitems.ItemIceBallLauncherRender;
import weapons.client.renderitems.ItemInfoHelmetRender;
import weapons.client.renderitems.ItemInfoRender;
import weapons.client.renderitems.ItemJetBootsRender;
import weapons.client.renderitems.ItemJetPackRender;
import weapons.client.renderitems.ItemPistolRender;
import weapons.client.renderitems.ItemRocketRender;
import weapons.client.renderitems.ItemRocktlauncherRender;
import weapons.client.renderitems.ItemScarHRender;
import weapons.network.PacketRequestEvent;
import weapons.network.PacketTypeHandler;
import weapons.server.ServerTick;
import weapons.tileentity.TileBase;
import weapons.tileentity.TileEntityDeath;
import weapons.tileentity.TileEntityWeaponCarver;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends weapons.CommonProxy
{
	public static Minecraft minecraft = FMLClientHandler.instance().getClient();
	private KeyHandeler keyHandler;
	@SuppressWarnings({ })
	public void load()
	{
		TickRegistry.registerTickHandler(new RenderTick(), Side.CLIENT);
		TickRegistry.registerTickHandler(new ClientTick(), Side.CLIENT);
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid, new ItemPistolRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 50, new ItemScarHRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 80, new ItemIceBallLauncherRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 100, new ItemRocktlauncherRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 125, new ItemFTRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 256, new ItemBulletRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 356, new ItemRocketRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 512, new ItemInfoRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 552, new ItemInfoHelmetRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 553, new ItemJetPackRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.bulletid + 555, new ItemJetBootsRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.startBlockID, new BlockItemWeaponCarverRender());
        MinecraftForgeClient.registerItemRenderer(Weapons.startBlockID + 1, new BlockItemDeathRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet(0.1F));
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket(0.1F));
        RenderingRegistry.registerEntityRenderingHandler(EnityIceBall.class, new RenderIceBall());
		
	}
	@Override
	public void serverInit(){
		TickRegistry.registerTickHandler(new ServerTick(), Side.SERVER);
	}
	

	@Override
    public void initTileEntities() {

        super.initTileEntities();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeaponCarver.class, new TileEntityRenderWeaponCarver());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDeath.class, new TileEntityRenderDeath());
    }

	@Override
    public void handleTileEntityPacket(int x, int y, int z, ForgeDirection orientation, short state, String owner, String customName) {

        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getBlockTileEntity(x, y, z);

        if (tileEntity != null) {
            if (tileEntity instanceof TileBase) {
                ((TileBase) tileEntity).setOrientation(orientation);
                ((TileBase) tileEntity).setState(state);
                ((TileBase) tileEntity).setOwner(owner);
                ((TileBase) tileEntity).setCustomName(customName);
            }
        }
    }
	

	@Override
	public void loadSound()
	{
	}

	public boolean isThePlayer(EntityPlayer player)
	{
		return player == FMLClientHandler.instance().getClient().thePlayer;
	}
	@Override
	public boolean isClient(){
		return true;
	}
	@Override
    public void sendRequestEventPacket(byte eventType, int originX, int originY, int originZ, byte sideHit, byte rangeX, byte rangeY, byte rangeZ, String data) {

        PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(new PacketRequestEvent(eventType, originX, originY, originZ, sideHit, rangeX, rangeY, rangeZ, data)));
    }
	@Override
	public void registerKeyBindingHandler()
	{
		keyHandler = new KeyHandeler();
		KeyBindingRegistry.registerKeyBinding(keyHandler);
		
	}

	public void sayClient(String message){
		System.out.println(message);
	}
	public void sayServer(String message){

	}
}