package de.simonsator.partyandfriends.velocity.logtool;

import com.velocitypowered.api.event.Subscribe;
import de.simonsator.partyandfriends.velocity.api.PAFExtension;
import de.simonsator.partyandfriends.velocity.api.events.message.FriendMessageEvent;
import de.simonsator.partyandfriends.velocity.api.events.message.FriendOnlineMessageEvent;
import de.simonsator.partyandfriends.velocity.api.events.message.PartyMessageEvent;
import de.simonsator.partyandfriends.velocity.friends.commands.Friends;
import de.simonsator.partyandfriends.velocity.logtool.configuration.LogToolConfig;
import de.simonsator.partyandfriends.velocity.logtool.friends.FriendSpySubCommand;
import de.simonsator.partyandfriends.velocity.logtool.logger.FriendLogger;
import de.simonsator.partyandfriends.velocity.logtool.logger.PartyLogger;
import de.simonsator.partyandfriends.velocity.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public class LogToolMain extends PAFExtension {
	private static LogToolMain instance;
	private PartyLogger partyLogger;
	private FriendLogger friendLogger;
	private ConfigurationCreator config;

	public LogToolMain(Path folder) {
		super(folder);
	}

	public static LogToolMain getInstance() {
		return instance;
	}

	public ConfigurationCreator getConfig() {
		return config;
	}

	@Override
	public void onEnable() {
		instance = this;
		try {
			config = new LogToolConfig(new File(getConfigFolder(), "config.yml"), this);
			if (config.getBoolean("Party.LoggerEnabled"))
				partyLogger = new PartyLogger(new File(getConfigFolder(), "party.log"), this);
			if (config.getBoolean("Friends.LoggerEnabled"))
				friendLogger = new FriendLogger(new File(getConfigFolder(), "friend.log"), this);
			registerAsExtension();
			if (getConfig().getBoolean("Friends.SpyCommand.Use"))
				Friends.getInstance().addCommand(new FriendSpySubCommand(getConfig().getStringList("Friends.SpyCommand.Names"),
						getConfig().getInt("Friends.SpyCommand.Priority"),
						getConfig().getString("Friends.SpyCommand.Messages.CommandUsage"),
						getConfig().getString("Friends.SpyCommand.Permission"),
						friendLogger));
		} catch (IOException e) {
			System.out.println("Fatal error");
			e.printStackTrace();
		}
		LogToolLoader.server.getEventManager().register(this, this);
	}

	@Override
	public String getName() {
		return "LogTool";
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

	@Subscribe
	public void friendMessage(FriendMessageEvent pEvent) {
		if (friendLogger != null)
			friendLogger.writeln(pEvent.getSender(), pEvent.getReceiver(), pEvent.getMessage());
	}

	@Subscribe
	public void friendMessage(FriendOnlineMessageEvent pEvent) {
		friendMessage((FriendMessageEvent) pEvent);
	}

	@Subscribe
	public void partyMessage(PartyMessageEvent pEvent) {
		if (partyLogger != null)
			partyLogger.writeln(pEvent.getSender(), pEvent.getParty(), pEvent.getMessage());
	}
}
