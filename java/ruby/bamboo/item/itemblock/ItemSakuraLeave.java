package ruby.bamboo.item.itemblock;

import ruby.bamboo.block.SakuraLeave;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemLeaves;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSakuraLeave extends ItemBlock{

    private final SakuraLeave leave;

    public ItemSakuraLeave(Block block)
    {
        super(block);
        this.leave = (SakuraLeave) block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int damage)
    {
        return damage;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        return this.leave.getRenderColor(this.leave.getStateFromMeta(stack.getMetadata()));
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + this.leave.getLeaveName(stack.getMetadata());
    }
}
