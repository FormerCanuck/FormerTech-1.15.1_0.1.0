package me.formercanuck.formertech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CrusherBlock extends Block {

    public CrusherBlock() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(2.0f));
        setRegistryName("crusherblock");
    }
}
