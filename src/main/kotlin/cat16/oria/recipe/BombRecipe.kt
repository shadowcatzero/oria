package cat16.oria.recipe

import cat16.oria.item.OriaItems
import cat16.oria.item.component.FuseItem
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.SpecialCraftingRecipe
import net.minecraft.recipe.book.CraftingRecipeCategory
import net.minecraft.util.Identifier
import net.minecraft.world.World

class BombRecipe(identifier: Identifier, category: CraftingRecipeCategory) : SpecialCraftingRecipe(identifier,
    category
) {
    override fun matches(inv: CraftingInventory, world: World): Boolean {
        var fuse = false
        for(i in 0 until inv.size()) {
            val stack = inv.getStack(i)
            if(!stack.isEmpty) {
                val item = stack.item
                if (item is FuseItem) {
                    if (fuse) return false
                    fuse = true
                } else {
                    return false
                }
            }
        }
        return fuse
    }

    override fun craft(inv: CraftingInventory): ItemStack {
        var fuse: FuseItem? = null
        for(i in 0 until inv.size()) {
            val item = inv.getStack(i).item
            if(item is FuseItem) fuse = item
        }
        return if(fuse != null) {
            val bomb = ItemStack(OriaItems.BOMB)
            bomb.orCreateNbt.putString("fuse", fuse.fuseName)
            bomb
        } else ItemStack.EMPTY
    }

    override fun fits(width: Int, height: Int): Boolean {
        return width * height >= 1
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return OriaRecipes.BOMB
    }
}