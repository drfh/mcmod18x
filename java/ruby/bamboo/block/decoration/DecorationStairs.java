package ruby.bamboo.block.decoration;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

//メモ
//registerBlock(53, "oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK))).setUnlocalizedName("stairsWood"));

public class DecorationStairs extends BlockStairs{

    protected DecorationStairs(IBlockState modelState) {
        super(modelState);
        this.setHardness(0.5F);
        this.setResistance(300F);
    }

}
