package com.ocelot.gui;

import com.ocelot.FactorioMod;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

public class GuiBurnerMiningDrill extends GuiContainer
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(FactorioMod.MOD_ID, "textures/gui/burner_mining_drill.png");

    private TileEntityBurnerMiningDrill te;
    @SuppressWarnings("unused")
    private IItemHandler inventory;

    public GuiBurnerMiningDrill(BlockPos pos, TileEntityBurnerMiningDrill te)
    {
        super(new ContainerBurnerMiningDrill(Minecraft.getInstance().player, Minecraft.getInstance().world, pos, te));
        this.te = te;
        this.inventory = te.getInventory();
        this.xSize = 176;
        this.ySize = 150;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.te.getDisplayName().getFormattedText(), 8.0F, 6.0F, 4210752);
        this.fontRenderer.drawString(this.mc.player.inventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }
}