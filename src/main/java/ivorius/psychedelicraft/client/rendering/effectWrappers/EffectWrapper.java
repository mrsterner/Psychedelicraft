/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.client.rendering.effectWrappers;

import ivorius.psychedelicraft.client.rendering.ScreenEffect;
import net.minecraft.client.gl.Framebuffer;

/**
 * Created by lukas on 26.04.14.
 * Updated by Sollace on 4 Jan 2023
 */
@Deprecated(forRemoval = true)
public interface EffectWrapper {
    default void update() {

    }

    void apply(float tickDelta, ScreenEffect.PingPong pingPong, Framebuffer buffer);

    default boolean wantsDepthBuffer(float tickDelta) {
        return false;
    }
}
