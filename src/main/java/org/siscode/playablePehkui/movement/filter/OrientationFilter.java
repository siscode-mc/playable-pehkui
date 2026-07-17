package org.siscode.playablePehkui.movement.filter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class OrientationFilter implements BlockStateFilter {
    private Orientation axis;
    public static Codec<OrientationFilter> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(Orientation.CODEC.fieldOf("axis").forGetter(OrientationFilter::getAxis)).apply(instance,OrientationFilter::new));

    public OrientationFilter(Orientation axis) {
        this.axis = axis;
    }

    @Override
    public boolean match(BlockState state) {
        var blockAxis = state.getValue(BlockStateProperties.AXIS);
        // Horizontalness (XZ) check.
        return (blockAxis.isHorizontal() && this.axis == Orientation.XZ_AXIS) ||
                this.axis.eq(blockAxis);
    }

    @Override
    public BlockStateFilterType<?> getType() {
        return BlockStateFilterType.ORIENTATION_FILTER_TYPE;
    }

    public Orientation getAxis() {
        return this.axis;
    }

    public enum Orientation implements StringRepresentable {
        X_AXIS("playable_pekhui:x_axis"),
        Y_AXIS("playable_pekhui:y_axis"),
        Z_AXIS("playable_pekhui:z_axis"),
        XZ_AXIS("playable_pekhui:xz_axis");

        Orientation(String name) {
            this.name = name;
        }

        public static Codec<Orientation> CODEC = StringRepresentable.fromEnum(Orientation::values);
        private final String name;

        public boolean eq(Direction.Axis axis) {
            return this == X_AXIS && axis == Direction.Axis.X ||
                    this == Y_AXIS && axis == Direction.Axis.Y ||
                    this == Z_AXIS && axis == Direction.Axis.Z;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
