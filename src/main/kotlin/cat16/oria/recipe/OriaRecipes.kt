package cat16.oria.recipe

import cat16.oria.Oria
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.SpecialRecipeSerializer
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object OriaRecipes {

    val BOMB = register("crafting_special_bomb", SpecialRecipeSerializer(::BombRecipe))

    fun init() {

    }

    private fun <S : RecipeSerializer<T>, T : Recipe<*>> register(
        id: String,
        serializer: S
    ): S {
        return Registry.register(
            Registries.RECIPE_SERIALIZER,
            Oria.id(id),
            serializer
        )
    }
}