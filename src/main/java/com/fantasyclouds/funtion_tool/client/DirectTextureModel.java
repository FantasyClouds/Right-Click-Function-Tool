package com.fantasyclouds.funtion_tool.client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class DirectTextureModel implements BakedModel {
    private final ResourceLocation textureLocation;
    private final BakedModel fallbackModel;

    public DirectTextureModel(ResourceLocation textureLocation, BakedModel fallbackModel) {
        this.textureLocation = textureLocation;
        this.fallbackModel = fallbackModel;
    }

    public static DirectTextureModel get(ResourceLocation textureLocation, BakedModel fallbackModel) {
        return new DirectTextureModel(textureLocation, fallbackModel);
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
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
        return true;
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
        return fallbackModel.getOverrides();
    }
}
