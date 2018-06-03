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
		set("Friends.SpyCommand.Use", true);

	}
}
