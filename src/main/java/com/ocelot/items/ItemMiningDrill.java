package com.ocelot.items;

import com.ocelot.blocks.BlockBurnerMiningDrill;
import com.ocelot.blocks.BlockFactorioMachine;
import com.ocelot.blocks.BlockOreOutcrop;
import com.ocelot.blocks.part.MachinePart;
import com.ocelot.tileentity.OreOutcrop;
import com.ocelot.util.MiningDrill;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ItemMiningDrill extends ItemFactorioMachine
{
    public ItemMiningDrill(BlockFactorioMachine miningDrill)
    {
        super(miningDrill);
    }

    private boolean hasOreAt(IWorld world, BlockPos pos)
    {
        return BlockOreOutcrop.getOreOutcrop(world, pos) != null;
    }

    @Override
    protected boolean canPlace(BlockItemUseContext context, IBlockState state)
    {
        IWorld world = context.getWorld();
        BlockPos pos = context.getPos();
        EnumFacing direction = context.getPlacementHorizontalFacing();

        boolean hasOre = false;
        for (MachinePart p : this.getMachine().getParts())
        {
            if (p.isBottom() && this.hasOreAt(world, p.offsetOrigin(pos, direction.getOpposite())))
            {
                hasOre = true;
                break;
            }
        }

        if (!hasOre)
        {
            return false;
        }

        return super.canPlace(context, state);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected boolean placePart(IWorld world, BlockPos pos, IBlockState state)
    {
        OreOutcrop ore = BlockOreOutcrop.getOreOutcrop(world, pos);
        if (world.setBlockState(pos, state, 3))
        {
            MachinePart part = state.get(this.getMachine().getPropertyPart());
            if (part.isBottom() && ore != null)
            {
                BlockPos offsetPos = part.offset(pos, state.get(BlockBurnerMiningDrill.FACING));
                if (world.getTileEntity(offsetPos) instanceof MiningDrill)
                {
                    ((MiningDrill) world.getTileEntity(offsetPos)).setOutcrop(part, ore);
                }
            }
            return true;
        }
        return false;
    }
}