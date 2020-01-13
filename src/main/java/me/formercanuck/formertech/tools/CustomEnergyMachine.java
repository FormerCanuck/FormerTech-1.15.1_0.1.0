package me.formercanuck.formertech.tools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyMachine extends EnergyStorage implements INBTSerializable<CompoundNBT> {

    private TileEntity tileEntity;

    public CustomEnergyMachine(int capacity, int maxTransfer, TileEntity tileEntity) {
        super(capacity, maxTransfer);
        maxReceive = 100;
        this.tileEntity = tileEntity;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        tileEntity.markDirty();
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        tileEntity.markDirty();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
        tileEntity.markDirty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt("energy"));
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }
}