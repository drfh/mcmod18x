package ruby.bamboo.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ruby.bamboo.core.BambooData;
import net.minecraft.block.Block;
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

	private static final HashMap<Class<? extends Block>, String> classToNameMap=new HashMap<Class<? extends Block>, String>();
	public static final DataLoader instance = new DataLoader();

	private DataLoader() {
	};

	/**
	 * 初期化処理
	 */
	public void init(String packagePath) {
		List<IData> rsltList = new ArrayList<IData>();
		try {
			for (Class entry : search(packagePath)) {
				if (entry.isAnnotationPresent(BambooData.class)) {
					IData data = this.createData(entry);
					rsltList.add(data);
					classToNameMap.put(entry, data.getName());
				}
			}
		} catch (Exception e) {
			FMLLog.warning("ブロック初期化例外");
			e.printStackTrace();
		}
		for (IData data : rsltList) {
			try {
				Object instance = Class.forName(data.getClazz().getName()).newInstance();
				if (instance instanceof Block) {
					registInstance((Block) instance, data.getName(), data.getItemBlock(), data.getCreativeTab());
				} else if (instance instanceof Item) {
					registInstance((Item) instance, data.getName(), data.getCreativeTab());
				}
			} catch (Exception e) {
				FMLLog.warning("インスタンス登録例外");
				e.printStackTrace();
			}
		}
	}

	public static String getName(Class clazz) {
		return classToNameMap.get(clazz);
	}

	public static String getModdedName(Class clazz) {
		return BambooCore.MODID + Constants.DMAIN_SEPARATE + getName(clazz);
	}
	public static Collection<String> getRegstedNameList(){
		return classToNameMap.values();
	}

	/**
	 * 登録用データを生成して戻す。
	 * 
	 * @param clazz
	 * @return
	 */
	private IData createData(Class clazz) {
		IData data = new Data();
		data.setClazz(clazz);
		BambooData anoData = (BambooData) clazz.getAnnotation(BambooData.class);
		String name = anoData.name();
		data.setName(name.isEmpty() ? clazz.getSimpleName().toLowerCase() : name.toLowerCase());
		data.setItemBlock(anoData.itemBlock());
		data.setCreativeTab(anoData.createiveTabs().getTabInstance());
		return data;
	}

	/**
	 * GameRegistoryに登録する(Block)
	 * 
	 * @param block
	 * @param name
	 * @param itemBlock
	 * @param creativeTabs
	 */
	private void registInstance(Block block, String name, Class itemBlock, CreativeTabs creativeTabs) {
		if (creativeTabs != null) {
			block.setCreativeTab(creativeTabs);
		}
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, itemBlock, name);
	}

	/**
	 * GameRegistoryに登録する(Item)
	 * 
	 * @param item
	 * @param name
	 * @param creativeTabs
	 */
	private void registInstance(Item item, String name, CreativeTabs creativeTabs) {
		if (creativeTabs != null) {
			item.setCreativeTab(creativeTabs);
		}
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);
	}

	public class Data implements IData {

		private String name;
		private CreativeTabs creativetab;
		private Class<? extends ItemBlock> itemBlock;
		private Class clazz;

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public CreativeTabs getCreativeTab() {
			return creativetab;
		}

		@Override
		public void setCreativeTab(CreativeTabs creativetab) {
			this.creativetab = creativetab;
		}

		@Override
		public Class<? extends ItemBlock> getItemBlock() {
			return itemBlock;
		}

		@Override
		public void setItemBlock(Class<? extends ItemBlock> itemBlock) {
			this.itemBlock = itemBlock;
		}

		@Override
		public Class getClazz() {
			return clazz;
		}

		@Override
		public void setClazz(Class clazz) {
			this.clazz = clazz;
		}

	}

}
