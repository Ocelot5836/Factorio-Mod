package com.ocelot.blocks;

import javax.annotation.Nullable;

import com.ocelot.blocks.part.MachinePart;
import com.ocelot.blocks.part.MachinePart222;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class BlockFactorioMachine extends ModBlock
{
    public static final EnumProperty<MachinePart222> PART222 = EnumProperty.<MachinePart222>create("part", MachinePart222.class);

    public BlockFactorioMachine(Properties properties)
    {
        super(properties);
    }

    public BlockFactorioMachine(String name, Properties properties)
    {
        super(name, properties);
    }

    public BlockFactorioMachine(Block parent)
    {
        super(parent);
    }

    public BlockFactorioMachine(String name, Block parent)
    {
        super(name, parent);
    }

    @Nullable
    public IBlockState getStateForPlacement(BlockItemUseContext context)
    {
        return super.getStateForPlacement(context).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    @Deprecated
    public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean isMoving)
    {
        MachinePart part = state.get(this.getPropertyPart());
        EnumFacing facing = state.get(FACING);
        BlockPos offsetPos = part.offset(pos, facing);

        if (!part.isBase())
        {
            this.breakPart(state, world, pos, offsetPos);
            this.breakPart(null, world, offsetPos, offsetPos);
            return;
        }

        for (MachinePart p : this.getParts())
        {
            this.breakPart(null, world, p.offsetOrigin(pos, facing), offsetPos);
        }

        super.onReplaced(state, world, pos, newState, isMoving);
        this.breakPart(state, world, pos, offsetPos);
    }

    @Override
    protected void fillStateContainer(Builder<Block, IBlockState> builder)
    {
        builder.add(FACING, this.getPropertyPart());
    }

    protected abstract void breakPart(@Nullable IBlockState state, IWorld world, BlockPos pos, BlockPos originPos);

    public abstract IBlockState setPart(IBlockState state, MachinePart part, IWorld world, BlockPos pos);

    public abstract MachinePart[] getParts();

    public abstract EnumProperty<? extends MachinePart> getPropertyPart();

}