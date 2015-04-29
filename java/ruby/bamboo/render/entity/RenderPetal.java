package ruby.bamboo.render.entity;

import java.util.HashMap;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ruby.bamboo.entity.SakuraPetal;
import ruby.bamboo.render.model.ModelPetal;

public class RenderPetal extends Render {
	private ModelPetal modelPetal;
	private static HashMap<String, ResourceLocation> resMap = new HashMap<String, ResourceLocation>();

	public RenderPetal(RenderManager manager) {
		super(manager);
		modelPetal = new ModelPetal();
	}

	public void renderPetal(SakuraPetal entity, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		bindEntityTexture(entity);
		/*
		 * GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		 * GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		 */
		GL11.glColor3f(entity.getRedColorF(), entity.getGreenColorF(), entity.getBlueColorF());
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glRotatef(180.0F, entity.getRx(), entity.getRy(), entity.getRz());
		modelPetal.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glColor3f(1,1,1);
		GL11.glPopMatrix();

	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		renderPetal((SakuraPetal) entity, d, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if (!resMap.containsKey(((SakuraPetal) entity).getTexPath())) {
			resMap.put(((SakuraPetal) entity).getTexPath(), new ResourceLocation(((SakuraPetal) entity).getTexPath()));
		}

		return resMap.get(((SakuraPetal) entity).getTexPath());
	}
}
