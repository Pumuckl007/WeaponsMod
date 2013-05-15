package weapons.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import weapons.network.PacketTileUpdate;
import weapons.network.PacketTypeHandler;

public class TileBase extends TileEntity {

    private ForgeDirection orientation;
    private short state;
    private String owner;
    private String customName;

    public TileBase() {

        orientation = ForgeDirection.SOUTH;
        state = 0;
        owner = "";
        customName = "";
    }

    public ForgeDirection getOrientation() {

        return orientation;
    }

    public void setOrientation(ForgeDirection orientation) {

        this.orientation = orientation;
    }

    public void setOrientation(int orientation) {

        this.orientation = ForgeDirection.getOrientation(orientation);
    }

    public short getState() {

        return state;
    }

    public void setState(short state) {

        this.state = state;
    }

    public String getOwner() {

        return owner;
    }

    public boolean hasOwner() {

        return owner != null && owner.length() > 0;
    }

    public void setOwner(String owner) {

        this.owner = owner;
    }

    public boolean hasCustomName() {

        return customName != null && customName.length() > 0;
    }

    public String getCustomName() {

        return customName;
    }

    public void setCustomName(String customName) {

        this.customName = customName;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {

        return owner.equals(player.username);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {

        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey("teDirection")) {
            orientation = ForgeDirection.getOrientation(nbtTagCompound.getByte("teDirection"));
        }

        if (nbtTagCompound.hasKey("teState")) {
            state = nbtTagCompound.getShort("teState");
        }

        if (nbtTagCompound.hasKey("teOwner")) {
            owner = nbtTagCompound.getString("teOwner");
        }

        if (nbtTagCompound.hasKey("CustomName")) {
            customName = nbtTagCompound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {

        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte("teDirection", (byte) orientation.ordinal());
        nbtTagCompound.setShort("teState", state);

        if (hasOwner()) {
            nbtTagCompound.setString("teOwner", owner);
        }

        if (this.hasCustomName()) {
            nbtTagCompound.setString("CustomName", customName);
        }
    }

    @Override
    public Packet getDescriptionPacket() {

        return PacketTypeHandler.populatePacket(new PacketTileUpdate(xCoord, yCoord, zCoord, orientation, state, owner, customName));
    }

}
