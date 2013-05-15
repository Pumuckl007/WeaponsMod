package weapons.client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(value = Side.CLIENT)
public class KeyHandeler extends KeyHandler
{  
	protected static KeyBinding jetpackWarp = new KeyBinding("JetPack Warp Key", Keyboard.KEY_CAPITAL);
	protected static KeyBinding jpthrotle = new KeyBinding("JetPack Throtle Key", Keyboard.KEY_C);
	protected static KeyBinding jetpackWarpCancel = new KeyBinding("JetPack Warp Cancel Key", Keyboard.KEY_X);
	protected static KeyBinding jpEnable = new KeyBinding("Enable JetPack Key", Keyboard.KEY_F);

	Minecraft mc;

	public KeyHandeler()
	{
		super(new KeyBinding[]
				{
				jetpackWarp,
				jpthrotle,
				jetpackWarpCancel,
				jpEnable,
				},
				new boolean[]
						{
				true, // JetPack Warp Key
				true, // JetPack Throtle Key
				true, // JetPack Warp Cancel Key
				true  // Enable JetPack Key
						});

		mc = Minecraft.getMinecraft();
	}

	@Override
	public String getLabel()
	{
		return "Weapons Mod Control key Ticker";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,	boolean tickEnd, boolean isRepeat)
	{
		if(mc.currentScreen != null || tickEnd)
			return;

		int keyNum = -1;
		boolean handled = true;

		if(kb == jetpackWarp){
			keyNum = 0;
		}
		else if(kb == jpthrotle)
			keyNum = 1;
		else if(kb == jetpackWarpCancel)
			keyNum = 2;
		else if(kb == jpEnable)
			keyNum = 3;

		else
			handled = false;
		if (keyNum > -1 && keyNum < 7)
		{

			//        AinchentShipControlEvent event = new AinchentShipControlEvent(keyNum);
			//        MinecraftForge.EVENT_BUS.post(event);
			//		}
			//		
			//        ZaperStrikeLighningEvent event2 = new ZaperStrikeLighningEvent(keyNum);
			//        MinecraftForge.EVENT_BUS.post(event2);




			EntityPlayer player = mc.thePlayer;
			Entity entityTest  = player.ridingEntity;

			if (entityTest != null && handled == true)
			{
				if (kb.keyCode == mc.gameSettings.keyBindInventory.keyCode)
				{
					mc.gameSettings.keyBindInventory.pressed = false;
					mc.gameSettings.keyBindInventory.pressTime = 0;
				}

				//if (handled)
				//	PacketDispatcher.sendPacketToServer(PacketVehicleControl.buildVehicleControlButton(keyNum));
			}
			else
				handled = false;


			if (handled == true)
				return;

			for (KeyBinding key : mc.gameSettings.keyBindings )
			{
				if (kb.keyCode == key.keyCode && key != kb)
				{
					key.pressed = true;
					key.pressTime = 1;
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		if (tickEnd)
			return;

		for (KeyBinding key : mc.gameSettings.keyBindings)
		{
			if (kb.keyCode == key.keyCode && key != kb)
				key.pressed = false;
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}

}