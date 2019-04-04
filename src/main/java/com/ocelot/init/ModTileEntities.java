package com.ocelot.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.ocelot.FactorioMod;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;
import com.ocelot.tileentity.TileEntityOreOutcrop;
import com.ocelot.tileentity.belt.TileEntityTransportBelt;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

public class ModTileEntities
{
	private static final List<TileEntityType<?>> TILE_ENTITIES = new ArrayList<TileEntityType<?>>();

	public static final TileEntityType<TileEntityTransportBelt> TILE_ENTITY_TRANSPORT_BELT;
	public static final TileEntityType<TileEntityOreOutcrop> TILE_ENTITY_ORE_OUTCROP;
	public static final TileEntityType<TileEntityBurnerMiningDrill> TILE_ENTITY_BURNER_MINING_DRILL;

	static
	{
		TILE_ENTITY_TRANSPORT_BELT = registerTileEntity("transport_belt", TileEntityTransportBelt::new);
		TILE_ENTITY_ORE_OUTCROP = registerTileEntity("ore_outcrop", TileEntityOreOutcrop::new);
		TILE_ENTITY_BURNER_MINING_DRILL = registerTileEntity("burner_mining_drill", TileEntityBurnerMiningDrill::new);
	}

	public static <T extends TileEntity> TileEntityType<T> registerTileEntity(String name, Supplier<T> factory)
	{
		TileEntityType<T> type = TileEntityType.Builder.create(factory).build(null);
		type.setRegistryName(new ResourceLocation(FactorioMod.MOD_ID, name));
		TILE_ENTITIES.add(type);
		return type;
	}

	public static TileEntityType<?>[] getTileEntityTypes()
	{
		return TILE_ENTITIES.toArray(new TileEntityType[0]);
	}
}