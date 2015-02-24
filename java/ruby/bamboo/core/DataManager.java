package ruby.bamboo.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

/**
 * ばんぶーの適当な管理
 * 
 * @author Ruby
 * 
 */
public class DataManager {

	private static final HashMap<Class, String> classToNameMap = new HashMap<Class, String>();
	
	/**
	 * 使用頻度が高いであろう"MODID" + ":" + "NAME" 形式で保持する
	 * @param cls
	 * @param str
	 */
	protected static void addClassToNameMap(Class cls, String str) {
		classToNameMap.put(cls, BambooCore.MODID + Constants.DMAIN_SEPARATE + str);
	}

	/**
	 * NAMEのみ
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getDefaultName(Class clazz) {
		return getName(clazz).split(Constants.DMAIN_SEPARATE)[1];
	}

	/**
	 * "MODID" + ":" + "NAME" 形式
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getName(Class clazz) {
		return classToNameMap.get(clazz);
	}

	/**
	 * パフォーマンス悪いけども、そんなに呼び出す機会は初期化程度
	 * 
	 * @return
	 */
	public static List<String> getRegstedNameList() {
		List<String> rslt = new ArrayList<String>();
		for (Class key : classToNameMap.keySet()) {
			rslt.add(getDefaultName(key));
		}
		return rslt;
	}

	/**
	 * ばんぶー専用
	 * 
	 * @param
	 * @return
	 */
	public static Item getItem(Class cls) {
		String name = getName(cls);
		if (name != null) {
			return Item.getByNameOrId(name);
		}
		return null;
	}

	/**
	 * ばんぶー専用
	 * 
	 * @param
	 * @return
	 */
	public static Block getBlock(Class cls) {
		String name = getName(cls);
		if (name != null) {
			return Block.getBlockFromName(name);
		}
		return null;
	}

	/**
	 * ばんぶー専用
	 * 
	 * @param
	 * @return
	 */
	public static IBlockState getState(Class cls) {
		return getBlock(cls).getDefaultState();
	}
}
