package me.formercanuck.formertech;

import me.formercanuck.formertech.blocks.ModBlocks;
import me.formercanuck.formertech.blocks.crushers.CrusherContainer;
import me.formercanuck.formertech.blocks.crushers.CrusherTile;
import me.formercanuck.formertech.blocks.furnaces.PoweredFurnaceContainer;
import me.formercanuck.formertech.blocks.furnaces.PoweredFurnaceTile;
import me.formercanuck.formertech.blocks.generators.FurnaceGeneratorContainer;
import me.formercanuck.formertech.blocks.generators.FurnaceGeneratorTile;
import me.formercanuck.formertech.items.ModItems;
import me.formercanuck.formertech.setup.ClientProxy;
import me.formercanuck.formertech.setup.IProxy;
import me.formercanuck.formertech.setup.ModSetup;
import me.formercanuck.formertech.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("formertech")
public class FormerTech {

    public static final String MODID = "formertech";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static ModSetup setup = new ModSetup();

    private static final Logger LOGGER = LogManager.getLogger();

    public FormerTech() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("formertech-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("formertech-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
        setup.init();
        proxy.init();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            blockRegistryEvent.getRegistry().register(ModBlocks.COPPERBLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.COPPERORE);

            blockRegistryEvent.getRegistry().register(ModBlocks.TINBLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.TINORE);

            blockRegistryEvent.getRegistry().register(ModBlocks.SILVERBLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.SILVERORE);

            blockRegistryEvent.getRegistry().register(ModBlocks.LEADBLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.LEADORE);

            blockRegistryEvent.getRegistry().register(ModBlocks.CRUSHERBLOCK);
            blockRegistryEvent.getRegistry().register(ModBlocks.POWEREDFURNACE);
            blockRegistryEvent.getRegistry().register(ModBlocks.FURNACEGENERATORBLOCK);
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            registerBlockItem(ModBlocks.COPPERBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.COPPERORE, itemRegistryEvent);
            itemRegistryEvent.getRegistry().register(ModItems.COPPERINGOT);

            registerBlockItem(ModBlocks.TINBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.TINORE, itemRegistryEvent);
            itemRegistryEvent.getRegistry().register(ModItems.TININGOT);

            registerBlockItem(ModBlocks.SILVERBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.SILVERORE, itemRegistryEvent);

            registerBlockItem(ModBlocks.LEADBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.LEADORE, itemRegistryEvent);

            registerBlockItem(ModBlocks.CRUSHERBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.POWEREDFURNACE, itemRegistryEvent);
            registerBlockItem(ModBlocks.FURNACEGENERATORBLOCK, itemRegistryEvent);
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().register(TileEntityType.Builder.create(CrusherTile::new, ModBlocks.CRUSHERBLOCK).build(null).setRegistryName("crusherblock"));
            event.getRegistry().register(TileEntityType.Builder.create(PoweredFurnaceTile::new, ModBlocks.POWEREDFURNACE).build(null).setRegistryName("poweredfurnace"));
            event.getRegistry().register(TileEntityType.Builder.create(FurnaceGeneratorTile::new, ModBlocks.FURNACEGENERATORBLOCK).build(null).setRegistryName("furnacegenerator"));
        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new CrusherContainer(windowId, FormerTech.proxy.getClientWorld(), pos, inv, FormerTech.proxy.getClientPlayer());
            }).setRegistryName("crusherblock"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new PoweredFurnaceContainer(windowId, FormerTech.proxy.getClientWorld(), pos, inv, FormerTech.proxy.getClientPlayer());
            }).setRegistryName("poweredfurnace"));

            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new FurnaceGeneratorContainer(windowId, FormerTech.proxy.getClientWorld(), pos, inv, FormerTech.proxy.getClientPlayer());
            }).setRegistryName("furnacegenerator"));
        }

        private static void registerBlockItem(Block block, final RegistryEvent.Register<Item> itemRegister) {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);
            itemRegister.getRegistry().register(new BlockItem(block, properties).setRegistryName(block.getRegistryName()));
        }
    }
}
