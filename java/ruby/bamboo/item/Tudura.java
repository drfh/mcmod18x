package ruby.bamboo.item;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.core.init.IRecipeable;
import ruby.bamboo.core.init.BambooData.BambooItem;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.item.itemblock.ItemBamboo;

@BambooItem(createiveTabs=EnumCreateTab.TAB_BAMBOO)
public class Tudura extends Item implements IRecipeable{

	@Override
	public List<IRecipe> getRecipe(List<IRecipe> recipeList) {
		recipeList.add(new ShapedOreRecipe(this," # ","# #"," # ",'#',"bamboo"));
		return recipeList;
	}

}
