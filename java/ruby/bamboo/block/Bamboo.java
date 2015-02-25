package ruby.bamboo.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.BambooData.BambooBlock;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.EnumCreateTab;
import ruby.bamboo.core.EnumMaterial;
import ruby.bamboo.item.itemblock.ItemBamboo;

/**
 * ばんぼー
 * 
 * @author Ruby
 * 
 */
@BambooBlock(itemBlock = ItemBamboo.class, createiveTabs = EnumCreateTab.TAB_BAMBOO, material = EnumMaterial.PLANTS)
public class Bamboo extends BlockBush implements IGrowable {

	public static final PropertyInteger AGE = PropertyInteger.create(Constants.AGE, 0, 10);
	public static final PropertyInteger META = PropertyInteger.create(Constants.META, 0, 10);
	public static final PropertyInteger TYPE = PropertyInteger.create(Constants.TYPE, 0, 3);

	public Bamboo(Material material) {
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(META,0).withProperty(TYPE,0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(AGE);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, AGE);
	}

	/**
	 * 中心軸からずらすアレ
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XYZ;
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {

		// this.tryBambooGrowth(world, rand, pos, state, 0.75F);
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

}
