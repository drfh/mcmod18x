package ruby.bamboo.block.decoration;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.item.itemblock.ItemDecorationSlab;

public class DecorationSlab extends BlockSlab {

    public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");

    public DecorationSlab(Material materialIn) {
        super(materialIn);
        IBlockState iblockstate = this.blockState.getBaseState();
        if (this.isDouble()) {
            iblockstate = iblockstate.withProperty(SEAMLESS, Boolean.valueOf(false));
        } else {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(iblockstate);
        this.setHardness(0.5F);
        this.setResistance(300F);
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }

    @Override
    public boolean isDouble() {
        // なぜこんな拡張性の低い設計なのか不思議。
        return false;
    }

    @Override
    @Deprecated
    public IProperty getVariantProperty() {
        return null;
    }

    @Override
    @Deprecated
    public Object getVariant(ItemStack stack) {
        return null;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Item item = Item.getItemFromBlock(this);
        if (item instanceof ItemDecorationSlab) {
            return Item.getItemFromBlock(((ItemDecorationSlab) item).getSingleSlab());
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos) {
        Item item = Item.getItemFromBlock(this);
        if (item instanceof ItemDecorationSlab) {
            return Item.getItemFromBlock(((ItemDecorationSlab) item).getSingleSlab());
        }
        return null;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();

        if (this.isDouble()) {
            iblockstate = iblockstate.withProperty(SEAMLESS, Boolean.valueOf((meta & 8) != 0));
        } else {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        byte b0 = 0;
        int i = 0;

        if (this.isDouble()) {
            if (((Boolean) state.getValue(SEAMLESS)).booleanValue()) {
                i |= 8;
            }
        } else if (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    @Override
    protected BlockState createBlockState() {
        return this.isDouble() ? new BlockState(this, new IProperty[] { SEAMLESS }) : new BlockState(this, new IProperty[] { HALF });
    }

}
