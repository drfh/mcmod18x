package ruby.bamboo.item;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import ruby.bamboo.block.RicePlant;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.init.BambooData.BambooItem;
import ruby.bamboo.core.init.EnumCreateTab;

@BambooItem(createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class RiceSeed extends ItemSeeds {

	public RiceSeed() {
		super(DataManager.getBlock(RicePlant.class), Blocks.farmland);
	}

}
