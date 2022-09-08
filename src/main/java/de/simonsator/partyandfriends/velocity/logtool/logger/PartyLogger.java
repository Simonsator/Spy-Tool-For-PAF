package de.simonsator.partyandfriends.velocity.logtool.logger;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public class PartyLogger extends Logger {
	public PartyLogger(File pFile, Plugin pPlugin) throws IOException {
		super(pFile, pPlugin);
	}

	@Override
	public void writeln(OnlinePAFPlayer pSender, Object pReceiver, String pMessage) {
		for (OnlinePAFPlayer pPlayer : ((PlayerParty) pReceiver).getAllPlayers())
			cache.add(pSender.getName() + "->" + pPlayer.getName() + ":" + pMessage);
	}
}
