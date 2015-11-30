package ruby.bamboo.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.init.BambooData.BambooItem;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.entity.EnumSlideDoor;
import ruby.bamboo.entity.SlideDoor;
import ruby.bamboo.item.itemblock.IEnumTex;
import ruby.bamboo.item.itemblock.ISubTexture;

@BambooItem(createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class ItemSlideDoor extends Item implements ISubTexture {

    public ItemSlideDoor() {
        this.hasSubtypes = true;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        for (IEnumTex tex : getName()) {
            subItems.add(new ItemStack(itemIn, 1, tex.getId()));
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            stack.stackSize--;
            EnumFacing dir = playerIn.getHorizontalFacing();
            SlideDoor entity = new SlideDoor(worldIn).setDataDoorId((short) stack.getItemDamage()).setDataDir((byte) dir.getHorizontalIndex());

            entity.setDataDir((byte) dir.getHorizontalIndex());

            if (playerIn.isSneaking()) {
                entity.setMirror(true);
            }

            entity.setPosition(pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F);
            worldIn.spawnEntityInWorld(entity);
        }

        return true;
    }

    @Override
    public IEnumTex[] getName() {
        return EnumSlideDoor.values();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

}
