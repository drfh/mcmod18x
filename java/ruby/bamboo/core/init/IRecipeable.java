package ruby.bamboo.core.init;

import java.util.List;

import net.minecraft.item.crafting.IRecipe;

public interface IRecipeable {
	public List<IRecipe> getRecipe(List<IRecipe> recipeList);
}
