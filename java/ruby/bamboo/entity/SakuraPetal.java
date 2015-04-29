package ruby.bamboo.entity;

import ruby.bamboo.core.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SakuraPetal extends Entity {

	float rx, ry, rz;
	boolean xflg = true;
	boolean yflg = true;
	boolean zflg = true;
	boolean stopFall;
	String texPath;
	int texNum;
	float particleRed;
	float particleGreen;
	float particleBlue;
	float rad;
	int particleAge;
	int particleMaxAge;

	public SakuraPetal(World world) {
		super(world);
		this.stopFall=false;
		this.rad += 0.025 * rand.nextFloat();
		this.setSize(0.2F, 0.2F);
		this.motionX = (rand.nextFloat() - 0.5) * 0.1;
		this.motionY = -0.01D;
		this.motionZ = (rand.nextFloat() - 0.5) * 0.1;
		this.particleAge = 0;
		this.particleMaxAge = rand.nextInt(120) + 60;
		this.rx = world.rand.nextFloat();
		this.ry = world.rand.nextFloat();
		this.rz = world.rand.nextFloat();
		this.texPath=Constants.RESOURCED_DOMAIN + "textures/entitys/petal.png";
	}

	public void setPosition(BlockPos pos)
	{
		this.setPosition(pos.getX(), pos.getY(), pos.getZ());
	}

	public void setColor(int color)
	{
		this.particleRed = (color >> 16) / 255F;
		this.particleGreen = ((color >> 8) & 0xff) / 255F;
		this.particleBlue = (color & 0xff) / 255F;
	}

	public void setTexNum(int num) {
		this.texNum = num;
	}

	public void setTexPath(String path) {
		texPath = path;
	}

	public String getTexPath() {
		return texPath;
	}

	public int getTexNum() {
		return texNum;
	}

	public float getRx() {
		rx += xflg ? rad : -rad;
		xflg = !(xflg ? rx > 1 : rx > -1);
		return rx;
	}

	public float getRy() {
		ry += yflg ? rad : -rad;
		yflg = !(yflg ? ry > 1 : ry > -1);
		return ry;
	}

	public float getRz() {
		rz += zflg ? rad : -rad;
		zflg = !(zflg ? rz > 1 : rz > -1);
		return rz;
	}

	public float getRedColorF() {
		return this.particleRed;
	}

	public float getGreenColorF() {
		return this.particleGreen;
	}

	public float getBlueColorF() {
		return this.particleBlue;
	}

	public void setCustomPetal(IBlockState state) {
		this.texPath = ((ICustomPetal) state.getBlock()).getTexPath(state);
		this.texNum = ((ICustomPetal) state.getBlock()).getTexNum(state);
	}

	public interface ICustomPetal {

		int getTexNum(IBlockState state);

		String getTexPath(IBlockState state);

	}

	public void setMotion(double mx, double my, double mz) {
		float randomF = (float) (Math.random() + Math.random() + 1.0D) * 0.15F;
		float sq = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
		this.motionX = mx / sq * randomF;
		this.motionY = my / sq;
		this.motionZ = mz / sq * randomF;
	}

	public void setStopFall() {
		stopFall = true;
		particleAge = 0;
		rad = 0.002F;
		motionX = motionZ = 0;
	}

	public boolean isStopFall() {
		return stopFall;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (particleAge++ >= particleMaxAge) {
			setDead();
		}

		if (!stopFall) {
			motionY -= 0.004D;
		} else {
			if (motionY != 0) {
				motionY /= 1.2;
			}
		}
		moveEntity(motionX, motionY, motionZ);
		handleWaterMovement();
		motionX *= 0.95D;
		motionY *= 0.95D;
		motionZ *= 0.95D;
		if (this.isInWater()) {
			if (!isStopFall()) {
				setStopFall();
			}
		}

		if (onGround) {
			rad = 0.0001F;
		}

	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
	}

}
