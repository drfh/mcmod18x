package ruby.bamboo.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialTransparent;

public class MaterialNone extends MaterialTransparent{
    public static MaterialNone instance = new MaterialNone();

    public MaterialNone() {
        super(MapColor.airColor);
        setNoPushMobility();
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }
}
