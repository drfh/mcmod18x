package ruby.bamboo.core;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public interface IData{
	Class getClazz();
	void setClazz(Class clazz);
	
	String getName();
	void setName(String name);

	CreativeTabs getCreativeTab();
	void setCreativeTab(CreativeTabs creativetab);
	
	Class<? extends ItemBlock> getItemBlock();
	void setItemBlock(Class<? extends ItemBlock> itemBlock);


}
