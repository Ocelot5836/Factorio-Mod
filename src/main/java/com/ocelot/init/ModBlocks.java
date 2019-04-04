package com.ocelot.init;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.ocelot.FactorioMod;
import com.ocelot.blocks.BlockBurnerMiningDrill;
import com.ocelot.blocks.BlockOreOutcrop;
import com.ocelot.blocks.BlockTransportBelt;
import com.ocelot.items.ItemBurnerMiningDrill;
import com.ocelot.items.ModItemBlock;
import com.ocelot.util.EnumOreType;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ModBlocks
{
	private static final List<Block> BLOCKS = Lists.<Block>newArrayList();

	public static final Block IRON_ORE_OUTCROP;

	public static final Block YELLOW_BELT;
	public static final Block BURNER_MINING_DRILL;

	static
	{
		IRON_ORE_OUTCROP = new BlockOreOutcrop(EnumOreType.IRON);

		YELLOW_BELT = new BlockTransportBelt("yellow_belt", BlockTransportBelt.Type.YELLOW);
		BURNER_MINING_DRILL = new BlockBurnerMiningDrill("burner_mining_drill");
	}

	protected static void init()
	{
		registerBlock(IRON_ORE_OUTCROP, new Item.Properties().group(FactorioMod.TAB));

		registerBlock(YELLOW_BELT, new Item.Properties().group(FactorioMod.TAB));
		registerBlock(BURNER_MINING_DRILL, new ItemBurnerMiningDrill());
	}

	public static void registerBlock(Block block)
	{
		if (block.getRegistryName() == null)
			throw new RuntimeException("Block \'" + block.getClass() + "\' is missing a registry name!");
		BLOCKS.add(block);
	}

	public static void registerBlock(Block block, @Nullable Item.Properties properties)
	{
		registerBlock(block, new ModItemBlock(block, properties != null ? properties : new Item.Properties()));
	}

	public static void registerBlock(Block block, Item item)
	{
		registerBlock(block);
		ModItems.registerItem(item.setRegistryName(block.getRegistryName()));
	}

	public static Block[] getBlocks()
	{
		return BLOCKS.toArray(new Block[0]);
	}
}