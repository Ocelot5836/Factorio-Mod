package com.ocelot.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.ocelot.FactorioMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler
{
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(FactorioMod.MOD_ID, "instance"), () -> FMLNetworkConstants.NETVERSION, version -> FMLNetworkConstants.NETVERSION.equals(version), version -> FMLNetworkConstants.NETVERSION.equals(version));

    private static int index;

    public static void init()
    {
        registerMessage(MessageOpenGui.class, MessageOpenGui::encode, MessageOpenGui::decode, ClientMessageHandlers::handle);
        registerMessage(MessagePlayBurnerMiningDrillSound.class, MessagePlayBurnerMiningDrillSound::encode, MessagePlayBurnerMiningDrillSound::decode, ClientMessageHandlers::handle);
    }

    public static <MSG> void sendToAllPlayers(World world, MSG message)
    {
        for (EntityPlayer player : world.playerEntities)
        {
            if (player instanceof EntityPlayerMP)
            {
                NetworkHandler.INSTANCE.sendTo(message, ((EntityPlayerMP) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer)
    {
        INSTANCE.registerMessage(index++, messageType, encoder, decoder, messageConsumer);
    }
}