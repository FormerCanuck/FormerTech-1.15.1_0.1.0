package me.formercanuck.formertech;

import me.formercanuck.formertech.blocks.ModBlocks;
import me.formercanuck.formertech.setup.ClientProxy;
import me.formercanuck.formertech.setup.IProxy;
import me.formercanuck.formertech.setup.ModSetup;
import me.formercanuck.formertech.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("formertech")
public class FormerTech {

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static ModSetup setup = new ModSetup();

    private static final Logger LOGGER = LogManager.getLogger();

    public FormerTech() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
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
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            registerBlockItem(ModBlocks.COPPERBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.COPPERORE, itemRegistryEvent);

            registerBlockItem(ModBlocks.TINBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.TINORE, itemRegistryEvent);

            registerBlockItem(ModBlocks.SILVERBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.SILVERORE, itemRegistryEvent);

            registerBlockItem(ModBlocks.LEADBLOCK, itemRegistryEvent);
            registerBlockItem(ModBlocks.LEADORE, itemRegistryEvent);

            registerBlockItem(ModBlocks.CRUSHERBLOCK, itemRegistryEvent);
        }

        private static void registerBlockItem(Block block, final RegistryEvent.Register<Item> itemRegister) {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);
            itemRegister.getRegistry().register(new BlockItem(block, properties).setRegistryName(block.getRegistryName()));
        }
    }
}
