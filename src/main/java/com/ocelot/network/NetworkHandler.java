package com.ocelot.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.ocelot.FactorioMod;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler
{
	public static final String VERSION = "1.0";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(FactorioMod.MOD_ID, "instance"), () -> VERSION, version -> VERSION.equals(version), version -> VERSION.equals(version));

	private static int index;

	public static void init()
	{
		registerMessage(MessageOpenGui.class, MessageOpenGui::encode, MessageOpenGui::decode, MessageOpenGuiHandler::handle);
	}

	private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer)
	{
		INSTANCE.registerMessage(index++, messageType, encoder, decoder, messageConsumer);
	}
}