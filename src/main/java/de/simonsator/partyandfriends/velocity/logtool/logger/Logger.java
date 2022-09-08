package de.simonsator.partyandfriends.velocity.logtool.logger;

import de.simonsator.partyandfriends.velocity.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.velocity.logtool.LogToolLoader;
import de.simonsator.partyandfriends.velocity.logtool.LogToolMain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public abstract class Logger {
	private final File FILE;
	protected List<String> cache = new ArrayList<>();

	public Logger(File pFile, LogToolMain pPlugin) throws IOException {
		FILE = pFile;
		File folder = FILE.getParentFile();
		if (!folder.exists())
			folder.mkdir();
		if (!FILE.exists())
			FILE.createNewFile();
		LogToolLoader.server.getScheduler().buildTask(pPlugin, () -> {
			try {
				save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).delay(15, TimeUnit.MINUTES).repeat(15, TimeUnit.MINUTES).schedule();
	}

	public abstract void writeln(OnlinePAFPlayer pSender, Object pReceiver, String pMessage);

	public void save() throws IOException {
		try (FileOutputStream fos = new FileOutputStream(FILE, true)) {
			for (String line : cache)
				fos.write((line + "\n").getBytes());
			cache = new ArrayList<>();
		}
	}
}
