package com.ocelot.init;

import com.ocelot.tileentity.belt.TileEntityTransportBelt;
import com.ocelot.tileentity.render.RenderTileEntityTransportBelt;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientInit
{
    public static void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransportBelt.class, new RenderTileEntityTransportBelt());
    }
}