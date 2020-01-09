package me.formercanuck.formertech.blocks.generators;

import me.formercanuck.formertech.Config;
import me.formercanuck.formertech.blocks.ModBlocks;
import me.formercanuck.formertech.tools.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeHooks;
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
import java.util.concurrent.atomic.AtomicInteger;

public class FurnaceGeneratorTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler);
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);

    private boolean isActive = false;
    private int counter = 0;
    private int temp;

    public FurnaceGeneratorTile() {
        super(ModBlocks.FURNACEGENERATOR_TILE);
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }

        if (counter > 0) {
            counter--;
            if (counter <= 0) {
                handler.ifPresent(h -> {
                    ItemStack stack = h.getStackInSlot(0);
                    if (AbstractFurnaceTileEntity.isFuel(stack)) {//Config.FURNACEGENERATOR_GENERATE.get() *
                        energy.ifPresent(e -> ((CustomEnergyStorage) e).addEnergy(ForgeHooks.getBurnTime(stack)));
                    }
                });
            }
            markDirty();
        }

        if (counter <= 0) {
            handler.ifPresent(h -> {
                ItemStack stack = h.getStackInSlot(0);
                if (AbstractFurnaceTileEntity.isFuel(stack)) {
                    h.extractItem(0, 1, false);
                    counter = Config.FURNACEGENERATOR_TICKS.get();
                    markDirty();
                }
            });
        }

        BlockState blockState = world.getBlockState(pos);
        if (blockState.get(BlockStateProperties.POWERED) != counter > 0) {
            world.setBlockState(pos, blockState.with(BlockStateProperties.POWERED, counter > 0), 3);
        }

        sendOutPower();
    }

//    @Override
//    public void tick() {
//        if (counter < temp) {
//            counter++;
//            energy.ifPresent(e -> ((CustomEnergyStorage) e).addEnergy(Config.FURNACEGENERATOR_GENERATE.get()));
//            markDirty();
//        } else {
//            counter = 0;
//            isActive = false;
//        }
//
//        if (!isActive) {
//            handler.ifPresent(h -> {
//                ItemStack stack = h.getStackInSlot(0);
//                if (AbstractFurnaceTileEntity.isFuel(stack)) {
//                    temp = ForgeHooks.getBurnTime(stack);
//                    h.extractItem(0, 1, false);
//                    isActive = true;
//                }
//            });
//        }
//        sendOutPower();
//    }


    private void sendOutPower() {
        energy.ifPresent(energy -> {
            AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());
            if (capacity.get() > 0) {
                for (Direction direction : Direction.values()) {
                    TileEntity te = world.getTileEntity(pos.offset(direction));
                    if (te != null) {
                        boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                                    if (handler.canReceive()) {//Config.FIRSTBLOCK_SEND.get()
                                        int received = handler.receiveEnergy(Math.min(capacity.get(), Config.FURNACEGENERATOR_SEND.get()), false);
                                        capacity.addAndGet(-received);
                                        ((CustomEnergyStorage) energy).consumeEnergy(received);
                                        markDirty();
                                        return capacity.get() > 0;
                                    } else {
                                        return true;
                                    }
                                }
                        ).orElse(true);
                        if (!doContinue) {
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));

        CompoundNBT energyTag = tag.getCompound("energy");
        energy.ifPresent(e -> ((INBTSerializable<CompoundNBT>) e).deserializeNBT(energyTag));

        counter = tag.getInt("counter");
        temp = tag.getInt("temp");
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

        tag.putInt("counter", counter);
        tag.putInt("temp", temp);
        return super.write(tag);
    }

    private IItemHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return AbstractFurnaceTileEntity.isFuel(stack);
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!AbstractFurnaceTileEntity.isFuel(stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private IEnergyStorage createEnergy() {
        return new CustomEnergyStorage(Config.FURNACEGENERATOR_MAXPOWER.get(), 0);
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

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FurnaceGeneratorContainer(i, world, pos, playerInventory, playerEntity);
    }
}
