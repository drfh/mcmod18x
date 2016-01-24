package ruby.bamboo.block.decoration;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class DecorationBlock extends Block {

    public DecorationBlock(Material materialIn) {
        super(materialIn);
        this.setHardness(0.5F);
        this.setResistance(300F);
    }

}
