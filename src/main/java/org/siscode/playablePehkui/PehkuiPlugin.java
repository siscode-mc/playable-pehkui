package org.siscode.playablePehkui;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.siscode.playablePehkui.platform.facade.ModIdentifierUtil;
import org.siscode.playablePehkui.platform.facade.PlayablePehkui;
import virtuoel.pehkui.api.*;

import java.util.stream.Collectors;

import static org.siscode.playablePehkui.platform.facade.ModIdentifierUtil.pphkResource;

public class PehkuiPlugin {
    public static ScaleModifier SCALE_MODIFIER_SQRT_BASE = new TypedScaleModifier(() -> ScaleTypes.BASE,
            (toModify, base) -> toModify * Math.sqrt(base)
    );
    public static ScaleModifier SCALE_MODIFIER_SQRT_HEIGHT = new TypedScaleModifier(() -> ScaleTypes.HEIGHT,
            (toModify, base) -> toModify * Math.sqrt(base)
    );
    public static final ScaleType WORLD_INTERACTION_SCALE = ScaleType.Builder.create().addBaseValueModifier(ScaleModifiers.BASE_MULTIPLIER).build();


    public static void registerScaleModifiers() {
        ScaleRegistries.SCALE_MODIFIERS.put(
                pphkResource("sqrt_base_multiplier"),
                SCALE_MODIFIER_SQRT_BASE
        );
        ScaleRegistries.SCALE_MODIFIERS.put(
                pphkResource("sqrt_base_multiplier"),
                SCALE_MODIFIER_SQRT_HEIGHT
        );

    }

    public static void replacePehkuiDefaultScalings() {
        if (PlayablePehkui.SERVER_CONFIG.useMotionRescale) {
            if (ScaleTypes.MOTION.getDefaultBaseValueModifiers().remove(ScaleModifiers.BASE_MULTIPLIER)) {
                ScaleTypes.MOTION.getDefaultBaseValueModifiers().add(SCALE_MODIFIER_SQRT_BASE);
            } else {
                var plausibleCulprits = ScaleTypes.MOTION.getDefaultBaseValueModifiers().stream().map((mod) ->
                        ScaleRegistries.SCALE_MODIFIERS.inverse().get(mod).getNamespace()
                ).collect(Collectors.joining(", "));
                LogUtils.getLogger().warn("[PlayablePehkui] Unable to remove pehkui default scaling for pehkui:motion, did someone else already remove it? Potential culprits: {}. Report this as a compatibility bug to playablepehkui devs", plausibleCulprits);
            }
        }
        if (PlayablePehkui.SERVER_CONFIG.useReachRescale) {
            if (ScaleTypes.REACH.getDefaultBaseValueModifiers().remove(ScaleModifiers.BASE_MULTIPLIER)) {
                ScaleTypes.REACH.getDefaultBaseValueModifiers().add(SCALE_MODIFIER_SQRT_BASE);
            } else {
                var plausibleCulprits = ScaleTypes.MOTION.getDefaultBaseValueModifiers().stream().map((mod) ->
                        ScaleRegistries.SCALE_MODIFIERS.inverse().get(mod).getNamespace()
                ).collect(Collectors.joining(", "));
                LogUtils.getLogger().warn("[PlayablePehkui] Unable to remove pehkui default scaling for pehkui:reach, did someone else already remove it? Potential culprits: {}. Report this as a compatibility bug to playablepehkui devs", plausibleCulprits);
            }
        };
        if (PlayablePehkui.SERVER_CONFIG.useThirdPersonRescale) {
            if (ScaleTypes.THIRD_PERSON.getDefaultBaseValueModifiers().remove(ScaleModifiers.HEIGHT_MULTIPLIER)) {
                ScaleTypes.THIRD_PERSON.getDefaultBaseValueModifiers().add(SCALE_MODIFIER_SQRT_HEIGHT);
            } else {
                var plausibleCulprits = ScaleTypes.MOTION.getDefaultBaseValueModifiers().stream().map((mod) ->
                        ScaleRegistries.SCALE_MODIFIERS.inverse().get(mod).getNamespace()
                ).collect(Collectors.joining(", "));
                LogUtils.getLogger().warn("[PlayablePehkui] Unable to remove pehkui default scaling for pehkui:third_person, did someone else already remove it? Potential culprits: {}. Report this as a compatibility bug to playablepehkui devs", plausibleCulprits);
            }
        }
    }

    public static void registerNewScales() {
        ScaleRegistries.SCALE_TYPES.put(pphkResource("world_interaction"), WORLD_INTERACTION_SCALE);
    }

    public static void initialize() {
        registerScaleModifiers();
        registerNewScales();
        replacePehkuiDefaultScalings();
    }
}
