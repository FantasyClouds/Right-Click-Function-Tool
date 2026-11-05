package com.fantasyclouds.funtion_tool.client;

import com.fantasyclouds.funtion_tool.FuntionTool;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Renders the image path stored in the item's {@code texture} NBT tag.
 */
@OnlyIn(Dist.CLIENT)
public class FunctionToolTextureRenderer extends BlockEntityWithoutLevelRenderer {
    public FunctionToolTextureRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                             MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return;
        }
        String texture = tag.getString("texture");
        if (!DynamicTextureModel.isTexturePath(texture)) {
            return;
        }
        try {
            ResourceLocation location = new ResourceLocation(texture);
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(location));
            float lx = 0.0F, ly = 0.0F, hx = 1.0F, hy = 1.0F;
            consumer.vertex(poseStack.last().pose(), lx, ly, 0.0F).color(255, 255, 255, 255).uv(0.0F, 1.0F)
                    .overlayCoords(combinedOverlay).uv2(0xF000F0).normal(0.0F, 0.0F, 1.0F).endVertex();
            consumer.vertex(poseStack.last().pose(), hx, ly, 0.0F).color(255, 255, 255, 255).uv(1.0F, 1.0F)
                    .overlayCoords(combinedOverlay).uv2(0xF000F0).normal(0.0F, 0.0F, 1.0F).endVertex();
            consumer.vertex(poseStack.last().pose(), hx, hy, 0.0F).color(255, 255, 255, 255).uv(1.0F, 0.0F)
                    .overlayCoords(combinedOverlay).uv2(0xF000F0).normal(0.0F, 0.0F, 1.0F).endVertex();
            consumer.vertex(poseStack.last().pose(), lx, hy, 0.0F).color(255, 255, 255, 255).uv(0.0F, 0.0F)
                    .overlayCoords(combinedOverlay).uv2(0xF000F0).normal(0.0F, 0.0F, 1.0F).endVertex();
        } catch (Exception exception) {
            FuntionTool.LOGGER.warn("[FunctionTool] render texture failed: {}", texture, exception);
        }
    }
}
