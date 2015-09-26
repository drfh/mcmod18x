package ruby.bamboo.item.itemblock;

import net.minecraft.block.Block;

public class ItemTatami extends MetaItem{

	public ItemTatami(Block block) {
		super(block);
	}


	@Override
	public String[] getName() {
		return new String[] { "tatami", "tatami_ns" };
	}


}
