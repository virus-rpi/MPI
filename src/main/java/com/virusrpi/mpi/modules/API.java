package com.virusrpi.mpi.modules;

import com.virusrpi.mpi.Client;
import com.virusrpi.mpi.helper.StringToPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static spark.Spark.*;

// TODO: Add endpoint to stream client into browser for remote control
// TODO: Add endpoint to get incoming packets
// TODO: Add endpoint to download loaded chunks

public class API {
	MinecraftClient mc;
	String address;
	boolean disconnect;
	boolean connect;
	boolean multiplayerScreen;
	Text reason;
	StringToPacket stp;
	ArrayList<Class<? extends Packet<?>>> packetsToSuppress;

	public API(MinecraftClient mc) {
		this.mc = mc;
		address = "";
		disconnect = false;
		multiplayerScreen = false;
		connect = false;
		reason = Text.of("");
		stp = new StringToPacket();
		packetsToSuppress = new ArrayList<>();

		port(25567);

		staticFiles.location("/public");

		get("/connect", (request, response) -> {
			disconnect = true;

			String ip = request.queryParams("ip");
			String portStr = request.queryParams("port");

			if (ip == null || portStr == null) {
				response.status(400);
				return "IP and port parameters are required.";
			}

			int port;
			try {
				port = Integer.parseInt(portStr);
			} catch (NumberFormatException e) {
				response.status(400);
				return "Invalid port number.";
			}

			address = ip + ":" + port;
			connect = true;

			// mc.openPauseMenu(true);

			System.out.println("Connection established to " + ip + ":" + port);

			response.status(200);
			return "OK";
		});

		get("/disconnect", (request, response) ->  {
			disconnect = true;
			response.status(200);
			return "OK";
		});

		get("/getScreen", (request, response) -> {
			response.status(200);
			return mc.currentScreen;
		});

		get("/favicon.ico", (request, response) -> {
			response.status(200);
			return "OK";
		});
		get("/getDisconnectReason", (request, response) -> {
			response.status(200);
			if (mc.currentScreen instanceof DisconnectedScreen) {
				return reason.getString();
			} else {
				return "Not on a DisconnectedScreen";
			}
		});
		get("/escapeDisconnect", (request, response) -> {
			multiplayerScreen = true;
			response.status(200);
			return "OK";
		});
		get("/setAutoReconnect", (request, response) -> {
			String autoReconnectStr = request.queryParams("autoReconnect");
			if (autoReconnectStr == null) {
				response.status(400);
				return "autoReconnect parameter is required.";
			}
			boolean autoReconnect;
			try {
				autoReconnect = Boolean.parseBoolean(autoReconnectStr);
			} catch (NumberFormatException e) {
				response.status(400);
				return "Invalid autoReconnect value.";
			}
			Client.INSTANCE.setAutoReconnect(autoReconnect);
			response.status(200);
			return "OK";
		});
		get("/setAutoRespawn", (request, response) -> {
			String autoRespawnStr = request.queryParams("autoRespawn");
			if (autoRespawnStr == null) {
				response.status(400);
				return "autoRespawn parameter is required.";
			}
			boolean autoRespawn;
			try {
				autoRespawn = Boolean.parseBoolean(autoRespawnStr);
			} catch (NumberFormatException e) {
				response.status(400);
				return "Invalid autoRespawn value.";
			}
			Client.INSTANCE.setAutoRespawn(autoRespawn);
			response.status(200);
			return "OK";
		});
		get("/setHeadless", (request, response) -> {
			String headlessStr = request.queryParams("headless");
			if (headlessStr == null) {
				response.status(400);
				return "headless parameter is required.";
			}
			boolean headless;
			try {
				headless = Boolean.parseBoolean(headlessStr);
			} catch (NumberFormatException e) {
				response.status(400);
				return "Invalid headless value.";
			}
			Client.INSTANCE.setHeadless(headless);
			response.status(200);
			return "OK";
		});

		get("/getAutoReconnect", (request, response) -> {
			response.status(200);
			return Client.INSTANCE.isAutoReconnect();
		});

		get("/getAutoRespawn", (request, response) -> {
			response.status(200);
			return Client.INSTANCE.isAutoRespawn();
		});

		get("/getHeadless", (request, response) -> {
			response.status(200);
			return Client.INSTANCE.isHeadless();
		});

		get("/getCurrentAddress", (request, response) -> {
			response.status(200);
			return address.isEmpty() ? "Not connected" : address;
		});

		get("/suppressPacket", (request, response) -> {
			String packetString = request.queryParams("packet");
			if (Objects.equals(packetString, "all")) {
				packetsToSuppress.addAll(Arrays.asList(stp.getPacketClasses()));
				response.status(200);
				return "OK";
			}
			if (packetString == null) {
				response.status(400);
				return "packet parameter is required.";
			}
			Class<? extends Packet<?>> packetClass = stp.stringToPacket(packetString);
			if (packetClass == null) {
				response.status(400);
				return "Invalid packet name: " + packetString + ".";
			}
			packetsToSuppress.add(packetClass);
			response.status(200);
			return "OK";
		});

		get("/unsuppressPacket", (request, response) -> {
			String packetString = request.queryParams("packet");
			if (Objects.equals(packetString, "all")) {
				packetsToSuppress.clear();
				response.status(200);
				return "OK";
			}
			if (packetString == null) {
				response.status(400);
				return "packet parameter is required.";
			}
			Class<? extends Packet<?>> packetClass = stp.stringToPacket(packetString);
			if (packetClass == null) {
				response.status(400);
				return "Invalid packet name: " + packetString + ".";
			}
			packetsToSuppress.remove(packetClass);
			response.status(200);
			return "OK";
		});

		get("/getSuppressedPackets", (request, response) -> {
			response.status(200);
			return packetsToSuppress;
		});

		get("/isSuppressed", (request, response) -> {
			String packetString = request.queryParams("packet");
			if (Objects.equals(packetString, "all")) {
				if (packetsToSuppress.isEmpty()) {
					response.status(200);
					return false;
				} else {
					response.status(200);
					return packetsToSuppress.size() == stp.getPacketClasses().length;
				}
			}
			if (packetString == null) {
				response.status(400);
				return "packet parameter is required.";
			}
			Class<? extends Packet<?>> packetClass = stp.stringToPacket(packetString);
			if (packetClass == null) {
				response.status(400);
				return "Invalid packet name: " + packetString + ".";
			}
			response.status(200);
			return packetsToSuppress.contains(packetClass);
		});

		get("/getHTMLForPackets", (request, response) -> {
		    StringBuilder packetNamesHtml = new StringBuilder();
		    String[] allPacketNames = stp.getPacketNames();
		    packetNamesHtml.append("<ul>");
			packetNamesHtml.append("<li>")
					.append("All")
					.append(" <button id='")
					.append("all")
					.append("' style=\"background-color: ")
					.append("red")
					.append(";\" onclick=\"togglePacketSuppression('")
					.append("all")
					.append("')\">")
					.append("Block")
					.append("</button></li>");
		    for (String packetName : allPacketNames) {
		        boolean isSuppressed = packetsToSuppress.contains(stp.stringToPacket(packetName));
		        String buttonText = isSuppressed ? "Unblock" : "Block";
		        String buttonColor = isSuppressed ? "green" : "red";
		        packetNamesHtml.append("<li>")
					.append(packetName)
		            .append(" <button id='")
		            .append(packetName)
		            .append("' style=\"background-color: ")
		            .append(buttonColor)
		            .append(";\" onclick=\"togglePacketSuppression('")
		            .append(packetName)
		            .append("')\">")
		            .append(buttonText)
		            .append("</button></li>");
		    }
		    packetNamesHtml.append("</ul>");

		    response.status(200);
		    return packetNamesHtml.toString();
		});
	}

	public String getAddress(){
		return address;
	}

	public boolean getDisconnect(){
		return disconnect;
	}

	public void resetDisconnect(){
		disconnect = false;
	}
	public boolean getMultiplayerScreen(){
		return multiplayerScreen;
	}
	public void resetMultiplayerScreen(){
		multiplayerScreen = false;
	}

	public void setReason(Text reason) {
		this.reason = reason;
	}

	public boolean getConnect() { return connect; }

	public void resetConnect() { connect = false; }

	public ArrayList<Class<? extends Packet<?>>> getPacketsToSuppress() {
		return packetsToSuppress;
	}
}
