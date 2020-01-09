package me.formercanuck.formertech;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_POWER = "power";
    public static final String SUBCATEGORY_FURNACEGENERATOR = "furnacegenerator";

    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.IntValue FURNACEGENERATOR_MAXPOWER;
    public static ForgeConfigSpec.IntValue FURNACEGENERATOR_GENERATE;
    public static ForgeConfigSpec.IntValue FURNACEGENERATOR_SEND;
    public static ForgeConfigSpec.IntValue FURNACEGENERATOR_TICKS;

    static {
        COMMON_BUILDER.comment("General Settings").push(CATEGORY_GENERAL);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Power Settings").push(CATEGORY_POWER);
        setupFurnaceGeneratorConfig();
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFurnaceGeneratorConfig() {
        COMMON_BUILDER.comment("Furnace Generator settings").push(SUBCATEGORY_FURNACEGENERATOR);
        FURNACEGENERATOR_MAXPOWER = COMMON_BUILDER.comment("Maximum power for the furnace generator.")
                .defineInRange("maxPower", 100000, 0, Integer.MAX_VALUE);
        FURNACEGENERATOR_GENERATE = COMMON_BUILDER.comment("Power generated per tick.")
                .defineInRange("generate", 1, 0, Integer.MAX_VALUE);
        FURNACEGENERATOR_SEND = COMMON_BUILDER.comment("Max amount of power to send out.")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);
        FURNACEGENERATOR_TICKS = COMMON_BUILDER.comment("Ticks per fuel.")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }
}
