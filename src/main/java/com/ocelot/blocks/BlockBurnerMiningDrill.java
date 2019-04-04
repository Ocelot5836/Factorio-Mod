package com.ocelot.blocks;

import java.util.function.Function;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.ocelot.FactorioMod;
import com.ocelot.tileentity.OreOutcrop;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;
import com.ocelot.tileentity.TileEntityOreOutcrop;
import com.ocelot.util.BlockFactorioInventory;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockBurnerMiningDrill extends ModBlock implements BlockFactorioInventory
{
	public static final EnumProperty<MinerDrillPart> PART = EnumProperty.<BlockBurnerMiningDrill.MinerDrillPart>create("part", MinerDrillPart.class);

	public BlockBurnerMiningDrill(String name)
	{
		super(name, Blocks.IRON_BLOCK);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(PART, BlockBurnerMiningDrill.MinerDrillPart.BOTTOM_RIGHT_DOWN));
	}

	private void breakPart(@Nullable IBlockState state, World world, BlockPos pos, @Nullable TileEntityBurnerMiningDrill burnerMiningDrill)
	{
		if (state == null)
			state = world.getBlockState(pos);

		if (state.getBlock() instanceof BlockBurnerMiningDrill)
		{
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);

			if (burnerMiningDrill != null)
			{
				OreOutcrop outcrop = burnerMiningDrill.getOutcrop(state.get(PART));
				if (outcrop != null && outcrop.getOre() != null && outcrop.getCount() > 0)
				{
					world.setBlockState(pos, outcrop.getOre().getState(), 3);
					if (world.getTileEntity(pos) instanceof TileEntityOreOutcrop)
					{
						TileEntityOreOutcrop te = (TileEntityOreOutcrop) world.getTileEntity(pos);
						te.setOre(outcrop.getOre()).setCount(outcrop.getCount());
					}
				}
			}
		}
	}

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        BlockPos offsetPos = state.get(PART).offset(pos, state.get(FACING));
        if(world.getTileEntity(offsetPos) instanceof TileEntityBurnerMiningDrill) {
            FactorioMod.getGuiOpener().openGui(FactorioMod.GUI_BURNER_MINING_DRILL_ID, player, world, offsetPos);
            return true;
        }
        return false;
    }

	@Override
	@Deprecated
	public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean isMoving)
	{
		BlockBurnerMiningDrill.MinerDrillPart part = state.get(PART);
		EnumFacing facing = state.get(FACING);
		BlockPos offsetPos = part.offset(pos, facing);

		TileEntityBurnerMiningDrill burnerMiningDrill = null;
		if (world.getTileEntity(offsetPos) instanceof TileEntityBurnerMiningDrill)
		{
			burnerMiningDrill = (TileEntityBurnerMiningDrill) world.getTileEntity(offsetPos);
		}

		if (part != MinerDrillPart.BOTTOM_RIGHT_DOWN)
		{
			this.breakPart(state, world, pos, burnerMiningDrill);
			this.breakPart(null, world, offsetPos, burnerMiningDrill);
			return;
		}

		for (BlockBurnerMiningDrill.MinerDrillPart p : BlockBurnerMiningDrill.MinerDrillPart.values())
		{
			this.breakPart(null, world, p.offsetOrigin(pos, facing), burnerMiningDrill);
		}

		super.onReplaced(state, world, pos, newState, isMoving);
		this.breakPart(state, world, pos, burnerMiningDrill);
	}

	@Nullable
	public IBlockState getStateForPlacement(BlockItemUseContext context)
	{
		return super.getStateForPlacement(context).with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return state.get(PART) == MinerDrillPart.BOTTOM_RIGHT_DOWN;
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

		private MinerDrillPart(String name, Function<Pair<BlockPos, EnumFacing>, BlockPos> offset, Function<Pair<BlockPos, EnumFacing>, BlockPos> currentOffset)
		{
			this.name = name;
			this.originOffset = offset;
			this.currentOffset = currentOffset;
		}

		/**
		 * Offsets the position to the origin.
		 * 
		 * @param pos
		 *            The position of the pos
		 * @param direction
		 *            The direction to go, eg block facing
		 * @return The new offset position
		 */
		public BlockPos offset(BlockPos pos, EnumFacing direction)
		{
			return this.originOffset.apply(new ImmutablePair<BlockPos, EnumFacing>(pos, direction));
		}

		/**
		 * Offsets the position from the origin to this position.
		 * 
		 * @param pos
		 *            The position of the pos
		 * @param direction
		 *            The direction to go, eg block facing
		 * @return The new offset position
		 */
		public BlockPos offsetOrigin(BlockPos pos, EnumFacing direction)
		{
			return this.currentOffset.apply(new ImmutablePair<BlockPos, EnumFacing>(pos, direction));
		}

		@Override
		public String getName()
		{
			return name;
		}

		/**
		 * @return Whether or not this is a bottom part
		 */
		public boolean isBottom()
		{
			return this == TOP_LEFT_DOWN || this == TOP_RIGHT_DOWN || this == BOTTOM_LEFT_DOWN || this == BOTTOM_RIGHT_DOWN;
		}

		/**
		 * @return Whether or not this is a top part
		 */
		public boolean isTop()
		{
			return !this.isBottom();
		}
	}

	@Override
	public boolean canReceive(ItemStack stack, IWorld world, BlockPos pos, EnumFacing face, BlockFactorioInventory otherInventory)
	{
		return false;
	}

	@Override
	public ItemStack receive(ItemStack stack, IWorld world, BlockPos pos, EnumFacing face, BlockFactorioInventory otherInventory)
	{
		return stack;
	}
}