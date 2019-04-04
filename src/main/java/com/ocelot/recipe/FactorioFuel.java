package com.ocelot.recipe;

/**
 * A factorio fuel is an item that gives a certain amount of joules when burned.
 * 
 * @author Ocelot5836
 */
public interface FactorioFuel
{
	/**
	 * @return The amount of joules this item gives when burned
	 */
	float getJoules();
}