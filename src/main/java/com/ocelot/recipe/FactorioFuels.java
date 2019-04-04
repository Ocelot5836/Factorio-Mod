package com.ocelot.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ocelot.FactorioMod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Handles all fuels in {@link FactorioMod}.
 * 
 * @author Ocelot5836
 * @see FactorioFuel
 */
public class FactorioFuels
{
	/**
	 * @return A new map full of the joule amounts
	 */
	public static Map<Item, Float> getJouleAmounts()
	{
		Map<Item, Float> jouleAmounts = new HashMap<Item, Float>();

		Map<Item, Integer> burnTimes = TileEntityFurnace.getBurnTimes();
		for (Entry<Item, Integer> entry : burnTimes.entrySet())
		{
			setJoules(jouleAmounts, entry.getKey(), entry.getValue() * 2500);
		}

		for (Item item : ForgeRegistries.ITEMS)
		{
			if (item instanceof FactorioFuel)
			{
				setJoules(jouleAmounts, item, ((FactorioFuel) item).getJoules());
			}
		}

		return jouleAmounts;
	}

	@SuppressWarnings("unused")
	private static void setJoules(Map<Item, Float> jouleAmounts, Tag<? extends IItemProvider> tag, float joules)
	{
		for (IItemProvider provider : tag.getAllElements())
		{
			setJoules(jouleAmounts, provider, joules);
		}
	}

	private static void setJoules(Map<Item, Float> jouleAmounts, IItemProvider itemProvider, float joules)
	{
		jouleAmounts.put(itemProvider.asItem(), joules);
	}

	/**
	 * Checks the item in the stack for the amount of joules.
	 * 
	 * @param stack
	 *            the stack to get the item from
	 * @return The amount of joules from the item in the stack
	 */
	public static float getJoules(ItemStack stack)
	{
		return getJoules(stack.getItem());
	}

	/**
	 * Checks the item for the amount of joules.
	 * 
	 * @param provider
	 *            the provider to get the item from
	 * @return The amount of joules from the item
	 */
	public static float getJoules(IItemProvider provider)
	{
		return getJouleAmounts().getOrDefault(provider.asItem(), 0.0f);
	}
}