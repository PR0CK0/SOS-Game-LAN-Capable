package prockot.sos4.model.game;
import java.util.HashSet;
import prockot.sos4.model.core.GameBoard;
import prockot.sos4.model.core.Player;
import prockot.sos4.model.core.SOS;
import prockot.sos4.model.core.Turn;
import prockot.sos4.model.rules.SOSRules;

public abstract class GamePlayModel
{
	public GamePlayModel(int aBoardSize, Player player1, Player player2, String aGameMode)
	{
		turnCallback = null;
		gameOverCallback = null;
		newSOSCallback = null;
		
		boardSize = aBoardSize;
		gameMode = aGameMode;
		
		board = new GameBoard(aBoardSize);
		turns = new Turn(player1, player2);
		rules = new SOSRules(board).create(aGameMode);
	}
	
	public abstract void resetGame();
	
	public void doPlayerTurn(int row, int column, String letter)
	{
		board.update(row, column, letter);
			
		int score = rules.getNewScore(row, column, letter, currentPlayer);
		
		if (score > 0)
		{
			turns.getCurrentPlayer().addScore(score);
		}
		else
		{
			turns.nextTurn();
			currentPlayer = turns.getCurrentPlayer();
		}
		
		handleCallbacks();
	}
	
	public String getWinner()
	{
		if (findWinner() == null)
		{
			return "no one... It's a tie.";
		}
		
		return findWinner().toString();
	}
	
	public void setOnTurnPlayed(GameAction action)
	{		
		turnCallback = action;
	}
	
	public void setOnGameOver(GameAction action)
	{
		gameOverCallback = action;
	}
	
	public void setOnNewSOS(GameAction action)
	{
		newSOSCallback = action;
	}
	
	public int getBoardSize()
	{
		return boardSize;
	}
	
	public String getGameMode()
	{
		return gameMode;
	}

	public Player getPlayer1()
	{
		return turns.getPlayer1();
	}
	
	public Player getPlayer2()
	{
		return turns.getPlayer2();
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public HashSet<SOS> getPlayerSOSes()
	{
		return rules.getPlayerSOSes();
	}
	
	public int getNewSOSCount()
	{
		return rules.getNewSOScount();
	}
	
	protected final GameBoard board;
	protected final Turn turns;
	protected final SOSRules rules;
	
	protected final int boardSize;
	protected final String gameMode;
		
	protected Player currentPlayer;
	protected GameAction gameOverCallback;
	protected GameAction turnCallback;
	protected GameAction newSOSCallback;
	
	private Player findWinner()
	{
		Player p1 = turns.getPlayer1();
		Player p2 = turns.getPlayer2();
		
		if (p1.getScore() > p2.getScore())
		{
			return p1;
		}
		else if (p2.getScore() > p1.getScore())
		{
			return p2;
		}
		else 
		{
			return null;
		}
	}
	
	private void handleCallbacks()
	{
		if (turnCallback != null)
		{
			turnCallback.execute();
		}
		
		if (gameOverCallback != null && !board.isPlayable())
		{
			gameOverCallback.execute();
		}
		
		if (newSOSCallback != null && rules.getNewSOScount() > 0)
		{
			newSOSCallback.execute();					
		}
	}
}