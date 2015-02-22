package ruby.bamboo.core;

import ruby.bamboo.block.BambooShoot;
import ruby.bamboo.item.ItemBambooShoot;

/**
 * てきとーな関数
 * 
 * @author Ruby
 * 
 */
public class Constants {
	/**
	 * ドメイン用
	 */
	public static final String DMAIN_SEPARATE = ":";
	/**
	 * ぱっけーじ用
	 */
	public static final String PACKAGE_SEPARATE = ".";
	/**
	 * パッケージパス
	 */
	public static final String BLOCK_PACKAGE = BambooShoot.class.getPackage().getName();
	public static final String ITEM_PACKAGE = ItemBambooShoot.class.getPackage().getName();
	
	public static final String STR_EMPTY = "";
}
