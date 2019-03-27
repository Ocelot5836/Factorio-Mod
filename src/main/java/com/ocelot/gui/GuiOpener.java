package com.ocelot.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

/**
 * Allows a modded gui to be opened with a container or not.
 * 
 * @author Ocelot5836
 */
public interface GuiOpener
{
	/**
	 * Opens the gui with the specified id.
	 * 
	 * @param guiId
	 *            The id of the gui
	 * @param player
	 *            The player to open the gui for
	 * @param world
	 *            The world the player is in
	 * @param pos
	 *            The position the gui was opened from
	 */
	void openGui(int guiId, EntityPlayer player, IWorld world, BlockPos pos);
}