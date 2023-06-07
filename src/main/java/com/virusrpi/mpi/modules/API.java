package com.virusrpi.mpi.modules;

import static spark.Spark.*;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;

public class API {
	MinecraftClient mc;
	String address;
	boolean disconnect;

	public API(MinecraftClient mc) {
		this.mc = mc;
		address = "";
		disconnect = false;

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

			mc.openPauseMenu(true);

			System.out.println("Connection established to " + ip + ":" + port);

			response.status(200);
			return "OK";
		});

		get("/say", (request, response) -> {
			String param = request.queryParams("param");

			System.out.println("Saied: " + param);

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

		get("/pause", (request, response) ->  {
			String p = request.queryParams("p");
			mc.openPauseMenu(Boolean.parseBoolean(p));

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
}
