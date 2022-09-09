package de.simonsator.partyandfriends.velocity.logtool;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import de.simonsator.partyandfriends.velocity.VelocityExtensionLoadingInfo;
import de.simonsator.partyandfriends.velocity.main.PAFPlugin;

import java.nio.file.Path;
@Plugin(id = "log-tool-for-paf", name = "Log-Tool-For-PAF", version = "1.0.5-SNAPSHOT",
		url = "https://www.spigotmc.org/resources/spy-tool-for-party-and-friends-for-bungeecord.32146/",
		description = "An add-on for party and friends to log things that are written via /msg or /partychat"
		, authors = {"JT122406", "Simonsator"}, dependencies = {@Dependency(id = "partyandfriends")})
public class LogToolLoader {

	private final Path folder;

	public static ProxyServer server = null;

	@Inject
	public LogToolLoader(@DataDirectory final Path pFolder, ProxyServer pServer) {
		server = pServer;
		folder = pFolder;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		PAFPlugin.loadExtension(new VelocityExtensionLoadingInfo(new LogToolMain(folder),
				"log-tool-for-paf",
				"Log-Tool-For-PAFs",
				"1.0.5-RELEASE", "JT122406, Simonsator"));
	}

}
