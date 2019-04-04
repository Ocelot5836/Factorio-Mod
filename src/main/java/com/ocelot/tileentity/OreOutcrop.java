package com.ocelot.tileentity;

import javax.annotation.Nullable;

import com.ocelot.util.EnumOreType;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class OreOutcrop implements INBTSerializable<NBTTagCompound>, IItemProvider
{
    private EnumOreType ore;
    private int count;

    public OreOutcrop(EnumOreType ore, int count)
    {
        this.ore = ore;
        this.count = count;
    }

    public OreOutcrop(NBTTagCompound nbt)
    {
        this.deserializeNBT(nbt);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        if (this.ore != null)
        {
            nbt.setString("ore", this.ore.getName());
        }
        nbt.setInt("count", this.count);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if (nbt.contains("ore", Constants.NBT.TAG_STRING))
        {
            this.ore = EnumOreType.byName(nbt.getString("ore"));
        }
        this.count = nbt.getInt("count");
    }

    @Override
    public Item asItem()
    {
        return this.ore == null ? Items.AIR : this.ore.getItemDropped().asItem();
    }

    @Nullable
    public EnumOreType getOre()
    {
        return ore;
    }

    public int getCount()
    {
        return count;
    }

    protected void setOre(@Nullable EnumOreType ore)
    {
        this.ore = ore;
    }

    protected void setCount(int count)
    {
        this.count = count;
    }
}