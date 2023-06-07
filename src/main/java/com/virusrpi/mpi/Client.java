package com.virusrpi.mpi;

import com.virusrpi.mpi.modules.API;
import com.virusrpi.mpi.modules.SENSORS;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Client implements ModInitializer {
    public static final Client INSTANCE = new Client();
    public static Logger logger = LogManager.getLogger(Client.class);

    public static final MinecraftClient mc = MinecraftClient.getInstance();
    private final SENSORS sensor = new SENSORS(mc);
    private static final API api = new API(mc);

    @Override
    public void onInitialize(){
        logger.info("Startup...");
    }

    public void onTick(){
        if (api.getDisconnect()) {
            try {
                mc.disconnect(new MultiplayerScreen(new TitleScreen()));
            } catch (Exception ignored) {
            }
            api.resetDisconnect();
        }
    }

    public static API getAPI() {
        return api;
    }
}
