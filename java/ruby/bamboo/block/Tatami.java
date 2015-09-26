package ruby.bamboo.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.item.itemblock.ItemTatami;

@BambooBlock(createiveTabs = EnumCreateTab.TAB_BAMBOO, itemBlock = ItemTatami.class)
public class Tatami extends XZAxisBlock {

	public static final PropertyInteger META = PropertyInteger.create(Constants.META, 0, 1);

	public Tatami() {
		super(Material.ground);
		this.setDefaultState(
				this.blockState.getBaseState().withProperty(META, 0).withProperty(AXIS, EnumFacing.Axis.X));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
	}

	@Override
	int getBlockMeta(IBlockState state) {
		return ((Integer) state.getValue(META)).intValue() & 7;
	}

	@Override
	IBlockState setBlockMeta(IBlockState state, int meta) {
		return state.withProperty(META, meta & 7);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { META, AXIS });
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		return false;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return ((Integer) state.getValue(META)).intValue() & 7;
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getBlockMeta( state));
	}

}
