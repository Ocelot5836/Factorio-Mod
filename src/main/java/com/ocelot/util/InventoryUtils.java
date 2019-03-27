package com.ocelot.util;

import javax.annotation.Nullable;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * Contains misc utilities that have to do with inventories.
 * 
 * @author Ocelot5836
 */
public class InventoryUtils
{

	/**
	 * Inserts the specified stack into the entire inventory.
	 * 
	 * @param inventory
	 *            The inventory to insert the items into
	 * @param stack
	 *            The stack to insert
	 * @param simulate
	 *            Whether or not to simulate the movement
	 * @return The stack that couldn't get inserted
	 */
	public static ItemStack insertStack(IItemHandler inventory, ItemStack stack, boolean simulate)
	{
		return insertStack(inventory, stack, 0, inventory.getSlots(), simulate);
	}

	/**
	 * Inserts the specified stack into the slots in between the slot min and slot max.
	 * 
	 * @param inventory
	 *            The inventory to insert the items into
	 * @param stack
	 *            The stack to insert
	 * @param slotMin
	 *            The minimum slot to insert items into
	 * @param slotMax
	 *            The maximum slot to insert items into
	 * @param simulate
	 *            Whether or not to simulate the movement
	 * @return The stack that couldn't get inserted
	 */
	public static ItemStack insertStack(IItemHandler inventory, ItemStack stack, int slotMin, int slotMax, boolean simulate)
	{
		ItemStack remaining = stack.copy();
		for (int i = slotMin; i < slotMax; i++)
		{
			remaining = inventory.insertItem(i, remaining, simulate);
			if (remaining.isEmpty())
				break;
		}
		return remaining;
	}

	/**
	 * Extracts the specified stack from the entire inventory.
	 * 
	 * @param inventory
	 *            The inventory to extract the items from
	 * @param stack
	 *            The item and number of that item to remove
	 * @param simulate
	 *            Whether or not to simulate the movement
	 * @return The stack that couldn't get removed
	 */
	public static ItemStack extractStack(IItemHandler inventory, ItemStack stack, boolean simulate)
	{
		return extractStack(inventory, stack, 0, inventory.getSlots(), simulate);
	}

	/**
	 * Extracts the specified stack from the slots in between the slot min and slot max.
	 * 
	 * @param inventory
	 *            The inventory to extract the items from
	 * @param stack
	 *            The item and number of that item to remove
	 * @param slotMin
	 *            The minimum slot to remove items from
	 * @param slotMax
	 *            The maximum slot to remove items from
	 * @param simulate
	 *            Whether or not to simulate the movement
	 * @return The stack that couldn't get removed
	 */
	public static ItemStack extractStack(IItemHandler inventory, ItemStack stack, int slotMin, int slotMax, boolean simulate)
	{
		ItemStack remaining = stack.copy();
		for (int i = slotMin; i < slotMax; i++)
		{
			if (ItemStack.areItemStacksEqual(inventory.getStackInSlot(i), remaining))
			{
				remaining = inventory.extractItem(i, remaining.getCount(), simulate);
				if (remaining.isEmpty())
					break;
			}
		}
		return remaining;
	}

	/**
	 * Drops all the items from the specified tile entity if it is an {@link IInventory}, {@link ISimpleInventory}, or an {@link IItemHandler}.
	 * 
	 * @param te
	 *            The tile entity to drop the items from
	 */
	public static void dropItems(@Nullable TileEntity te)
	{
		if (te != null)
		{
			World world = te.getWorld();
			BlockPos pos = te.getPos();
			if (te instanceof IInventory)
			{
				InventoryHelper.dropInventoryItems(world, pos, (IInventory) te);
			}
			else if (te instanceof ISimpleInventory)
			{
				ISimpleInventory inventory = (ISimpleInventory) te;
				for (int i = 0; i < inventory.getSlots(); ++i)
				{
					ItemStack stack = inventory.getStackInSlot(i);
					if (!stack.isEmpty())
					{
						InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
					}
				}
			}
			else if (te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent())
			{
				te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((inventory) ->
				{
					for (int i = 0; i < inventory.getSlots(); ++i)
					{
						ItemStack stack = inventory.getStackInSlot(i);
						if (!stack.isEmpty())
						{
							InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
						}
					}
				});
			}
		}
	}

	/**
	 * Checks to see if the inventory is empty.
	 * 
	 * @param inventory
	 *            The inventory to check
	 * @return Whether or not the inventory is empty
	 */
	public static boolean isEmpty(IItemHandler inventory)
	{
		for (int i = 0; i < inventory.getSlots(); i++)
		{
			if (!inventory.getStackInSlot(i).isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks to see if the inventory is full.
	 * 
	 * @param inventory
	 *            The inventory to check
	 * @return Whether or not the inventory is full
	 */
	public static boolean isFull(IItemHandler inventory)
	{
		for (int i = 0; i < inventory.getSlots(); i++)
		{
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack.isEmpty() || stack.getCount() < Math.min(stack.getMaxStackSize(), inventory.getSlotLimit(i)))
			{
				return false;
			}
		}
		return true;
	}
}