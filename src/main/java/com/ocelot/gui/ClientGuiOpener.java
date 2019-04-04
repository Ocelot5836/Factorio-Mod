package com.ocelot.gui;

import javax.annotation.Nullable;

import com.ocelot.FactorioMod;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ClientGuiOpener extends ServerGuiOpener
{
	@Override
	public void openGui(int guiId, EntityPlayer player, IWorld world, BlockPos pos)
	{
		super.openGui(guiId, player, world, pos);

		if (world.isRemote())
		{
			GuiScreen value = getGuiElement(guiId, player, world, pos);
			if (value != null)
			{
				Minecraft.getInstance().displayGuiScreen(value);
			}
		}
	}

	@Nullable
	private static GuiScreen getGuiElement(int id, EntityPlayer player, IWorld world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		if(id == FactorioMod.GUI_BURNER_MINING_DRILL_ID)
		    return new GuiBurnerMiningDrill(pos, (TileEntityBurnerMiningDrill) te);
		return null;
	}
}