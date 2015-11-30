package ruby.bamboo.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.core.init.BambooData.BambooBlock;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.item.RiceSeed;
import ruby.bamboo.item.Straw;

@BambooBlock(createiveTabs = EnumCreateTab.NONE)
public class RicePlant extends GrowableBase {

    @Override
    public Item getSeed() {
        return DataManager.getItem(RiceSeed.class);
    }

    @Override
    public Item getProduct() {
        return DataManager.getItem(Straw.class);
    }

    @Override
    public int getMaxGrowthStage() {
        return 4;
    }

    @Override
    public boolean canPlaceBlockOn(Block block) {
        return block == Blocks.farmland;
    }

    @Override
    public float getGrowRate(Block block, World world, BlockPos pos) {
        return super.getGrowRate(block, world, pos) * 0.525F;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }
}
