package com.ocelot.blocks;

import javax.annotation.Nullable;

import com.ocelot.items.ItemBurnerMiningDrill;
import com.ocelot.tileentity.OreOutcrop;
import com.ocelot.tileentity.TileEntityOreOutcrop;
import com.ocelot.util.EnumOreType;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockOreOutcrop extends ModBlock
{
	public static final IntegerProperty STONES = IntegerProperty.create("stones", 1, 8);

	private final EnumOreType ore;

	public BlockOreOutcrop(EnumOreType ore)
	{
		super(ore.getName() + "_ore_outcrop", Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(3.0F, 3.0F));
		this.setDefaultState(this.stateContainer.getBaseState().with(STONES, 1).with(WATERLOGGED, false));
		this.ore = ore;
	}

	private IBlockState updateStones(IBlockState state, IWorld world, BlockPos pos)
	{
		if (world.getTileEntity(pos) instanceof TileEntityOreOutcrop)
		{
			TileEntityOreOutcrop te = (TileEntityOreOutcrop) world.getTileEntity(pos);
			te.setOre(this.ore);
			return state.with(STONES, MathHelper.clamp(MathHelper.ceil((double) te.getOutcrop().getCount() / 800.0), 1, 8));
		}
		return state.with(STONES, 1);
	}

	/**
	 * Harvests the specified amount of ore from this ore.
	 * 
	 * @param world
	 *            The world the ore is in
	 * @param pos
	 *            The position of the ore
	 * @param count
	 *            The amount of ore to harvest
	 * @return The amount of ore that could not be harvested
	 */
	public int harvest(IWorld world, BlockPos pos, int count)
	{
		if (world.getTileEntity(pos) instanceof TileEntityOreOutcrop)
		{
			TileEntityOreOutcrop te = (TileEntityOreOutcrop) world.getTileEntity(pos);

			if (te.getOutcrop().getCount() - count <= 0)
			{
				int amountExtracted = te.getOutcrop().getCount();
				te.setCount(0);
				return amountExtracted;
			}
			else
			{
				te.setCount(te.getOutcrop().getCount() - count);
				return 0;
			}
		}
		return count;
	}

	@Override
	public boolean isReplaceable(IBlockState state, BlockItemUseContext useContext)
	{
		return useContext.getItem().getItem() instanceof ItemBurnerMiningDrill;
	}

	@Override
	public ToolType getHarvestTool(IBlockState state)
	{
		return ToolType.PICKAXE;
	}

	@Override
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, IBlockReader worldIn, BlockPos pos)
	{
		return 0.05f * ((1 + player.getDigSpeed(state, pos)) * 0.5f / this.ore.getMiningTime());
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest, IFluidState fluid)
	{
		if (!player.isCreative() && this.harvest(world, pos, 1) <= 0)
		{
			if (!world.isRemote)
			{
				world.playEvent(2001, pos, Block.getStateId(state));
			}
			spawnAsEntity(world, pos, new ItemStack(this.ore.getItemDropped()));
			return false;
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos, this.updateStones(state, world, pos));
	}

	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.updateStones(super.getStateForPlacement(context), context.getWorld(), context.getPos());
	}

	@Override
	public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
	{
		return this.updateStones(state, world, currentPos);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world)
	{
		return new TileEntityOreOutcrop();
	}

	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder)
	{
		builder.add(STONES, WATERLOGGED);
	}

	@Nullable
	public static EnumOreType getOre(IWorld world, BlockPos pos)
	{
		if (world.getBlockState(pos).getBlock() instanceof BlockOreOutcrop)
		{
			return ((BlockOreOutcrop) world.getBlockState(pos)).ore;
		}
		return null;
	}

	@Nullable
	public static OreOutcrop getOreOutcrop(IWorld world, BlockPos pos)
	{
		if (world.getTileEntity(pos) instanceof TileEntityOreOutcrop)
		{
			return ((TileEntityOreOutcrop) world.getTileEntity(pos)).getOutcrop();
		}
		return null;
	}
}