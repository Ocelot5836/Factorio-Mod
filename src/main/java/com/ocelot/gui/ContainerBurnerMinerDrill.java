package com.ocelot.gui;

import com.ocelot.tileentity.TileEntityBurnerMiningDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBurnerMinerDrill extends Container
{
	public ContainerBurnerMinerDrill(EntityPlayer player, TileEntityBurnerMiningDrill te)
	{
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseGet(null);

		this.addSlot(new SlotItemHandler(handler, 0, 0, 0));

		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				this.addSlot(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18));
			}
		}

		for (int x = 0; x < 9; x++)
		{
			this.addSlot(new Slot(player.inventory, x, 8 + x * 18, 161));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		// TODO Auto-generated method stub
		return super.transferStackInSlot(playerIn, index);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return false;
	}
}