package me.formercanuck.formertech.items;

import me.formercanuck.formertech.FormerTech;
import net.minecraft.item.Item;

public class BaseOreDust extends Item {

    public BaseOreDust(String regName) {
        super(new Properties().group(FormerTech.setup.itemGroup));
        setRegistryName(regName);
    }
}
