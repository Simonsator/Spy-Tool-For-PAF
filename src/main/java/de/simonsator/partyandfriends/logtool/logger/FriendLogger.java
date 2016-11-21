package de.simonsator.partyandfriends.logtool.logger;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public class FriendLogger extends Logger {
	public FriendLogger(File pFile, Plugin pPlugin) throws IOException {
		super(pFile, pPlugin);
	}

	@Override
	public void writeln(OnlinePAFPlayer pSender, Object pReceiver, String pMessage) {
		cache.add(pSender.getName() + "->" + ((PAFPlayer) pReceiver).getName() + ":" + pMessage);
	}
}
