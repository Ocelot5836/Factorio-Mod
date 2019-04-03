package com.ocelot.init;

import java.util.List;

import com.google.common.collect.Lists;
import com.ocelot.FactorioMod;
import com.ocelot.items.ModItem;

import net.minecraft.item.Item;

public class ModItems
{
	private static final List<Item> ITEMS = Lists.<Item>newArrayList();

	public static final Item IRON_ORE;
	public static final Item IRON_PLATE;

	static
	{
		IRON_ORE = new ModItem("iron_ore", new Item.Properties().group(FactorioMod.TAB));
		IRON_PLATE = new ModItem("iron_plate", new Item.Properties().group(FactorioMod.TAB));
	}

	protected static void init()
	{
		registerItem(IRON_ORE);
		registerItem(IRON_PLATE);
	}

	public static void registerItem(Item item)
	{
		if (item.getRegistryName() == null)
			throw new RuntimeException("Item \'" + item.getClass() + "\' is missing a registry name!");
		ITEMS.add(item);
	}

	public static Item[] getItems()
	{
		return ITEMS.toArray(new Item[0]);
	}
}