package me.formercanuck.formertech.setup;

import me.formercanuck.formertech.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup {

    public ItemGroup itemGroup = new ItemGroup("formertech") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.SILVERBLOCK);
        }
    };

    public void init() {

    }
}
