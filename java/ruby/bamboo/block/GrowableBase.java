package ruby.bamboo.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ruby.bamboo.core.Constants;

public abstract class GrowableBase extends BlockBush implements IGrowable {

    public final static PropertyInteger AGE = PropertyInteger.create(Constants.AGE, 0, 4);

    public GrowableBase() {
        super();
        this.setCreativeTab((CreativeTabs) null);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.disableStats();
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((Integer) state.getValue(AGE)).intValue();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, AGE);
    }

    public void tryBonemealGrow(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        int meta = this.getMetaFromState(state) + MathHelper.getRandomIntegerInRange(world.rand, 1, 2);

        if (meta > this.getMaxGrowthStage()) {
            meta = this.getMaxGrowthStage();
        }
        world.setBlockState(pos, state.withProperty(AGE, meta), 2);
    }

    public abstract Item getSeed();

    // 実
    public abstract Item getProduct();

    @Override
    public abstract net.minecraftforge.common.EnumPlantType getPlantType(IBlockAccess world, BlockPos pos);

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);

        if (world.getLightFromNeighbors(pos.up()) >= 9) {
            int l = this.getMetaFromState(state);

            if (l < this.getMaxGrowthStage()) {
                float f = this.getGrowRate(state.getBlock(), world, pos);

                if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
                    ++l;
                    state.withProperty(AGE, l);
                    world.setBlockState(pos, state, 2);
                }
            }
        }
    }

    public float getGrowRate(Block block, World world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos1 = pos.down();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                IBlockState iblockstate = world.getBlockState(blockpos1.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(world, blockpos1.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) block)) {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(world, blockpos1.add(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos2 = pos.north();
        BlockPos blockpos3 = pos.south();
        BlockPos blockpos4 = pos.west();
        BlockPos blockpos5 = pos.east();
        boolean flag = block == world.getBlockState(blockpos4).getBlock() || block == world.getBlockState(blockpos5).getBlock();
        boolean flag1 = block == world.getBlockState(blockpos2).getBlock() || block == world.getBlockState(blockpos3).getBlock();

        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = block == world.getBlockState(blockpos4.north()).getBlock() || block == world.getBlockState(blockpos5.north()).getBlock() || block == world.getBlockState(blockpos5.south()).getBlock() || block == world.getBlockState(blockpos4.south()).getBlock();

            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, 0);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ((Integer) state.getValue(AGE)).intValue() == this.getMaxGrowthStage() ? this.getProduct() : this.getSeed();
    }

    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        java.util.List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
        int age = ((Integer) state.getValue(AGE)).intValue();
        Random rand = world instanceof World ? ((World) world).rand : new Random();

        if (age >= this.getMaxGrowthStage()) {
            int k = 3 + fortune;

            for (int i = 0; i < 3 + fortune; ++i) {
                if (rand.nextInt(15) <= age) {
                    ret.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
        return ret;
    }

    public abstract int getMaxGrowthStage();

    // blockに対して設置できるか？
    @Override
    public abstract boolean canPlaceBlockOn(Block block);

    // 最大成長状態？
    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return ((Integer) state.getValue(AGE)).intValue() < this.getMaxGrowthStage();
    }

    // 骨粉は有効か
    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    // 骨粉を使用された時に呼ばれる
    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        this.tryBonemealGrow(world, pos);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
        return new ItemStack(this.getSeed());
    }
}
