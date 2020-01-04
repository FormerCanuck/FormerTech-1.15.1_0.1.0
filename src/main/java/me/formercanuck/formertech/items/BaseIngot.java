package me.formercanuck.formertech.items;

import me.formercanuck.formertech.FormerTech;
import net.minecraft.item.Item;

public class BaseIngot extends Item {

    public BaseIngot(String regName) {
        super(new Item.Properties().group(FormerTech.setup.itemGroup));
        setRegistryName(regName);
    }
}
