package com.ocelot.event;

import java.text.DecimalFormat;

import org.apache.commons.lang3.tuple.MutablePair;

import com.ocelot.FactorioMod;
import com.ocelot.recipe.FactorioFuels;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents
{
	public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#,##0");

	private MutablePair<ItemStack, Float> lastJoules = new MutablePair<ItemStack, Float>(ItemStack.EMPTY, 0.0f);

	@SubscribeEvent
	public void itemTooltipEvent(ItemTooltipEvent event)
	{
		ItemStack stack = event.getItemStack();

		if (!ItemStack.areItemsEqual(this.lastJoules.getLeft(), stack))
		{
			this.lastJoules.setLeft(stack);
			this.lastJoules.setRight(FactorioFuels.getJoules(stack));
		}

		if (this.lastJoules.getRight() > 0)
		{
			event.getToolTip().add(new TextComponentString(I18n.format("item." + FactorioMod.MOD_ID + ".tooltip.joules", DECIMALFORMAT.format(this.lastJoules.getRight()))).setStyle(new Style().setColor(TextFormatting.GRAY)));
		}
	}
}