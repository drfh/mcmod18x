package ruby.bamboo.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.block.SakuraLeave.EnumLeave;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.BambooData.BambooBlock.StateIgnore;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.item.itemblock.ItemSakuraLeave;

import com.google.common.base.Predicate;

@BambooBlock(name = "broad_leave", itemBlock = ItemSakuraLeave.class, createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class BroadLeave extends BlockLeaves implements ILeave{

	private static final int metaSlide=4;
	// クソゴミ仕様に合わせるのつらい、なんで同じ処理2回も書かないといけないの？state回り設計した奴はマジで馬鹿なの？OleLeaveとNewLeave書いてる時に疑問持てよ無能
	public final static PropertyEnum VARIANT = PropertyEnum.create("variant", EnumLeave.class, new Predicate()
	{
		public boolean apply(EnumLeave type)
		{
			;
			return type.getMetadata() >= 4;
		}

		public boolean apply(Object p_apply_1_)
		{
			return this.apply((EnumLeave) p_apply_1_);
		}
	});;

	public BroadLeave() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumLeave.GREEN).withProperty(CHECK_DECAY, Boolean.valueOf(true))
				.withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.fancyGraphics = true;
		this.setLightLevel(0.75F);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		byte b0 = 0;
		int i = b0 | ((EnumLeave) state.getValue(VARIANT)).getMetadata()-metaSlide;

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

	// どうやらメタデータは15までというゴミ糞カス仕様のようだ、stateにした意味無いクソゴミボケ仕様
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumLeave.getLeave((meta&3)+metaSlide)).withProperty(DECAYABLE, (meta & 4) == 0)
				.withProperty(CHECK_DECAY, (meta & 8) > 0);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT, DECAYABLE, CHECK_DECAY });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (EnumLeave leave : EnumLeave.BROAD_LEAVES) {
			list.add(new ItemStack(itemIn, 1, leave.getMetadata()-metaSlide));
		}
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		IBlockState state = world.getBlockState(pos);
		return new ArrayList(Arrays.asList(new ItemStack(this, 1, ((EnumLeave) state.getValue(VARIANT)).getMetadata()-metaSlide)));
	}

	// 拡張性0abstractクソゴミ仕様
	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}

	public String getLeaveName(int metadata) {
		return EnumLeave.getLeave(metadata+4).getName();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(DataManager.getBlock(SakuraSapling.class));
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, ((EnumLeave) state.getValue(VARIANT)).getMetadata()-metaSlide);
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

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
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
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (placer != null) {
			worldIn.setBlockState(pos, state.withProperty(DECAYABLE, false),4);
		}
	}

}
