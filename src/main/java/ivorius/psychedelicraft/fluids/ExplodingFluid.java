/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.fluids;

import net.minecraft.item.ItemStack;

/**
 * A fluid that can explode.
 */
public interface ExplodingFluid
{
    String SUBTYPE = "exploding";

    /**
     * Determines the flame distance of the explosion.
     *
     * @param fluidStack The fluid stack.
     * @return The flame distance in blocks.
     */
    float fireStrength(ItemStack fluidStack);

    /**
     * Determines the explosion size.
     *
     * @param fluidStack The fluid stack.
     * @return The explosion size in blocks.
     */
    float explosionStrength(ItemStack fluidStack);
}
