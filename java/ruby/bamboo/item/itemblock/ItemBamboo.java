package ruby.bamboo.item.itemblock;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import ruby.bamboo.block.Bamboo;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.init.BambooData;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.core.init.BambooData.BambooItem;
import ruby.bamboo.core.init.IOreNameable;
/**
 * いつか殺す
 * @author Ruby
 *
 */
public class ItemBamboo extends ItemBlock implements IOreNameable{

	public ItemBamboo(Block block) {
		super(block);
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	return false;
    }

	@Override
	public Map<String,ItemStack> getOreName(Map<String, ItemStack> map) {
		map.put(Constants.ORE_BAMBOO, new ItemStack(this));
		return map;
	}

}
