package com.ocelot.init;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.ocelot.FactorioMod;
import com.ocelot.blocks.BlockTransportBelt;
import com.ocelot.items.ModItemBlock;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ModBlocks
{
	private static final List<Block> BLOCKS = Lists.<Block>newArrayList();

	public static final Block YELLOW_BELT;

	// public static final TileEntityType<TileEntityVendingMachine> TILE_ENTITY_VENDING_MACHINE;

	static
	{
		YELLOW_BELT = new BlockTransportBelt("yellow_belt", BlockTransportBelt.Type.YELLOW);

		// TILE_ENTITY_VENDING_MACHINE = registerTileEntity("vending_machine", TileEntityVendingMachine::new);
	}

	protected static void init()
	{
		registerBlock(YELLOW_BELT, new Item.Properties().group(FactorioMod.TAB));
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

	public static <T extends TileEntity> TileEntityType<T> registerTileEntity(String name, Supplier<T> factory)
	{
		return TileEntityType.register(FactorioMod.MOD_ID + ":" + name, TileEntityType.Builder.create(factory));
	}

	public static Block[] getBlocks()
	{
		return BLOCKS.toArray(new Block[0]);
	}
}