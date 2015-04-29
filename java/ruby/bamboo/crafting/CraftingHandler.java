package ruby.bamboo.crafting;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import ruby.bamboo.item.Sack;

public class CraftingHandler {

    @SubscribeEvent
    public void onCrafting(ItemCraftedEvent event) {
        if (event.player.worldObj.isRemote) {
            return;
        }

        if (event.crafting.getItem() instanceof Sack) {
            int lim = event.craftMatrix.getSizeInventory();

            while (lim-- > 0) {
                if (event.craftMatrix.getStackInSlot(lim) == null) {
                    continue;
                }

                Item target = event.craftMatrix.getStackInSlot(lim).getItem();

                if (target instanceof Sack) {
                    ((Sack) target).release(event.craftMatrix.getStackInSlot(lim), event.player.worldObj, event.player);
                }
            }
        } 
    }
}
