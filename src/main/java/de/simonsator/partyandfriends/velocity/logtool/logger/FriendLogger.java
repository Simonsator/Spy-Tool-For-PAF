package de.simonsator.partyandfriends.velocity.logtool.logger;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import de.simonsator.partyandfriends.velocity.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.velocity.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.velocity.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.velocity.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.velocity.friends.commands.Friends;
import de.simonsator.partyandfriends.velocity.logtool.FriendSpyContainer;
import de.simonsator.partyandfriends.velocity.logtool.LogToolLoader;
import de.simonsator.partyandfriends.velocity.logtool.LogToolMain;
import de.simonsator.partyandfriends.velocity.main.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static de.simonsator.partyandfriends.utilities.PatterCollection.*;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public class FriendLogger extends Logger {
	private List<FriendSpyContainer> spyList;

	public FriendLogger(File pFile, LogToolMain pPlugin) throws IOException {
		super(pFile, pPlugin);
		if (pPlugin.getConfig().getBoolean("Friends.SpyCommand.Use")) {
			spyList = new ArrayList<>();
			LogToolLoader.server.getEventManager().register(pPlugin, this);
		}
	}

	@Override
	public void writeln(OnlinePAFPlayer pSender, Object pReceiver, String pMessage) {
		PAFPlayer receiverPAFPlayer = ((PAFPlayer) pReceiver);
		cache.add(pSender.getName() + "->" + ((PAFPlayer) pReceiver).getName() + ":" + pMessage);
		if (spyList != null)
			for (FriendSpyContainer spyContainer : spyList) {
				if ((spyContainer.SPYED_ON.equals(pSender) || spyContainer.SPYED_ON.equals(pReceiver)) && !(spyContainer.SPYER.equals(pSender) || spyContainer.SPYER.equals(pReceiver))) {
					String message = (Friends.getInstance().getPrefix() + CONTENT_PATTERN.matcher(PLAYER_PATTERN.matcher(SENDER_NAME_PATTERN.matcher(Main.getInstance()
							.getMessages().getString("Friends.Command.MSG.SentMessage")).replaceAll(Matcher.quoteReplacement(pSender.getDisplayName()))).replaceAll(Matcher.quoteReplacement(receiverPAFPlayer.getDisplayName()))).replaceAll(Matcher.quoteReplacement(pMessage.replace(" ", Main.getInstance().getMessages().getString("Friends.Command.MSG.ColorOfMessage")))));
					spyContainer.SPYER.sendMessage(message);
				}
			}
	}

	public boolean addSpy(FriendSpyContainer pSpyContainer) {
		if (spyList.contains(pSpyContainer))
			return false;
		spyList.add(pSpyContainer);
		return true;
	}

	public void removeFromSpyList(OnlinePAFPlayer pSpyer, PAFPlayer pSpyedOn) {
		spyList.remove(new FriendSpyContainer(pSpyer, pSpyedOn));
	}

	@Subscribe
	public void onLeave(DisconnectEvent pEvent) {
		BukkitBungeeAdapter.getInstance().runAsync(LogToolMain.getInstance(), () -> {
			OnlinePAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pEvent.getPlayer());
			List<FriendSpyContainer> toRemove = new ArrayList<>();
			for (FriendSpyContainer spyContainer : spyList)
				if (spyContainer.SPYER.equals(player))
					toRemove.add(spyContainer);
			spyList.removeAll(toRemove);
		});
	}
}
