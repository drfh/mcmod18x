package ruby.bamboo.proxy;

import java.util.ArrayList;
import java.util.List;

import ruby.bamboo.core.BambooCore;
import ruby.bamboo.core.BambooData.BambooBlock;
import ruby.bamboo.core.BambooData.BambooItem;
import ruby.bamboo.core.Constants;
import ruby.bamboo.core.DataLoader;
import ruby.bamboo.core.DataManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * クライアントプロクシ
 * 
 * @author Ruby
 * 
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
	}

	@Override
	public void init() {
		super.init();
		registJson();
	}

	/**
	 * json登録の自動化 1IDに対して複数タイプは名前の後ろに0(連番)付与
	 */
	private void registJson() {
		List<ItemStack> isList = new ArrayList<ItemStack>();
		List<String> tmpNameList = new ArrayList<String>();
		for (String name : DataManager.getRegstedNameArray()) {
			Item item = Item.getByNameOrId(name);
			isList.clear();
			item.getSubItems(item, item.getCreativeTab(), isList);
			if (isList.size() == 1) {
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(name, "inventory"));
			} else {
				// TODO:複数IDパターン要チェック
				for (int i = 0; i < isList.size(); i++) {
					ModelBakery.addVariantName(item, name + i);
					Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, new ModelResourceLocation(name + i, "inventory"));
				}
			}
		}
	}
}
