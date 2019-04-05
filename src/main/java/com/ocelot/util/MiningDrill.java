package com.ocelot.util;

import javax.annotation.Nullable;

import com.ocelot.blocks.BlockOreOutcrop;
import com.ocelot.blocks.part.MachinePart;
import com.ocelot.tileentity.OreOutcrop;

import net.minecraft.util.ITickable;

/**
 * A mining drill is a block that has the capability to mine {@link BlockOreOutcrop}s.
 * 
 * @author Ocelot5836
 */
public interface MiningDrill<T extends MachinePart> extends ITickable
{
    /**
     * @return The amount of watts consumed
     */
    int getEnergyConsumption();

    /**
     * @return The amount of time it takes to mine a single resource
     */
    float getMiningTime();

    /**
     * @return The speed at which resources are mined
     */
    float getMiningSpeed();

    /**
     * @return The amount of ticks that have been currently processed
     */
    public int getMiningProgress();

    /**
     * @return The amount of ticks that need to be processed to complete one operation
     */
    public int getMaxMiningProgress();

    /**
     * @return The amount of pollution being currently generated per second
     */
    int getPollution();

    /**
     * @return Whether or not the drill is running
     */
    boolean isRunning();

    /**
     * Checks for an outcrop at the position of the specified part.
     * 
     * @param part
     *            The part to get the outcrop of
     * @return The outcrop found at that part or null if there was no outcrop
     */
    @Nullable
    public OreOutcrop getOutcrop(T part);

    /**
     * Sets the outcrop at the position of the specified part to the new outcrop provided.
     * 
     * @param part
     *            The part to set the outcrop of
     * @param outcrop
     *            The outcrop to put under that part
     */
    public void setOutcrop(T part, @Nullable OreOutcrop outcrop);
}