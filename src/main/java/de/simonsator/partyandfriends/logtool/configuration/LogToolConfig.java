package de.simonsator.partyandfriends.logtool.configuration;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

public class LogToolConfig extends ConfigurationCreator {
	public LogToolConfig(File file, PAFExtension pPlugin) throws IOException {
		super(file, pPlugin);
		readFile();
		loadDefaults();
		saveFile();
		process(configuration);
	}

	private void loadDefaults() {
		set("Party.LoggerEnabled", true);
		set("Friends.LoggerEnabled", true);
		set("Friends.SpyCommand.Names", "spy", "spyon");
		set("Friends.SpyCommand.Priority", 10000);
		set("Friends.SpyCommand.Permission", "de.simonsator.partyandfriends.friends.spy");
		set("Friends.SpyCommand.Use", true);
		set("Friends.SpyCommand.Messages.CommandUsage", "&8/&5friend spy [name of the player]&r &8- &7Let you spy on a player");
		set("Friends.SpyCommand.Messages.NowSpying", " &7You are now spying on &e[PLAYER]&7.");
		set("Friends.SpyCommand.Messages.AlreadySpying", " &7You are already spying on this person.");
	}
}
