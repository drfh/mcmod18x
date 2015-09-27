package ruby.bamboo.render.entity;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import ruby.bamboo.entity.SlideDoor;
import ruby.bamboo.render.model.ModelSlideDoor;

public class RenderSlideDoor extends Render {
    private ModelSlideDoor modelSlideDoor;
    private ModelSlideDoor modelSlideDoorM;
    private static HashMap<String, ResourceLocation> resMap = new HashMap<String, ResourceLocation>();

    public RenderSlideDoor(RenderManager renderManager) {
        super(renderManager);
        modelSlideDoor = new ModelSlideDoor(false);
        modelSlideDoorM = new ModelSlideDoor(true);
    }

    public void renderSlideDoor(SlideDoor entity, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();

        if (entity.isBlend()) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        EnumFacing dir = entity.getDir();
        // System.out.println(byte0);
        GL11.glTranslatef((float) d, (float) d1 + 1, (float) d2);
        GL11.glScalef(0.999375F, 0.999375F, 0.999375F);
        float rotate = 0F;
        switch (dir) {
        case EAST:
            rotate = 90F;
            break;
        case NORTH:
            rotate = 180F;
            break;
        case SOUTH:
            rotate = 0F;
            break;
        case WEST:
            rotate = 270F;
            break;
        default:
            break;
        }


        GL11.glRotatef(rotate, 0.0F, 1.0F, 0.0F);
        // GL11.glTranslatef((float)d, (float)d1, (float)d2);
        //        if (entity.isMirror()) {
        //            GL11.glRotatef((byte0 - 2) * 90.0F, 0.0F, 1.0F, 0.0F);
        //        } else {
        //            GL11.glRotatef(byte0 * 90.0F, 0.0F, 1.0F, 0.0F);
        //        }

        func_110777_b(entity.getTex());

        if (entity.isMirror()) {
            modelSlideDoorM.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        } else {
            modelSlideDoor.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        }

        // 光源処理
        int l = renderManager.worldObj.getCombinedLight(entity.getPosition(), 0);
        int i1 = l % 0x10000;
        int j1 = l / 0x10000;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i1, j1);
        // 光源追加ここまで
        /*
         * if(entity.isBlend()){ GL11.glDisable(GL11.GL_BLEND); }
         */
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderSlideDoor((SlideDoor) entity, d, d1, d2, f, f1);
    }

    protected void func_110777_b(String tex) {
        if (!resMap.containsKey(tex)) {
            resMap.put(tex, new ResourceLocation(tex));
        }

        this.bindTexture(resMap.get(tex));
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
