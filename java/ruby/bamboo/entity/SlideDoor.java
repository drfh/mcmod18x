package ruby.bamboo.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruby.bamboo.core.DataManager;
import ruby.bamboo.item.ItemSlideDoor;

public class SlideDoor extends Entity {
    private byte closeTimer;

    private static final byte DIRECTION = 17;
    private static final byte ISMIRROR = 18;
    private static final byte ISMOVE = 19;
    private static final byte DOORID = 20;
    private static final byte ISSTOP = 22;
    private static final byte MOVEDIR = 23;
    private static EnumSlideDoor[] tex = EnumSlideDoor.values();

    public SlideDoor(World world) {
        super(world);
        setSize(1F, 2F);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return isBlend() ? pass == 1 : pass == 0;
    }

    @Override
    public boolean interactFirst(EntityPlayer par1EntityPlayer) {
        if (par1EntityPlayer.isSneaking()) {
            setDataisStop(!getDataIsStop());
            par1EntityPlayer.swingItem();
            return true;
        }

        if (getDataIsStop()) {
            if (!getDataMoveflg()) {
                doorOpen(par1EntityPlayer);
            } else {
                doorClose();
            }

            par1EntityPlayer.swingItem();
            return true;
        }

        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !isDead;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return getEntityBoundingBox();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
    {

    }

    @Override
    public void applyEntityCollision(Entity entityIn)
    {

    }

    // 1.3.2用少し時間が経つとYが1.5ほど上がるバグの対策
    // ついでにブロックやEntityの衝突時yが変わるのを見た目だけ対策

    @Override
    public AxisAlignedBB getCollisionBox(Entity par1Entity) {
        return null;
    }

    public String getTex() {
        return getEnumSlideDoor(getDataDoorId()).getTex();
    }

    public void setMirror(boolean b) {
        setDataMirror(b);
    }

    public boolean isMirror() {
        return getDataMirror();
    }

    public EnumFacing getDir() {
        return getDataDir();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        setDataDir(nbttagcompound.getByte("dir"));
        setDataMirror(nbttagcompound.getBoolean("mirror"));
        setDataMoveflg(nbttagcompound.getBoolean("moveflg"));
        setDataisStop(nbttagcompound.getBoolean("isStop"));
        //
        //        if (getDataDir() == 0 || getDataDir() == 3) {
        //            setDataMovedir((byte) -1);
        //        } else {
        //            setDataMovedir((byte) 1);
        //        }

        if (nbttagcompound.hasKey("doorId")) {
            setDataDoorId(nbttagcompound.getShort("doorId"));
        } else {
            if (nbttagcompound.getString("toptex").indexOf("husuma") != -1) {
                setDataDoorId(EnumSlideDoor.HUSUMA.getId());
            } else {
                setDataDoorId(EnumSlideDoor.SHOZI.getId());
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setByte("dir", (byte) getDataDir().getHorizontalIndex());
        nbttagcompound.setBoolean("mirror", getDataMirror());
        nbttagcompound.setBoolean("moveflg", getDataMoveflg());
        nbttagcompound.setBoolean("isStop", getDataIsStop());
        nbttagcompound.setByte("doorId", getDataDoorId());
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (!worldObj.isRemote && !getDataIsStop() && !isDead) {
            EntityPlayer entityplayer = null;

            if (par1DamageSource.damageType == "player") {
                entityplayer = (EntityPlayer) par1DamageSource.getEntity();
                setDead();
            } else {
                return false;
            }

            if (entityplayer != null && entityplayer.capabilities.isCreativeMode) {
                return true;
            }

            dropItem(getDataDoorId());
        }
        if (getDataIsStop() && par1DamageSource.getEntity() != null) {
            double offsetPosX = posX;
            double offsetPosZ = posZ;
            switch (par1DamageSource.getEntity().getHorizontalFacing()) {
            case EAST:
                offsetPosX += 0.05;
                break;
            case NORTH:
                offsetPosZ -= 0.05;
                break;
            case SOUTH:
                offsetPosZ += 0.05;
                break;
            case WEST:
                offsetPosX -= 0.05;
                break;
            case UP:
                break;
            case DOWN:
                break;
            default:
                break;
            }

            setPosition(offsetPosX, posY, offsetPosZ);
        }
        return true;
    }

    private void dropItem(short damage) {
        this.entityDropItem(new ItemStack(DataManager.getItem(ItemSlideDoor.class), 1, damage), 1F);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (!getDataIsStop() && !getDataMoveflg()) {
            doorOpen(par1EntityPlayer);
        }

        closeTimer = 10;
    }

    @Override
    public void onUpdate() {
        // プレイヤー以外の衝突チェック用
        if (!worldObj.isRemote) {
            List collideEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D));

            if (collideEntity != null) {
                Iterator ite = collideEntity.iterator();

                while (ite.hasNext()) {
                    Entity entity = (Entity) ite.next();

                    if (!entity.isDead && entity instanceof EntityTameable) {
                        if (!getDataIsStop() && !getDataMoveflg()) {
                            doorOpen(entity);
                        }

                        closeTimer = 30;
                    }
                }
            }
        }
        this.noClip=true;
        if (!getDataIsStop()) {
            if (closeTimer < 0 && getDataMoveflg()) {
                doorClose();
            } else {
                closeTimer--;
            }
        }
    }

    private void doorOpen(Entity entity) {
        if (!getDataMoveflg() && isOpendir(entity)) {
            EnumFacing facing = this.getDir();
            if (this.getDataMirror()) {
                facing = facing.getOpposite();
            }
            switch (facing.getAxis()) {
            case X:
                posZ -= facing.getAxisDirection().getOffset();
                this.setEntityBoundingBox(getEntityBoundingBox().offset(0, 0, -facing.getAxisDirection().getOffset()));
                break;
            case Z:
                posX += facing.getAxisDirection().getOffset();
                this.setEntityBoundingBox(getEntityBoundingBox().offset(facing.getAxisDirection().getOffset(), 0, 0));
                break;
            default:
                break;

            }
            setDataMoveflg(true);

        }
        //        if (!getDataMoveflg() && isOpendir(0, 2, entity)) {
        //            posX -= getDataMovedir();
        //            getEntityBoundingBox()().offset(-getDataMovedir(), 0, 0);
        //            setDataMoveflg(true);
        //        }
        //
        //        if (!getDataMoveflg() && isOpendir(1, 3, entity)) {
        //            posZ -= getDataMovedir();
        //            getEntityBoundingBox()().offset(0, 0, -getDataMovedir());
        //            setDataMoveflg(true);
        //        }
    }

    private void doorClose() {
        if (getDataMoveflg()) {
            EnumFacing facing = this.getDir();
            if (this.getDataMirror()) {
                facing = facing.getOpposite();
            }
            switch (facing.getAxis()) {
            case X:
                posZ += facing.getAxisDirection().getOffset();
                this.setEntityBoundingBox(getEntityBoundingBox().offset(0, 0, facing.getAxisDirection().getOffset()));
                break;
            case Z:
                posX -= facing.getAxisDirection().getOffset();
                this.setEntityBoundingBox(getEntityBoundingBox().offset(-facing.getAxisDirection().getOffset(), 0, 0));
                break;
            default:
                break;

            }
            setDataMoveflg(false);

        }
        //        if (getDataMoveflg() && (getDataDir() == 0 || getDataDir() == 2)) {
        //            posX += getDataMovedir();
        //            getEntityBoundingBox()().offset(getDataMovedir(), 0, 0);
        //            setDataMoveflg(false);
        //        }
        //
        //        if (getDataMoveflg() && (getDataDir() == 1 || getDataDir() == 3)) {
        //            posZ += getDataMovedir();
        //            getEntityBoundingBox()().offset(0, 0, getDataMovedir());
        //            setDataMoveflg(false);
        //        }
    }

    private boolean isOpendir(Entity entity) {
        // 向きチェック、同一方向もしくは背面
        if (entity.getHorizontalFacing() != this.getDir() && entity.getHorizontalFacing() != this.getDir().getOpposite()) {
            return false;
        }

        return this.getDistanceToEntity(entity) < 1.25F;
    }

    /*
     * nbttagcompound.setByte("dir", direction);
     * nbttagcompound.setBoolean("mirror", mirror);
     * nbttagcompound.setBoolean("moveflg", moveflg);
     * nbttagcompound.setString("toptex", toptex);
     * nbttagcompound.setString("bottomtex", bottomtex);
     * nbttagcompound.setInteger("itemid", itemID);
     * nbttagcompound.setBoolean("isStop",isStop);(非 Javadoc)
     *
     * @see net.minecraft.src.Entity#entityInit()
     */

    @Override
    protected void entityInit() {
        dataWatcher.addObject(DIRECTION, (byte) 0);
        dataWatcher.addObject(ISMIRROR, (byte) 0);
        dataWatcher.addObject(ISMOVE, (byte) 0);
        dataWatcher.addObject(ISSTOP, (byte) 0);
        dataWatcher.addObject(MOVEDIR, (byte) 0);
        dataWatcher.addObject(DOORID, (byte) 0);
    }

    // data getter
    private EnumFacing getDataDir() {
        return EnumFacing.getHorizontal(dataWatcher.getWatchableObjectByte(DIRECTION));
    }

    private boolean getDataMirror() {
        return dataWatcher.getWatchableObjectByte(ISMIRROR) == 1;
    }

    private boolean getDataMoveflg() {
        return dataWatcher.getWatchableObjectByte(ISMOVE) == 1;
    }

    private byte getDataMovedir() {
        return dataWatcher.getWatchableObjectByte(MOVEDIR);
    }

    private boolean getDataIsStop() {
        return dataWatcher.getWatchableObjectByte(ISSTOP) == 1;
    }

    private byte getDataDoorId() {
        return dataWatcher.getWatchableObjectByte(DOORID);
    }

    // data setter
    public SlideDoor setDataDir(byte b) {
        dataWatcher.updateObject(DIRECTION, b);
        return this;
    }

    public void setDataMirror(boolean flg) {
        dataWatcher.updateObject(ISMIRROR, flg ? (byte) 1 : (byte) 0);
    }

    public void setDataMoveflg(boolean flg) {
        dataWatcher.updateObject(ISMOVE, flg ? (byte) 1 : (byte) 0);
    }

    public void setDataMovedir(byte dir) {
        dataWatcher.updateObject(MOVEDIR, dir);
    }

    public SlideDoor setDataisStop(boolean flg) {
        dataWatcher.updateObject(ISSTOP, flg ? (byte) 1 : (byte) 0);
        return this;
    }

    public SlideDoor setDataDoorId(int id) {
        dataWatcher.updateObject(DOORID, (byte) id);
        return this;
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    public boolean isBlend() {
        return getEnumSlideDoor(getDataDoorId()).isBlend();
    }

    private EnumSlideDoor getEnumSlideDoor(int id) {
        if (id < tex.length) {
            return tex[id];
        } else {
            setDataDoorId(EnumSlideDoor.HUSUMA.getId());
            return EnumSlideDoor.HUSUMA;
        }
    }
}
