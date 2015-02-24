package ruby.bamboo.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ruby.bamboo.core.BambooData.BambooBlock;
import ruby.bamboo.core.BambooData.BambooItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.MinecraftError;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * ブロック自動登録
 * 
 * @author Ruby
 * 
 */
public class DataLoader extends ClassFinder {

	/**
	 * 初期化処理
	 */
	public void init(String packagePath) {
		List<IData> rsltList = new ArrayList<IData>();
		try {
			for (Class entry : search(packagePath)) {
				if (entry.isAnnotationPresent(BambooBlock.class)) {
					registBlock(entry);
				} else if (entry.isAnnotationPresent(BambooItem.class)) {
					registItem(entry);
				}
			}
		} catch (Exception e) {
			FMLLog.warning("ブロック初期化例外");
			e.printStackTrace();
		}
	}

	/**
	 * ブロック側アノテーション処理
	 * 
	 * @param cls
	 */
	private void registBlock(Class<? extends Block> cls) {
		try {
			// マテリアルを持つコンストラクタはアノテーションでしたマテリアルで初期化する(継承対策)
			Class c = Class.forName(cls.getName());
			Block instance = null;
			try {
				Constructor cnst = c.getConstructor(Material.class);
				instance = (Block) cnst.newInstance(((BambooBlock) c.getAnnotation(BambooBlock.class)).material().MATERIAL);
			} catch (NoSuchMethodException e) {
				instance = (Block) c.newInstance();
			}

			BambooBlock anoData = (BambooBlock) cls.getAnnotation(BambooBlock.class);
			String name = anoData.name().isEmpty() ? cls.getSimpleName().toLowerCase() : anoData.name().toLowerCase();
			if (anoData.createiveTabs() != EnumCreateTab.NONE) {
				instance.setCreativeTab(anoData.createiveTabs().getTabInstance());
			}
			instance.setUnlocalizedName(name);
			GameRegistry.registerBlock(instance, anoData.itemBlock(), name);
			DataManager.addClassToNameMap(cls, name);
		} catch (Exception e) {
			FMLLog.warning("ブロックインスタンス登録例外");
			e.printStackTrace();
		}
	}

	/**
	 * アイテム側アノテーション処理
	 * 
	 * @param cls
	 */
	private void registItem(Class<? extends Item> cls) {
		try {
			Class c = Class.forName(cls.getName());
			Item instance = (Item) c.newInstance();

			BambooItem anoData = (BambooItem) cls.getAnnotation(BambooItem.class);
			String name = anoData.name().isEmpty() ? cls.getSimpleName().toLowerCase() : anoData.name().toLowerCase();
			if (anoData.createiveTabs() != EnumCreateTab.NONE) {
				instance.setCreativeTab(anoData.createiveTabs().getTabInstance());
			}
			instance.setUnlocalizedName(name);
			if (anoData.blockOverride()) {
				GameRegistry.registerItem(instance, null, BambooCore.MODID);
			} else {
				GameRegistry.registerItem(instance, name);
			}
			DataManager.addClassToNameMap(cls, name);
		} catch (Exception e) {
			FMLLog.warning("アイテムインスタンス登録例外");
			e.printStackTrace();
		}
	}

}
