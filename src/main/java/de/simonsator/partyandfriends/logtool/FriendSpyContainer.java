package de.simonsator.partyandfriends.logtool;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;

public class FriendSpyContainer {
	public final OnlinePAFPlayer SPYER;
	public final PAFPlayer SPYED_ON;

	public FriendSpyContainer(OnlinePAFPlayer pSpyer, PAFPlayer pSpyedOn) {
		SPYER = pSpyer;
		SPYED_ON = pSpyedOn;
	}
}
