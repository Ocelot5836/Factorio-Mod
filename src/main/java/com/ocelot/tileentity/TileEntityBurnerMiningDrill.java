package com.ocelot.tileentity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.ocelot.blocks.BlockBurnerMiningDrill;
import com.ocelot.init.ModBlocks;
import com.ocelot.util.MiningDrill;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBurnerMiningDrill extends ModTileEntity implements ITickable, MiningDrill
{
	private ItemStackHandler inventory;
	private int energy;
	private int maxEnergy;
	private int miningProgress;
	private Map<BlockBurnerMiningDrill.MinerDrillPart, OreOutcrop> coveredOres;

	public TileEntityBurnerMiningDrill()
	{
		super(ModBlocks.TILE_ENTITY_BURNER_MINING_DRILL);
		this.inventory = new ItemStackHandler(1);
		this.energy = 0;
		this.maxEnergy = 0;
		this.miningProgress = 0;
		this.coveredOres = new HashMap<BlockBurnerMiningDrill.MinerDrillPart, OreOutcrop>();
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

		if (nbt.contains("coveredOres", Constants.NBT.TAG_COMPOUND))
		{
			NBTTagCompound coveredOresNbt = nbt.getCompound("coveredOres");
			for (BlockBurnerMiningDrill.MinerDrillPart part : BlockBurnerMiningDrill.MinerDrillPart.values())
			{
				if (part.isBottom() && coveredOresNbt.contains(part.getName(), Constants.NBT.TAG_COMPOUND))
				{
					this.coveredOres.put(part, new OreOutcrop(coveredOresNbt.getCompound(part.getName())));
				}
			}
		}
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt)
	{
		super.write(nbt);

		nbt.setTag("inventory", this.inventory.serializeNBT());
		nbt.setInt("energy", this.energy);
		nbt.setInt("maxEnergy", this.maxEnergy);
		nbt.setInt("miningProgress", this.miningProgress);

		NBTTagCompound coveredOresNbt = new NBTTagCompound();
		for (Entry<BlockBurnerMiningDrill.MinerDrillPart, OreOutcrop> entry : this.coveredOres.entrySet())
		{
			BlockBurnerMiningDrill.MinerDrillPart part = entry.getKey();
			if (part.isBottom())
			{
				coveredOresNbt.setTag(part.getName(), entry.getValue().serializeNBT());
			}
		}
		nbt.setTag("coveredOres", coveredOresNbt);

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

	@Nullable
	public OreOutcrop getOutcrop(BlockBurnerMiningDrill.MinerDrillPart part)
	{
		return part.isBottom() ? this.coveredOres.get(part) : null;
	}

	public void setOre(BlockBurnerMiningDrill.MinerDrillPart part, @Nullable OreOutcrop ore)
	{
		if (part.isBottom())
		{
			if (ore != null)
			{
				this.coveredOres.put(part, ore);
			}
			else
			{
				this.coveredOres.remove(part);
			}
		}
	}
}