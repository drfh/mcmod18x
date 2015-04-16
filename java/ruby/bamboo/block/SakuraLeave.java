package ruby.bamboo.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.BambooData.BambooBlock.StateIgnore;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.item.itemblock.ItemSakuraLeave;

@BambooBlock(name = "sakura_leave",itemBlock=ItemSakuraLeave.class, createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class SakuraLeave extends BlockLeaves {
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumLeave.class);

	public SakuraLeave() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumLeave.WHITE).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.fancyGraphics=true;
		this.setLightLevel(0.75F);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		byte b0 = 0;
		int i = b0 | ((EnumLeave) state.getValue(VARIANT)).getMetadata();

		if (!((Boolean) state.getValue(DECAYABLE)).booleanValue())
		{
			i |= 4;
		}

		if (((Boolean) state.getValue(CHECK_DECAY)).booleanValue())
		{
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT, DECAYABLE, CHECK_DECAY });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (EnumLeave leave : EnumLeave.LEAVES) {
			list.add(new ItemStack(itemIn, 1, leave.getMetadata()));
		}
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return null;
	}

	// 拡張性0abstractクソゴミ仕様
	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}

	public String getLeaveName(int metadata) {
		return EnumLeave.getLeave(metadata).getName();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumLeave.getLeave(meta)).withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0))
				.withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(DataManager.getBlock(SakuraSapling.class));
    }

	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state)
	{
		return ((EnumLeave) state.getValue(VARIANT)).getColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
	{
		return ((EnumLeave) worldIn.getBlockState(pos).getValue(VARIANT)).getColor();
	}
	
	@StateIgnore
	public IProperty[] getIgnoreState() {
		return new IProperty[] { DECAYABLE, CHECK_DECAY };
	}
	
	// はいはい糞仕様糞仕様
	@Override
    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT_MIPPED ;
    }
	
	@Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	public enum EnumLeave implements IStringSerializable {
		BLACK(0, 0x5C5C5C, 1),
		RED(1, 0xc80010, 2),
		GREEN(2, 0x3F9E55, 0),
		CACAO(3, 0x98744B, 0),
		BLUE(4, 0x5F89DC, 1),
		PURPLE(5, 0xB087CC, 1),
		CYAN(6, 0x87DBF6, 1),
		LIGHT_GRAY(7, 0xD3D3D3, 2),
		GRAY(8, 0x8D8D8D, 1),
		PINK(9, 0xFFC5CC, 1),
		LIME(10, 0xBCF472, 0),
		YELLOW(11, 0xf5e600, 3),
		LIGHT_BLUE(12, 0xB8EFFF, 1),
		MAGENTA(13, 0xFF87FA, 1),
		ORANGE(14, 0xFFC600, 3),
		WHITE(15, 0xFFFFFF, 1);
		EnumLeave(int meta, int color, int petal) {
			this.meta = (byte) meta;
			this.color = color;
			this.petal = (byte) petal;
		}

		public static final EnumLeave[] LEAVES = { BLACK, RED, GREEN, CACAO, BLUE, PURPLE, CYAN, LIGHT_GRAY, GRAY, PINK, LIME, YELLOW, LIGHT_BLUE, MAGENTA, ORANGE, WHITE };
		private byte meta;
		private int color;
		private byte petal;

		public static EnumLeave getLeave(int meta) {
			return LEAVES[meta < LEAVES.length ? meta : 0];
		}

		public byte getMetadata() {
			return this.meta;
		}

		public int getColor() {
			return this.color;
		}

		public byte getPetal() {
			return this.petal;
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

	}

}
