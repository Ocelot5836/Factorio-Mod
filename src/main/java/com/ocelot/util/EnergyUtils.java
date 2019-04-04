package com.ocelot.util;

import java.text.DecimalFormat;

/**
 * Contains misc utilities that have to do with energy.
 * 
 * @author Ocelot5836
 */
public class EnergyUtils
{
    public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#,##0");

    /**
     * Converts a joules float into a compressed joules string.
     * 
     * @param joules
     *            The joules to convert
     * @return The joules in J, KJ, MJ, or GJ
     */
    public static String joulesToString(float joules)
    {
        if (joules > 1000000000)
        {
            return DECIMALFORMAT.format(joules / 1000000000.0) + "GJ";
        }
        if (joules > 1000000)
        {
            return DECIMALFORMAT.format(joules / 1000000.0) + "MJ";
        }
        if (joules > 1000)
        {
            return DECIMALFORMAT.format(joules / 1000.0) + "KJ";
        }
        return DECIMALFORMAT.format(joules) + "J";
    }
}