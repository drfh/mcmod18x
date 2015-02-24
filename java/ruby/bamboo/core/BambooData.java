package ruby.bamboo.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
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
		@Nonnull
		String name() default Constants.STR_EMPTY;

		@Nonnull
		EnumMaterial material() default EnumMaterial.GROUND;

		@Nonnull
		Class<? extends ItemBlock> itemBlock() default ItemBlock.class;

		@Nonnull
		EnumCreateTab createiveTabs() default EnumCreateTab.NONE;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface BambooItem {
		/**
		 * 空白は自動的にクラス名を小文字化したものになる
		 * 
		 * @return
		 */
		@Nonnull
		String name() default Constants.STR_EMPTY;

		@Nonnull
		EnumCreateTab createiveTabs() default EnumCreateTab.NONE;

		boolean blockOverride() default false;
	}
}
