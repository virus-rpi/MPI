package com.virusrpi.mpi.mixin;

import com.virusrpi.mpi.Client;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Inject(method = "tick", at = @At("HEAD"))
	public void onTick(CallbackInfo ci){
		Client.INSTANCE.onTick();
	}
}