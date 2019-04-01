package com.ocelot.util;

import com.ocelot.blocks.BlockOreOutcrop;

/**
 * A mining drill is a block that has the capability to mine {@link BlockOreOutcrop}s.
 * 
 * @author Ocelot5836
 */
public interface MiningDrill
{
	/**
	 * @return The amount of energy consumed per item mined
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
	 * @return The amount of pollution generated per second while running
	 */
	int getPollution();
}