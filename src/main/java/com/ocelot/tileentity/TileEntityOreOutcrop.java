package com.ocelot.tileentity;

import com.ocelot.init.ModBlocks;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityOreOutcrop extends ModTileEntity
{
	private int count;
	private int spawnCount;

	public TileEntityOreOutcrop()
	{
		this(0);
	}

	public TileEntityOreOutcrop(int spawnCount)
	{
		super(ModBlocks.TILE_ENTITY_ORE_OUTCROP);
		this.count = spawnCount;
		this.spawnCount = spawnCount;
	}

	@Override
	public void read(NBTTagCompound nbt)
	{
		super.read(nbt);
		this.count = nbt.getInt("count");
		this.spawnCount = nbt.getInt("spawnCount");
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt)
	{
		super.write(nbt);
		nbt.setInt("count", this.count);
		nbt.setInt("spawnCount", this.spawnCount);
		return nbt;
	}

	public int getCount()
	{
		return count;
	}

	public int getSpawnCount()
	{
		return spawnCount;
	}

	public void setCount(int count)
	{
		this.count = count;
		this.markDirty();
		if (this.hasWorld())
		{
			this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public void setSpawnCount(int count)
	{
		this.spawnCount = count;
		this.count = count;
		this.markDirty();
		if (this.hasWorld())
		{
			this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
		}
	}
}