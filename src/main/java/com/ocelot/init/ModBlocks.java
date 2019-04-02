package com.ocelot.init;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.ocelot.FactorioMod;
import com.ocelot.blocks.BlockBurnerMiningDrill;
import com.ocelot.blocks.BlockOreOutcrop;
import com.ocelot.blocks.BlockTransportBelt;
import com.ocelot.items.ItemBurnerMiningDrill;
import com.ocelot.items.ModItemBlock;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;
import com.ocelot.tileentity.TileEntityOreOutcrop;
import com.ocelot.tileentity.belt.TileEntityTransportBelt;
import com.ocelot.util.EnumOreType;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ModBlocks
{
	private static final List<Block> BLOCKS = Lists.<Block>newArrayList();

	public static final Block IRON_ORE_OUTCROP;

	public static final Block YELLOW_BELT;
	public static final Block BURNER_MINING_DRILL;

	public static final TileEntityType<TileEntityTransportBelt> TILE_ENTITY_TRANSPORT_BELT;
	public static final TileEntityType<TileEntityOreOutcrop> TILE_ENTITY_ORE_OUTCROP;
	public static final TileEntityType<TileEntityBurnerMiningDrill> TILE_ENTITY_BURNER_MINING_DRILL;

	static
	{
		IRON_ORE_OUTCROP = new BlockOreOutcrop(EnumOreType.IRON);

		YELLOW_BELT = new BlockTransportBelt("yellow_belt", BlockTransportBelt.Type.YELLOW);
		BURNER_MINING_DRILL = new BlockBurnerMiningDrill("burner_mining_drill");

		TILE_ENTITY_TRANSPORT_BELT = registerTileEntity("transport_belt", TileEntityTransportBelt::new);
		TILE_ENTITY_ORE_OUTCROP = registerTileEntity("ore_outcrop", TileEntityOreOutcrop::new);
		TILE_ENTITY_BURNER_MINING_DRILL = registerTileEntity("burner_mining_drill", TileEntityBurnerMiningDrill::new);
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

	public static <T extends TileEntity> TileEntityType<T> registerTileEntity(String name, Supplier<T> factory)
	{
		return TileEntityType.register(FactorioMod.MOD_ID + ":" + name, TileEntityType.Builder.create(factory));
	}

	public static Block[] getBlocks()
	{
		return BLOCKS.toArray(new Block[0]);
	}
}