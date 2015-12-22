package ruby.bamboo.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.EnumCreateTab;

@BambooBlock(createiveTabs = EnumCreateTab.TAB_BAMBOO)

public class Kitunebi extends Block {

    private boolean isVisible = false;

    // 必要になったら
    //public static final PropertyInteger META = PropertyInteger.create(Constants.META, 0, 7);
    public static final PropertyBool VISIBLE = PropertyBool.create(Constants.FLG);

    public Kitunebi() {
        super(MaterialNone.instance);
        //this.setDefaultState(this.blockState.getBaseState().withProperty(META, 0).withProperty(VISIBLE, false));
        this.setDefaultState(this.blockState.getBaseState().withProperty(VISIBLE, false));
        setTickRandomly(true);
        setHardness(0.0F);
        setLightLevel(1F);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        //return this.getDefaultState().withProperty(META, meta & 7).withProperty(VISIBLE, (meta & 8) > 0);
        return this.getDefaultState().withProperty(VISIBLE, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        //int meta= (Integer) state.getValue(META);
        //return meta;
        return 0;
    }

    @Override
    protected BlockState createBlockState() {
        //return new BlockState(this, META, VISIBLE);
        return new BlockState(this, VISIBLE);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        setVisibleFlg(worldIn, pos, state,rand);
    }

    private void setVisibleFlg(World world, BlockPos pos, IBlockState state,Random rand) {
        if (world.isRemote) {
            ItemStack is = FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem();
            isVisible = false;

            if (is != null) {
                Item item = is.getItem();

                if (item instanceof ItemBlock) {
                    if (Block.getBlockFromItem(item) == this) {
                        isVisible = true;
                    }
                }
            }

            if (isVisible) {
                //world.setBlockState(pos, state.withProperty(VISIBLE, true), 3);
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, pos.getX()+rand.nextFloat(), pos.getY()+rand.nextFloat(), pos.getZ()+rand.nextFloat(), 0,0,0, 0);
            } else {
                setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            }
        }
    }

}
