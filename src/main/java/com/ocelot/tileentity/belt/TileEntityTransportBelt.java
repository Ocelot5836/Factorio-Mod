package com.ocelot.tileentity.belt;

import com.ocelot.blocks.BlockTransportBelt;
import com.ocelot.init.ModBlocks;
import com.ocelot.tileentity.ModTileEntity;
import com.ocelot.util.ISimpleInventory;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

public class TileEntityTransportBelt extends ModTileEntity implements ITickable, ISimpleInventory
{
	private TransportBeltItem[] inventory;

	public TileEntityTransportBelt()
	{
		super(ModBlocks.TILE_ENTITY_TRANSPORT_BELT);
		this.inventory = new TransportBeltItem[4 * 2];
		this.clear();
		this.setItem(0, new TransportBeltItem(new ItemStack(Blocks.DIAMOND_BLOCK)));
	}

	@Override
	public void tick()
	{
		if (this.hasWorld())
		{
			if (!this.getWorld().isRemote)
			{
				boolean update = false;
				for (int i = 0; i < this.inventory.length; i++)
				{
					TransportBeltItem item = this.getItem(i);
					if (!item.isEmpty())
					{
						if (item.getSlot() >= TransportBeltItem.ITEM_SIZE)
						{
							item.setSlot(TransportBeltItem.ITEM_SIZE);
							int nextSlot = i + 1;
							if (nextSlot >= (i / 4 > 0 ? 8 : 4))
							{
								if (this.moveToNextBelt(i / 4 > 0, item))
								{
									this.setItem(i, TransportBeltItem.EMPTY);
								}
							}
							else
							{
								if (this.getItem(nextSlot).isEmpty())
								{
									item.setSlot(0);
									this.setItem(nextSlot, item);
									this.setItem(i, TransportBeltItem.EMPTY);
								}
							}
						}
						else
						{
							item.move(this.getBeltType().getSpeed());
							update = true;
						}
					}
				}

				if (update)
				{
					this.markDirty();
					this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
				}
			}
		}
	}

	private boolean moveToNextBelt(boolean right, TransportBeltItem item)
	{
		EnumFacing facing = this.getBlockState().get(BlockTransportBelt.FACING);
		BlockTransportBelt.Shape shape = this.getBlockState().get(BlockTransportBelt.SHAPE);

		BlockPos nextPos = this.getPos();
		switch (shape)
		{
		case STRAIT:
			nextPos = this.getPos().offset(facing);
			break;
		case STRAIT_UP:
			nextPos = this.getPos().offset(facing).up();
			break;
		case BEND_LEFT:
			nextPos = this.getPos().offset(facing.rotateYCCW());
			break;
		case BEND_RIGHT:
			nextPos = this.getPos().offset(facing.rotateY());
			break;
		}

		if (this.getWorld().getTileEntity(nextPos) instanceof TileEntityTransportBelt)
		{
			TileEntityTransportBelt belt = (TileEntityTransportBelt) this.getWorld().getTileEntity(nextPos);
			if (belt.getItem(right ? 4 : 0).isEmpty())
			{
				item.setSlot(0);
				belt.setItem(right ? 4 : 0, item);
				return true;
			}
		}

		return false;
	}

	public void clear()
	{
		for (int i = 0; i < this.inventory.length; i++)
		{
			this.inventory[i] = TransportBeltItem.EMPTY;
		}
	}

	public void setItem(int slot, TransportBeltItem item)
	{
		if (slot < 0 || slot >= this.inventory.length)
			return;
		this.inventory[slot] = item;
		if (this.hasWorld())
		{
			this.markDirty();
			this.getWorld().notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public TransportBeltItem getItem(int slot)
	{
		if (slot < 0 || slot >= this.inventory.length)
			return TransportBeltItem.EMPTY;
		return this.inventory[slot];
	}

	@Override
	public void read(NBTTagCompound nbt)
	{
		super.read(nbt);

		this.clear();
		NBTTagList inventory = nbt.getList("inventory", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < inventory.size(); i++)
		{
			NBTTagCompound itemTag = inventory.getCompound(i);
			int slot = itemTag.getInt("slot");
			this.setItem(slot, new TransportBeltItem(itemTag.getCompound("item")));
		}
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt)
	{
		NBTTagList inventory = new NBTTagList();
		for (int i = 0; i < this.inventory.length; i++)
		{
			TransportBeltItem item = this.getItem(i);
			if (!item.isEmpty())
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setTag("item", item.serializeNBT());
				itemTag.setInt("slot", i);
				inventory.add(itemTag);
			}
		}
		nbt.setTag("inventory", inventory);

		return nbt;
	}

	public BlockTransportBelt.Type getBeltType()
	{
		return this.getBlockState().getBlock() instanceof BlockTransportBelt ? ((BlockTransportBelt) this.getBlockState().getBlock()).getType() : BlockTransportBelt.Type.YELLOW;
	}

	@Override
	public int getSlots()
	{
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.getItem(slot).getStack();
	}
}