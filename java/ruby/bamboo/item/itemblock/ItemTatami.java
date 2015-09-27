package ruby.bamboo.item.itemblock;

import net.minecraft.block.Block;

public class ItemTatami extends MetaItem {

    public enum EnumType implements IEnumTex {
        NOMAL(0, "tatami"),
        NS(1, "tatami_ns"),
        TAN(2, "tatami_tan"),
        TAN_NS(3, "tatami_tan_ns");

        EnumType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getJsonName() {
            return name;
        }

    }

    public ItemTatami(Block block) {
        super(block);
    }

    @Override
    public IEnumTex[] getName() {
        return EnumType.values();
    }

}
