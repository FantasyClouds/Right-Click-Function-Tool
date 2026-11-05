package com.fantasyclouds.funtion_tool.client;

import com.fantasyclouds.funtion_tool.FuntionTool;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = FuntionTool.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {
    private ClientEvents() {
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> models = event.getModels();
        ResourceLocation toolKey = null;
        for (ResourceLocation key : models.keySet()) {
            if (key.getPath().contains("function_tool")) {
                toolKey = key;
                break;
            }
        }
        if (toolKey == null) {
            FuntionTool.LOGGER.error("No model key containing 'function_tool' found.");
            return;
        }
        BakedModel original = models.get(toolKey);
        models.put(toolKey, new DynamicTextureModel(original));
    }
}
