package com.ocelot.tileentity;

import com.ocelot.util.EnumOreType;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class OreOutcrop implements INBTSerializable<NBTTagCompound>, IItemProvider
{
	private EnumOreType ore;
	private int count;

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("ore", this.ore.getName());
		nbt.setInt("count", this.count);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.ore = EnumOreType.byName(nbt.getString("ore"));
		this.count = nbt.getInt("count");
	}

	@Override
	public Item asItem()
	{
		return this.ore.getItem().asItem();
	}
}