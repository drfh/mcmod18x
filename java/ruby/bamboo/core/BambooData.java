package ruby.bamboo.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BambooData {
		@Nullable
		String name() default Constants.STR_EMPTY;
		
		@Nonnull
		Class<? extends ItemBlock> itemBlock() default ItemBlock.class;
	
		@Nonnull
		EnumCreateTab createiveTabs() default EnumCreateTab.NONE;
}
