package de.simonsator.partyandfriends.logtool;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.events.message.FriendMessageEvent;
import de.simonsator.partyandfriends.api.events.message.FriendOnlineMessageEvent;
import de.simonsator.partyandfriends.api.events.message.PartyMessageEvent;
import de.simonsator.partyandfriends.logtool.configuration.LogToolConfig;
import de.simonsator.partyandfriends.logtool.logger.FriendLogger;
import de.simonsator.partyandfriends.logtool.logger.PartyLogger;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public class LogToolMain extends PAFExtension implements Listener {
	private PartyLogger partyLogger;
	private FriendLogger friendLogger;

	@Override
	public void onEnable() {
		try {
			Configuration config = new LogToolConfig(new File(getConfigFolder(), "config.yml"), this).getCreatedConfiguration();
			if (config.getBoolean("Party.LoggerEnabled"))
				partyLogger = new PartyLogger(new File(getConfigFolder(), "party.log"), this);
			if (config.getBoolean("Friends.LoggerEnabled"))
				friendLogger = new FriendLogger(new File(getConfigFolder(), "friend.log"), this);
			registerAsExtension();
		} catch (IOException e) {
			System.out.println("Fatal error");
			e.printStackTrace();
		}
		getProxy().getPluginManager().registerListener(this, this);
	}

	@Override
	public void onDisable() {
		try {
			if (partyLogger != null)
				partyLogger.save();
			if (friendLogger != null)
				friendLogger.save();
			super.onDisable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void friendMessage(FriendMessageEvent pEvent) {
		if (friendLogger != null)
			friendLogger.writeln(pEvent.getSender(), pEvent.getReceiver(), pEvent.getMessage());
	}

	@EventHandler
	public void friendMessage(FriendOnlineMessageEvent pEvent) {

	}

	@EventHandler
	public void partyMessage(PartyMessageEvent pEvent) {
		if (partyLogger != null)
			partyLogger.writeln(pEvent.getSender(), pEvent.getParty(), pEvent.getMessage());
	}
}
