package ruby.bamboo.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class XZAxisBlock extends BlockRotatedPillar {

	public XZAxisBlock(Material materialIn) {
		super(materialIn);

		this.setHardness(0.2F);
		this.setResistance(10F);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		state = this.setBlockMeta(state, meta);
		state = this.setDirMeta(state, meta);
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return this.getBlockMeta(state) + this.getDirMeta(state);
	}

	abstract int getBlockMeta(IBlockState state);

	abstract IBlockState setBlockMeta(IBlockState state, int meta);

	private int getDirMeta(IBlockState state) {
		return ((EnumFacing.Axis) state.getValue(AXIS)) == EnumFacing.Axis.Z ? 8 : 0;
	}

	private IBlockState setDirMeta(IBlockState state, int meta) {
		return state.withProperty(AXIS, meta > 7 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}

	@Override
	protected abstract BlockState createBlockState();

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		EnumFacing dir = placer.getHorizontalFacing();
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(AXIS,
				dir.getAxis());
	}

}
