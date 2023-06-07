package com.virusrpi.mpi.mixin;

import com.virusrpi.mpi.Client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;

import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.apache.logging.log4j.Logger;

@Mixin(MultiplayerScreen.class)
public abstract class MixinDirectConnect  {
    MinecraftClient mc = MinecraftClient.getInstance();
    @Shadow protected abstract void connect(ServerInfo entry);

    public void connectTo(String address) {
        ServerInfo server = new ServerInfo("Server", address, false);
        connect(server);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        connectTo(Client.getAPI().getAddress());
    }
}