package com.ocelot.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageOpenGuiHandler
{
	public static void handle(MessageOpenGui msg, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			Minecraft.getInstance().player.openContainer.windowId = msg.windowId;
		});
		ctx.get().setPacketHandled(true);
	}
}