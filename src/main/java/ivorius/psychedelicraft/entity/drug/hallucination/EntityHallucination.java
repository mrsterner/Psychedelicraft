/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.entity.drug.hallucination;

import ivorius.psychedelicraft.PSTags;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.*;

public class EntityHallucination extends AbstractEntityHallucination {
    private float rotationYawPlus;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public EntityHallucination(PlayerEntity player) {
        super(player, player.world.getRegistryManager().get(RegistryKeys.ENTITY_TYPE)
                .getOrCreateEntryList(PSTags.Entities.HALLUCINATIONS)
                .getRandom(player.world.random)
                .map(RegistryEntry::value)
                .orElse((EntityType)EntityType.PIG)
                .create(player.world));

        entity.setPosition(
                player.getX() + random.nextDouble() * 50D - 25D,
                player.getY() + random.nextDouble() * 10D - 5D,
                player.getZ() + random.nextDouble() * 50D - 25D
        );
        entity.setVelocity(
                (random.nextDouble() - 0.5D) / 10D,
                (random.nextDouble() - 0.5D) / 10D,
                (random.nextDouble() - 0.5D) / 10D
        );
        entity.setYaw(random.nextInt(360));
        maxAge = (random.nextInt(59) + 3) * 20;
        rotationYawPlus = random.nextFloat() * 10 * (random.nextBoolean() ? 0 : 1);

        color = new float[] {
            random.nextFloat(),
            random.nextFloat(),
            random.nextFloat()
        };

        scale = 1;
        while (random.nextFloat() < 0.3F) {
            scale *= random.nextFloat() * 2.7f + 0.3F;
        }
        scale = Math.min(scale, 20);
    }

    @Override
    protected void animateEntity() {
        entity.setPosition(entity.getPos().add(entity.getVelocity()));
        entity.setYaw(MathHelper.wrapDegrees(entity.getYaw() + rotationYawPlus));
    }
}
