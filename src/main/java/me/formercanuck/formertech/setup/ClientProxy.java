package me.formercanuck.formertech.setup;

import me.formercanuck.formertech.blocks.CrusherScreen;
import me.formercanuck.formertech.blocks.FurnaceGeneratorScreen;
import me.formercanuck.formertech.blocks.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {
    @Override
    public void init() {
        ScreenManager.registerFactory(ModBlocks.CRUSHER_CONTAINER, CrusherScreen::new);
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
