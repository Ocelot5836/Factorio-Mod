package com.ocelot.blocks;

import javax.annotation.Nullable;

import com.ocelot.FactorioMod;
import com.ocelot.util.InventoryUtils;

import net.minecraft.block.Block;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ModBlock extends Block implements IBucketPickupHandler, ILiquidContainer
{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ModBlock(Block.Properties properties)
    {
        super(properties);
    }

    public ModBlock(String name, Block.Properties properties)
    {
        super(properties);
        this.setRegistryName(new ResourceLocation(FactorioMod.MOD_ID, name));
    }

    public ModBlock(Block parent)
    {
        super(Block.Properties.from(parent));
    }

    public ModBlock(String name, Block parent)
    {
        super(Block.Properties.from(parent));
        this.setRegistryName(new ResourceLocation(FactorioMod.MOD_ID, name));
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (te instanceof INameable && ((INameable) te).hasCustomName())
        {
            player.addStat(StatList.BLOCK_MINED.get(this));
            player.addExhaustion(0.005F);
            if (!world.isRemote)
            {
                int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
                Item item = this.getItemDropped(state, world, pos, i).asItem();
                if (item != Items.AIR)
                {
                    ItemStack itemstack = new ItemStack(item, this.getItemsToDropCount(state, i, world, pos, RANDOM));
                    itemstack.setDisplayName(((INameable) te).getCustomName());
                    spawnAsEntity(world, pos, itemstack);
                }
            }
        }
        else
        {
            super.harvestBlock(world, player, pos, state, (TileEntity) null, stack);
        }
    }

    @Override
    @Deprecated
    public void onReplaced(IBlockState state, World world, BlockPos pos, IBlockState newState, boolean isMoving)
    {
        if (state.getBlock() != newState.getBlock())
        {
            InventoryUtils.dropItems(world.getTileEntity(pos));
            world.updateComparatorOutputLevel(pos, this);
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public IBlockState rotate(IBlockState state, Rotation rot)
    {
        return state.has(FACING) ? state.with(FACING, rot.rotate(state.get(FACING))) : state;
    }

    @Override
    @Deprecated
    public IBlockState mirror(IBlockState state, Mirror mirror)
    {
        return state.has(FACING) ? state.rotate(mirror.toRotation(state.get(FACING))) : state;
    }

    @Override
    public Fluid pickupFluid(IWorld worldIn, BlockPos pos, IBlockState state)
    {
        if (state.has(WATERLOGGED) && state.get(WATERLOGGED))
        {
            worldIn.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        }
        else
        {
            return Fluids.EMPTY;
        }
    }

    @Override
    public IFluidState getFluidState(IBlockState state)
    {
        return state.has(WATERLOGGED) && state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public boolean canContainFluid(IBlockReader world, BlockPos pos, IBlockState state, Fluid fluid)
    {
        return state.has(WATERLOGGED) && !state.get(WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    public boolean receiveFluid(IWorld world, BlockPos pos, IBlockState state, IFluidState fluidState)
    {
        if (state.has(WATERLOGGED) && !state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER)
        {
            if (!world.isRemote())
            {
                world.setBlockState(pos, state.with(WATERLOGGED, true), 3);
                world.getPendingFluidTicks().scheduleTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
    {
        if (state.has(WATERLOGGED) && state.get(WATERLOGGED))
        {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return state;
    }

    @Nullable
    public IBlockState getStateForPlacement(BlockItemUseContext context)
    {
        IBlockState state = super.getStateForPlacement(context);
        if (state.has(WATERLOGGED))
        {
            IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
            state = state.with(WATERLOGGED, ifluidstate == Fluids.WATER);
        }
        return state;
    }
}