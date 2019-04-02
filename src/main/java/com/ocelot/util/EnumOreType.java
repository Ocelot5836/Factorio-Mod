package com.ocelot.util;

import java.util.Map;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.IItemProvider;

/**
 * A type of ore that can be processed by Factorio machines.
 * 
 * @author Ocelot5836
 */
public enum EnumOreType
{
	IRON("iron", 1, () -> Blocks.IRON_ORE), COPPER("copper", 1, () -> Blocks.GOLD_ORE), COAL("coal", 1, () -> Items.COAL), STONE("stone", 1, () -> Blocks.STONE), URANIUM("uranium", 2, () -> Items.SLIME_BALL);

	private static final Map<String, EnumOreType> NAME_LOOKUP = Maps.<String, EnumOreType>newHashMap();

	private String name;
	private float miningTime;
	private Supplier<IItemProvider> item;

	private EnumOreType(String name, float miningTime, Supplier<IItemProvider> item)
	{
		this.name = name;
		this.miningTime = miningTime;
		this.item = item;
	}

	/**
	 * @return The name of this ore
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return The amount of time it takes to mine this ore
	 */
	public float getMiningTime()
	{
		return miningTime;
	}
	
	/**
	 * @return The item this ore drops
	 */
	public IItemProvider getItem()
	{
		return item.get();
	}

	/**
	 * Checks the ore types for an ore with a matching name.
	 * 
	 * @param name
	 *            The name to get an ore by
	 * @return The ore found by that name or {@link #IRON} if one could not be found
	 */
	public static EnumOreType byName(String name)
	{
		return NAME_LOOKUP.getOrDefault(name, IRON);
	}

	static
	{
		for (EnumOreType type : values())
		{
			NAME_LOOKUP.put(type.getName(), type);
		}
	}
}