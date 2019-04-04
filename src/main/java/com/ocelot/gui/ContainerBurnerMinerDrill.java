package com.ocelot.gui;

import com.ocelot.tileentity.TileEntityBurnerMiningDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBurnerMinerDrill extends Container
{

	public ContainerBurnerMinerDrill(TileEntityBurnerMiningDrill te)
	{
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseGet(null);

		this.addSlot(new SlotItemHandler(handler, 0, 0, 0));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return false;
	}
}