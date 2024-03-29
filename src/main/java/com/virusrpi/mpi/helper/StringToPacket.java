package com.virusrpi.mpi.helper;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.*;
import net.minecraft.network.packet.c2s.config.ReadyC2SPacket;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.EnterConfigurationC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginKeyC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginQueryResponseC2SPacket;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.c2s.query.QueryPingC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryRequestC2SPacket;
import net.minecraft.network.packet.s2c.common.*;
import net.minecraft.network.packet.s2c.config.DynamicRegistriesS2CPacket;
import net.minecraft.network.packet.s2c.config.FeaturesS2CPacket;
import net.minecraft.network.packet.s2c.config.ReadyS2CPacket;
import net.minecraft.network.packet.s2c.login.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.network.packet.s2c.query.PingResultS2CPacket;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;

import java.util.HashMap;
import java.util.Map;

public class StringToPacket {
    private static final Map<String, Class<? extends Packet<?>>> nameToPacket = new HashMap<>();

    public StringToPacket() {
        nameToPacket.put("ClientOptionsC2SPacket", ClientOptionsC2SPacket.class);
        nameToPacket.put("CommonPongC2SPacket", CommonPongC2SPacket.class);
        nameToPacket.put("CustomPayloadC2SPacket", CustomPayloadC2SPacket.class);
        nameToPacket.put("KeepAliveC2SPacket", KeepAliveC2SPacket.class);
        nameToPacket.put("ResourcePackStatusC2SPacket", ResourcePackStatusC2SPacket.class);

        nameToPacket.put("ReadyC2SPacket", ReadyC2SPacket.class);

        nameToPacket.put("HandshakeC2SPacket", HandshakeC2SPacket.class);

        nameToPacket.put("EnterConfigurationsC2SPacket", EnterConfigurationC2SPacket.class);
        nameToPacket.put("LoginHelloC2SPacket", LoginHelloC2SPacket.class);
        nameToPacket.put("LoginKeyC2SPacket", LoginKeyC2SPacket.class);
        nameToPacket.put("LoginQueryRequestC2SPacket", LoginQueryResponseC2SPacket.class);

        nameToPacket.put("AcknowledgeChunksC2SPacket", AcknowledgeChunksC2SPacket.class);
        nameToPacket.put("AcknowledgeReconfigurationC2SPacket", AcknowledgeReconfigurationC2SPacket.class);
        nameToPacket.put("AdvancementTabC2SPacket", AdvancementTabC2SPacket.class);
        nameToPacket.put("BoatPaddleStateC2SPacket", BoatPaddleStateC2SPacket.class);
        nameToPacket.put("BookUpdateC2SPacket", BookUpdateC2SPacket.class);
        nameToPacket.put("ButtonClickC2SPacket", ButtonClickC2SPacket.class);
        nameToPacket.put("ChatMessageC2SPacket", ChatMessageC2SPacket.class);
        nameToPacket.put("ClickSlotC2SPacket", ClickSlotC2SPacket.class);
        nameToPacket.put("ClientCommandC2SPacket", ClientCommandC2SPacket.class);
        nameToPacket.put("ClientStatusC2SPacket", ClientStatusC2SPacket.class);
        nameToPacket.put("CloseHandledScreenC2SPacket", CloseHandledScreenC2SPacket.class);
        nameToPacket.put("CommandExecutionC2SPacket", CommandExecutionC2SPacket.class);
        nameToPacket.put("CraftRequestC2SPacket", CraftRequestC2SPacket.class);
        nameToPacket.put("CreativeInventoryActionC2SPacket", CreativeInventoryActionC2SPacket.class);
        nameToPacket.put("HandSwingC2SPacket", HandSwingC2SPacket.class);
        nameToPacket.put("JigsawGeneratingC2SPacket", JigsawGeneratingC2SPacket.class);
        nameToPacket.put("MessageAcknowledgmentC2SPacket", MessageAcknowledgmentC2SPacket.class);
        nameToPacket.put("PickFromInventoryC2SPacket", PickFromInventoryC2SPacket.class);
        nameToPacket.put("PlayerActionC2SPacket", PlayerActionC2SPacket.class);
        nameToPacket.put("PlayerInputC2SPacket", PlayerInputC2SPacket.class);
        nameToPacket.put("PlayerInteractBlockC2SPacket", PlayerInteractBlockC2SPacket.class);
        nameToPacket.put("PlayerInteractEntityC2SPacket", PlayerInteractEntityC2SPacket.class);
        nameToPacket.put("PlayerInteractItemC2SPacket", PlayerInteractItemC2SPacket.class);
        nameToPacket.put("PlayerMoveC2SPacket.Full", PlayerMoveC2SPacket.Full.class);
        nameToPacket.put("PlayerMoveC2SPacket.LookAndOnGround", PlayerMoveC2SPacket.LookAndOnGround.class);
        nameToPacket.put("PlayerMoveC2SPacket.OnGroundOnly", PlayerMoveC2SPacket.OnGroundOnly.class);
        nameToPacket.put("PlayerMoveC2SPacket.PositionAndOnGround", PlayerMoveC2SPacket.PositionAndOnGround.class);
        nameToPacket.put("PlayerMoveC2SPacket", PlayerMoveC2SPacket.class);
        nameToPacket.put("PlayerSessionC2SPacket", PlayerSessionC2SPacket.class);
        nameToPacket.put("QueryBlockNbtC2SPacket", QueryBlockNbtC2SPacket.class);
        nameToPacket.put("QueryEntityNbtC2SPacket", QueryEntityNbtC2SPacket.class);
        nameToPacket.put("RecipeBookDataC2SPacket", RecipeBookDataC2SPacket.class);
        nameToPacket.put("RecipeCategoryOptionsC2SPacket", RecipeCategoryOptionsC2SPacket.class);
        nameToPacket.put("RenameItemC2SPacket", RenameItemC2SPacket.class);
        nameToPacket.put("RequestCommandCompletionsC2SPacket", RequestCommandCompletionsC2SPacket.class);
        nameToPacket.put("SelectMerchantTradeC2SPacket", SelectMerchantTradeC2SPacket.class);
        nameToPacket.put("SlotChangedStateC2SPacket", SlotChangedStateC2SPacket.class);
        nameToPacket.put("SpectatorTeleportC2SPacket", SpectatorTeleportC2SPacket.class);
        nameToPacket.put("TeleportConfirmC2SPacket", TeleportConfirmC2SPacket.class);
        nameToPacket.put("UpdateBeaconC2SPacket", UpdateBeaconC2SPacket.class);
        nameToPacket.put("UpdateCommandBlockC2SPacket", UpdateCommandBlockC2SPacket.class);
        nameToPacket.put("UpdateCommandBlockMinecartC2SPacket", UpdateCommandBlockMinecartC2SPacket.class);
        nameToPacket.put("UpdateDifficultyC2SPacket", UpdateDifficultyC2SPacket.class);
        nameToPacket.put("UpdateDifficultyLockC2SPacket", UpdateDifficultyLockC2SPacket.class);
        nameToPacket.put("UpdateJigsawC2SPacket", UpdateJigsawC2SPacket.class);
        nameToPacket.put("UpdatePlayerAbilitiesC2SPacket", UpdatePlayerAbilitiesC2SPacket.class);
        nameToPacket.put("UpdateSelectedSlotC2SPacket", UpdateSelectedSlotC2SPacket.class);
        nameToPacket.put("UpdateSignC2SPacket", UpdateSignC2SPacket.class);
        nameToPacket.put("UpdateStructureBlockC2SPacket", UpdateStructureBlockC2SPacket.class);
        nameToPacket.put("VehicleMoveC2SPacket", VehicleMoveC2SPacket.class);

        nameToPacket.put("QueryPingC2SPacket", QueryPingC2SPacket.class);
        nameToPacket.put("QueryRequestC2SPacket", QueryRequestC2SPacket.class);

        nameToPacket.put("CommonPingS2CPacket", CommonPingS2CPacket.class);
        nameToPacket.put("CustomPayloadS2CPacket", CustomPayloadS2CPacket.class);
        nameToPacket.put("DisconnectS2CPacket", DisconnectS2CPacket.class);
        nameToPacket.put("KeepAliveS2CPacket", KeepAliveS2CPacket.class);
        nameToPacket.put("ResourcePackRemoveS2CPacket", ResourcePackRemoveS2CPacket.class);
        nameToPacket.put("ResourcePackSendS2CPacket", ResourcePackSendS2CPacket.class);
        nameToPacket.put("SynchronizeTagsS2CPacket", SynchronizeTagsS2CPacket.class);

        nameToPacket.put("DynamicRegistriesS2CPacket", DynamicRegistriesS2CPacket.class);
        nameToPacket.put("FeaturesS2CPacket", FeaturesS2CPacket.class);
        nameToPacket.put("ReadyS2CPacket", ReadyS2CPacket.class);

        nameToPacket.put("LoginCompressionS2CPacket", LoginCompressionS2CPacket.class);
        nameToPacket.put("LoginDisconnectS2CPacket", LoginDisconnectS2CPacket.class);
        nameToPacket.put("LoginHelloS2CPacket", LoginHelloS2CPacket.class);
        nameToPacket.put("LoginQueryResponseS2CPacket", LoginQueryRequestS2CPacket.class);
        nameToPacket.put("LoginSuccessS2CPacket", LoginSuccessS2CPacket.class);

        nameToPacket.put("AdvancementUpdateS2CPacket", AdvancementUpdateS2CPacket.class);
        nameToPacket.put("BlockBreakingProgressS2CPacket", BlockBreakingProgressS2CPacket.class);
        nameToPacket.put("BlockEntityUpdateS2CPacket", BlockEntityUpdateS2CPacket.class);
        nameToPacket.put("BlockEventS2CPacket", BlockEventS2CPacket.class);
        nameToPacket.put("BlockUpdateS2CPacket", BlockUpdateS2CPacket.class);
        nameToPacket.put("BossBarS2CPacket", BossBarS2CPacket.class);
        nameToPacket.put("ChatMessageS2CPacket", ChatMessageS2CPacket.class);
        nameToPacket.put("ChatSuggestionsS2CPacket", ChatSuggestionsS2CPacket.class);
        nameToPacket.put("ChunkBiomeDataS2CPacket", ChunkBiomeDataS2CPacket.class);
        nameToPacket.put("ChunkDataS2CPacket", ChunkDataS2CPacket.class);
        nameToPacket.put("ChunkDeltaUpdateS2CPacket", ChunkDeltaUpdateS2CPacket.class);
        nameToPacket.put("ChunkLoadDistanceS2CPacket", ChunkLoadDistanceS2CPacket.class);
        nameToPacket.put("ChunkRenderDistanceCenterS2CPacket", ChunkRenderDistanceCenterS2CPacket.class);
        nameToPacket.put("ChunkSentS2CPacket", ChunkSentS2CPacket.class);
        nameToPacket.put("ClearTitleS2CPacket", ClearTitleS2CPacket.class);
        nameToPacket.put("CloseScreenS2CPacket", CloseScreenS2CPacket.class);
        nameToPacket.put("CommandSuggestionsS2CPacket", CommandSuggestionsS2CPacket.class);
        nameToPacket.put("CommandTreeS2CPacket", CommandTreeS2CPacket.class);
        nameToPacket.put("CooldownUpdateS2CPacket", CooldownUpdateS2CPacket.class);
        nameToPacket.put("CraftFailedResponseS2CPacket", CraftFailedResponseS2CPacket.class);
        nameToPacket.put("DamageTiltS2CPacket", DamageTiltS2CPacket.class);
        nameToPacket.put("DeathMessageS2CPacket", DeathMessageS2CPacket.class);
        nameToPacket.put("DifficultyS2CPacket", DifficultyS2CPacket.class);
        nameToPacket.put("EndCombatS2CPacket", EndCombatS2CPacket.class);
        nameToPacket.put("EnterCombatS2CPacket", EnterCombatS2CPacket.class);
        nameToPacket.put("EnterReconfigurationS2CPacket", EnterReconfigurationS2CPacket.class);
        nameToPacket.put("EntitiesDestroyS2CPacket", EntitiesDestroyS2CPacket.class);
        nameToPacket.put("EntityAnimationS2CPacket", EntityAnimationS2CPacket.class);
        nameToPacket.put("EntityAttachS2CPacket", EntityAttachS2CPacket.class);
        nameToPacket.put("EntityAttributesS2CPacket", EntityAttributesS2CPacket.class);
        nameToPacket.put("EntityDamageS2CPacket", EntityDamageS2CPacket.class);
        nameToPacket.put("EntityEquipmentUpdateS2CPacket", EntityEquipmentUpdateS2CPacket.class);
        nameToPacket.put("EntityPassengersSetS2CPacket", EntityPassengersSetS2CPacket.class);
        nameToPacket.put("EntityPositionS2CPacket", EntityPositionS2CPacket.class);
        nameToPacket.put("EntityS2CPacket.MoveRelative", EntityS2CPacket.MoveRelative.class);
        nameToPacket.put("EntityS2CPacket.Rotate", EntityS2CPacket.Rotate.class);
        nameToPacket.put("EntityS2CPacket.RotateAndMoveRelative", EntityS2CPacket.RotateAndMoveRelative.class);
        nameToPacket.put("EntitySetHeadYawS2CPacket", EntitySetHeadYawS2CPacket.class);
        nameToPacket.put("EntitySpawnS2CPacket", EntitySpawnS2CPacket.class);
        nameToPacket.put("EntityStatusEffectS2CPacket", EntityStatusEffectS2CPacket.class);
        nameToPacket.put("EntityStatusS2CPacket", EntityStatusS2CPacket.class);
        nameToPacket.put("EntityTrackerUpdateS2CPacket", EntityTrackerUpdateS2CPacket.class);
        nameToPacket.put("EntityVelocityUpdateS2CPacket", EntityVelocityUpdateS2CPacket.class);
        nameToPacket.put("ExperienceBarUpdateS2CPacket", ExperienceBarUpdateS2CPacket.class);
        nameToPacket.put("ExperienceOrbSpawnS2CPacket", ExperienceOrbSpawnS2CPacket.class);
        nameToPacket.put("ExplosionS2CPacket", ExplosionS2CPacket.class);
        nameToPacket.put("GameJoinS2CPacket", GameJoinS2CPacket.class);
        nameToPacket.put("GameMessageS2CPacket", GameMessageS2CPacket.class);
        nameToPacket.put("GameStateChangeS2CPacket", GameStateChangeS2CPacket.class);
        nameToPacket.put("HealthUpdateS2CPacket", HealthUpdateS2CPacket.class);
        nameToPacket.put("InventoryS2CPacket", InventoryS2CPacket.class);
        nameToPacket.put("ItemPickupAnimationS2CPacket", ItemPickupAnimationS2CPacket.class);
        nameToPacket.put("LightUpdateS2CPacket", LightUpdateS2CPacket.class);
        nameToPacket.put("LookAtS2CPacket", LookAtS2CPacket.class);
        nameToPacket.put("MapUpdateS2CPacket", MapUpdateS2CPacket.class);
        nameToPacket.put("NbtQueryResponseS2CPacket", NbtQueryResponseS2CPacket.class);
        nameToPacket.put("OpenHorseScreenS2CPacket", OpenHorseScreenS2CPacket.class);
        nameToPacket.put("OpenScreenS2CPacket", OpenScreenS2CPacket.class);
        nameToPacket.put("OpenWrittenBookS2CPacket", OpenWrittenBookS2CPacket.class);
        nameToPacket.put("OverlayMessageS2CPacket", OverlayMessageS2CPacket.class);
        nameToPacket.put("ParticleS2CPacket", ParticleS2CPacket.class);
        nameToPacket.put("PlayerAbilitiesS2CPacket", PlayerAbilitiesS2CPacket.class);
        nameToPacket.put("PlayerActionResponseS2CPacket", PlayerActionResponseS2CPacket.class);
        nameToPacket.put("PlayerListHeaderS2CPacket", PlayerListHeaderS2CPacket.class);
        nameToPacket.put("PlayerListS2CPacket", PlayerListS2CPacket.class);
        nameToPacket.put("PlayerPositionLookS2CPacket", PlayerPositionLookS2CPacket.class);
        nameToPacket.put("PlayerRemoveS2CPacket", PlayerRemoveS2CPacket.class);
        nameToPacket.put("PlayerRespawnS2CPacket", PlayerRespawnS2CPacket.class);
        nameToPacket.put("PlayerSpawnPositionS2CPacket", PlayerSpawnPositionS2CPacket.class);
        nameToPacket.put("PlaySoundFromEntityS2CPacket", PlaySoundFromEntityS2CPacket.class);
        nameToPacket.put("PlaySoundS2CPacket", PlaySoundS2CPacket.class);
        nameToPacket.put("ProfilelessChatMessageS2CPacket", ProfilelessChatMessageS2CPacket.class);
        nameToPacket.put("RemoveEntityStatusEffectS2CPacket", RemoveEntityStatusEffectS2CPacket.class);
        nameToPacket.put("RemoveMessageS2CPacket", RemoveMessageS2CPacket.class);
        nameToPacket.put("ScoreboardDisplayS2CPacket", ScoreboardDisplayS2CPacket.class);
        nameToPacket.put("ScoreboardObjectiveUpdateS2CPacket", ScoreboardObjectiveUpdateS2CPacket.class);
        nameToPacket.put("ScoreboardScoreResetS2CPacket", ScoreboardScoreResetS2CPacket.class);
        nameToPacket.put("ScoreboardScoreUpdateS2CPacket", ScoreboardScoreUpdateS2CPacket.class);
        nameToPacket.put("ScreenHandlerPropertyUpdateS2CPacket", ScreenHandlerPropertyUpdateS2CPacket.class);
        nameToPacket.put("ScreenHandlerSlotUpdateS2CPacket", ScreenHandlerSlotUpdateS2CPacket.class);
        nameToPacket.put("SelectAdvancementTabS2CPacket", SelectAdvancementTabS2CPacket.class);
        nameToPacket.put("ServerMetadataS2CPacket", ServerMetadataS2CPacket.class);
        nameToPacket.put("SetCameraEntityS2CPacket", SetCameraEntityS2CPacket.class);
        nameToPacket.put("SetTradeOffersS2CPacket", SetTradeOffersS2CPacket.class);
        nameToPacket.put("SignEditorOpenS2CPacket", SignEditorOpenS2CPacket.class);
        nameToPacket.put("SimulationDistanceS2CPacket", SimulationDistanceS2CPacket.class);
        nameToPacket.put("StartChunkSendS2CPacket", StartChunkSendS2CPacket.class);
        nameToPacket.put("StatisticsS2CPacket", StatisticsS2CPacket.class);
        nameToPacket.put("StopSoundS2CPacket", StopSoundS2CPacket.class);
        nameToPacket.put("SubtitleS2CPacket", SubtitleS2CPacket.class);
        nameToPacket.put("SynchronizeRecipesS2CPacket", SynchronizeRecipesS2CPacket.class);
        nameToPacket.put("TeamS2CPacket", TeamS2CPacket.class);
        nameToPacket.put("TickStepS2CPacket", TickStepS2CPacket.class);
        nameToPacket.put("TitleFadeS2CPacket", TitleFadeS2CPacket.class);
        nameToPacket.put("TitleS2CPacket", TitleS2CPacket.class);
        nameToPacket.put("UnloadChunkS2CPacket", UnloadChunkS2CPacket.class);
        nameToPacket.put("UnlockRecipesS2CPacket", UnlockRecipesS2CPacket.class);
        nameToPacket.put("UpdateSelectedSlotS2CPacket", UpdateSelectedSlotS2CPacket.class);
        nameToPacket.put("UpdateTickRateS2CPacket", UpdateTickRateS2CPacket.class);
        nameToPacket.put("VehicleMoveS2CPacket", VehicleMoveS2CPacket.class);
        nameToPacket.put("WorldBorderCenterChangedS2CPacket", WorldBorderCenterChangedS2CPacket.class);
        nameToPacket.put("WorldBorderInitializeS2CPacket", WorldBorderInitializeS2CPacket.class);
        nameToPacket.put("WorldBorderInterpolateSizeS2CPacket", WorldBorderInterpolateSizeS2CPacket.class);
        nameToPacket.put("WorldBorderSizeChangedS2CPacket", WorldBorderSizeChangedS2CPacket.class);
        nameToPacket.put("WorldBorderWarningBlocksChangedS2CPacket", WorldBorderWarningBlocksChangedS2CPacket.class);
        nameToPacket.put("WorldBorderWarningTimeChangedS2CPacket", WorldBorderWarningTimeChangedS2CPacket.class);
        nameToPacket.put("WorldEventS2CPacket", WorldEventS2CPacket.class);
        nameToPacket.put("WorldTimeUpdateS2CPacket", WorldTimeUpdateS2CPacket.class);

        nameToPacket.put("PingResultS2CPacket", PingResultS2CPacket.class);
        nameToPacket.put("QueryResponseS2CPacket", QueryResponseS2CPacket.class);
    }

    public Class<? extends Packet<?>> stringToPacket(String string) {
        return nameToPacket.get(string);
    }

    public String[] getPacketNames() {
        return nameToPacket.keySet().toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends Packet<?>>[] getPacketClasses() {
        return nameToPacket.values().toArray(new Class[0]);
    }
}
