package de.simonsator.partyandfriends.velocity.logtool;

import de.simonsator.partyandfriends.velocity.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.velocity.api.pafplayers.PAFPlayer;

public class FriendSpyContainer {
	public final OnlinePAFPlayer SPYER;
	public final PAFPlayer SPYED_ON;

	public FriendSpyContainer(OnlinePAFPlayer pSpyer, PAFPlayer pSpyedOn) {
		SPYER = pSpyer;
		SPYED_ON = pSpyedOn;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FriendSpyContainer)
			return SPYER.equals(((FriendSpyContainer) obj).SPYER) && SPYED_ON.equals(((FriendSpyContainer) obj).SPYED_ON);
		return super.equals(obj);
	}
}
