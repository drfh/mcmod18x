package ruby.bamboo.core.init;

import java.util.Map;

import net.minecraft.item.ItemStack;

public interface IOreNameable {
	public Map<String, ItemStack> getOreName(Map<String,ItemStack> nameMap);
}
