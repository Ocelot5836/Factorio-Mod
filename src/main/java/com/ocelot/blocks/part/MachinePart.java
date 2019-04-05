package com.ocelot.blocks.part;

import com.ocelot.blocks.BlockFactorioMachine;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;

/**
 * Represents a single part that a {@link BlockFactorioMachine} can have.
 * 
 * @author Ocelot5836
 */
public interface MachinePart extends IStringSerializable
{
    /**
     * Offsets the position to the origin.
     * 
     * @param pos
     *            The position of the pos
     * @param direction
     *            The direction to go, eg block facing
     * @return The new offset position
     */
    public BlockPos offset(BlockPos pos, EnumFacing direction);

    /**
     * Offsets the position from the origin to this position.
     * 
     * @param pos
     *            The position of the pos
     * @param direction
     *            The direction to go, eg block facing
     * @return The new offset position
     */
    public BlockPos offsetOrigin(BlockPos pos, EnumFacing direction);

    /**
     * @return Whether or not this part is on the bottom layer
     */
    public boolean isBottom();

    /**
     * @return Whether or not this is the spot that the tile entity is in
     */
    public boolean isBase();
}