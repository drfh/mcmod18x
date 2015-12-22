package ruby.bamboo.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class AxisBase extends BlockRotatedPillar {

	protected AxisBase(Material materialIn) {
		super(materialIn);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing.Axis axis = EnumFacing.Axis.Y;
		int j = meta & 12;

		if (j == 4) {
			axis = EnumFacing.Axis.X;
		} else if (j == 8) {
			axis = EnumFacing.Axis.Z;
		}

		return this.getDefaultState().withProperty(AXIS, axis);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		EnumFacing.Axis axis = (EnumFacing.Axis) state.getValue(AXIS);

		if (axis == EnumFacing.Axis.X) {
			i |= 4;
		} else if (axis == EnumFacing.Axis.Z) {
			i |= 8;
		}

		return i;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		EnumFacing dir = facing;
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			dir = placer.getHorizontalFacing();
		}
		return this.getStateFromMeta(meta).withProperty(AXIS, dir.getAxis());
	}

}
