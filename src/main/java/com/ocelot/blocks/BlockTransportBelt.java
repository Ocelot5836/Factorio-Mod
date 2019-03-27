package com.ocelot.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BlockTransportBelt extends ModBlock
{
	public static final EnumProperty<Shape> SHAPE = EnumProperty.<BlockTransportBelt.Shape>create("shape", Shape.class);

	private final Type type;

	public BlockTransportBelt(String name, Type type)
	{
		super(name, Blocks.IRON_BLOCK);
		this.type = type;
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(SHAPE, Shape.STRAIT).with(WATERLOGGED, false));
	}

	private Shape getBeltShape(IWorld world, BlockPos pos, EnumFacing facing)
	{
		IBlockState up = world.getBlockState(pos.offset(facing).up());
		IBlockState left = world.getBlockState(pos.offset(facing.rotateYCCW()));
		IBlockState right = world.getBlockState(pos.offset(facing.rotateY()));
		IBlockState back = world.getBlockState(pos.offset(facing.getOpposite()));

		boolean upInsert = up.getBlock() instanceof BlockTransportBelt ? up.get(FACING) == facing : false;
		boolean leftInsert = left.getBlock() instanceof BlockTransportBelt ? left.get(FACING) == facing.rotateY() : false;
		boolean rightInsert = right.getBlock() instanceof BlockTransportBelt ? right.get(FACING) == facing.rotateYCCW() : false;
		boolean backInsert = back.getBlock() instanceof BlockTransportBelt ? back.get(FACING) == facing : false;

		// System.out.println(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "=" + leftInsert + "," + rightInsert + "," + backInsert);

		if (!backInsert && !(leftInsert && rightInsert))
		{
			if (leftInsert)
			{
				return Shape.BEND_LEFT;
			}
			if (rightInsert)
			{
				return Shape.BEND_RIGHT;
			}
		}

		return upInsert ? Shape.STRAIT_UP : Shape.STRAIT;
	}

	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context)
	{
		EnumFacing facing = context.getPlacementHorizontalFacing();
		return super.getStateForPlacement(context).with(FACING, facing).with(SHAPE, this.getBeltShape(context.getWorld(), context.getPos(), facing));
	}

	@Override
	public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
	{
		return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos).with(SHAPE, this.getBeltShape(world, currentPos, world.getBlockState(currentPos).get(FACING)));
	}

	@Override
	public void onNeighborChange(IBlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor)
	{
		System.out.println(state.get(SHAPE));
	}

	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder)
	{
		builder.add(FACING, SHAPE, WATERLOGGED);
	}

	public Type getType()
	{
		return type;
	}

	public static enum Type implements IStringSerializable
	{
		YELLOW("yellow"), RED("red"), BLUE("blue");

		private String name;

		private Type(String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return name;
		}
	}

	public static enum Shape implements IStringSerializable
	{
		STRAIT("strait"), STRAIT_UP("strait_up"), BEND_LEFT("bend_left"), BEND_RIGHT("bend_right");

		private String name;

		private Shape(String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return name;
		}
	}
}