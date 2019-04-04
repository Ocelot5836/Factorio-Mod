package com.ocelot.recipe;

import java.util.HashMap;
import java.util.Map;

import com.ocelot.FactorioMod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
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
	 * Initializes the joule amounts for each item.
	 */
	public static Map<Item, Float> getJouleAmounts()
	{
		Map<Item, Float> jouleAmounts = new HashMap<Item, Float>();

		setJoules(jouleAmounts, Blocks.COAL_BLOCK, 40000000);// 40,000,000J
		setJoules(jouleAmounts, Items.COAL, 4000000);// 4,000,000J
		setJoules(jouleAmounts, Items.CHARCOAL, 4000000);// 4,000,000J
		setJoules(jouleAmounts, ItemTags.LOGS, 750000);// 750,000J
		setJoules(jouleAmounts, ItemTags.PLANKS, 750000);// 750,000J
		setJoules(jouleAmounts, ItemTags.WOODEN_STAIRS, 750000);// 750,000J
		setJoules(jouleAmounts, ItemTags.WOODEN_SLABS, 150);// 375,000J
		setJoules(jouleAmounts, ItemTags.WOODEN_TRAPDOORS, 750000);// 750,000J
		setJoules(jouleAmounts, ItemTags.WOODEN_PRESSURE_PLATES, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.OAK_FENCE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.BIRCH_FENCE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.SPRUCE_FENCE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.JUNGLE_FENCE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.DARK_OAK_FENCE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.ACACIA_FENCE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.OAK_FENCE_GATE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.BIRCH_FENCE_GATE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.SPRUCE_FENCE_GATE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.JUNGLE_FENCE_GATE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.DARK_OAK_FENCE_GATE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.ACACIA_FENCE_GATE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.NOTE_BLOCK, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.BOOKSHELF, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.JUKEBOX, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.CHEST, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.TRAPPED_CHEST, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.CRAFTING_TABLE, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.DAYLIGHT_DETECTOR, 750000);// 750,000J
		setJoules(jouleAmounts, ItemTags.BANNERS, 750000);// 750,000J
		setJoules(jouleAmounts, Items.BOW, 750000);// 750,000J
		setJoules(jouleAmounts, Items.FISHING_ROD, 750000);// 750,000J
		setJoules(jouleAmounts, Blocks.LADDER, 750000);// 750,000J
		setJoules(jouleAmounts, Items.SIGN, 500000);// 500,000J
		setJoules(jouleAmounts, Items.WOODEN_SHOVEL, 500000);// 500,000J
		setJoules(jouleAmounts, Items.WOODEN_SWORD, 500000);// 500,000J
		setJoules(jouleAmounts, Items.WOODEN_HOE, 500000);// 500,000J
		setJoules(jouleAmounts, Items.WOODEN_AXE, 500000);// 500,000J
		setJoules(jouleAmounts, Items.WOODEN_PICKAXE, 500000);// 500,000J
		setJoules(jouleAmounts, ItemTags.WOODEN_DOORS, 500000);// 500,000J
		setJoules(jouleAmounts, ItemTags.BOATS, 500000);// 500,000J
		setJoules(jouleAmounts, ItemTags.WOOL, 250000);// 250,000J
		setJoules(jouleAmounts, ItemTags.WOODEN_BUTTONS, 250000);// 250,000J
		setJoules(jouleAmounts, Items.STICK, 250000);// 250,000J
		setJoules(jouleAmounts, ItemTags.SAPLINGS, 250000);// 250,000J
		setJoules(jouleAmounts, Items.BOWL, 250000);// 250,000J
		setJoules(jouleAmounts, ItemTags.CARPETS, 167500);// 167,500J
		setJoules(jouleAmounts, Blocks.DRIED_KELP_BLOCK, 10002500);// 10,002,500J

		for (Item item : ForgeRegistries.ITEMS)
		{
			if (item instanceof FactorioFuel)
			{
				setJoules(jouleAmounts, item, ((FactorioFuel) item).getJoules());
			}
		}

		return jouleAmounts;
	}

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