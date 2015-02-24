package ruby.bamboo.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.BambooData.BambooBlock;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.EnumCreateTab;
import ruby.bamboo.core.EnumMaterial;
import ruby.bamboo.item.itemblock.ItemBambooShoot;

/**
 * たけのこ
 * 
 * @author Ruby
 * 
 */
@BambooBlock(itemBlock = ItemBambooShoot.class, createiveTabs = EnumCreateTab.TAB_BAMBOO, material = EnumMaterial.PLANTS)
public class BambooShoot extends BlockBush implements IGrowable {

	public static final PropertyInteger METADATA = PropertyInteger.create(Constants.META, 0, 1);

	public BambooShoot(Material material) {
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
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

	public void tryBambooGrowth(World world, Random rand, BlockPos pos, IBlockState state, float prob) {
		if (!world.isRemote) {
			if (rand.nextFloat() < prob && canChildGrow(world, pos, state)) {
				world.setBlockState(pos, DataManager.getState(Bamboo.class));
				// world.setBlockState(pos,
				// bambooList.get(world.rand.nextInt(bambooList.size())), 0, 3);
			}
		}
	}

	public boolean canChildGrow(World world, BlockPos pos, IBlockState state) {
		// 頭上が空気以外だったら成長させない置物処理
		boolean flg = world.isAirBlock(pos.up());

		if (flg) {
			Chunk chunk = world.getChunkFromBlockCoords(pos);
			// 成長の条件は人工の光7以上
			flg = chunk.getLightFor(EnumSkyBlock.BLOCK, pos) > 7;
		}
		return flg;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(this);
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!canBlockStay(world, pos, state)) {
			world.setBlockToAir(pos);
		}
	}

	@Override
	public boolean canPlaceBlockOn(Block ground) {
		return ground == Blocks.grass || ground == Blocks.dirt || ground == Blocks.farmland;
	}

	@Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			worldIn.setBlockState(pos, Blocks.air.getDefaultState(), 3);
		}
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
		this.tryBambooGrowth(world, rand, pos, state, 0.75F);
	}

}
