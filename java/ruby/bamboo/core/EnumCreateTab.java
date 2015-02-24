package ruby.bamboo.core;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import ruby.bamboo.item.itemblock.ItemBambooShoot;

public enum EnumCreateTab {
	NONE, TAB_BAMBOO;
	static HashMap<EnumCreateTab, CreativeTabs> map;

	static {
		map = new HashMap<EnumCreateTab, CreativeTabs>();
		map.put(NONE, null);
		map.put(TAB_BAMBOO, new CreativeTabs(BambooCore.MODID) {
			@Override
			public String getTranslatedTabLabel() {
				return StatCollector.translateToLocal(this.getTabLabel());
			}

			@Override
			public Item getTabIconItem() {
				return Item.getItemFromBlock(Blocks.stone);//Block.getBlockFromName(BlockData.getModdedName(ItemBambooShoot.class)));
			}
		});
	}

	public CreativeTabs getTabInstance() {
		return map.get(this);
	}
};