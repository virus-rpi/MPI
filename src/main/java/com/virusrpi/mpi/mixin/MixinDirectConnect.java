package com.virusrpi.mpi.mixin;

import com.google.common.eventbus.Subscribe;
import com.virusrpi.mpi.Client;
import com.virusrpi.mpi.event.ConnectEvent;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.virusrpi.mpi.event.EventManager.eventBus;

@Mixin(MultiplayerScreen.class)
public abstract class MixinDirectConnect  {
    @Shadow protected abstract void connect(ServerInfo entry);

    @Unique
    public void connectTo(String address) {
        ServerInfo.ServerType type = ServerInfo.ServerType.OTHER;
        ServerInfo server = new ServerInfo("Server", address, type);
        connect(server);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        eventBus.register(this);
        if (Client.INSTANCE.isAutoReconnect()) {
            connectTo(Client.getAPI().getAddress());
        }
    }

    @Unique
    @Subscribe
    public void onConnect(ConnectEvent event) {
        // connectTo(event.address());
        System.out.println("Connecting to " + event.address());
    }
}