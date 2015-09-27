package ruby.bamboo.entity;

import ruby.bamboo.core.Constants;
import ruby.bamboo.item.itemblock.IEnumTex;

public enum EnumSlideDoor implements IEnumTex{
    HUSUMA(0, "husuma", false),
    SHOZI(1, "syouzi", false),
    GLASS(2, "glassdoor", true),
    GGLASS(3, "gridglassdoor", true),
    YUKI(4, "yukimi", true),
    AMADO(5, "amado", false);
    EnumSlideDoor(int id, String iconName, boolean isBlend) {
        this.id = (short) id;
        this.iconName = iconName;
        this.isBlend = isBlend;
        this.tex = Constants.RESOURCED_DOMAIN + "textures/entitys/" + iconName.toLowerCase() + ".png";
    }

    private final short id;
    private final String tex;
    private final boolean isBlend;
    private String jpName;
    private String iconName;

    public int getId() {
        return id;
    }

    public String getTex() {
        return tex;
    }

    public String getIconName() {
        return iconName;
    }

    public boolean isBlend() {
        return isBlend;
    }

    @Override
    public String getJsonName() {
        return iconName;
    }
}
