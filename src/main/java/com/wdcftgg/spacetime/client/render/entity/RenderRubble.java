package com.wdcftgg.spacetime.client.render.entity;

import com.wdcftgg.spacetime.client.model.ModelRubbleBlock;
import com.wdcftgg.spacetime.entity.EntityRubble;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class RenderRubble extends Render<EntityRubble> {

    private final ModelBase rubbleModel = new ModelRubbleBlock();


    public RenderRubble(RenderManager renderManager) {
        super(renderManager);

    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(EntityRubble entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
//        return new ResourceLocation(SpaceTime.MODID, "textures/models/ModelRubbleScrap.png");
    }

    @Override
    public void doRender(EntityRubble rubble, double x, double y, double z, float entityYaw, float partialTicks) {




        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glScalef(0.03F, 0.03F, 0.03F);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(((rubble.ticksExisted + partialTicks) % 360) * 10, 1, 1, 1);

        try {
            int block = rubble.getDataManager().get(EntityRubble.Rubble_Block);
            int meta = rubble.getDataManager().get(EntityRubble.Rubble_Meta);

            Block b = Block.getBlockById(block);
            String modid;
            String res;
            String s = getBlockIconName(b, meta);

            if(getBlockIconName(b, meta) == null || getBlockIconName(b, meta) == "") {

                s = "minecraft:stone";
            }

            String[] split = s.split(":");

            String prefix = "";
            String suffix = "";

            if(split.length == 2) {
                prefix = split[0];
                suffix = split[1];
            } else {
                prefix = "minecraft";
                suffix = s;
            }

            float f = this.rotLerp(rubble.prevRotationYaw, rubble.rotationYaw, partialTicks);
            float f1 = rubble.prevRotationPitch + (rubble.rotationPitch - rubble.prevRotationPitch) * partialTicks;

            this.bindEntityTexture(rubble);
            ResourceLocation resourceLocation = new ResourceLocation(prefix + ":textures/" + suffix + ".png");
            bindTexture(resourceLocation);
            this.rubbleModel.render(rubble, 0.0F, 0.0F, 0.0F, f, f1, 2f);



        } catch(Exception ex) { }

        GL11.glPopMatrix();

    }

    private float rotLerp(float p_188347_1_, float p_188347_2_, float p_188347_3_)
    {
        float f;

        for (f = p_188347_2_ - p_188347_1_; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return p_188347_1_ + p_188347_3_ * f;
    }

    private String getBlockIconName(Block block, int meta) {
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        TextureAtlasSprite texture = dispatcher.getModelForState(block.getStateFromMeta(meta)).getParticleTexture();
        String textureLocation = texture.getIconName();
        return textureLocation;
    }
}
