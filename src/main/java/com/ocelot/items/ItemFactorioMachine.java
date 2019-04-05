package com.ocelot.items;

import com.ocelot.FactorioMod;
import com.ocelot.blocks.BlockFactorioMachine;
import com.ocelot.blocks.BlockOreOutcrop;
import com.ocelot.blocks.part.MachinePart;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ItemFactorioMachine extends ModItemBlock
{
    private BlockFactorioMachine machine;

    public ItemFactorioMachine(BlockFactorioMachine machine)
    {
        super(machine, new Item.Properties().group(FactorioMod.TAB));
        this.machine = machine;
    }

    protected boolean canPlaceAt(BlockItemUseContext context, IWorld world, BlockPos pos, IBlockState state)
    {
        MachinePart part = state.get(this.machine.getPropertyPart());
        return ((!part.isBottom() ? !(world.getBlockState(pos).getBlock() instanceof BlockOreOutcrop) : true) && world.getBlockState(pos).isReplaceable(new BlockItemUseContext(context.getWorld(), context.getPlayer(), context.getItem(), pos, context.getFace(), context.getHitX(), context.getHitY(), context.getHitZ()))) && world.checkNoEntityCollision(state, pos);
    }

    protected boolean placePart(IWorld world, BlockPos pos, IBlockState state)
    {
        return world.setBlockState(pos, state, 3);
    }

    @Override
    protected boolean canPlace(BlockItemUseContext context, IBlockState state)
    {
        IWorld world = context.getWorld();
        BlockPos pos = context.getPos();
        EnumFacing direction = context.getPlacementHorizontalFacing();

        for (MachinePart p : this.machine.getParts())
        {
            if (!this.canPlaceAt(context, world, p.offsetOrigin(pos, direction.getOpposite()), this.machine.setPart(state, p, world, pos)))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, IBlockState state)
    {
        IWorld world = context.getWorld();
        BlockPos pos = context.getPos();
        EnumFacing direction = context.getPlacementHorizontalFacing();

        for (MachinePart p : this.machine.getParts())
        {
            if (!this.placePart(world, p.offsetOrigin(pos, direction.getOpposite()), this.machine.setPart(state, p, world, pos)))
            {
                return false;
            }
        }
        return true;
    }

    public BlockFactorioMachine getMachine()
    {
        return machine;
    }
}