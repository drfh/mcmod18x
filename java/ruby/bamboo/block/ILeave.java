package ruby.bamboo.block;

import net.minecraft.block.state.IBlockState;

public interface ILeave {
	public String getLeaveName(int meta);
	public IBlockState getStateFromMeta(int meta);
	public int getRenderColor(IBlockState stateFromMeta);
}
