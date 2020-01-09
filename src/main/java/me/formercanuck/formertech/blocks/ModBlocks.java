package me.formercanuck.formertech.blocks;

import me.formercanuck.formertech.blocks.crushers.CrusherBlock;
import me.formercanuck.formertech.blocks.crushers.CrusherContainer;
import me.formercanuck.formertech.blocks.crushers.CrusherTile;
import me.formercanuck.formertech.blocks.furnaces.PoweredFurnaceBlock;
import me.formercanuck.formertech.blocks.furnaces.PoweredFurnaceContainer;
import me.formercanuck.formertech.blocks.furnaces.PoweredFurnaceTile;
import me.formercanuck.formertech.blocks.generators.FurnaceGeneratorBlock;
import me.formercanuck.formertech.blocks.generators.FurnaceGeneratorContainer;
import me.formercanuck.formertech.blocks.generators.FurnaceGeneratorTile;
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

    @ObjectHolder("formertech:poweredfurnace")
    public static PoweredFurnaceBlock POWEREDFURNACE = new PoweredFurnaceBlock();

    @ObjectHolder("formertech:poweredfurnace")
    public static TileEntityType<PoweredFurnaceTile> POWEREDFURNACE_TILE;

    @ObjectHolder("formertech:poweredfurnace")
    public static ContainerType<PoweredFurnaceContainer> POWEREDFURNACE_CONTAINER;

    @ObjectHolder("formertech:furnacegenerator")
    public static FurnaceGeneratorBlock FURNACEGENERATORBLOCK = new FurnaceGeneratorBlock();

    @ObjectHolder("formertech:furnacegenerator")
    public static TileEntityType<FurnaceGeneratorTile> FURNACEGENERATOR_TILE;

    @ObjectHolder("formertech:furnacegenerator")
    public static ContainerType<FurnaceGeneratorContainer> FURNACEGENERATOR_CONTAINER;
    ////////////////////////////// MACHINES //////////////////////////////
}
