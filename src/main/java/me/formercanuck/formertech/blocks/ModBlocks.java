package me.formercanuck.formertech.blocks;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {
    ////////////////////////////// BLOCKS //////////////////////////////
    @ObjectHolder("formertech:copperblock")
    public static BaseBlock COPPERBLOCK = new BaseBlock("copperblock");

    @ObjectHolder("formertech:copperore")
    public static BaseOre COPPERORE = new BaseOre("copperore");

    @ObjectHolder("formertech:tinblock")
    public static BaseBlock TINBLOCK = new BaseBlock("tinblock");

    @ObjectHolder("formertech:tinore")
    public static BaseBlock TINORE = new BaseBlock("tinore");

    @ObjectHolder("formertech:silverblock")
    public static BaseBlock SILVERBLOCK = new BaseBlock("silverblock");

    @ObjectHolder("formertech:silverore")
    public static BaseOre SILVERORE = new BaseOre("silverore");

    @ObjectHolder("formertech:leadblock")
    public static BaseBlock LEADBLOCK = new BaseBlock("leadblock");

    @ObjectHolder("formertech:leadore")
    public static BaseOre LEADORE = new BaseOre("leadore");
    ////////////////////////////// BLOCKS //////////////////////////////

    ////////////////////////////// MACHINES //////////////////////////////
    @ObjectHolder("formertech:crusherblock")
    public static CrusherBlock CRUSHERBLOCK = new CrusherBlock();

    @ObjectHolder("formertech:crusherblock")
    public static TileEntityType<CrusherTile> CRUSHER_TILE;

    @ObjectHolder("formertech:crusherblock")
    public static ContainerType<CrusherContainer> CRUSHER_CONTAINER;
    ////////////////////////////// MACHINES //////////////////////////////
}
