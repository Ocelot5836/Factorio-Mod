package com.ocelot.gui;

import com.ocelot.FactorioMod;
import com.ocelot.gui.container.ContainerBurnerMiningDrill;
import com.ocelot.init.ModBlocks;
import com.ocelot.tileentity.OreOutcrop;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;
import com.ocelot.util.EnergyUtils;
import com.ocelot.util.EnumOreType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

public class GuiBurnerMiningDrill extends GuiContainer {
    public static final ResourceLocation TEXTURE = new ResourceLocation(FactorioMod.MOD_ID, "textures/gui/burner_mining_drill.png");

    private TileEntityBurnerMiningDrill te;
    @SuppressWarnings("unused")
    private IItemHandler inventory;
    private ItemStack outcropCache;

    private double lastEnergy;
    private double lastMiningProgress;

    public GuiBurnerMiningDrill(BlockPos pos, TileEntityBurnerMiningDrill te) {
        super(new ContainerBurnerMiningDrill(Minecraft.getInstance().player, Minecraft.getInstance().world, pos, te));
        this.te = te;
        this.inventory = te.getInventory();
        this.outcropCache = ItemStack.EMPTY;
        this.xSize = 176;
        this.ySize = 150;
        this.lastEnergy = (double) this.te.getJoules() / (double) this.te.getMaxJoules();
        this.lastMiningProgress = (double) this.te.getMiningProgress() / (double) this.te.getMaxMiningProgress();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        if (!this.outcropCache.isEmpty() && mouseX - this.guiLeft > 138 && mouseX - this.guiLeft <= 138 + 18 && mouseY - this.guiTop > 36 && mouseY - this.guiTop <= 36 + 18) {
            this.renderToolTip(this.outcropCache, mouseX, mouseY);
        }

        if (!this.outcropCache.isEmpty() && mouseX - this.guiLeft > 38 && mouseX - this.guiLeft <= 38 + 92 && mouseY - this.guiTop > 21 && mouseY - this.guiTop <= 21 + 4) {
            this.drawHoveringText(EnergyUtils.joulesToString(this.te.getJoules()), mouseX, mouseY);
        }

        if (!this.outcropCache.isEmpty() && mouseX - this.guiLeft > 18 && mouseX - this.guiLeft <= 18 + 112 && mouseY - this.guiTop > 43 && mouseY - this.guiTop <= 43 + 4) {
            this.drawHoveringText(Integer.toString((int) ((this.lastMiningProgress + ((double) this.te.getMiningProgress() / (double) this.te.getMaxMiningProgress() - this.lastMiningProgress) * partialTicks) * 100.0)) + "%", mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        float partialTicks = this.mc.getRenderPartialTicks();

        this.fontRenderer.drawString(I18n.format(ModBlocks.BURNER_MINING_DRILL.getTranslationKey()), 8.0F, 6.0F, 4210752);
        this.fontRenderer.drawString(this.mc.player.inventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);

        this.mc.getTextureManager().bindTexture(TEXTURE);

        double energy = (double) this.te.getJoules() / (double) this.te.getMaxJoules();
        double miningProgress = (double) this.te.getMiningProgress() / (double) this.te.getMaxMiningProgress();

        drawScaledCustomSizeModalRect(40, 23, 176, 0, 1, 2, (int) ((this.lastEnergy + (energy - this.lastEnergy) * partialTicks) * 90.0), 2, 256, 256);
        drawScaledCustomSizeModalRect(20, 45, 176, 0, 1, 2, (int) ((this.lastMiningProgress + (miningProgress - this.lastMiningProgress) * partialTicks) * 110.0), 2, 256, 256);

        this.lastEnergy = energy;
        this.lastMiningProgress = miningProgress;

        OreOutcrop outcrop = this.te.getMiningOre();
        EnumOreType ore = outcrop != null ? outcrop.getOre() : null;
        if (ore != null) {
            if (this.outcropCache.getItem() != ore.getItemDropped().asItem()) {
                this.outcropCache = new ItemStack(ore.getItemDropped());
            }

            RenderHelper.enableGUIStandardItemLighting();
            this.mc.getItemRenderer().renderItemAndEffectIntoGUI(this.outcropCache, 140, 38);
            this.mc.getItemRenderer().renderItemOverlays(this.mc.fontRenderer, this.outcropCache, 140, 38);
            RenderHelper.disableStandardItemLighting();
        }
    }
}