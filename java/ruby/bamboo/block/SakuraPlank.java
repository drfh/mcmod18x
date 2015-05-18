package ruby.bamboo.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.core.init.EnumMaterial;

@BambooBlock(name = "sakura_planks", createiveTabs = EnumCreateTab.TAB_BAMBOO, material = EnumMaterial.GROUND)
public class SakuraPlank extends AxisBase implements ICustomState {
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", SakuraPlank.EnumType.class);

	public SakuraPlank(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, SakuraPlank.EnumType.SAKURA).withProperty(AXIS, EnumFacing.Axis.Y));
		this.setHardness(0.2F);
		this.setResistance(1F);
	}

	@Override
	public Object getCustomState() {
		return (new StateMap.Builder()).setProperty(VARIANT).setBuilderSuffix("_planks").build();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return ((EnumType) state.getValue(VARIANT)).getMetadata();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		EnumType[] aenumtype = EnumType.values();
		int i = aenumtype.length;

		for (int j = 0; j < i; ++j) {
			EnumType enumtype = aenumtype[j];
			list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
		}
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT, AXIS });
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
