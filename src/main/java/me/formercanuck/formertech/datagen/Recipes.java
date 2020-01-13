package me.formercanuck.formertech.datagen;

import me.formercanuck.formertech.blocks.ModBlocks;
import me.formercanuck.formertech.items.ModItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.COPPERBLOCK)
                .patternLine("iii")
                .patternLine("iii")
                .patternLine("iii")
                .key('i', ModItems.COPPERINGOT)
                .setGroup("formertech")
                .addCriterion("copper", InventoryChangeTrigger.Instance.forItems(ModItems.COPPERINGOT))
                .build(consumer);
    }
}
