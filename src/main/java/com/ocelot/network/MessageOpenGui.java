package com.ocelot.network;

import net.minecraft.network.PacketBuffer;

public class MessageOpenGui
{
	protected int windowId;

	public MessageOpenGui(int windowId)
	{
		this.windowId = windowId;
	}

	public static void encode(MessageOpenGui msg, PacketBuffer buf)
	{
		buf.writeInt(msg.windowId);
	}

	public static MessageOpenGui decode(PacketBuffer buf)
	{
		return new MessageOpenGui(buf.readInt());
	}
}