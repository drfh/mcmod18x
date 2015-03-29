package ruby.bamboo.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.BambooData.BambooBlock.StateIgnore;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.core.init.EnumMaterial;
import ruby.bamboo.item.itemblock.ItemBamboo;

/**
 * ばんぼー
 * 
 * @author Ruby
 * 
 */
@BambooBlock(itemBlock = ItemBamboo.class, createiveTabs = EnumCreateTab.TAB_BAMBOO, material = EnumMaterial.PLANTS)
public class Bamboo extends BlockBush implements IGrowable {
	// 現在の長さ 
	public static final PropertyInteger AGE = PropertyInteger.create(Constants.AGE, 0, 15);
	// 最大の長さ
	public static final PropertyInteger MAX_LENGTH = PropertyInteger.create(Constants.META, 7, 15);

	public Bamboo(Material material) {
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(MAX_LENGTH, 10));
		this.setLightOpacity(0);
		this.setTickRandomly(true);
		this.setHardness(1.0F);
		this.setResistance(1F);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(AGE)).intValue();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, AGE, MAX_LENGTH);
	}

	/**
	 * 中心軸からずらすアレ
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XYZ;
	}

	private void tryBambooGrowth(World world, BlockPos pos, IBlockState state, float probability) {
		if (!world.isRemote) {
			if (world.isAirBlock(pos.up())) {
				if (world.rand.nextFloat() < probability) {
					int meta = this.getMetaFromState(state);

					if (meta != 15) {
						if (meta < this.getMaxLength(state)) {
							this.growBamboo(world, pos, meta);
						} else {
							if (world.isRaining() || world.rand.nextFloat() < probability) {
								this.tryChildSpawn(world, pos, state);
							}
						}
					}
				}
			}
		}
	}

	private void growBamboo(World world, BlockPos pos, int meta) {
		world.setBlockState(pos.up(), this.getStateFromMeta(meta++));
	}

	private void tryChildSpawn(World world, IBlockState state, BlockPos pos) {
		if (!world.isRemote) {
			BlockPos p = pos.down(this.getMetaFromState(state));

			for (int i1 = -1; i1 <= 1; i1++) {
				for (int j1 = -1; j1 <= 1; j1++) {
					for (int k1 = -1; k1 <= 1; k1++) {
						BlockPos p2 = p.add(i1, j1, k1);
						if (canChildSpawn(world, p2, state)) {
							world.setBlockState(p2.down(), Blocks.dirt.getDefaultState());
							world.setBlockState(p2, DataManager.getState(BambooShoot.class));
						}
					}
				}
			}
		}
	}

	private boolean canChildSpawn(World world, BlockPos pos, IBlockState state) {
		if (world.isAirBlock(pos)) {
			BlockPos pd = pos.down();
			if (DataManager.getBlock(BambooShoot.class).canBlockStay(world, pos, state)) {
				// 天候・耕地確変
				if (world.rand.nextFloat() < (world.isRaining() ? 0.4F : world.getBlockState(pd).getBlock() == Blocks.farmland ? 0.25F : 0.1F)) {
					return true;
				}
			}
		}

		return false;
	}

	private void tryChildSpawn(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			BlockPos p = pos.down(this.getMetaFromState(state));

			for (int i1 = -1; i1 <= 1; i1++) {
				for (int j1 = -1; j1 <= 1; j1++) {
					for (int k1 = -1; k1 <= 1; k1++) {
						BlockPos p2 = p.add(i1, j1, k1);
						if (this.canChildSpawn(world, p2, state)) {
							world.setBlockState(p2.down(), Blocks.dirt.getDefaultState());
							world.setBlockState(p2, DataManager.getState(BambooShoot.class));
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockOn(Block ground) {
		return super.canPlaceBlockOn(ground) || ground == this;
	}

	private int getMaxLength(IBlockState state) {
		return ((Integer) state.getValue(MAX_LENGTH)).intValue();
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
		BlockPos tmp = pos;
		while (!world.isAirBlock(tmp.up())) {
			tmp = tmp.up();
		}
		this.tryBambooGrowth(world, tmp, state, 0.75F);
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@StateIgnore
	public IProperty[] getIgnoreState() {
		return new IProperty[] { AGE, MAX_LENGTH };
	}
}
