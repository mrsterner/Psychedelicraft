package ivorius.psychedelicraft.client.screen;

import ivorius.psychedelicraft.Psychedelicraft;
import ivorius.psychedelicraft.block.entity.BarrelBlockEntity;
import ivorius.psychedelicraft.screen.FluidContraptionScreenHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lukas on 13.11.14.
 * Updated by Sollace on 4 Jan 2023
 */
public class BarrelScreen extends FlaskScreen<BarrelBlockEntity> {
    public static final Identifier BACKGROUND = Psychedelicraft.id("textures/gui/barrel.png");

    private static final List<Text> MATURING_LABEL = Arrays.asList(Text.translatable("fluid.status.maturing").formatted(Formatting.GREEN));

    public BarrelScreen(FluidContraptionScreenHandler<BarrelBlockEntity> handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected Identifier getBackgroundTexture() {
        return BACKGROUND;
    }

    @Override
    protected void drawAdditionalInfo(MatrixStack matrices, int baseX, int baseY) {
        float progress = handler.getBlockEntity().getProgress();
        if (progress > 0 && progress < 1) {
            drawTexture(matrices, baseX + 110, baseY + 20, 233, 22, 23, 22);
            int barHeight = (int)(22 * (1 - progress));
            drawTexture(matrices, baseX + 110, baseY + 20 + barHeight, 233, barHeight, 23, 23 - barHeight);
        }
    }

    @Override
    protected List<Text> getAdditionalTankText() {
        return handler.getBlockEntity().isActive() ? MATURING_LABEL : List.of();
    }
}
