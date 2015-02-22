package ruby.bamboo.block;

import java.util.List;

import ruby.bamboo.core.BambooData;
import ruby.bamboo.core.EnumCreateTab;
import ruby.bamboo.item.ItemBambooShoot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * たけのこ
 * 
 * @author Ruby
 * 
 */
@BambooData(itemBlock = ItemBambooShoot.class, createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class BambooShoot extends BlockBush {

	public BambooShoot() {
		super();
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemIn, 1, 0));
	}

}
