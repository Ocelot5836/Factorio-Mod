package com.ocelot.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

/**
 * Determines if an inventory in factorio can have {@link ItemStack}s inserted into it.
 * 
 * @author Ocelot5836
 */
public interface BlockFactorioInventory
{
    /**
     * Checks to see if the specified stack can be inserted into this inventory via the provided face.
     * 
     * @param stack
     *            The stack to insert
     * @param world
     *            The world the block is in
     * @param pos
     *            The position of the block
     * @param face
     *            The face the machine is attempting to insert from
     * @param otherInventory
     *            The other inventory attempting to insert
     * @return Whether or not the specified stack can be inserted
     */
    boolean canReceive(ItemStack stack, IWorld world, BlockPos pos, EnumFacing face, BlockFactorioInventory otherInventory);

    /**
     * Inserts the specified stack into this inventory via the provided face.
     * 
     * @param stack
     *            The stack to insert
     * @param world
     *            The world the block is in
     * @param pos
     *            The position of the block
     * @param otherInventory
     *            The other inventory attempting to insert
     * @param face
     *            The face the machine is attempting to insert from
     * @return The items that could not be inserted
     */
    ItemStack receive(ItemStack stack, IWorld world, BlockPos pos, EnumFacing face, BlockFactorioInventory otherInventory);
}