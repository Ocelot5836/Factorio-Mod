package com.ocelot.tileentity.belt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class TransportBeltItem implements INBTSerializable<NBTTagCompound>
{
	public static final int ITEM_SIZE = 9;
	public static final TransportBeltItem EMPTY = new TransportBeltItem(ItemStack.EMPTY);

	private ItemStack stack;
	private byte lastSlot;
	private byte slot;

	public TransportBeltItem(ItemStack stack)
	{
		this.stack = stack;
		this.lastSlot = 0;
		this.slot = 0;
	}

	public TransportBeltItem(NBTTagCompound nbt)
	{
		this(ItemStack.EMPTY);
		this.deserializeNBT(nbt);
	}

	public void move(int amount)
	{
		if (this == EMPTY)
			return;
		this.lastSlot = this.slot;
		this.slot += amount;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("stack", this.stack.write(new NBTTagCompound()));
		nbt.setByte("lastSlot", this.lastSlot);
		nbt.setByte("slot", this.slot);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.stack = ItemStack.read(nbt.getCompound("stack"));
		this.lastSlot = nbt.getByte("lastSlot");
		this.slot = nbt.getByte("slot");
	}

	public boolean isEmpty()
	{
		return this == EMPTY || this.stack.isEmpty();
	}

	public ItemStack getStack()
	{
		return stack;
	}

	public byte getLastSlot()
	{
		return lastSlot;
	}

	public byte getSlot()
	{
		return slot;
	}

	public void setStack(ItemStack stack)
	{
		this.stack = stack;
	}

	public void setSlot(int slot)
	{
		this.lastSlot = (byte) slot;
		this.slot = (byte) slot;
	}
}