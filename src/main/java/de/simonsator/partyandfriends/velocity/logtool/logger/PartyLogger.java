package de.simonsator.partyandfriends.velocity.logtool.logger;

import de.simonsator.partyandfriends.velocity.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.velocity.api.party.PlayerParty;
import de.simonsator.partyandfriends.velocity.logtool.LogToolMain;

import java.io.File;
import java.io.IOException;

/**
 * @author simonbrungs
 * @version 1.0.0 20.11.16
 */
public class PartyLogger extends Logger {
	public PartyLogger(File pFile, LogToolMain pPlugin) throws IOException {
		super(pFile, pPlugin);
	}

	@Override
	public void writeln(OnlinePAFPlayer pSender, Object pReceiver, String pMessage) {
		for (OnlinePAFPlayer pPlayer : ((PlayerParty) pReceiver).getAllPlayers())
			cache.add(pSender.getName() + "->" + pPlayer.getName() + ":" + pMessage);
	}
}
