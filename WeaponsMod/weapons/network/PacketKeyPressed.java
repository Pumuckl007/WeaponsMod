package weapons.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public class PacketKeyPressed extends PacketBase {

    public String key;
    public String username;

    public PacketKeyPressed() {

        super(PacketTypeHandler.KEY, false);
    }

    public PacketKeyPressed(String key, String username) {

        super(PacketTypeHandler.KEY, false);
        this.key = key;
        this.username = username;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException {

        data.writeUTF(key);
        data.writeUTF(username);
    }

    @Override
    public void readData(DataInputStream data) throws IOException {

        key = data.readUTF();
        username = data.readUTF();
    }

    @Override
    public void execute(INetworkManager manager, Player player) {

        EntityPlayer thePlayer = (EntityPlayer) player;

        if (thePlayer.getCurrentEquippedItem() != null && thePlayer.getCurrentEquippedItem().getItem() instanceof IKeyBound) {
            ((IKeyBound) thePlayer.getCurrentEquippedItem().getItem()).doKeyBindingAction(thePlayer, thePlayer.getCurrentEquippedItem(), key);
        }
    }
}
