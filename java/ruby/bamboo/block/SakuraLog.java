package ruby.bamboo.block;

import java.util.List;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.BambooData.BambooBlock.StateCustom;
import ruby.bamboo.core.init.EnumCreateTab;

/*
 * さくら？
 * メモ：VARIANTは種類であることは確かだが、何らかのメソッドで指定しないとただのメタデータとしてjsonの中身を覗く＝読み込まない
 * 何かすると別名ファイルをよむようだが詳細は謎
 * 
 */

@BambooBlock(name = "sakura_log", createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class SakuraLog extends BlockLog {
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumType.class);

	public SakuraLog() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.SAKURA).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemIn, 1, EnumType.SAKURA.getMetadata()));
	}

	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata((meta & 3)));

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
		int i = b0 | ((EnumType) state.getValue(VARIANT)).getMetadata();

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
		return new ItemStack(Item.getItemFromBlock(this), 1, ((EnumType) state.getValue(VARIANT)).getMetadata());
	}

	public int damageDropped(IBlockState state) {
		return ((EnumType) state.getValue(VARIANT)).getMetadata();
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

	@StateCustom
	public IStateMapper getCustomState() {
		return (new StateMap.Builder()).setProperty(VARIANT).setBuilderSuffix("_log").build();
	}

	// あとから種類増やして拡張する…？
	public static enum EnumType implements IStringSerializable {
		SAKURA(0, "sakura"),
		// SPRUCE(1, "spruce"),
		// BIRCH(2, "birch"),
		// JUNGLE(3, "jungle"),
		// ACACIA(4, "acacia"),
		// DARK_OAK(5, "dark_oak", "big_oak")
		;
		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;

		private EnumType(int meta, String name) {
			this(meta, name, name);
		}

		private EnumType(int meta, String name, String unlocalizedName) {
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		public int getMetadata() {
			return this.meta;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return Constants.RESOURCED_DOMAIN + this.name;
		}

		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}

		static {
			EnumType[] var0 = values();
			int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				EnumType var3 = var0[var2];
				META_LOOKUP[var3.getMetadata()] = var3;
			}
		}
	}
}
