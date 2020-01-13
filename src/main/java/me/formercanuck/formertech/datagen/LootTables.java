package me.formercanuck.formertech.datagen;

import me.formercanuck.formertech.blocks.ModBlocks;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlocks.CRUSHERBLOCK, createStandardTable("crusher", ModBlocks.CRUSHERBLOCK));
    }
}
