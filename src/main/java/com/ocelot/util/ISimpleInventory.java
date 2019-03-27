package com.ocelot.util;

import net.minecraft.item.ItemStack;

/**
 * A very simple holder of {@link ItemStack}s.
 * 
 * @author Ocelot5836
 */
public interface ISimpleInventory
{
	/**
	 * @return The number of slots in the inventory
	 */
	int getSlots();

	/**
	 * @param slot
	 *            The slot to get the stack from
	 * @return The stack in that slot or {@link ItemStack#EMPTY} if it is empty
	 */
	ItemStack getStackInSlot(int slot);
}