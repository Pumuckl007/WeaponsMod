package weapons.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public enum PacketTypeHandler {
    KEY(PacketKeyPressed.class),
    REQUEST_EVENT(PacketRequestEvent.class),
    TILE(PacketTileUpdate.class);

    private Class<? extends PacketBase> clazz;

    PacketTypeHandler(Class<? extends PacketBase> clazz) {

        this.clazz = clazz;
    }

    public static PacketBase buildPacket(byte[] data) {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);

        PacketBase packet = null;

        try {
            packet = values()[selector].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dis);

        return packet;
    }

    public static PacketBase buildPacket(PacketTypeHandler type) {

    	PacketBase packet = null;

        try {
            packet = values()[type.ordinal()].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(PacketBase packet) {

        byte[] data = packet.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = "weapons";
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packet.isChunkDataPacket;

        return packet250;
    }
}
