package ruby.bamboo.block;

import net.minecraft.block.state.IBlockState;

public interface ILeave {
	public String getLeaveName(int meta);
	public IBlockState getLeaveStateFromMeta(int meta);
	public int getLeaveRenderColor(IBlockState stateFromMeta);
}
