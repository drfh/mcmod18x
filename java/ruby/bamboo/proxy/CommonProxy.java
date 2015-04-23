package ruby.bamboo.proxy;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.init.DataLoader;
import ruby.bamboo.crafting.BambooRecipes;
import ruby.bamboo.generate.GenerateHandler;

/**
 * サーバープロクシ
 * 
 * @author Ruby
 * 
 */
public class CommonProxy {
	public void preInit() {
		// ブロックアイテム初期化
		try {
			FMLLog.info("********** BambooMod Data Init Start **********");
			DataLoader loader = new DataLoader();
			loader.init(Constants.BLOCK_PACKAGE);
			loader.init(Constants.ITEM_PACKAGE);
			FMLLog.info("********** BambooMod Data Init END **********");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// じぇねれーた
		GenerateHandler gen = new GenerateHandler();
		GameRegistry.registerWorldGenerator(gen, 1);
	}

	public void init() {
		this.registRecipe();
	}

	// 鉱石名登録
	private void registRecipe() {
		BambooRecipes recipeIns = new BambooRecipes();
		recipeIns.craftingTableRecipes();
		recipeIns.oreDicRegist();
		recipeIns.smeltingRecipes();
		recipeIns.registFuel();
	}

}
