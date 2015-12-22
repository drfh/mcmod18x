package ruby.bamboo.proxy;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ruby.bamboo.block.tile.TileJPChest;
import ruby.bamboo.core.BambooCore;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.init.DataLoader;
import ruby.bamboo.core.init.EntityRegister;
import ruby.bamboo.crafting.BambooRecipes;
import ruby.bamboo.crafting.CraftingHandler;
import ruby.bamboo.generate.GenerateHandler;
import ruby.bamboo.gui.GuiHandler;

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
		// クラフトハンドラ
		FMLCommonHandler.instance().bus().register(new CraftingHandler());
		GameRegistry.registerTileEntity(TileJPChest.class, "jpchest");
	}

	public void init() {
		this.registRecipe();
		new EntityRegister().entityRegist();
	}

	public void postInit() {
		NetworkRegistry.INSTANCE.registerGuiHandler(BambooCore.instance, new GuiHandler());
	}

	// 鉱石名等登録
	private void registRecipe() {
		BambooRecipes recipeIns = new BambooRecipes();
		recipeIns.oreDicRegist();
		recipeIns.craftingTableRecipes();
		recipeIns.smeltingRecipes();
		recipeIns.registFuel();
		recipeIns.registSeed();
	}

}
