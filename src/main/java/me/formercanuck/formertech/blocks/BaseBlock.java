package me.formercanuck.formertech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BaseBlock extends Block {

    public BaseBlock(String regName) {
        super(Block.Properties.create(Material.IRON)
                        .sound(SoundType.METAL)
                        .hardnessAndResistance(2.0f));
                setRegistryName(regName);
    }
}
