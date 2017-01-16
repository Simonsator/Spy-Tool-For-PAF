package de.simonsator.partyandfriends.logtool;

import de.simonsator.partyandfriends.api.events.message.FriendMessageEvent;
import de.simonsator.partyandfriends.api.events.message.FriendOnlineMessageEvent;
import de.simonsator.partyandfriends.api.events.message.PartyMessageEvent;
import de.simonsator.partyandfriends.logtool.logger.FriendLogger;
import de.simonsator.partyandfriends.logtool.logger.PartyLogger;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public class LogToolMain extends Plugin implements Listener {
	private PartyLogger partyLogger;
	private FriendLogger friendLogger;

	@Override
	public void onEnable() {
		try {
			partyLogger = new PartyLogger(new File(getDataFolder(), "party.log"), this);
			friendLogger = new FriendLogger(new File(getDataFolder(), "friend.log"), this);
		} catch (IOException e) {
			System.out.println("Fatal error");
			e.printStackTrace();
		}
		getProxy().getPluginManager().registerListener(this, this);
	}

	@Override
	public void onDisable() {
		try {
			partyLogger.save();
			friendLogger.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void friendMessage(FriendMessageEvent pEvent) {
		friendLogger.writeln(pEvent.getSender(), pEvent.getReceiver(), pEvent.getMessage());
	}

	@EventHandler
	public void friendMessage(FriendOnlineMessageEvent pEvent) {

	}

	@EventHandler
	public void partyMessage(PartyMessageEvent pEvent) {
		partyLogger.writeln(pEvent.getSender(), pEvent.getParty(), pEvent.getMessage());
	}
}
