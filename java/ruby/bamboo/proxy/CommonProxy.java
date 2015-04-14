package ruby.bamboo.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.init.DataLoader;
import ruby.bamboo.core.init.IOreNameable;
import ruby.bamboo.core.init.IRecipeable;
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
		GenerateHandler gen=new GenerateHandler();
		GameRegistry.registerWorldGenerator(gen, 1);
	}

	public void init() {
		//this.registRecipe();
	}

	// レシピと鉱石名登録
	private void registRecipe() {
		List<IRecipe> list = new ArrayList<IRecipe>();
		Map<String, ItemStack> map = new HashMap<String, ItemStack>();
		for (Class cls : DataManager.getRegstedClassArray()) {
			Object obj = DataManager.getBlock(cls);
			if (obj == null) {
				DataManager.getItem(cls);
			}
			if (IOreNameable.class.isInstance(obj)) {
				map.clear();
				((IOreNameable) obj).getOreName(map);
				for (Entry<String, ItemStack> entry : map.entrySet()) {
					OreDictionary.registerOre(entry.getKey(), entry.getValue());
				}
			}
			if (IRecipeable.class.isInstance(obj)) {
				list.clear();
				((IRecipeable) obj).getRecipe(list);
				for (IRecipe recipe : list) {
					GameRegistry.addRecipe(recipe);
				}
			}
		}
	}

}
