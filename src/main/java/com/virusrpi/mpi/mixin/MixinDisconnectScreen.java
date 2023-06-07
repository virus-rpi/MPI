package com.virusrpi.mpi.mixin;

import com.virusrpi.mpi.Client;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(DisconnectedScreen.class)
public class MixinDisconnectScreen {
    @Shadow @Final private Text reason;

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
       Client.getAPI().setReason(reason);
    }
}
