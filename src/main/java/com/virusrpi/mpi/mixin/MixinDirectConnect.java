package com.virusrpi.mpi.mixin;

import com.virusrpi.mpi.Client;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class MixinDirectConnect  {
    @Shadow protected abstract void connect(ServerInfo entry);

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (Client.INSTANCE.isAutoReconnect() || Client.getAPI().getConnect()) {
            if (Client.getMc().currentScreen instanceof MultiplayerScreen) {
                connectTo(Client.getAPI().getAddress());
            }
        }
    }

    @Unique
    public void connectTo(String address) {
        Client.getAPI().resetConnect();
        ServerInfo.ServerType type = ServerInfo.ServerType.OTHER;
        ServerInfo server = new ServerInfo("Server", address, type);
        connect(server);
    }
}