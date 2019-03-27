package com.ocelot.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ModTileEntity extends TileEntity
{
	public ModTileEntity(TileEntityType<? extends TileEntity> tileEntityType)
	{
		super(tileEntityType);
	}

	protected void writeUpdateTag(NBTTagCompound nbt)
	{
		this.write(nbt);
	}

	protected void readUpdateTag(NBTTagCompound nbt)
	{
		this.read(nbt);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeUpdateTag(nbt);
		return nbt;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readUpdateTag(pkt.getNbtCompound());
	}
}