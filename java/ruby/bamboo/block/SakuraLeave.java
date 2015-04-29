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
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.BambooData.BambooBlock.StateIgnore;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.entity.SakuraPetal;
import ruby.bamboo.entity.SakuraPetal.ICustomPetal;
import ruby.bamboo.item.itemblock.ItemSakuraLeave;

import com.google.common.base.Predicate;

@BambooBlock(name = "sakura_leave", itemBlock = ItemSakuraLeave.class, createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class SakuraLeave extends BlockLeaves implements ILeave, ICustomPetal {

	// クソゴミ仕様に合わせるのつらい
	public final static PropertyEnum VARIANT = PropertyEnum.create("variant", EnumLeave.class, new Predicate()
	{
		public boolean apply(EnumLeave type)
		{
			;
			return type.getMetadata() < 4;
		}

		public boolean apply(Object p_apply_1_)
		{
			return this.apply((EnumLeave) p_apply_1_);
		}
	});;

	public SakuraLeave() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumLeave.WHITE).withProperty(CHECK_DECAY, Boolean.valueOf(true))
				.withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.fancyGraphics = true;
		this.setLightLevel(0.75F);
		this.setHardness(0);
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

	// どうやらメタデータは15までというゴミ糞カス仕様のようだ、stateにした意味無いクソゴミボケ仕様
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumLeave.getLeave(meta & 3)).withProperty(DECAYABLE, (meta & 4) == 0)
				.withProperty(CHECK_DECAY, (meta & 8) > 0);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT, DECAYABLE, CHECK_DECAY });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (EnumLeave leave : EnumLeave.SAKURA_LEAVES) {
			list.add(new ItemStack(itemIn, 1, leave.getMetadata()));
		}
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		IBlockState state = world.getBlockState(pos);
		return new ArrayList(Arrays.asList(new ItemStack(this, 1, ((EnumLeave) state.getValue(VARIANT)).getMetadata())));
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(DataManager.getBlock(SakuraSapling.class));
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, ((EnumLeave) state.getValue(VARIANT)).getMetadata());
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
			worldIn.setBlockState(pos, state.withProperty(DECAYABLE, false), 4);
		}
	}

	public enum EnumLeave implements IStringSerializable {
		WHITE(0, 0xFFFFFF, 1, 15),
		PURPLE(1, 0xFFC5FC, 1, 5),
		MAGENTA(2, 0xF09090, 1, 13),
		PINK(3, 0xFFC5CC, 1, 9),
		GREEN(4, 0x3F9E55, 1, 2),
		RED(5, 0xc80010, 2, 1),
		YELLOW(6, 0xf5e600, 3, 11),
		ORANGE(7, 0xFFC600, 3, 14), ;
		EnumLeave(int meta, int color, int petal, int dyeCode) {
			this.meta = (byte) meta;
			this.color = color;
			this.petal = (byte) petal;
		}

		public static final EnumLeave[] SAKURA_LEAVES = { WHITE, PURPLE, MAGENTA, PINK };
		public static final EnumLeave[] BROAD_LEAVES = { GREEN, RED, YELLOW, ORANGE };
		private byte meta;
		private int color;
		private byte petal;

		public static EnumLeave getLeave(int meta) {
			return meta < SAKURA_LEAVES.length ? SAKURA_LEAVES[meta] : BROAD_LEAVES[meta % 4];
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

	@Override
	public IBlockState getLeaveStateFromMeta(int meta) {
		return this.getStateFromMeta(meta);
	}

	@Override
	public int getLeaveRenderColor(IBlockState stateFromMeta) {
		return this.getRenderColor(stateFromMeta);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (rand.nextInt(100) != 0) {
            return;
        }
        if (world.isAirBlock(pos.down())) {
        	SakuraPetal petal=new SakuraPetal(world);
        	petal.setPosition(pos.getX()+ rand.nextFloat(),pos.getY(),pos.getZ()+ rand.nextFloat());
        	petal.setCustomPetal(state);
        	petal.setColor(this.getRenderColor(state));
            world.spawnEntityInWorld(petal);
        }
    }

	@Override
	public int getTexNum(IBlockState state) {
		return ((EnumLeave) state.getValue(VARIANT)).getPetal();
	}

	@Override
	public String getTexPath(IBlockState state) {
		return Constants.RESOURCED_DOMAIN + "textures/entitys/petal.png";
	}

}
