package com.ocelot.util;

import java.util.Map;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.ocelot.init.ModBlocks;
import com.ocelot.init.ModItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;

/**
 * A type of ore that can be processed by Factorio machines.
 * 
 * @author Ocelot5836
 */
public enum EnumOreType implements IStringSerializable
{
	IRON("iron", 1, () -> ModItems.IRON_ORE, () -> ModBlocks.IRON_ORE_OUTCROP.getDefaultState()), COPPER("copper", 1, () -> Blocks.GOLD_ORE, () -> Blocks.GOLD_ORE.getDefaultState()), COAL("coal", 1, () -> Items.COAL, () -> Blocks.COAL_BLOCK.getDefaultState()), STONE("stone", 1, () -> Blocks.STONE, () -> Blocks.STONE.getDefaultState()), URANIUM("uranium", 2, () -> Items.SLIME_BALL, () -> Blocks.SLIME_BLOCK.getDefaultState());

	private static final Map<String, EnumOreType> NAME_LOOKUP = Maps.<String, EnumOreType>newHashMap();

	private String name;
	private float miningTime;
	private Supplier<IItemProvider> item;
	private Supplier<IBlockState> state;

	private EnumOreType(String name, float miningTime, Supplier<IItemProvider> item, Supplier<IBlockState> state)
	{
		this.name = name;
		this.miningTime = miningTime;
		this.item = item;
		this.state = state;
	}

	/**
	 * @return The name of this ore
	 */
	@Override
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
	public IItemProvider getItemDropped()
	{
		return item.get();
	}

	/**
	 * @return The state this ore uses when on the ground
	 */
	public IBlockState getState()
	{
		return state.get();
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