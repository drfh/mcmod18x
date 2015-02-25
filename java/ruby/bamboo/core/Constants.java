package ruby.bamboo.core;

import ruby.bamboo.block.BambooShoot;
import ruby.bamboo.item.itemblock.ItemBamboo;
import ruby.bamboo.item.itemblock.ItemBambooShoot;

/**
 * てきとーな関数
 * 
 * @author Ruby
 * 
 */
public class Constants {
	//定数
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
	public static final String ITEM_PACKAGE = ItemBamboo.class.getPackage().getName();
	

}
