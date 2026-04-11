package org.siscode.playablePehkui.movement;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ScaleSensitiveClimbables {
    public static final HashMap<Block, Function<BlockState, ClimbabilityData>> REGISTERED_CLIMBABLES = new HashMap<>();

    public static void register(Block block, ClimbabilityData data) {
        REGISTERED_CLIMBABLES.put(block, (s) -> data);
    }

    public static void register_many(ClimbabilityData data, Block... blocks) {
        for (Block block : blocks) {
            register(block, data);
        }
    }

    public static void register_conditional(Block block, ClimbabilityData data, Predicate<BlockState> pred) {
        REGISTERED_CLIMBABLES.put(block, (s) -> {
            if (pred.test(s)) { return data; } else { return new ClimbabilityData(0,0, 1); }
        });
    }

    public static void register_conditional_many(ClimbabilityData data, Predicate<BlockState> pred, Block... blocks) {
        for (Block block : blocks) {
            register_conditional(block, data, pred);
        }
    }

    public static void initialize() {
        var GRASS_CLIMBABILITY = new ClimbabilityData(1/32.0, 1/1.5, 0.33);
        var DIRT_CLIMBABILITY = new ClimbabilityData(1/32.0, 1/8.0, 0.33);
        var LOG_CLIMBABILITY = new ClimbabilityData(1/16.0, 1/4.0, 0.5);
        var PLANKS_CLIMBABILITY = new ClimbabilityData(1/8.0, 1/4.0, 0.5);
        var BRICKS_CLIMBABILITY = new ClimbabilityData(1/8.0, 1/4.0, 0.5);
        var COBBLE_CLIMBABILITY = new ClimbabilityData(1/12.0, 1/3.0, 0.33);
        register(Blocks.GRASS_BLOCK, GRASS_CLIMBABILITY);
        register(Blocks.DIRT, DIRT_CLIMBABILITY);
        register_conditional(Blocks.JUNGLE_LOG, LOG_CLIMBABILITY, (s) ->
                s.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y
        );
        register_conditional_many(LOG_CLIMBABILITY, ((s) ->
                        s.getValue(BlockStateProperties.AXIS) != Direction.Axis.Y
                ),
                Blocks.ACACIA_LOG, Blocks.BIRCH_LOG, Blocks.CHERRY_LOG, Blocks.MANGROVE_LOG,
                Blocks.DARK_OAK_LOG, Blocks.OAK_LOG, Blocks.OAK_LOG
        );
        register_many(PLANKS_CLIMBABILITY,
                Blocks.OAK_PLANKS, Blocks.BIRCH_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.JUNGLE_PLANKS,
                Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS,
                Blocks.CRIMSON_PLANKS, Blocks.WARPED_PLANKS,
                Blocks.MANGROVE_PLANKS, Blocks.CHERRY_PLANKS, Blocks.BAMBOO_PLANKS,

                Blocks.OAK_SLAB, Blocks.BIRCH_SLAB, Blocks.SPRUCE_SLAB, Blocks.JUNGLE_SLAB,
                Blocks.ACACIA_SLAB, Blocks.DARK_OAK_SLAB,
                Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB,
                Blocks.MANGROVE_SLAB, Blocks.CHERRY_SLAB, Blocks.BAMBOO_SLAB,

                Blocks.OAK_STAIRS, Blocks.BIRCH_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.JUNGLE_STAIRS,
                Blocks.ACACIA_STAIRS, Blocks.DARK_OAK_STAIRS,
                Blocks.CRIMSON_STAIRS, Blocks.WARPED_STAIRS,
                Blocks.MANGROVE_STAIRS, Blocks.CHERRY_STAIRS, Blocks.BAMBOO_STAIRS,

                Blocks.OAK_FENCE, Blocks.BIRCH_FENCE, Blocks.SPRUCE_FENCE, Blocks.JUNGLE_FENCE,
                Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE,
                Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE,
                Blocks.MANGROVE_FENCE, Blocks.CHERRY_FENCE, Blocks.BAMBOO_FENCE,

                Blocks.OAK_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE,
                Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE,
                Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE,
                Blocks.MANGROVE_FENCE_GATE, Blocks.CHERRY_FENCE_GATE, Blocks.BAMBOO_FENCE_GATE
        );

        register_many(BRICKS_CLIMBABILITY,
                Blocks.BRICKS, Blocks.BRICK_SLAB, Blocks.BRICK_STAIRS, Blocks.BRICK_WALL
        );

        register_many(COBBLE_CLIMBABILITY,
                Blocks.COBBLESTONE, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE_WALL,
                Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_WALL
        );
    }
}
