package com.ocelot.items;

import com.ocelot.FactorioMod;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ModItem extends Item
{
    public ModItem(String name, Item.Properties properties)
    {
        super(properties);
        this.setRegistryName(new ResourceLocation(FactorioMod.MOD_ID, name));
    }

    public ModItem(Item.Properties properties)
    {
        super(properties);
    }
}