package com.fantasyclouds.funtion_tool;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class FunctionToolItem extends Item {
    public FunctionToolItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer && serverPlayer.getServer() != null) {
            CompoundTag tag = stack.getOrCreateTag();
            String command = tag.getString("function");
            if (!command.isEmpty()) {
                CommandSourceStack source = serverPlayer.createCommandSourceStack()
                        .withPermission(4)
                        .withSuppressedOutput();
                serverPlayer.getServer().getCommands().performPrefixedCommand(source, command);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
