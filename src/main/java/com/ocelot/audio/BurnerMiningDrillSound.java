package com.ocelot.audio;

import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BurnerMiningDrillSound extends SimpleSound
{
    public BurnerMiningDrillSound(SoundEvent sound, SoundCategory category, float volume, float pitch, BlockPos pos)
    {
        super(sound, category, volume, pitch, pos);
        this.repeat = true;
    }
}