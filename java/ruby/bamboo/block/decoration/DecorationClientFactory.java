package ruby.bamboo.block.decoration;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DecorationClientFactory extends DecorationFactory {

    void registerNormal(EnumDecoration deco) {
        registerJson(deco.getModName());
    }

    void registerDoubleSlab(EnumDecoration deco) {
        ModelLoader.setCustomStateMapper(Block.getBlockFromName(deco.getModName() + deco.DOUBLE_SLAB), (new StateMap.Builder()).addPropertiesToIgnore(DecorationSlab.SEAMLESS).build());
        registerJson(deco.getModName() + deco.DOUBLE_SLAB);
    }

    void registerSlab(EnumDecoration deco) {
        //ModelLoader.setCustomStateMapper(Block.getBlockFromName(deco.getModName() + deco.SLAB), (new StateMap.Builder()).addPropertiesToIgnore(DecorationSlab.HALF).build());
        registerJson(deco.getModName() + deco.SLAB);
    }

    void registerStair(EnumDecoration deco) {
        registerJson(deco.getModName() + deco.STAIRS);
    }

    private void registerJson(String name) {
        Item item = Item.getByNameOrId(name);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
    }

}
