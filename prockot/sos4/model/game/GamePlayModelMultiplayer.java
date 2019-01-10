package prockot.sos4.model.game;
import prockot.sos4.model.core.Player;

public class GamePlayModelMultiplayer extends GamePlayModel
{	
	public GamePlayModelMultiplayer(int aBoardSize, Player player1, Player player2, String aGameMode)
	{
		super(aBoardSize, player1, player2, aGameMode);	
		
		currentPlayer = player1;
	}
	
	@Override
	public void resetGame()
	{
		turns.resetTurns();		
		
		board.reset();
		rules.reset();
	}
	
	public void setTurnCounter(int newVal)
	{
		turns.setTurnCounter(newVal);
	}
	
	public int getTurnCounter()
	{
		return turns.getTurnCounter();
	}
}