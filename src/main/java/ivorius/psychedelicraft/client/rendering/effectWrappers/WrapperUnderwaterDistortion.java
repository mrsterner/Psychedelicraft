/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.client.rendering.effectWrappers;

import org.jetbrains.annotations.Nullable;

import ivorius.psychedelicraft.Psychedelicraft;
import ivorius.psychedelicraft.client.rendering.shaders.PSRenderStates;
import ivorius.psychedelicraft.client.rendering.shaders.ShaderHeatDistortions;
import ivorius.psychedelicraft.entities.drugs.DrugProperties;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.util.Identifier;

/**
 * Created by lukas on 26.04.14.
 */
public class WrapperUnderwaterDistortion extends ShaderWrapper<ShaderHeatDistortions> {
    public Identifier heatDistortionNoiseTexture = Psychedelicraft.id(Psychedelicraft.filePathTextures + "heatDistortionNoise.png");

    public WrapperUnderwaterDistortion(String utils) {
        super(new ShaderHeatDistortions(Psychedelicraft.logger), getRL("shaderBasic.vert"), getRL("shaderHeatDistortion.frag"), utils);
    }

    @Override
    public void setShaderValues(float partialTicks, int ticks, @Nullable Framebuffer buffer) {
        float strength = getStrength();

        if (PSRenderStates.doWaterDistortion && buffer != null && strength > 0) {
            shaderInstance.depthTextureIndex = buffer.getDepthAttachment();
            shaderInstance.noiseTextureIndex = PSRenderStates.getTextureIndex(heatDistortionNoiseTexture);
            shaderInstance.strength = strength;
            shaderInstance.wobbleSpeed = 0.03f;
        } else {
            shaderInstance.strength = 0;
        }
    }

    private float getStrength() {
        return DrugProperties.of(MinecraftClient.getInstance().cameraEntity)
                .map(d -> d.renderer.getCurrentWaterDistortion())
                .orElse(0F);
    }

    @Override
    public boolean wantsDepthBuffer(float partialTicks) {
        return PSRenderStates.doWaterDistortion && getStrength() > 0;
    }
}
