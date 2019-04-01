package com.ocelot.tileentity;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.EnergyStorage;

public class FactorioEnergyStorage extends EnergyStorage
{
	public FactorioEnergyStorage(int capacity)
	{
		super(capacity);
	}

	public FactorioEnergyStorage(int capacity, int maxTransfer)
	{
		super(capacity, maxTransfer);
	}

	public FactorioEnergyStorage(int capacity, int maxReceive, int maxExtract)
	{
		super(capacity, maxReceive, maxExtract);
	}

	public FactorioEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy)
	{
		super(capacity, maxReceive, maxExtract, energy);
	}
	
	public void setEnergy(int energy) {
		this.energy = MathHelper.clamp(energy, 0, this.getMaxEnergyStored());
	}
	
	public void setCapacity(int capacity) {
		this.capacity = Math.max(capacity, 0);
		this.energy = MathHelper.clamp(this.energy, 0, this.getMaxEnergyStored());
	}
}