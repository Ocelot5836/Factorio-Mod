package com.ocelot.blocks;

import java.util.function.Function;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.ocelot.tileentity.TileEntityBurnerMiningDrill;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockBurnerMiningDrill extends ModBlock
{
	public static final EnumProperty<MinerDrillPart> PART = EnumProperty.<BlockBurnerMiningDrill.MinerDrillPart>create("part", MinerDrillPart.class);

	public BlockBurnerMiningDrill(String name)
	{
		super(name, Blocks.IRON_BLOCK);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world)
	{
		return new TileEntityBurnerMiningDrill();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder)
	{
		builder.add(FACING, PART);
	}

	public enum MinerDrillPart implements IStringSerializable
	{
		TOP_LEFT("top_left", pair ->
		{
			BlockPos pos = pair.getLeft();
			EnumFacing direction = pair.getRight().getOpposite();
			return pos.offset(direction).offset(direction.rotateYCCW());
		}), TOP_RIGHT("top_right", pair ->
		{
			BlockPos pos = pair.getLeft();
			EnumFacing direction = pair.getRight().getOpposite();
			return pos.offset(direction);
		}), BOTTOM_LEFT("bottom_left", pair ->
		{
			BlockPos pos = pair.getLeft();
			EnumFacing direction = pair.getRight().getOpposite();
			return pos.offset(direction.rotateYCCW());
		}), BOTTOM_RIGHT("bottom_right", pair ->
		{
			BlockPos pos = pair.getLeft();
			return new BlockPos(pos);
		});

		private String name;
		private Function<Pair<BlockPos, EnumFacing>, BlockPos> offset;

		private MinerDrillPart(String name, Function<Pair<BlockPos, EnumFacing>, BlockPos> offset)
		{
			this.name = name;
			this.offset = offset;
		}

		/**
		 * Offsets the position (assumed to be the bottom right) to the position of the part.
		 * 
		 * @param pos
		 *            The position of the origin
		 * @param direction
		 *            The direction to go, eg facing
		 * @return The new offset position
		 */
		public BlockPos offset(BlockPos pos, EnumFacing direction)
		{
			return this.offset.apply(new ImmutablePair<BlockPos, EnumFacing>(pos, direction));
		}

		@Override
		public String getName()
		{
			return name;
		}
	}
}