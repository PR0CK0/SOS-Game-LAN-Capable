package prockot.sos4.model.rules;
import prockot.sos4.model.core.GameBoard;
import prockot.sos4.model.core.Player;
import prockot.sos4.model.core.SOS;

public class SOSRulesCombat extends SOSRules
{
	public SOSRulesCombat(GameBoard board)
	{
		super(board);
	}
	
	@Override
	protected int getUpdatedScore(SOS sosToAdd)
	{
		int addedScore = 0;
		
		getPlayerSOSes().add(sosToAdd);
		incrementNewSOSCounter();
		addedScore += scoreAmount;
		
		addedScore += checkForStolenSOSes(sosToAdd);
		
		return addedScore;
	}
	
	private int checkForStolenSOSes(SOS newSOS)
	{
		int stolenPoints = 0;
		
		for (SOS sosToCheck : getPlayerSOSes())
		{
			if (newSOS.intersects(sosToCheck))
			{
				incrementNewSOSCounter();
				stolenPoints++;
				
				updateStolenSOS(sosToCheck, newSOS.getPlayer());
			}
		}
			
		return stolenPoints;
	}
	
	private void updateStolenSOS(SOS stolenSOS, Player stealingPlayer)
	{
		stolenSOS.getPlayer().subtractScore(scoreAmount);
		stolenSOS.setPlayer(stealingPlayer);
	}
}