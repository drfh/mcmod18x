package ruby.bamboo.block.decoration;

import net.minecraft.block.material.Material;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.init.EnumCreateTab;

public enum EnumDecoration {
    KAWARA("kawara", Material.ground, EnumCreateTab.TAB_BAMBOO, 7);

    public static final String SLAB = "_slab";
    public static final String DOUBLE_SLAB = "_double_slab";
    public static final String STAIRS = "_stairs";

    private String name;
    private Material material;
    private EnumCreateTab tab;

    private byte typeFlg;

    //flg 1:NOMAL 2:HALF 4:STAIR 3:ALL
    EnumDecoration(String name, Material material, EnumCreateTab tab, int typeFlg) {
        this.name = name;
        this.material = material;
        this.tab = tab;
        this.typeFlg = (byte) typeFlg;
    }

    public String getName() {
        return this.name;
    }

    public String getModName() {
        return Constants.MODID + Constants.DMAIN_SEPARATE + this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public EnumCreateTab getCreateTab() {
        return this.tab;
    }

    public byte getTypeFlg() {
        return this.typeFlg;
    }

}
