package com.ocelot.blocks.part;

import java.util.function.Function;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public enum MachinePart222 implements MachinePart
{
    BOTTOM_RIGHT_DOWN("bottom_right_down", pair ->
    {
        BlockPos pos = pair.getLeft();
        return new BlockPos(pos);
    }, pair ->
    {
        BlockPos pos = pair.getLeft();
        return new BlockPos(pos);
    }),

    BOTTOM_LEFT_DOWN("bottom_left_down", pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight().getOpposite();
        return pos.offset(direction.rotateY());
    }, pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight().getOpposite();
        return pos.offset(direction.rotateYCCW());
    }),

    TOP_RIGHT_DOWN("top_right_down", pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight();
        return pos.offset(direction);
    }, pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight();
        return pos.offset(direction.getOpposite());
    }),

    TOP_LEFT_DOWN("top_left_down", pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight();
        return pos.offset(direction).offset(direction.rotateYCCW());
    }, pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight();
        return pos.offset(direction.getOpposite()).offset(direction.rotateY());
    }),

    BOTTOM_RIGHT_UP("bottom_right_up", pair ->
    {
        BlockPos pos = pair.getLeft();
        return pos.down();
    }, pair ->
    {
        BlockPos pos = pair.getLeft();
        return pos.up();
    }),

    BOTTOM_LEFT_UP("bottom_left_up", pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight().getOpposite();
        return pos.offset(direction.rotateY()).down();
    }, pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight().getOpposite();
        return pos.offset(direction.rotateYCCW()).up();
    }),

    TOP_RIGHT_UP("top_right_up", pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight();
        return pos.offset(direction).down();
    }, pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight();
        return pos.offset(direction.getOpposite()).up();
    }),

    TOP_LEFT_UP("top_left_up", pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight();
        return pos.offset(direction).offset(direction.rotateYCCW()).down();
    }, pair ->
    {
        BlockPos pos = pair.getLeft();
        EnumFacing direction = pair.getRight();
        return pos.offset(direction.getOpposite()).offset(direction.rotateY()).up();
    });

    private String name;
    private Function<Pair<BlockPos, EnumFacing>, BlockPos> originOffset;
    private Function<Pair<BlockPos, EnumFacing>, BlockPos> currentOffset;

    private MachinePart222(String name, Function<Pair<BlockPos, EnumFacing>, BlockPos> offset, Function<Pair<BlockPos, EnumFacing>, BlockPos> currentOffset)
    {
        this.name = name;
        this.originOffset = offset;
        this.currentOffset = currentOffset;
    }

    @Override
    public BlockPos offset(BlockPos pos, EnumFacing direction)
    {
        return this.originOffset.apply(new ImmutablePair<BlockPos, EnumFacing>(pos, direction));
    }

    @Override
    public BlockPos offsetOrigin(BlockPos pos, EnumFacing direction)
    {
        return this.currentOffset.apply(new ImmutablePair<BlockPos, EnumFacing>(pos, direction));
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isBottom()
    {
        return this == TOP_LEFT_DOWN || this == TOP_RIGHT_DOWN || this == BOTTOM_LEFT_DOWN || this == BOTTOM_RIGHT_DOWN;
    }

    @Override
    public boolean isBase()
    {
        return this == BOTTOM_RIGHT_DOWN;
    }
}