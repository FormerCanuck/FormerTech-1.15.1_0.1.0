package me.formercanuck.formertech.blocks.crushers;

import me.formercanuck.formertech.blocks.BaseOre;
import me.formercanuck.formertech.blocks.ModBlocks;
import me.formercanuck.formertech.items.BaseIngot;
import me.formercanuck.formertech.items.BaseOreDust;
import me.formercanuck.formertech.items.ModItems;
import me.formercanuck.formertech.tools.CustomEnergyMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrusherTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);

    public CrusherTile() {
        super(ModBlocks.CRUSHER_TILE);
    }

    private int cookTime = 20 * 30;
    private int timeCooked = 0;

    private int energyPerTick = 100;

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }

        energy.ifPresent(e -> {
            if (e.getEnergyStored() > energyPerTick) {
                handler.ifPresent(h -> {
                    ItemStack stack = h.getStackInSlot(0);
                    if (canCrush(Block.getBlockFromItem(stack.getItem()))) {
                        if (timeCooked < cookTime) {
                            timeCooked++;
                            ((CustomEnergyMachine) e).consumeEnergy(energyPerTick);
                            markDirty();
                            System.out.println(String.format("TimeCooked: %s, CookTime: %s", timeCooked, cookTime));
                            if (timeCooked == cookTime) {
                                h.extractItem(0, 1, false);
                                h.insertItem(1, getResult(Block.getBlockFromItem(stack.getItem())), false);
                                markDirty();
                                timeCooked = 0;
                            }
                        }
                    }
                });
            }
        });

        BlockState blockState = world.getBlockState(pos);
        if (blockState.get(BlockStateProperties.POWERED) != timeCooked > 0) {
            world.setBlockState(pos, blockState.with(BlockStateProperties.POWERED, timeCooked > 0), 3);
        }
        markDirty();
    }

    private boolean canCrush(Block block) {
        if (block instanceof BaseOre) {
            return true;
        }
        return false;
    }

    private ItemStack getResult(Block block) {
        if (block == ModBlocks.COPPERORE) {
            return new ItemStack(ModItems.COPPERDUST, 2);
        }
        if (block == ModBlocks.LEADORE) {
            return new ItemStack(ModItems.LEADDUST, 2);
        }
        if (block == ModBlocks.SILVERORE) {
            return new ItemStack(ModItems.SILVERDUST, 2);
        }
        if (block == ModBlocks.TINORE) {
            return new ItemStack(ModItems.TINDUST, 2);
        }
        return null;
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));

        CompoundNBT energyTag = tag.getCompound("energy");
        energy.ifPresent(e -> ((INBTSerializable<CompoundNBT>) e).deserializeNBT(energyTag));

        timeCooked = tag.getInt("timeCooked");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
        });

        energy.ifPresent(e -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) e).serializeNBT();
            tag.put("energy", compound);
        });

        tag.putInt("timeCooked", timeCooked);
        return super.write(tag);
    }

    private IItemHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0)
                    return Block.getBlockFromItem(stack.getItem()) instanceof BaseOre;
                if (slot == 1)
                    return stack.getItem() instanceof BaseOreDust;
                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack))
                    return stack;
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    private IEnergyStorage createEnergy() {
        return new CustomEnergyMachine(100000, 0);
    } // TODO: Add config options

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CrusherContainer(1, world, pos, playerInventory, playerEntity);
    }
}
