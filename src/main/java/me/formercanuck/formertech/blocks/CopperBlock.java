package me.formercanuck.formertech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CopperBlock extends Block {
    public CopperBlock() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(2.0f)
                .lightValue(15));
        setRegistryName("copperblock");
    }

    /**
     @Override public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
     if (entityIn instanceof LivingEntity) {
     ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effect.get(1), 5000, 100));
     }
     }
     **/
}
