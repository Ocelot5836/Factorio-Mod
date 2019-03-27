package com.ocelot.init;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.SoundEvent;

public class ModSounds
{
	private static final List<SoundEvent> SOUNDS = Lists.newArrayList();

	static
	{
	}

	protected static void init()
	{

	}

	public static void registerSound(SoundEvent sound)
	{
		if (sound.getRegistryName() == null)
			throw new RuntimeException("Sound \'" + sound.getClass() + "\' is missing a registry name!");
		SOUNDS.add(sound);
	}

	public static SoundEvent[] getSounds()
	{
		return SOUNDS.toArray(new SoundEvent[0]);
	}
}