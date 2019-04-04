package com.ocelot.gui.container;

import com.ocelot.gui.container.slot.SlotJouleFuel;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ContainerBurnerMiningDrill extends Container
{
    private IWorld world;
    private BlockPos pos;

    public ContainerBurnerMiningDrill(EntityPlayer player, IWorld world, BlockPos pos, TileEntityBurnerMiningDrill te)
    {
        this.world = world;
        this.pos = pos;

        this.addSlot(new SlotJouleFuel(te.getInventory(), 0, 20, 17));

        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(player.inventory, x + y * 9 + 9, 8 + x * 18, 68 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(player.inventory, x, 8 + x * 18, 126));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index > 0)
            {
                if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.mergeItemStack(itemstack1, 1, 37, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        if (!(this.world.getTileEntity(this.pos) instanceof TileEntityBurnerMiningDrill))
        {
            return false;
        }
        else
        {
            return !(player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) > 64.0D);
        }
    }
}