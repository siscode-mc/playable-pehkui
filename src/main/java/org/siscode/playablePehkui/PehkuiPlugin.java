package org.siscode.playablePehkui;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.siscode.playablePehkui.platform.facade.ModIdentifierUtil;
import virtuoel.pehkui.api.*;

import java.util.stream.Collectors;

public class PehkuiPlugin {
    public static ScaleModifier SCALE_MODIFIER_SQRT_BASE = new TypedScaleModifier(() -> ScaleTypes.BASE,
            (toModify, base) -> toModify * Math.sqrt(base)
    );
    public static ScaleModifier SCALE_MODIFIER_SQRT_HEIGHT = new TypedScaleModifier(() -> ScaleTypes.HEIGHT,
            (toModify, base) -> toModify * Math.sqrt(base)
    );


    public static void registerScaleModifiers() {
        ScaleRegistries.SCALE_MODIFIERS.put(
                ModIdentifierUtil.pphkResource("sqrt_base_multiplier"),
                SCALE_MODIFIER_SQRT_BASE
        );
        ScaleRegistries.SCALE_MODIFIERS.put(
                ModIdentifierUtil.pphkResource("sqrt_base_multiplier"),
                SCALE_MODIFIER_SQRT_HEIGHT
        );

    }

    public static void replacePehkuiDefaultScalings() {
        boolean USE_MOTION_RESCALE = true;  // TODO: these should be configuration points
        boolean USE_REACH_RESCALE = true;
        boolean USE_THIRD_PERSON_RESCALE = true;
        if (USE_MOTION_RESCALE) {
            if (ScaleTypes.MOTION.getDefaultBaseValueModifiers().remove(ScaleModifiers.BASE_MULTIPLIER)) {
                ScaleTypes.MOTION.getDefaultBaseValueModifiers().add(SCALE_MODIFIER_SQRT_BASE);
            } else {
                var plausibleCulprits = ScaleTypes.MOTION.getDefaultBaseValueModifiers().stream().map((mod) ->
                        ScaleRegistries.SCALE_MODIFIERS.inverse().get(mod).getNamespace()
                ).collect(Collectors.joining(", "));
                LogUtils.getLogger().warn("[PlayablePehkui] Unable to remove pehkui default scaling for pehkui:motion, did someone else already remove it? Potential culprits: {}. Report this as a compatibility bug to playablepehkui devs", plausibleCulprits);
            }
        }
        if (USE_REACH_RESCALE) {
            if (ScaleTypes.REACH.getDefaultBaseValueModifiers().remove(ScaleModifiers.BASE_MULTIPLIER)) {
                ScaleTypes.REACH.getDefaultBaseValueModifiers().add(SCALE_MODIFIER_SQRT_BASE);
            } else {
                var plausibleCulprits = ScaleTypes.MOTION.getDefaultBaseValueModifiers().stream().map((mod) ->
                        ScaleRegistries.SCALE_MODIFIERS.inverse().get(mod).getNamespace()
                ).collect(Collectors.joining(", "));
                LogUtils.getLogger().warn("[PlayablePehkui] Unable to remove pehkui default scaling for pehkui:reach, did someone else already remove it? Potential culprits: {}. Report this as a compatibility bug to playablepehkui devs", plausibleCulprits);
            }
        };
        if (USE_THIRD_PERSON_RESCALE) {
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

    public static void initialize() {
        registerScaleModifiers();
        replacePehkuiDefaultScalings();
    }
}
