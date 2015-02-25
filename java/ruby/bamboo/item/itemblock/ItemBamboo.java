package ruby.bamboo.item.itemblock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import ruby.bamboo.block.Bamboo;
import ruby.bamboo.core.BambooData;
import ruby.bamboo.core.BambooData.BambooItem;
import ruby.bamboo.core.EnumCreateTab;
/**
 * いつか殺す
 * @author Ruby
 *
 */
public class ItemBamboo extends ItemBlock {

	public ItemBamboo(Block block) {
		super(block);
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	return false;
    }

}
