package com.ocelot.blocks;

import javax.annotation.Nullable;

import com.ocelot.blocks.part.MachinePart;
import com.ocelot.blocks.part.MachinePart222;
import com.ocelot.tileentity.TileEntityBurnerMiningDrill;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.EnumProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BlockStoneFurnace extends BlockFactorioMachine
{
    public BlockStoneFurnace(String name)
    {
        super(name, Blocks.COBBLESTONE);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(PART222, MachinePart222.BOTTOM_RIGHT_DOWN));
    }

    // @Override
    // public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    // {
    // BlockPos offsetPos = state.get(this.getPropertyPart()).offset(pos, state.get(FACING));
    // if (world.getTileEntity(offsetPos) instanceof TileEntityBurnerMiningDrill)
    // {
    // FactorioMod.getGuiOpener().openGui(FactorioMod.GUI_BURNER_MINING_DRILL_ID, player, world, offsetPos);
    // return true;
    // }
    // return false;
    // }

    @Override
    @Nullable
    public void breakPart(@Nullable IBlockState state, IWorld world, BlockPos pos, BlockPos originPos)
    {
        if (state == null)
            state = world.getBlockState(pos);

        if (state.getBlock() == this)
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world)
    {
        return new TileEntityBurnerMiningDrill();
    }

    @Override
    public MachinePart[] getParts()
    {
        return MachinePart222.values();
    }

    @Override
    public EnumProperty<? extends MachinePart> getPropertyPart()
    {
        return PART222;
    }

    @Override
    public IBlockState setPart(IBlockState state, MachinePart part, IWorld world, BlockPos pos)
    {
        if (part instanceof MachinePart222)
        {
            return state.with(PART222, (MachinePart222) part);
        }
        return state;
    }
}