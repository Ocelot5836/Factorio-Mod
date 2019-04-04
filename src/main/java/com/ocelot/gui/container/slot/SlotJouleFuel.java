package com.ocelot.gui.container.slot;

import com.ocelot.recipe.FactorioFuels;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotJouleFuel extends SlotItemHandler
{
    public SlotJouleFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return FactorioFuels.getJoules(stack) > 0;
    }
}