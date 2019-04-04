package com.ocelot.items;

import java.util.List;

import com.ocelot.FactorioMod;
import com.ocelot.recipe.FactorioFuel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ModItem extends Item
{
	public ModItem(String name, Item.Properties properties)
	{
		super(properties);
		this.setRegistryName(new ResourceLocation(FactorioMod.MOD_ID, name));
	}

	public ModItem(Item.Properties properties)
	{
		super(properties);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag)
	{
		if (this instanceof FactorioFuel)
		{
			tooltip.add(new TextComponentString("Fuel Value: " + ((FactorioFuel) this).getJoules() + "J"));
		}
	}
}