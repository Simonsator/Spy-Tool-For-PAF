package de.simonsator.partyandfriends.logtool.logger;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.logtool.FriendSpyContainer;
import de.simonsator.partyandfriends.logtool.LogToolMain;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public class FriendLogger extends Logger implements Listener {
	private List<FriendSpyContainer> spyList;

	public FriendLogger(File pFile, LogToolMain pPlugin) throws IOException {
		super(pFile, pPlugin);
		if (pPlugin.getConfig().getBoolean("Friends.SpyCommand.Use")) {
			spyList = new ArrayList<>();
			pPlugin.getProxy().getPluginManager().registerListener(pPlugin, this);
		}
	}

	@Override
	public void writeln(OnlinePAFPlayer pSender, Object pReceiver, String pMessage) {
		cache.add(pSender.getName() + "->" + ((PAFPlayer) pReceiver).getName() + ":" + pMessage);
	}

	public boolean removeFromSpyList(OnlinePAFPlayer pSpyer, PAFPlayer pSpyedOn) {
		return spyList.remove(new FriendSpyContainer(pSpyer, pSpyedOn));
	}

	@EventHandler
	public void onLeave(PlayerDisconnectEvent pEvent) {
		OnlinePAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pEvent.getPlayer());
		for (int i = 0; i < spyList.size(); i++) {
			if (spyList.get(i).SPYER.equals(player)) {
				spyList.remove(i);
			}
		}
	}
}
