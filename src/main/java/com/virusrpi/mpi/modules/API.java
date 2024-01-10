package com.virusrpi.mpi.modules;

import com.virusrpi.mpi.Client;
import com.virusrpi.mpi.helper.StringToPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.Packet;
import net.minecraft.text.Text;

import java.util.Base64;
import java.util.Objects;

import static spark.Spark.get;
import static spark.Spark.port;

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

	public API(MinecraftClient mc) {
		this.mc = mc;
		address = "";
		disconnect = false;
		multiplayerScreen = false;
		connect = false;
		reason = Text.of("");
		stp = new StringToPacket();

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

		get("/sendPacket", (request, response) -> {
		    String packetString = request.queryParams("packet");
		    String bytesString = request.queryParams("bytes");
		    if (packetString == null) {
		        response.status(400);
		        return "packet parameter is required.";
		    }
		    if (bytesString == null) {
		        response.status(400);
		        return "bytes parameter is required.";
		    }
		    byte[] bytes;
		    try {
		        bytes = Base64.getDecoder().decode(bytesString);
		    } catch (IllegalArgumentException e) {
		        response.status(400);
		        return "Invalid bytes value.";
		    }
		    PacketByteBuf buf = new PacketByteBuf(Unpooled.wrappedBuffer(bytes));
		    Class<? extends Packet<?>> packetClass = stp.stringToPacket(packetString);
		    if (packetClass == null) {
		        response.status(400);
		        return "Invalid packet name: " + packetString + ".";
		    }
		    Packet<?> packet;
		    try {
		        packet = packetClass.getConstructor(PacketByteBuf.class).newInstance(buf);
		    } catch (Exception e) {
				e.printStackTrace();
		        response.status(500);
		        return "Error instantiating packet: " + e.getMessage();
		    }
		    Objects.requireNonNull(Client.getMc().getNetworkHandler()).getConnection().send(packet);
		    response.status(200);
		    return "OK";
		});

		get("/", (request, response) -> {
			String disconnectMessage;
			if (mc.currentScreen instanceof DisconnectedScreen) {
				response.status(200);
				disconnectMessage = reason.getString();
			} else {
				response.status(400);
				disconnectMessage = "Not on a DisconnectedScreen";
			}
			response.status(200);
            assert mc.currentScreen != null;
            return generateHtml(address, mc.currentScreen.toString(), disconnectMessage, Client.INSTANCE.isAutoReconnect(), Client.INSTANCE.isAutoRespawn(), Client.INSTANCE.isHeadless());
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

	private String generateHtml(String address, String currentScreen, String disconnectReason, boolean autoReconnect, boolean autoRespawn, boolean headless) {
		String autoReconnectColor = autoReconnect ? "green" : "red";
		String autoRespawnColor = autoRespawn ? "green" : "red";
		String headlessColor = headless ? "green" : "red";

		address = address.isEmpty() ? "Not connected" : address;

		String html = """
            <!DOCTYPE html>
            <html>
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width">
                    <title>MPI WebUI</title>
                    <style>
                        body {
                            font-family: sans-serif;
                            background-color: #1e1e1e;
                            color: #fff;
                            padding: 0 20px;
                            padding-left: 5%%;
                            font-size: 1.5em;
                        }
                        h1 {
                            text-align: center;
                            font-size: 3em;
                        }
                        .circle {
                            width: 30px;
                            height: 30px;
                            border-radius: 50%%;
                            margin: 10px;
                            display: inline-block;
                            vertical-align: middle;
                        }
                        .red {
                            background-color: #ff0000;
                        }
                        .green {
                            background-color: #00ff00;
                        }
                        button {
                            display: inline-block;
                            vertical-align: middle;
                            background-color: #008CBA;
                            border: none;
                            color: white;
                            text-align: center;
                            text-decoration: none;
                            display: inline-block;
                            font-size: 16px;
                            padding: 12px 24px;
                            margin: 6px 4px;
                            cursor: pointer;
                            border-radius: 10px;
                        }
                        input {
                            width: 200px;
                            padding: 12px 20px;
                            margin: 8px 0;
                            box-sizing: border-box;
                            border: 2px solid #ccc;
                            border-radius: 4px;
                        }
                        .cursive {
                            font-style: italic;
                        }
                    </style>
                    <script>
                        function setAutoReconnect(autoReconnect) {
                                fetch('/setAutoReconnect?autoReconnect=' + autoReconnect)
                                .then(() => fetch('/getAutoReconnect'))
                                .then(response => response.text())
                                .then(autoReconnect => {
                                    document.getElementById('autoReconnectCircle').className = autoReconnect === 'true' ? 'circle green' : 'circle red';
                                });
                            }
				            
                            function setAutoRespawn(autoRespawn) {
                                fetch('/setAutoRespawn?autoRespawn=' + autoRespawn)
                                .then(() => fetch('/getAutoRespawn'))
                                .then(response => response.text())
                                .then(autoRespawn => {
                                    document.getElementById('autoRespawnCircle').className = autoRespawn === 'true' ? 'circle green' : 'circle red';
                                });
                            }
				            
                            function setHeadless(headless) {
                                fetch('/setHeadless?headless=' + headless)
                                .then(() => fetch('/getHeadless'))
                                .then(response => response.text())
                                .then(headless => {
                                    document.getElementById('headlessCircle').className = headless === 'true' ? 'circle green' : 'circle red';
                                });
                            }
                            
                            function connect() {
                                let ip = document.getElementById('ip').value;
                                let port = document.getElementById('port').value;
                                if (port === '') {
                                    port = '25565';
                                }
                                if (ip === '') {
                                    return;
                                }
                                fetch('/connect?ip=' + ip + '&port=' + port);
                                
                                refresh();
                            }
                            
                            function disconnect() {
                                fetch('/disconnect');
                            }
						
                            function escapeDisconnect() {
                                 fetch('/escapeDisconnect');
                            }
                            
                            function refresh() {
                                 fetch('/getAutoReconnect')
                                 .then(response => response.text())
                                 .then(autoReconnect => {
                                     document.getElementById('autoReconnectCircle').className = autoReconnect === 'true' ? 'circle green' : 'circle red';
                                 });
				                
                                 fetch('/getAutoRespawn')
                                 .then(response => response.text())
                                 .then(autoRespawn => {
                                     document.getElementById('autoRespawnCircle').className = autoRespawn === 'true' ? 'circle green' : 'circle red';
                                 });
				                
                                 fetch('/getHeadless')
                                 .then(response => response.text())
                                 .then(headless => {
                                     document.getElementById('headlessCircle').className = headless === 'true' ? 'circle green' : 'circle red';
                                 });
                                 
                                 fetch('/getScreen')
                                 .then(response => response.text())
                                 .then(screen => {
                                     document.getElementById('screen').innerHTML = screen;
                                 });
                                 
                                 fetch('/getCurrentAddress')
                                 .then(response => response.text())
                                 .then(address => {
                                     document.getElementById('address').innerHTML = address;
                                 });
								 
                                 fetch('/getDisconnectReason')
                                 .then(response => response.text())
                                 .then(disconnectReason => {
                                     document.getElementById('disconnect_reason').innerHTML = disconnectReason;
                                 });
                            }
                    </script>
                </head>
                <body>
                    <h1>MPI WebUI</h1>
                    
                    <button onclick="refresh()">Refresh</button>
                    </br>
                    
                    <p>Current address: <span id="address" class="cursive">%s</span></p>
                    <p>Current screen: <span id="screen" class="cursive">%s</span></p>
                    <p>Current disconnect reason: <span id="disconnect_reason" class="cursive">%s</span></p>
                    
                    <p>Auto reconnect:</p>
                    <div id='autoReconnectCircle' class='circle %s'></div>
                    <button onclick="setAutoReconnect(true)">On</button>
                    <button onclick="setAutoReconnect(false)">Off</button>
                    
                    <p>Auto respawn:</p>
                    <div id='autoRespawnCircle' class='circle %s'></div>
                    <button onclick="setAutoRespawn(true)">On</button>
                    <button onclick="setAutoRespawn(false)">Off</button>
                    
                    <p>Headless:</p>
                    <div id='headlessCircle' class='circle %s'></div>
                    <button onclick="setHeadless(true)">On</button>
                    <button onclick="setHeadless(false)">Off</button>
                    
                    <p>Connect:</p>
                    <input id="ip" type="text" placeholder="IP">
                    <input id="port" type="text" placeholder="Port">
                    <button onclick="connect()">Connect</button>
				
                    <p>Disconnect:</p>
                    <button onclick="disconnect()">Disconnect</button>
				
                    <p>Escape Disconnect:</p>
                    <button onclick="escapeDisconnect()">Escape Disconnect</button>
                </body>
            </html>
            """;

		return String.format(html, address, currentScreen, disconnectReason, autoReconnectColor, autoRespawnColor, headlessColor);
	}
}
