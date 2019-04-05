package com.ocelot.tileentity;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import com.ocelot.blocks.BlockBurnerMiningDrill;
import com.ocelot.blocks.part.MachinePart222;
import com.ocelot.init.ModTileEntities;
import com.ocelot.network.MessagePlayBurnerMiningDrillSound;
import com.ocelot.network.NetworkHandler;
import com.ocelot.recipe.FactorioFuels;
import com.ocelot.util.EnumOreType;
import com.ocelot.util.ISimpleInventory;
import com.ocelot.util.InventoryUtils;
import com.ocelot.util.MiningDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBurnerMiningDrill extends ModTileEntity implements MiningDrill<MachinePart222>, ISimpleInventory
{
    private ItemStackHandler inventory;
    private float joules;
    private float maxJoules;
    private int miningProgress;
    private Map<MachinePart222, OreOutcrop> coveredOres;
    private boolean running;

    private Map<Item, Float> joulesMap;

    public TileEntityBurnerMiningDrill()
    {
        super(ModTileEntities.TILE_ENTITY_BURNER_MINING_DRILL);
        this.inventory = new ItemStackHandler(1)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                joulesMap = FactorioFuels.getJouleAmounts();
                super.onContentsChanged(slot);
            }
        };
        this.joules = 0;
        this.maxJoules = 0;
        this.miningProgress = 0;
        this.coveredOres = new ConcurrentHashMap<MachinePart222, OreOutcrop>();
        this.running = false;

        this.joulesMap = FactorioFuels.getJouleAmounts();
    }

    private boolean canOutput()
    {
        EnumFacing facing = this.getBlockState().get(BlockBurnerMiningDrill.FACING);
        World world = this.getWorld();
        BlockPos pos = this.getPos();
        BlockPos containerPos = pos.offset(facing);

        if (world.getTileEntity(containerPos) != null)
        {
            IItemHandler container = world.getTileEntity(containerPos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
            if (container != null)
            {
                return !InventoryUtils.isFull(container);
            }
        }

        return true;
    }

    private void output(EnumOreType ore, int amount)
    {
        if (ore == null)
            return;

        EnumFacing facing = this.getBlockState().get(BlockBurnerMiningDrill.FACING);
        World world = this.getWorld();
        BlockPos pos = this.getPos();
        BlockPos containerPos = pos.offset(facing);

        if (world.getTileEntity(containerPos) != null)
        {
            IItemHandler container = world.getTileEntity(containerPos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
            if (container != null && !InventoryUtils.isFull(container))
            {
                InventoryUtils.insertStack(container, new ItemStack(ore.getItemDropped(), amount), false);
                return;
            }
        }

        InventoryHelper.spawnItemStack(world, containerPos.getX(), containerPos.getY(), containerPos.getZ(), new ItemStack(ore.getItemDropped(), amount));
    }

    @Override
    public void tick()
    {
        if (this.hasWorld() && !this.getWorld().isRemote())
        {
            boolean canRun = true;

            if (!this.coveredOres.isEmpty())
            {
                if (this.joules < this.getEnergyConsumption() / 20)
                {
                    canRun = false;
                    if (this.joulesMap != null)
                    {
                        ItemStack stack = this.inventory.getStackInSlot(0);
                        if (!stack.isEmpty() && this.joulesMap.get(stack.getItem()) > 0)
                        {
                            if (stack.hasContainerItem())
                            {
                                this.inventory.setStackInSlot(0, stack.getContainerItem());
                            }
                            else if (!stack.isEmpty())
                            {
                                this.inventory.extractItem(0, 1, false);
                                if (stack.isEmpty())
                                {
                                    Item containerItem = stack.getItem().getContainerItem();
                                    this.inventory.setStackInSlot(0, containerItem == null ? ItemStack.EMPTY : new ItemStack(containerItem));
                                }
                            }

                            this.joules = this.joulesMap.get(stack.getItem());
                            this.maxJoules = this.joules;
                            this.markDirty();
                            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
                            canRun = true;
                        }
                    }
                }
                else
                {
                    MachinePart222 part = null;
                    OreOutcrop outcrop = null;

                    int index = this.getWorld().rand.nextInt(this.coveredOres.size());
                    int i = 0;
                    for (MachinePart222 key : this.coveredOres.keySet())
                    {
                        if (index == i)
                        {
                            part = key;
                            outcrop = this.coveredOres.get(key);
                            break;
                        }
                        i++;
                    }

                    EnumOreType ore = outcrop.getOre();
                    if (ore != null && outcrop.getCount() > 0)
                    {
                        if (this.miningProgress < this.getMaxMiningProgress())
                        {
                            this.miningProgress++;
                            this.joules -= this.getEnergyConsumption() / 20;
                            this.markDirty();
                            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
                            canRun = true;
                        }
                        else if (this.canOutput())
                        {
                            outcrop.setCount(outcrop.getCount() - 1);
                            if (outcrop.getCount() <= 0)
                            {
                                this.coveredOres.remove(part);
                            }

                            this.output(outcrop.getOre(), 1);
                            this.miningProgress = 0;
                            this.markDirty();
                            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
                        }
                        else
                        {
                            canRun = false;
                        }
                    }
                    else
                    {
                        this.coveredOres.remove(part);
                    }
                }
            }

            if (this.running != canRun)
            {
                this.running = canRun;
                for (EntityPlayer player : this.getWorld().playerEntities)
                {
                    if (player instanceof EntityPlayerMP)
                    {
                        NetworkHandler.INSTANCE.sendTo(new MessagePlayBurnerMiningDrillSound(this.pos, canRun), ((EntityPlayerMP) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                    }
                }
            }
        }
    }

    @Override
    public void read(NBTTagCompound nbt)
    {
        super.read(nbt);

        this.inventory.deserializeNBT(nbt.getCompound("inventory"));
        this.joules = nbt.getFloat("energy");
        this.maxJoules = nbt.getFloat("maxEnergy");
        this.miningProgress = nbt.getInt("miningProgress");

        if (nbt.contains("coveredOres", Constants.NBT.TAG_COMPOUND))
        {
            NBTTagCompound coveredOresNbt = nbt.getCompound("coveredOres");
            for (MachinePart222 part : MachinePart222.values())
            {
                if (part.isBottom() && coveredOresNbt.contains(part.getName(), Constants.NBT.TAG_COMPOUND))
                {
                    this.coveredOres.put(part, new OreOutcrop(coveredOresNbt.getCompound(part.getName())));
                }
            }
        }
    }

    @Override
    public NBTTagCompound write(NBTTagCompound nbt)
    {
        super.write(nbt);

        nbt.setTag("inventory", this.inventory.serializeNBT());
        nbt.setFloat("energy", this.joules);
        nbt.setFloat("maxEnergy", this.maxJoules);
        nbt.setInt("miningProgress", this.miningProgress);

        NBTTagCompound coveredOresNbt = new NBTTagCompound();
        for (Entry<MachinePart222, OreOutcrop> entry : this.coveredOres.entrySet())
        {
            MachinePart222 part = entry.getKey();
            if (part.isBottom())
            {
                coveredOresNbt.setTag(part.getName(), entry.getValue().serializeNBT());
            }
        }
        nbt.setTag("coveredOres", coveredOresNbt);

        return nbt;
    }

    @Override
    public void remove()
    {
        super.remove();
        for (EntityPlayer player : this.getWorld().playerEntities)
        {
            if (player instanceof EntityPlayerMP)
            {
                NetworkHandler.INSTANCE.sendTo(new MessagePlayBurnerMiningDrillSound(this.pos, false), ((EntityPlayerMP) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        }
    }

    @Override
    public int getEnergyConsumption()
    {
        return 150000;
    }

    @Override
    public float getMiningTime()
    {
        return 0.3f;
    }

    @Override
    public float getMiningSpeed()
    {
        return 0.25f;
    }

    @Override
    public int getPollution()
    {
        return 12;
    }

    @Override
    public boolean isRunning()
    {
        return running;
    }

    public IItemHandler getInventory()
    {
        return inventory;
    }

    @Override
    public int getSlots()
    {
        return this.inventory.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return this.inventory.getStackInSlot(slot);
    }

    public float getJoules()
    {
        return joules;
    }

    public float getMaxJoules()
    {
        return maxJoules;
    }

    @Override
    public int getMiningProgress()
    {
        return miningProgress;
    }

    @Override
    public int getMaxMiningProgress()
    {
        return (int) Math.ceil(1f / this.getMiningSpeed()) * 20;
    }

    @Override
    @Nullable
    public OreOutcrop getOutcrop(MachinePart222 part)
    {
        return part.isBottom() ? this.coveredOres.get(part) : null;
    }

    @Override
    public void setOutcrop(MachinePart222 part, @Nullable OreOutcrop ore)
    {
        if (part.isBottom())
        {
            if (ore != null)
            {
                this.coveredOres.put(part, ore);
            }
            else
            {
                this.coveredOres.remove(part);
            }
        }
    }
}