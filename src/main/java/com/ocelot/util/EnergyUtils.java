package com.ocelot.util;

import java.text.DecimalFormat;

public class EnergyUtils
{
	public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#,##0");

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