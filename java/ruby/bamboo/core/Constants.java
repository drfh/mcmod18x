package ruby.bamboo.core;

import ruby.bamboo.block.BambooShoot;
import ruby.bamboo.item.Tudura;
import ruby.bamboo.item.itemblock.ItemBamboo;
import ruby.bamboo.item.itemblock.ItemBambooShoot;

/**
 * てきとーな定数
 * 
 * @author Ruby
 * 
 */
public class Constants {

	public static final String MODID = "BambooMod";
	public static final String MC_VER = "@MC_VERSION@";
	public static final String BAMBOO_VER = "@VERSION@";
	public static final String RESOURCED_DOMAIN = "bamboomod";
	public static final String STR_EMPTY = "";
	
	//すてーとたいぷ(良くわかってない)
	public static final String META = "meta";
	public static final String TYPE = "type";
	public static final String AGE = "age";
	
	//せぱれー
	/**
	 * ドメイン用
	 */
	public static final String DMAIN_SEPARATE = ":";
	/**
	 * ぱっけーじ用
	 */
	public static final String PACKAGE_SEPARATE = ".";
	
	// ぱす
	/**
	 * パッケージパス
	 */
	public static final String BLOCK_PACKAGE = BambooShoot.class.getPackage().getName();
	public static final String ITEM_PACKAGE = Tudura.class.getPackage().getName();
	
	// 鉱石辞書名
	public static final String ORE_BAMBOO="bamboo";
}
