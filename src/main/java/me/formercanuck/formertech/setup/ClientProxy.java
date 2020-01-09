package me.formercanuck.formertech.setup;

import me.formercanuck.formertech.blocks.ModBlocks;
import me.formercanuck.formertech.blocks.crushers.CrusherScreen;
import me.formercanuck.formertech.blocks.furnaces.PoweredFurnaceScreen;
import me.formercanuck.formertech.blocks.generators.FurnaceGeneratorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {
    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.CRUSHER_CONTAINER, CrusherScreen::new);
        ScreenManager.registerFactory(ModBlocks.POWEREDFURNACE_CONTAINER, PoweredFurnaceScreen::new);
        ScreenManager.registerFactory(ModBlocks.FURNACEGENERATOR_CONTAINER, FurnaceGeneratorScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
