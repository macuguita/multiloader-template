package com.macuguita.modtemplate.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import com.macuguita.modtemplate.common.ModTemplate;

public class ModRecipeProvider extends FabricRecipeProvider {

	public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
		return new RecipeProvider(registries, output) {
			@Override
			protected void buildRecipes() {
				final var itemLookup = registries.lookupOrThrow(Registries.ITEM);
				ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.FOOD, Items.COOKED_CHICKEN)
					.requires(Items.LAVA_BUCKET)
					.requires(Items.CHICKEN)
					.unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
					.unlockedBy("has_chicken", has(Items.CHICKEN))
					.save(output, "lava_chicken_recipe");
			}
		};
	}

	@Override
	public String getName() {
		return ModTemplate.MOD_ID + "_recipe_provider";
	}
}
