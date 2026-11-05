package com.fantasyclouds.funtion_tool.client;

import com.fantasyclouds.funtion_tool.FuntionTool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class DynamicTextureModel implements BakedModel {
    private final BakedModel fallbackModel;

    public DynamicTextureModel(BakedModel fallbackModel) {
        this.fallbackModel = fallbackModel;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
        return fallbackModel.getQuads(state, side, rand);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return fallbackModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return fallbackModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return fallbackModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return fallbackModel.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return fallbackModel.getTransforms();
    }

    @Override
    public ItemOverrides getOverrides() {
        return new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel originalModel, ItemStack stack,
                                      net.minecraft.client.multiplayer.ClientLevel level,
                                      net.minecraft.world.entity.LivingEntity entity, int seed) {
                CompoundTag tag = stack.getOrCreateTag();
                String textureItem = tag.getString("texture");
                if (textureItem.isEmpty()) {
                    return fallbackModel;
                }
                if (isTexturePath(textureItem)) {
                    try {
                        return DirectTextureModel.get(toTextureLocation(textureItem), fallbackModel);
                    } catch (Exception exception) {
                        FuntionTool.LOGGER.info("[FunctionTool] Bad texture path: {}", textureItem);
                        return fallbackModel;
                    }
                }
                ResourceLocation itemId = ResourceLocation.tryParse(textureItem);
                Item targetItem = itemId == null ? null : ForgeRegistries.ITEMS.getValue(itemId);
                if (targetItem == null) {
                    FuntionTool.LOGGER.info("[FunctionTool] Item not found in registry: {}", textureItem);
                    return fallbackModel;
                }
                BakedModel targetModel = Minecraft.getInstance()
                        .getItemRenderer()
                        .getItemModelShaper()
                        .getItemModel(new ItemStack(targetItem));
                if (targetModel == null) {
                    FuntionTool.LOGGER.info("[FunctionTool] Target model not found for: {}", itemId);
                    return fallbackModel;
                }
                return targetModel;
            }
        };
    }

    public static boolean isTexturePath(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return value.endsWith(".png") || value.startsWith("textures/") || value.contains(":textures/");
    }

    public static ResourceLocation toTextureLocation(String value) {
        ResourceLocation location = new ResourceLocation(value);
        String path = location.getPath();
        if (path.startsWith("textures/")) {
            path = path.substring("textures/".length());
        }
        if (path.endsWith(".png")) {
            path = path.substring(0, path.length() - ".png".length());
        }
        return new ResourceLocation(location.getNamespace(), path);
    }
}
