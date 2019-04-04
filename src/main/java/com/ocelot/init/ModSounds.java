package com.ocelot.init;

import java.util.List;

import com.google.common.collect.Lists;
import com.ocelot.FactorioMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSounds
{
    private static final List<SoundEvent> SOUNDS = Lists.newArrayList();

    public static final SoundEvent BLOCK_BURNER_MINING_DRILL_MINE;

    static
    {
        BLOCK_BURNER_MINING_DRILL_MINE = new SoundEvent(new ResourceLocation(FactorioMod.MOD_ID, "block.burner_mining_drill.mine"));
    }

    protected static void init()
    {
        registerSound(BLOCK_BURNER_MINING_DRILL_MINE);
    }

    public static void registerSound(SoundEvent sound)
    {
        sound.setRegistryName(sound.getName());
        if (sound.getRegistryName() == null)
            throw new RuntimeException("Sound \'" + sound.getClass() + "\' is missing a registry name!");
        SOUNDS.add(sound);
    }

    public static SoundEvent[] getSounds()
    {
        return SOUNDS.toArray(new SoundEvent[0]);
    }
}