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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	// 最大の長さ
	public static final PropertyInteger LENGTH = PropertyInteger.create(Constants.META, 0, 15);

	public Bamboo(Material material) {
		this.setDefaultState(this.blockState.getBaseState().withProperty(LENGTH, 0));
		this.setLightOpacity(0);
		this.setTickRandomly(true);
		this.setHardness(0.75F);
		this.setResistance(1F);
		this.setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
		this.setHarvestLevel("axe", 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(LENGTH, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(LENGTH)).intValue();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, LENGTH);
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
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
		BlockPos p = pos.down(this.getMetaFromState(state));
		worldIn.destroyBlock(p, true);
	}

	private void tryBambooGrowth(World world, BlockPos pos, IBlockState state, float probability) {
		if (!world.isRemote) {
			if (world.isAirBlock(pos.up())) {
				if (world.rand.nextFloat() < probability) {
					int meta = this.getMetaFromState(state);
					if (meta < this.getLength()) {
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

	int getLength() {
		return 10;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		tryBambooGrowth(world, pos, state, world.isRaining() ? 0.25F : 0.125F);
	}

	private void growBamboo(World world, BlockPos pos, int meta) {
		world.setBlockState(pos.up(), this.getStateFromMeta(++meta));
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

			for (BlockPos target : (Iterable<BlockPos>) BlockPos.getAllInBox(p.add(-1, -1, -1), p.add(1, 1, 1))) {
				if (this.canChildSpawn(world, target, state)) {
					world.setBlockState(target.down(), Blocks.dirt.getDefaultState());
					world.setBlockState(target, DataManager.getState(BambooShoot.class));
				}
			}
		}
	}

	@Override
	public boolean canPlaceBlockOn(Block ground) {
		return super.canPlaceBlockOn(ground) || ground == this;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}

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
		while (!world.isAirBlock(tmp = tmp.up())) {
			pos = tmp;
		}
		this.tryBambooGrowth(world, pos, world.getBlockState(pos), 0.65F);
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@StateIgnore
	public IProperty[] getIgnoreState() {
		return new IProperty[] {LENGTH };
	}

}
