/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import ivorius.psychedelicraft.block.entity.RiftJarBlockEntity;

public class RiftJarItem extends BlockItem {
    public static ItemStack createFilledRiftJar(float riftFraction, Item item) {
        ItemStack stack = item.getDefaultStack();
        if (riftFraction > 0) {
            stack.getOrCreateNbt().putFloat("riftFraction", riftFraction);
        }
        return stack;
    }

    public RiftJarItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof RiftJarBlockEntity jar) {
            jar.currentRiftFraction = getRiftFraction(stack);
        }
        return super.postPlacement(pos, world, player, stack, state);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        float fillAmount = getRiftFraction(stack);
        tooltip.add(Text.translatable(getTranslationKey() + "." + getUnlocalizedFractionName(fillAmount)).formatted(Formatting.GRAY));
    }

    public float getRiftFraction(ItemStack itemStack) {
        return itemStack.hasNbt() ? itemStack.getNbt().getFloat("riftFraction") : 0;
    }

    private static String getUnlocalizedFractionName(float fraction) {
        if (fraction <= 0) {
            return "empty";
        }
        if (fraction < 0.4F) {
            return "slightly_filled";
        }
        if (fraction < 0.6F) {
            return "half_filled";
        }
        if (fraction < 0.8F) {
            return "filled";
        }

        return "over_filled";
    }
}
