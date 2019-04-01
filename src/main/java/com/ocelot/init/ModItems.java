package com.ocelot.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Item;

public class ModItems
{
	private static final List<Item> ITEMS = Lists.<Item>newArrayList();

	static
	{
	}

	protected static void init()
	{
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