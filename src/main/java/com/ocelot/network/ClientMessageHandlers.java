package com.ocelot.network;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.ocelot.audio.BurnerMiningDrillSound;
import com.ocelot.init.ModSounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientMessageHandlers
{
    private static final Map<Long, BurnerMiningDrillSound> BURNER_MINING_DRILL_SOUNDS = new HashMap<Long, BurnerMiningDrillSound>();

    public static void handle(MessageOpenGui msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            Minecraft.getInstance().player.openContainer.windowId = msg.windowId;
        });
        ctx.get().setPacketHandled(true);
    }

    public static void handle(MessagePlayBurnerMiningDrillSound msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            SoundHandler soundHandler = Minecraft.getInstance().getSoundHandler();
            BlockPos pos = msg.pos;
            long longPos = pos.toLong();

            if (msg.running)
            {
                if (!BURNER_MINING_DRILL_SOUNDS.containsKey(longPos))
                {
                    BURNER_MINING_DRILL_SOUNDS.put(longPos, new BurnerMiningDrillSound(ModSounds.BLOCK_BURNER_MINING_DRILL_MINE, SoundCategory.BLOCKS, 0.5f, 1.0f, pos));
                }
                if (!soundHandler.isPlaying(BURNER_MINING_DRILL_SOUNDS.get(longPos)))
                {
                    soundHandler.play(BURNER_MINING_DRILL_SOUNDS.get(longPos));
                }
            }
            else if (BURNER_MINING_DRILL_SOUNDS.containsKey(longPos))
            {
                soundHandler.stop(BURNER_MINING_DRILL_SOUNDS.get(longPos));
                BURNER_MINING_DRILL_SOUNDS.remove(longPos);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}