package ruby.bamboo.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;

public class TileJPChest extends ChestBase{

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerChest(playerInventory,this,playerIn);
	}

	@Override
	public String getGuiID() {
		return "bamboo:jpchest";
	}

	@Override
	String getDefaultName() {
		return "container.jpchest";
	}

	@Override
	public int getSizeInventory() {
		return 54;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

}
