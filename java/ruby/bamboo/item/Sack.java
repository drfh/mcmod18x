package ruby.bamboo.item;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.BambooCore;
import ruby.bamboo.core.init.BambooData.BambooItem;
import ruby.bamboo.core.init.EnumCreateTab;
import ruby.bamboo.gui.GuiHandler;

@BambooItem(createiveTabs = EnumCreateTab.TAB_BAMBOO)
public class Sack extends Item {
	// private static ItemStack backkup;
	private Field remainingHighlightTicks;

	public Sack() {
		super();
		setHasSubtypes(true);
		setMaxDamage(1025);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par3World, EntityPlayer par2EntityPlayer) {
		if (par1ItemStack.getTagCompound() == null) {
			par2EntityPlayer.openGui(BambooCore.instance, GuiHandler.GUI_SACK, par3World, (int) par2EntityPlayer.posX, (int) par2EntityPlayer.posY, (int) par2EntityPlayer.posZ);
			return par1ItemStack;
		}

		short count = par1ItemStack.getTagCompound().getShort("count");
		String type = par1ItemStack.getTagCompound().getString("type");
		short meta = par1ItemStack.getTagCompound().getShort("meta");

		if (!isStorage(getItem(type))) {
			return par1ItemStack;
		}

		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par3World, par2EntityPlayer, par2EntityPlayer.isSneaking());

		if (movingobjectposition == null) {
			ItemStack[] is = par2EntityPlayer.inventory.mainInventory;

			for (int i = 0; i < is.length; i++) {
				if (is[i] == null) {
					continue;
				}

				if (is[i].getItem() == getItem(type) && is[i].getItemDamage() == meta) {
					if ((count + is[i].stackSize) < getMaxDamage()) {
						count += is[i].stackSize;
						is[i] = null;
					} else {
						is[i].stackSize -= (getMaxDamage() - count - 1);
						count = (short) (getMaxDamage() - 1);
					}
				}
			}

			par1ItemStack.getTagCompound().setShort("count", count);
			par1ItemStack.setItemDamage(getMaxDamage() - count);
		} else {
			int stacksize = getItem(type).onItemRightClick(new ItemStack(getItem(type), count, meta), par3World, par2EntityPlayer).stackSize;

			if (stacksize < count) {
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + count + stacksize);
				count -= stacksize;
				par1ItemStack.getTagCompound().setShort("count", count);
			}
		}

		return par1ItemStack;
	}

	private Item getItem(String str) {
		return (Item) Item.itemRegistry.getObject(str);
	}

	private boolean isStorage(Item item) {
		return item instanceof ItemBlock ? true : item instanceof ItemSeeds ? true : item instanceof ItemSeedFood ? true : false;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (stack.getTagCompound() == null) {
			return false;
		}

		short count = stack.getTagCompound().getShort("count");
		String type = stack.getTagCompound().getString("type");
		short meta = stack.getTagCompound().getShort("meta");

		if (!isStorage(getItem(type))) {
			return false;
		}

		if (!world.canMineBlockBody(player, pos)) {
			return false;
		}

		if (count != 0) {
			ItemStack is = new ItemStack(getItem(type), 1, meta);
			if (Block.getBlockFromItem(getItem(type)) != Blocks.air) {
				if (getItem(type) instanceof ItemBlock) {
					if (getItem(type).onItemUse(is, player, world, pos, side, hitX, hitY, hitZ)) {
						stack.setItemDamage(stack.getItemDamage() + 1);
						count--;
						stack.getTagCompound().setShort("count", count);
						player.swingItem();
					}
				}
			}
			if (getItem(type) instanceof ItemSeeds || getItem(type) instanceof ItemSeedFood) {
				for (int i = -2; i <= 2; i++) {
					for (int j = -2; j <= 2; j++) {
						if (getItem(type).onItemUse(is, player, world, pos.add(i, 0, j), side, hitX, hitY, hitZ)) {
							stack.setItemDamage(stack.getItemDamage() + 1);
							count--;
							stack.getTagCompound().setShort("count", count);
							player.swingItem();

							if (count < 1) {
								return true;
							}
						}
					}
				}
			}
		}

		return true;
	}

	public void release(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par1ItemStack.getTagCompound() != null && !par2World.isRemote) {
			short count = par1ItemStack.getTagCompound().getShort("count");
			String type = par1ItemStack.getTagCompound().getString("type");
			short meta = par1ItemStack.getTagCompound().getShort("meta");
			double x = par3EntityPlayer.posX;
			double y = par3EntityPlayer.posY;
			double z = par3EntityPlayer.posZ;

			if (count > 0) {
				par2World.spawnEntityInWorld(new EntityItem(par2World, x, y, z, new ItemStack(getItem(type), count, meta)));
				count = 0;
			}

			par1ItemStack.setTagCompound(null);
		}
	}

	private void returnItem(EntityPlayer entity, ItemStack is) {
		if (!entity.inventory.addItemStackToInventory(is)) {
			entity.entityDropItem(is, 0.5F);
		}
	}

	@SideOnly(Side.CLIENT)
	private void renderToolHighlight() {
		// リフレクションじゃないほうがいいかな～？
		try {
			if (remainingHighlightTicks == null) {
				for (int i = 0; i < GuiIngame.class.getDeclaredFields().length; i++) {
					if (GuiIngame.class.getDeclaredFields()[i].getType() == ItemStack.class) {
						remainingHighlightTicks = GuiIngame.class.getDeclaredFields()[i - 1];
					}
				}
				remainingHighlightTicks.setAccessible(true);
			}
			remainingHighlightTicks.setInt(FMLClientHandler.instance().getClient().ingameGUI, 40);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		String name = super.getItemStackDisplayName(par1ItemStack);
		if (par1ItemStack.getTagCompound() != null && getItem(par1ItemStack.getTagCompound().getString("type")) != null) {
			name += (":"
					+ getItem(par1ItemStack.getTagCompound().getString("type")).getItemStackDisplayName(new ItemStack(getItem(par1ItemStack.getTagCompound().getString("type")), 1, par1ItemStack
							.getTagCompound().getShort("meta"))) + ":" + par1ItemStack.getTagCompound().getShort("count")).trim();
		}

		return name;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (par5 && par2World.isRemote) {
			renderToolHighlight();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return par1ItemStack.getTagCompound() != null;
	}
}
