package com.ocelot.event;

import org.apache.commons.lang3.tuple.MutablePair;

import com.ocelot.FactorioMod;
import com.ocelot.recipe.FactorioFuels;
import com.ocelot.util.EnergyUtils;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents
{
    private MutablePair<ItemStack, Float> joulesCache = new MutablePair<ItemStack, Float>(ItemStack.EMPTY, 0.0f);

    @SubscribeEvent
    public void itemTooltipEvent(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();

        if (!ItemStack.areItemsEqual(this.joulesCache.getLeft(), stack))
        {
            this.joulesCache.setLeft(stack);
            this.joulesCache.setRight(FactorioFuels.getJoules(stack));
        }

        if (this.joulesCache.getRight() > 0)
        {
            event.getToolTip().add(new TextComponentString(I18n.format("item." + FactorioMod.MOD_ID + ".tooltip.fuel_value", EnergyUtils.joulesToString(this.joulesCache.getRight()))).setStyle(new Style().setColor(TextFormatting.GRAY)));
        }
    }
}