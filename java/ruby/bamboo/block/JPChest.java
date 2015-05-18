package ruby.bamboo.block;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import ruby.bamboo.block.tile.TileJPChest;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.gui.GuiHandler;

@BambooBlock(createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class JPChest extends BlockChest {

	public JPChest() {
		super(1);
		setHardness(3);
		setResistance(10);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {}

	@Override
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public boolean isFullCube()
	{
		return true;
	}

	@Override
	public int getRenderType()
	{
		return 3;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileJPChest();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{

		ILockableContainer ilockablecontainer = (ILockableContainer) worldIn.getTileEntity(pos);

		if (ilockablecontainer != null)
		{
			GuiHandler.openGui(worldIn, playerIn, GuiHandler.GUI_JPCHEST, pos);
		}
		return true;
	}

}
