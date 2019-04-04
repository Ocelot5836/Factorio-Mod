package com.ocelot.tileentity;

import javax.annotation.Nullable;

import com.ocelot.init.ModTileEntities;
import com.ocelot.util.EnumOreType;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityOreOutcrop extends ModTileEntity
{
	private OreOutcrop outcrop;

	public TileEntityOreOutcrop()
	{
		super(ModTileEntities.TILE_ENTITY_ORE_OUTCROP);
		this.outcrop = new OreOutcrop(null, 15);
	}

	@Override
	public void read(NBTTagCompound nbt)
	{
		super.read(nbt);
		this.outcrop.deserializeNBT(nbt);
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt)
	{
		super.write(nbt);
		nbt.merge(this.outcrop.serializeNBT());
		return nbt;
	}

	public OreOutcrop getOutcrop()
	{
		return outcrop;
	}

	public TileEntityOreOutcrop setOre(@Nullable EnumOreType ore)
	{
		this.outcrop.setOre(ore);
		this.markDirty();
		if (this.hasWorld())
		{
			this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
		}
		return this;
	}

	public TileEntityOreOutcrop setCount(int count)
	{
		this.outcrop.setCount(count);
		this.markDirty();
		if (this.hasWorld())
		{
			this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
		}
		return this;
	}
}