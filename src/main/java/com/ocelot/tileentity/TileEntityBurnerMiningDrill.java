package com.ocelot.tileentity;

import com.ocelot.init.ModBlocks;
import com.ocelot.util.MiningDrill;

import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBurnerMiningDrill extends ModTileEntity implements ITickable, MiningDrill
{
	private ItemStackHandler inventory;
	private FactorioEnergyStorage energy;
	private float miningProgress;

	public TileEntityBurnerMiningDrill()
	{
		super(ModBlocks.TILE_ENTITY_BURNER_MINING_DRILL);
		this.inventory = new ItemStackHandler(2);
		// this.energy = new FactorioEnergyStorage();
	}

	@Override
	public void tick()
	{

	}

	@Override
	public int getEnergyConsumption()
	{
		return 150;
	}

	@Override
	public float getMiningTime()
	{
		return 0.3f;
	}

	@Override
	public float getMiningSpeed()
	{
		return 0.25f;
	}

	@Override
	public int getPollution()
	{
		return 12;
	}
}