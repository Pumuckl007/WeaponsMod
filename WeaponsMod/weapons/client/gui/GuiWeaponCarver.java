package weapons.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import weapons.container.ContainerWeaponCarver;
import weapons.tileentity.TileEntityWeaponCarver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWeaponCarver extends GuiContainer {

    private TileEntityWeaponCarver tile;

    public GuiWeaponCarver(InventoryPlayer inventoryPlayer, TileEntityWeaponCarver tile) {

        super(new ContainerWeaponCarver(inventoryPlayer, tile));
        this.tile = tile;
        xSize = 176;
        ySize = 187;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {

        String containerName = tile.isInvNameLocalized() ? tile.getInvName() : StatCollector.translateToLocal(tile.getInvName());
        fontRenderer.drawString(containerName, xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 93, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/gui/furnace.png");
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        int i1;
        if (this.tile.isWorking())
        {
            i1 = this.tile.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(xStart + 56, yStart + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.tile.getCookProgressScaled(24);
        this.drawTexturedModalRect(xStart + 79, yStart + 34, 176, 14, i1 + 1, 16);
    }
}
