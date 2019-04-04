package com.ocelot.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Registry
{
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> registry)
	{
		ModItems.init();
		registry.getRegistry().registerAll(ModItems.getItems());
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> registry)
	{
		ModBlocks.init();
		registry.getRegistry().registerAll(ModBlocks.getBlocks());
	}

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> registry)
	{
		ModSounds.init();
		registry.getRegistry().registerAll(ModSounds.getSounds());
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<TileEntityType<?>> registry)
	{
		registry.getRegistry().registerAll(ModTileEntities.getTileEntityTypes());
	}
}