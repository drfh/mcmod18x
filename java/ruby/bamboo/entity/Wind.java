package ruby.bamboo.entity;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ruby.bamboo.entity.SakuraPetal.ICustomPetal;

public class Wind extends EntityThrowable {
	private int age;
	private int maxAge;

	public Wind(World par1World) {
		super(par1World);
		setSize(5F, 5F);
	}

	public Wind(World par1World, EntityLivingBase par2EntityLiving) {
		super(par1World, par2EntityLiving);
		setSize(5F, 5F);
		maxAge = 5;
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		if (var1.entityHit instanceof EntityLivingBase) {
			if (var1.entityHit.ridingEntity == null && var1.entityHit.riddenByEntity == null) {

				var1.entityHit.motionX = this.motionX;
				var1.entityHit.motionY = this.motionY;
				var1.entityHit.motionZ = this.motionZ;
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (age++ > maxAge) {
			setDead();
		}

		if ((age & 1) == 0) {
			checkBlockCollision();
		}
	}

	
	protected void checkBlockCollision() {
		int var1 = MathHelper.floor_double(this.getEntityBoundingBox().minX + 0.001D);
		int var2 = MathHelper.floor_double(this.getEntityBoundingBox().minY + 0.001D);
		int var3 = MathHelper.floor_double(this.getEntityBoundingBox().minZ + 0.001D);
		int var4 = MathHelper.floor_double(this.getEntityBoundingBox().maxX - 0.001D);
		int var5 = MathHelper.floor_double(this.getEntityBoundingBox().maxY - 0.001D);
		int var6 = MathHelper.floor_double(this.getEntityBoundingBox().maxZ - 0.001D);
		Iterable<BlockPos> posList = BlockPos
				.getAllInBox(new BlockPos(getEntityBoundingBox().minX, getEntityBoundingBox().minY, getEntityBoundingBox().minZ), new BlockPos(getEntityBoundingBox().maxX, getEntityBoundingBox().maxY, getEntityBoundingBox().maxZ));

		for (BlockPos pos : posList) {
			IBlockState state = this.worldObj.getBlockState(pos);

			if (!worldObj.isAirBlock(pos)) {
				if (isRemove(state)) {
					removeLeaves(this.worldObj, pos, state);
				}
			}
		}

	}

	private boolean isRemove(IBlockState state) {
		if (state.getBlock().getMaterial() == Material.leaves || state.getBlock().getMaterial() == Material.vine) {
			return true;
		}
		if (state.getBlock() instanceof BlockDoublePlant) {
			// TODO: 背の高い草限定を追加する必要がある?
			return true;
		}
		return false;
	}

	private void removeLeaves(World par1World, BlockPos pos, IBlockState state) {
		if (par1World.isRemote) {
			SakuraPetal entity = new SakuraPetal(par1World);
			entity.setMotion(this.motionX, this.motionY, this.motionZ);
			entity.setPosition(pos);
			entity.setColor(state.getBlock().getRenderColor(state));

			if (state.getBlock() instanceof ICustomPetal) {
				entity.setCustomPetal(state);
			}

			par1World.spawnEntityInWorld(entity);
		} else {
			state.getBlock().dropBlockAsItem(par1World, pos, state, 0);
			par1World.setBlockToAir(pos);
		}
	}
}