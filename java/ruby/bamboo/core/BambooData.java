package ruby.bamboo.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public @interface BambooData {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface BambooBlock {
		/**
		 * 空白は自動的にクラス名を小文字化したものになる
		 * 
		 * @return
		 */

		String name() default Constants.STR_EMPTY;

		EnumMaterial material() default EnumMaterial.GROUND;

		Class<? extends ItemBlock> itemBlock() default ItemBlock.class;

		EnumCreateTab createiveTabs() default EnumCreateTab.NONE;

		boolean disableItem() default false;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface BambooItem {
		/**
		 * 空白は自動的にクラス名を小文字化したものになる
		 * 
		 * @return
		 */

		String name() default Constants.STR_EMPTY;

		EnumCreateTab createiveTabs() default EnumCreateTab.NONE;

		Class<? extends Block> overrideModel();
	}
}
