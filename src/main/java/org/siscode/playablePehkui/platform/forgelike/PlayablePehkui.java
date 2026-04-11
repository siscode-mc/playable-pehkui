package org.siscode.playablePehkui.platform.forgelike;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("playable_pehkui")
public class PlayablePehkui {
    public PlayablePehkui(FMLJavaModLoadingContext context) {
        // Register the mod onto Minecraft Forge
        MinecraftForge.EVENT_BUS.register(this);
        context.getModEventBus().addListener(this::onCommonSetup);
        org.siscode.playablePehkui.platform.facade.PlayablePehkui.onInitialize();
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        // Do whatever.
    }
}
