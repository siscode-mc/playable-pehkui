package org.siscode.playablePehkui.movement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * This is the class where we load the actual climbability data from the json files.
 * Because of it we need to design the internal representation of the check to be fast here, since its the point
 * where the data can be converted to a more optimized approach.
 */
public class ClimbabilityDataLoader extends SimpleJsonResourceReloadListener {
    static Map<TagKey<Block>, ClimbabilityData> tagTargetMap = new HashMap<>();
    static Map<BlockState, ClimbabilityData> blockTargetMap = new HashMap<>();
    static Map<BlockState, Optional<ClimbabilityData>> queryCache = new HashMap<>();
    static Logger logger = LogUtils.getLogger();
    static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public ClimbabilityDataLoader() {
        super(GSON, "climbability_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        object.forEach((location, element) -> {
            ClimbabilityData.CODEC.parse(JsonOps.INSTANCE, element).resultOrPartial(error -> {
                logger.error("Could not load data for: {}. Cause: {}", location, error);
            }).ifPresent(climbabilityData -> {
                climbabilityData.block().ifLeft(tag -> {
                    if (tagTargetMap.put(tag, climbabilityData) != null) {
                        logger.warn("Value for tag: {} was overwritten by {}.", tag, location);
                    }
                }).ifRight(block -> {
                    if (blockTargetMap.put(block, climbabilityData) != null) {
                        logger.warn("Value for block: {} was overwritten by {}.", block, location);
                    }
                });
            });
        });

        var count = object.size();
        var loaded = tagTargetMap.size() + blockTargetMap.size();
        logger.info("Found {} climbability data files. Loaded {}. Errors {}", count, loaded, count - loaded);
    }

    public static Optional<ClimbabilityData> getClimbabilityForBlockState(BlockState state) {
        if (tagTargetMap.isEmpty() && blockTargetMap.isEmpty()) {
            return Optional.empty();
        }

        if (queryCache.containsKey(state)) {
            return queryCache.get(state);
        }
        // Check if the block directly has an entry.
        if (blockTargetMap.containsKey(state)) {
            var result = Optional.of(blockTargetMap.get(state));
            queryCache.put(state, result);
            return result;
        }
        // Check if any tag is applicable for the block.
        for (Map.Entry<TagKey<Block>, ClimbabilityData> entry : tagTargetMap.entrySet()) {
            if (state.is(entry.getKey())) {
                var value  = Optional.of(entry.getValue());
                queryCache.put(state, value);
                return value;
            }
        }

        queryCache.put(state, Optional.empty());
        return Optional.empty();
    }
}
