package com.ocelot.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class MessagePlayBurnerMiningDrillSound
{
    protected BlockPos pos;
    protected boolean running;

    public MessagePlayBurnerMiningDrillSound(BlockPos pos, boolean running)
    {
        this.pos = pos;
        this.running = running;
    }

    public static void encode(MessagePlayBurnerMiningDrillSound msg, PacketBuffer buf)
    {
        buf.writeBlockPos(msg.pos).writeBoolean(msg.running);
    }

    public static MessagePlayBurnerMiningDrillSound decode(PacketBuffer buf)
    {
        return new MessagePlayBurnerMiningDrillSound(buf.readBlockPos(), buf.readBoolean());
    }
}