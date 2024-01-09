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
    private boolean headless = true;
    private boolean autoRespawn = true;
    private boolean autoReconnect = true;

    @Override
    public void onInitialize(){
        logger.info("Startup MPI...");
    }

    public void onTick(){
        if (api.getDisconnect()) {
            try {
                mc.disconnect(new MultiplayerScreen(new TitleScreen()));
            } catch (Exception ignored) {
            }
            api.resetDisconnect();
        }
        if (api.getMultiplayerScreen()) {
            try {
                mc.setScreen(new MultiplayerScreen(new TitleScreen()));
            } catch (Exception ignored) {
            }
            api.resetMultiplayerScreen();
        }
    }

    public static API getAPI() {
        return api;
    }

    public static MinecraftClient getMc() {
        return mc;
    }

    public boolean isHeadless() {
        return headless;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    public boolean isAutoRespawn() {
        return autoRespawn;
    }

    public void setAutoRespawn(boolean autoRespawn) {
        this.autoRespawn = autoRespawn;
    }

    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }
}
