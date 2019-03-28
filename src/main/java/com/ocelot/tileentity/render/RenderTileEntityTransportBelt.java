package com.ocelot.tileentity.render;

import com.ocelot.tileentity.belt.TileEntityTransportBelt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.item.EntityItem;

public class RenderTileEntityTransportBelt extends TileEntityRenderer<TileEntityTransportBelt>
{
	private static final EntityItem ITEM = new EntityItem(Minecraft.getInstance().world);

	@Override
	public void render(TileEntityTransportBelt te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		System.out.println("s");
		ITEM.setWorld(this.getWorld());
		ITEM.setItem(te.getStackInSlot(3));
		System.out.println(ITEM.getItem());

		GlStateManager.pushMatrix();

		GlStateManager.translated(x, y, z);
		Minecraft.getInstance().getRenderManager().renderEntity(ITEM, 0, 0, 0, 0, 0, true);

		GlStateManager.popMatrix();
	}
}