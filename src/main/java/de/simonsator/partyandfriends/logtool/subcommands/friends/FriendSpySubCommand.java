package de.simonsator.partyandfriends.logtool.subcommands.friends;

import de.simonsator.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.logtool.FriendSpyContainer;
import de.simonsator.partyandfriends.logtool.LogToolMain;
import de.simonsator.partyandfriends.logtool.logger.FriendLogger;

import java.util.List;

public class FriendSpySubCommand extends FriendSubCommand {
	private final FriendLogger FRIEND_LOGGER;

	public FriendSpySubCommand(List<String> pCommands, int pPriority, String pHelp, String pPermission, FriendLogger pFriendLogger) {
		super(pCommands, pPriority, pHelp, pPermission);
		FRIEND_LOGGER = pFriendLogger;
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer spyOn = PAFPlayerManager.getInstance().getPlayer(args[1]);
		if (!doesPlayerExist(pPlayer, spyOn))
			return;
		if (FRIEND_LOGGER.addSpy(new FriendSpyContainer(pPlayer, spyOn))) {
			pPlayer.sendMessage(PREFIX + LogToolMain.getInstance().getConfig().getString("Friends.SpyCommand.Messages.NowSpying").replace("[PLAYER]", spyOn.getDisplayName()));
			return;
		}
		pPlayer.sendMessage(PREFIX + LogToolMain.getInstance().getConfig().getString("Friends.SpyCommand.Messages.AlreadySpying"));
	}

	private boolean doesPlayerExist(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (!pGivenPlayer.doesExist()) {
			sendError(pPlayer, "Friends.General.DoesNotExist");
			return false;
		}
		return true;
	}
}
