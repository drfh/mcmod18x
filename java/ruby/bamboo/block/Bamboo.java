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
import ruby.bamboo.core.EnumMaterial;

/**
 * ばんぼー
 * 
 * @author Ruby
 * 
 */
@BambooBlock(material = EnumMaterial.PLANTS)
public class Bamboo extends BlockBush implements IGrowable {

	public static final PropertyInteger METADATA = PropertyInteger.create(Constants.META, 0, 10);
	public static final PropertyInteger TYPEDATA = PropertyInteger.create(Constants.TYPE, 0, 3);

	public Bamboo(Material material) {
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(METADATA, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(METADATA);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, METADATA);
	}

	/**
	 * 中心軸からずらすアレ
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XYZ;
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
