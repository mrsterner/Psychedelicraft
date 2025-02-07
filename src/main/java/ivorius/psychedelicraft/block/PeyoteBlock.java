/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.block;

import ivorius.psychedelicraft.block.entity.PSBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;

public class PeyoteBlock extends PlantBlock implements Fertilizable, BlockEntityProvider {
    public static final IntProperty AGE = Properties.AGE_3;
    public static final int MAX_AGE = 3;

    private static final VoxelShape SHAPE = Block.createCuboidShape(4.8, 0, 4.8, 11.2, 6.4, 11.2);

    public PeyoteBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isSideSolid(world, pos, Direction.UP, SideShapeType.CENTER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isFertilizable(world, pos, state, false)) {
            if (world.random.nextInt(state.get(AGE) < MAX_AGE ? 20 : 120) == 0) {
                applyGrowth(world, random, pos, state, false);
            }
        }
    }

    public void applyGrowth(World world, Random random, BlockPos pos, BlockState state, boolean bonemeal) {
        if (state.get(AGE) < MAX_AGE) {
            world.setBlockState(pos, state.cycle(AGE), Block.NOTIFY_ALL);
            return;
        }

        BlockPos.Mutable plantingPos = new BlockPos.Mutable();
        int i = 0;
        do {
            plantingPos.set(pos);
            plantingPos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

            if (world.isAir(plantingPos) && canPlaceAt(state, world, pos)) {
                world.setBlockState(plantingPos, getDefaultState(), Block.NOTIFY_ALL);
                return;
            }
        } while (++i < 4);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean client) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        applyGrowth(world, random, pos, state, true);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return PSBlockEntities.PEYOTE.instantiate(pos, state);
    }
}
