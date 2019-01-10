package prockot.sos4.model.game;
import prockot.sos4.model.core.Player;

public class GamePlayModelLocal extends GamePlayModel
{
	public GamePlayModelLocal(int aBoardSize, Player aPlayer1, Player aPlayer2, String aGameMode)
	{
		super(aBoardSize, aPlayer1, aPlayer2, aGameMode);
		
		player1 = aPlayer1;
		player2 = aPlayer2;
		turns.initializeRandomPlayerOrder(player1, player2);
		currentPlayer = turns.getCurrentPlayer();
	}
	
	@Override
	public void resetGame()
	{
		turns.initializeRandomPlayerOrder(player1, player2);
		turns.resetTurns();		
		currentPlayer = turns.getCurrentPlayer();
		
		board.reset();
		rules.reset();
	}

	private Player player1;
	private Player player2;
}