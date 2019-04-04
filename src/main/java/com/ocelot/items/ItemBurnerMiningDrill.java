package com.ocelot.items;

import com.ocelot.FactorioMod;
import com.ocelot.blocks.BlockBurnerMiningDrill;
import com.ocelot.blocks.BlockOreOutcrop;
import com.ocelot.init.ModBlocks;
import com.ocelot.tileentity.OreOutcrop;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ItemBurnerMiningDrill extends ModItemBlock
{
    public ItemBurnerMiningDrill()
    {
        super(ModBlocks.BURNER_MINING_DRILL, new Item.Properties().group(FactorioMod.TAB));
    }

    private boolean hasOreAt(IWorld world, BlockPos pos)
    {
        return BlockOreOutcrop.getOreOutcrop(world, pos) != null;
    }

    private boolean canPlaceAt(BlockItemUseContext context, IWorld world, BlockPos pos, IBlockState state)
    {
        return ((state.get(BlockBurnerMiningDrill.PART).isTop() ? !(world.getBlockState(pos).getBlock() instanceof BlockOreOutcrop) : true) && world.getBlockState(pos).isReplaceable(new BlockItemUseContext(context.getWorld(), context.getPlayer(), context.getItem(), pos, context.getFace(), context.getHitX(), context.getHitY(), context.getHitZ()))) && world.checkNoEntityCollision(state, pos);
    }

    private boolean placePart(IWorld world, BlockPos pos, IBlockState state)
    {
        OreOutcrop ore = BlockOreOutcrop.getOreOutcrop(world, pos);
        if (world.setBlockState(pos, state, 3))
        {
            BlockBurnerMiningDrill.MinerDrillPart part = state.get(BlockBurnerMiningDrill.PART);
            if (part.isBottom() && ore != null)
            {
                BlockPos offsetPos = part.offset(pos, state.get(BlockBurnerMiningDrill.FACING));
                if (world.getTileEntity(offsetPos) instanceof TileEntityBurnerMiningDrill)
                {
                    ((TileEntityBurnerMiningDrill) world.getTileEntity(offsetPos)).setOre(part, ore);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean canPlace(BlockItemUseContext context, IBlockState state)
    {
        IWorld world = context.getWorld();
        BlockPos pos = context.getPos();
        EnumFacing direction = context.getPlacementHorizontalFacing();

        boolean hasOre = false;
        for (BlockBurnerMiningDrill.MinerDrillPart p : BlockBurnerMiningDrill.MinerDrillPart.values())
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

        for (BlockBurnerMiningDrill.MinerDrillPart p : BlockBurnerMiningDrill.MinerDrillPart.values())
        {
            if (!this.canPlaceAt(context, world, p.offsetOrigin(pos, direction.getOpposite()), state.with(BlockBurnerMiningDrill.PART, p)))
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

        for (BlockBurnerMiningDrill.MinerDrillPart p : BlockBurnerMiningDrill.MinerDrillPart.values())
        {
            if (!this.placePart(world, p.offsetOrigin(pos, direction.getOpposite()), state.with(BlockBurnerMiningDrill.PART, p)))
            {
                return false;
            }
        }
        return true;
    }
}