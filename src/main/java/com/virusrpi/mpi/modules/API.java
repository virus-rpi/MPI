package com.virusrpi.mpi.modules;

import static spark.Spark.*;

import com.virusrpi.mpi.Client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.text.Text;

public class API {
	MinecraftClient mc;
	String address;
	boolean disconnect;
	boolean connect;
	boolean multiplayerScreen;
	Text reason;

	public API(MinecraftClient mc) {
		this.mc = mc;
		address = "";
		disconnect = false;
		multiplayerScreen = false;
		connect = false;
		reason = Text.of("");

		port(25567);

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
			if (mc.currentScreen instanceof DisconnectedScreen) {
				response.status(200);
				return reason.getString();
			} else {
				response.status(400);
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
}
