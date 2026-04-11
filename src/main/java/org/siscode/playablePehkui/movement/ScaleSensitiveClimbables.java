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
        var GRAVEL_CLIMBABILITY = new ClimbabilityData(1/16.0, 1/8.0, 0.33);
        var WOOL_CLIMBABILITY = new ClimbabilityData(0, 1/4.0, 0.33);
        var STICKY_CLIMBABILITY = new ClimbabilityData(0, 1/8.0, 0.25);
        var LADDERLIKE_CLIMBABILITY = new ClimbabilityData(1/12.0, 1/3.0, 1);

        register_many(GRASS_CLIMBABILITY,
                Blocks.GRASS_BLOCK, Blocks.MYCELIUM, Blocks.DIRT_PATH, Blocks.ROOTED_DIRT,
                Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM,

                // grass happens to be similar even though these aren't really grasses
                Blocks.GLOW_LICHEN, Blocks.SCULK_VEIN, Blocks.HAY_BLOCK, Blocks.MANGROVE_ROOTS,
                Blocks.SNOW, Blocks.SNOW_BLOCK
        );

        register_many(DIRT_CLIMBABILITY,
                Blocks.DIRT, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.FARMLAND, Blocks.SOUL_SAND, Blocks.SOUL_SOIL,
                Blocks.SCULK
        );
        register_conditional_many(LOG_CLIMBABILITY, (s) ->
                s.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y,
                Blocks.JUNGLE_LOG, Blocks.MANGROVE_LOG, Blocks.CHERRY_LOG
        );
        register_conditional_many(LOG_CLIMBABILITY, ((s) ->
                        s.getValue(BlockStateProperties.AXIS) != Direction.Axis.Y
                ),
                Blocks.ACACIA_LOG, Blocks.BIRCH_LOG,
                Blocks.DARK_OAK_LOG, Blocks.OAK_LOG, Blocks.OAK_LOG
        );
        register_many(PLANKS_CLIMBABILITY,
                Blocks.OAK_PLANKS, Blocks.BIRCH_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.JUNGLE_PLANKS,
                Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS,
                Blocks.CRIMSON_PLANKS, Blocks.WARPED_PLANKS,
                Blocks.MANGROVE_PLANKS, Blocks.CHERRY_PLANKS, Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_MOSAIC,

                Blocks.OAK_SLAB, Blocks.BIRCH_SLAB, Blocks.SPRUCE_SLAB, Blocks.JUNGLE_SLAB,
                Blocks.ACACIA_SLAB, Blocks.DARK_OAK_SLAB,
                Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB,
                Blocks.MANGROVE_SLAB, Blocks.CHERRY_SLAB, Blocks.BAMBOO_SLAB, Blocks.BAMBOO_MOSAIC_SLAB,

                Blocks.OAK_STAIRS, Blocks.BIRCH_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.JUNGLE_STAIRS,
                Blocks.ACACIA_STAIRS, Blocks.DARK_OAK_STAIRS,
                Blocks.CRIMSON_STAIRS, Blocks.WARPED_STAIRS,
                Blocks.MANGROVE_STAIRS, Blocks.CHERRY_STAIRS, Blocks.BAMBOO_STAIRS, Blocks.BAMBOO_MOSAIC_STAIRS,

                Blocks.OAK_FENCE, Blocks.BIRCH_FENCE, Blocks.SPRUCE_FENCE, Blocks.JUNGLE_FENCE,
                Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE,
                Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE,
                Blocks.MANGROVE_FENCE, Blocks.CHERRY_FENCE, Blocks.BAMBOO_FENCE,

                Blocks.OAK_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE,
                Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE,
                Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE,
                Blocks.MANGROVE_FENCE_GATE, Blocks.CHERRY_FENCE_GATE, Blocks.BAMBOO_FENCE_GATE,

                Blocks.OAK_TRAPDOOR, Blocks.SPRUCE_TRAPDOOR, Blocks.JUNGLE_TRAPDOOR, Blocks.ACACIA_TRAPDOOR, Blocks.CHERRY_TRAPDOOR,
                Blocks.BAMBOO_TRAPDOOR, Blocks.CRIMSON_TRAPDOOR, Blocks.WARPED_TRAPDOOR,

                Blocks.CRAFTING_TABLE, Blocks.COMPOSTER, Blocks.BEE_NEST, Blocks.BEEHIVE, Blocks.BOOKSHELF, Blocks.CHISELED_BOOKSHELF,
                Blocks.BARREL
        );

        register_many(BRICKS_CLIMBABILITY,
                Blocks.BRICKS, Blocks.BRICK_SLAB, Blocks.BRICK_STAIRS, Blocks.BRICK_WALL,
                Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_SLAB, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_BRICK_WALL, Blocks.NETHER_BRICK_FENCE,
                Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_NETHER_BRICK_WALL,

                Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_TILE_STAIRS, Blocks.DEEPSLATE_TILE_WALL,

                Blocks.DARK_PRISMARINE, Blocks.DARK_PRISMARINE_SLAB, Blocks.DARK_PRISMARINE_STAIRS
        );

        register_many(COBBLE_CLIMBABILITY,
                /* Reasoning log:
                We include cracked stone bricks but not plain stone bricks
                Yes non-cobbled deepslate, no plain stone
                */
                Blocks.COBBLESTONE, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE_WALL,
                Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_WALL,
                Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.COBBLED_DEEPSLATE_STAIRS, Blocks.COBBLED_DEEPSLATE_WALL,
                Blocks.NETHERRACK, Blocks.END_STONE,

                Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,

                Blocks.SANDSTONE, Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_WALL,
                Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_WALL,

                Blocks.PRISMARINE, Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE_WALL,
                Blocks.GLOWSTONE,

                Blocks.INFESTED_COBBLESTONE, Blocks.INFESTED_DEEPSLATE, Blocks.INFESTED_CRACKED_STONE_BRICKS
        );

        register_many(GRAVEL_CLIMBABILITY,
                Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, Blocks.SAND, Blocks.RED_SAND
        );

        register_many(WOOL_CLIMBABILITY,
                Blocks.RED_BED, Blocks.WHITE_BED, Blocks.LIGHT_GRAY_BED, Blocks.GRAY_BED, Blocks.BLACK_BED, Blocks.BROWN_BED,
                Blocks.ORANGE_BED, Blocks.YELLOW_BED, Blocks.GREEN_BED, Blocks.LIME_BED, Blocks.CYAN_BED, Blocks.LIGHT_BLUE_BED,
                Blocks.BLUE_BED, Blocks.PURPLE_BED, Blocks.MAGENTA_BED, Blocks.PINK_BED,


                Blocks.WHITE_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.GRAY_WOOL, Blocks.BLACK_WOOL, Blocks.BROWN_WOOL, Blocks.RED_WOOL,
                Blocks.ORANGE_WOOL, Blocks.YELLOW_WOOL, Blocks.GREEN_WOOL, Blocks.LIME_WOOL, Blocks.CYAN_WOOL, Blocks.LIGHT_BLUE_WOOL,
                Blocks.BLUE_WOOL, Blocks.PURPLE_WOOL, Blocks.MAGENTA_WOOL, Blocks.PINK_WOOL
        );

        register_many(STICKY_CLIMBABILITY,
                Blocks.CLAY, Blocks.SLIME_BLOCK, Blocks.HONEY_BLOCK, Blocks.HONEYCOMB_BLOCK, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS
        );

        register_many(LADDERLIKE_CLIMBABILITY,
                Blocks.CHAIN, Blocks.IRON_BARS
        );
    }
}
