package com.ocelot.tileentity;

import com.ocelot.init.ModBlocks;
import com.ocelot.util.MiningDrill;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBurnerMiningDrill extends ModTileEntity implements ITickable, MiningDrill
{
	private ItemStackHandler inventory;
	private int energy;
	private int maxEnergy;
	private int miningProgress;

	public TileEntityBurnerMiningDrill()
	{
		super(ModBlocks.TILE_ENTITY_BURNER_MINING_DRILL);
		this.inventory = new ItemStackHandler(2);
		this.energy = 0;
		this.maxEnergy = 0;
		this.miningProgress = 0;
	}

	@Override
	public void tick()
	{
		if (this.hasWorld() && !this.getWorld().isRemote())
		{

		}
	}

	@Override
	public void read(NBTTagCompound nbt)
	{
		super.read(nbt);
		this.inventory.deserializeNBT(nbt.getCompound("inventory"));
		this.energy = nbt.getInt("energy");
		this.maxEnergy = nbt.getInt("maxEnergy");
		this.miningProgress = nbt.getInt("miningProgress");
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt)
	{
		super.write(nbt);
		nbt.setTag("inventory", this.inventory.serializeNBT());
		nbt.setInt("energy", this.energy);
		nbt.setInt("maxEnergy", this.maxEnergy);
		nbt.setInt("miningProgress", this.miningProgress);
		return nbt;
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

	public ItemStackHandler getInventory()
	{
		return inventory;
	}

	public int getEnergy()
	{
		return energy;
	}

	public int getMaxEnergy()
	{
		return maxEnergy;
	}

	public int getMiningProgress()
	{
		return miningProgress;
	}

	public int getMaxMiningProgress()
	{
		return (int) Math.ceil(1f / this.getMiningSpeed());
	}
}