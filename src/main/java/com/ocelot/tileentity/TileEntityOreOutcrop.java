package com.ocelot.tileentity;

import javax.annotation.Nullable;

import com.ocelot.init.ModBlocks;
import com.ocelot.util.EnumOreType;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityOreOutcrop extends ModTileEntity
{
	private OreOutcrop outcrop;
	private int spawnCount;

	public TileEntityOreOutcrop()
	{
		super(ModBlocks.TILE_ENTITY_ORE_OUTCROP);
		this.outcrop = new OreOutcrop(null, 0);
		this.spawnCount = 0;
	}

	@Override
	public void read(NBTTagCompound nbt)
	{
		super.read(nbt);
		this.outcrop.deserializeNBT(nbt.getCompound("outcrop"));
		this.spawnCount = nbt.getInt("spawnCount");
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt)
	{
		super.write(nbt);
		nbt.setTag("outcrop", this.outcrop.serializeNBT());
		nbt.setInt("spawnCount", this.spawnCount);
		return nbt;
	}

	public OreOutcrop getOutcrop()
	{
		return outcrop;
	}

	public int getSpawnCount()
	{
		return spawnCount;
	}

	public void setOre(@Nullable EnumOreType ore)
	{
		this.outcrop.setOre(ore);
		this.markDirty();
		if (this.hasWorld())
		{
			this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public void setCount(int count)
	{
		this.outcrop.setCount(count);
		this.markDirty();
		if (this.hasWorld())
		{
			this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public void setSpawnCount(int count)
	{
		this.outcrop.setCount(count);
		this.spawnCount = count;
		this.markDirty();
		if (this.hasWorld())
		{
			this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
		}
	}
}