package ruby.bamboo.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import ruby.bamboo.block.Bamboo;
import ruby.bamboo.block.SakuraLog;
import ruby.bamboo.block.SakuraPlank;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.item.FoldingFan;
import ruby.bamboo.item.Sack;
import ruby.bamboo.item.Tudura;

public class BambooRecipes {
	private int WILD = Short.MAX_VALUE;
	private String bamboo = "bamboo";
	private String tudura = "tudura";

	/**
	 * 鉱石辞書
	 */
	public void oreDicRegist() {
		OreDictionary.registerOre("logWood", getIS(SakuraLog.class));
		OreDictionary.registerOre("plankWood", getIS(SakuraPlank.class));
		OreDictionary.registerOre(bamboo, getIS(Bamboo.class));
		OreDictionary.registerOre(tudura, getIS(Tudura.class));
	}

	/**
	 * クラフトテーブル
	 */
	public void craftingTableRecipes() {
		// サクラ原木→木材
		addShapelessRecipe(getIS(SakuraPlank.class, 4, 0), SakuraLog.class);
		// 袋開放
		addShapelessRecipe(getIS(Sack.class), getIS(Sack.class, 1, WILD));
		// 扇子
		addRecipe(getIS(FoldingFan.class), "PPB", "PPB", "BBB", 'P', Items.paper, 'B', bamboo);
		// 袋
		addRecipe(getIS(Sack.class), "SSS", "WTW", "WWW", 'S', Items.string, 'T', tudura, 'W', getIS(Blocks.wool, 1, WILD));
	}

	/**
	 * 竈
	 */
	public void smeltingRecipes() {
		GameRegistry.addSmelting(getIS(SakuraLog.class), getIS(Items.coal, 1, 1), 0.2F);
	}

	/**
	 * 燃料
	 */
	public void registFuel() {
		IFuelHandler handler = new IFuelHandler() {
			private Item sakuraLog = Item.getItemFromBlock(DataManager.getBlock(SakuraLog.class));

			@Override
			public int getBurnTime(ItemStack fuel) {
				if (fuel.getItem() == sakuraLog) {
					return 270;
				}
				return 0;
			}
		};
		GameRegistry.registerFuelHandler(handler);
	}

	private void addRecipe(ItemStack output, Object... params) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, params));
	}

	private void addShapelessRecipe(Object... objArray) {
		ItemStack output;
		Object[] params = new Object[objArray.length - 1];
		if (objArray[0] instanceof Class) {
			output = getIS((Class) objArray[0]);
		} else {
			output = (ItemStack) objArray[0];
		}
		for (int i = 1; i < objArray.length; i++) {
			if (objArray[i] instanceof Class) {
				params[i - 1] = getIS((Class) objArray[i]);
			} else {
				params[i - 1] = objArray[i];
			}
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(output, params));
	}

	private ItemStack getIS(Block block) {
		return this.getIS(block, 0);
	}

	private ItemStack getIS(Block block, int meta) {
		return this.getIS(block, 1, meta);
	}

	private ItemStack getIS(Block block, int amo, int meta) {
		return new ItemStack(block, amo, meta);
	}

	private ItemStack getIS(Item item) {
		return this.getIS(item, 0);
	}

	private ItemStack getIS(Item item, int meta) {
		return this.getIS(item, 1, meta);
	}

	private ItemStack getIS(Item item, int amo, int meta) {
		return new ItemStack(item, amo, meta);
	}

	private ItemStack getIS(Class cls) {
		return this.getIS(cls, 0);
	}

	private ItemStack getIS(Class cls, int meta) {
		return this.getIS(cls, 1, meta);
	}

	private ItemStack getIS(Class cls, int amo, int meta) {
		if (Block.class.isAssignableFrom(cls)) {
			return new ItemStack(DataManager.getBlock(cls), amo, meta);
		} else {
			return new ItemStack(DataManager.getItem(cls), amo, meta);
		}
	}
}
