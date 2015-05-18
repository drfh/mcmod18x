package ruby.bamboo.block;

import java.util.List;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.EnumCreateTab;

/*
 * さくら？
 * メモ：カスタムステートでjsonの切り替えできるっぽい
 * 
 */

@BambooBlock(name = "sakura_log", createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class SakuraLog extends BlockLog implements ICustomState {
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", SakuraPlank.EnumType.class);

	public SakuraLog() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, SakuraPlank.EnumType.SAKURA).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemIn, 1, SakuraPlank.EnumType.SAKURA.getMetadata()));
	}

	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, SakuraPlank.EnumType.byMetadata((meta & 3)));

		switch (meta & 12) {
		case 0:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
			break;
		case 4:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
			break;
		case 8:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
			break;
		default:
			iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}

		return iblockstate;
	}

	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | ((SakuraPlank.EnumType) state.getValue(VARIANT)).getMetadata();

		switch (SakuraLog.SwitchEnumAxis.AXIS_LOOKUP[((BlockLog.EnumAxis) state.getValue(LOG_AXIS)).ordinal()]) {
		case 1:
			i |= 4;
			break;
		case 2:
			i |= 8;
			break;
		case 3:
			i |= 12;
		}

		return i;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT, LOG_AXIS });
	}

	protected ItemStack createStackedBlock(IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, ((SakuraPlank.EnumType) state.getValue(VARIANT)).getMetadata());
	}

	public int damageDropped(IBlockState state) {
		return ((SakuraPlank.EnumType) state.getValue(VARIANT)).getMetadata();
	}

	static final class SwitchEnumAxis {
		static final int[] AXIS_LOOKUP = new int[BlockLog.EnumAxis.values().length];
		static {
			try {
				AXIS_LOOKUP[BlockLog.EnumAxis.X.ordinal()] = 1;
			} catch (NoSuchFieldError var3) {
				;
			}

			try {
				AXIS_LOOKUP[BlockLog.EnumAxis.Z.ordinal()] = 2;
			} catch (NoSuchFieldError var2) {
				;
			}

			try {
				AXIS_LOOKUP[BlockLog.EnumAxis.NONE.ordinal()] = 3;
			} catch (NoSuchFieldError var1) {
				;
			}
		}
	}

	@Override
	public Object getCustomState() {
		return (new StateMap.Builder()).setProperty(VARIANT).setBuilderSuffix("_log").build();
	}
}
