package com.virusrpi.mpi.mixin;

import com.virusrpi.mpi.Client;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.network.ClientConnection.class)
public class MixinClientConnection {
    @Inject(method = "sendImmediately", at = @At("HEAD"), cancellable = true)
    private void sendImmediately$Inject$HEAD(Packet<?> packet, PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        var suppressList = Client.getAPI().getPacketsToSuppress();
        if (suppressList.contains(packet.getClass())) {
            ci.cancel();
            System.out.println("Suppressing packet " + packet.getClass().getSimpleName());
        }
    }
}
