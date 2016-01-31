package ruby.bamboo.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;

public class InventorySack implements IInventory {
    private ItemStack sack;
    private short count;
    private short type;
    private short meta;
    public ItemStack slot0;

    public InventorySack(ItemStack itemStack) {
        this.sack = itemStack;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return slot0;
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.slot0 != null) {
            ItemStack itemstack;

            if (this.slot0.stackSize <= par2) {
                itemstack = this.slot0;
                this.slot0 = null;
                return itemstack;
            } else {
                itemstack = this.slot0.splitStack(par2);

                if (this.slot0.stackSize == 0) {
                    this.slot0 = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.slot0 != null) {
            ItemStack itemstack = this.slot0;
            this.slot0 = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.slot0 = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }


    @Override
    public void markDirty() {
    }

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
	}
}
