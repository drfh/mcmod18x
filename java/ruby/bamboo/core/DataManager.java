package ruby.bamboo.core;

import java.util.Collection;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * ばんぶーの適当な管理
 * 
 * @author Ruby
 * 
 */
public class DataManager {

	private static final BiMap<Class, String> map = HashBiMap.create();

	/**
	 * 使用頻度が高いであろう"MODID" + ":" + "NAME" 形式で保持する
	 * 
	 * @param cls
	 * @param str
	 */
	public static void addMap(Class cls, String str) {
		map.put(cls, Constants.MODID + Constants.DMAIN_SEPARATE + str);
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
		return map.get(clazz);
	}

	/**
	 * 名前一覧取得
	 * 
	 * @return
	 */
	public static Collection<String> getRegstedNameArray() {
		return map.values();
	}

	/**
	 * クラス一覧投げ
	 * 
	 * @return
	 */
	public static Set<Class> getRegstedClassArray() {

		return map.keySet();
	}

	/**
	 * ばんぶー専用
	 * 
	 * @param
	 * @return
	 */
	public static <T extends Item> T getItem(Class<T> cls) {
		String name = getName(cls);
		if (name != null) {
			return (T) Item.getByNameOrId(name);
		}
		return null;
	}

	/**
	 * ばんぶー専用
	 * 
	 * @param
	 * @return
	 */
	public static <T extends Block> T getBlock(Class<T> cls) {
		String name = getName(cls);
		if (name != null) {
			return (T) Block.getBlockFromName(name);
		}
		return null;
	}

	/**
	 * ばんぶー専用(getDefaultState())
	 * 
	 * @param
	 * @return
	 */
	public static IBlockState getState(Class cls) {
		return getBlock(cls).getDefaultState();
	}

	/**
	 * 名前からクラス
	 * 
	 * @param name
	 * @return
	 */
	public static Class getClass(String name) {
		return map.inverse().get(name);
	}

}
