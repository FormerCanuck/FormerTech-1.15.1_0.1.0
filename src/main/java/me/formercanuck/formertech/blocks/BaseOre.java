package me.formercanuck.formertech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BaseOre extends OreBlock {

    public BaseOre(String regName) {
        super(Properties.create(Material.ROCK)
                .sound(SoundType.STONE)
                .hardnessAndResistance(2.0f));
        setRegistryName(regName);
    }
}
