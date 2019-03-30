package com.ocelot.tileentity.render;

import com.ocelot.tileentity.belt.TileEntityTransportBelt;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

public class RenderTileEntityTransportBelt extends TileEntityRenderer<TileEntityTransportBelt>
{
	@Override
	public void render(TileEntityTransportBelt te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		// TODO find out why this crap doesn't work
		// System.out.println("s");

		// GlStateManager.pushMatrix();
		//
		// GlStateManager.translated(x, y, z);
		//
		// GlStateManager.popMatrix();
	}
}