package prockot.sos4.model.core;

public class Turn 
{	
	public Turn(Player player1, Player player2)
	{
		turnCounter = 0;
		
		players = new Player[maxPlayerCount];
	
		initializePlayerOrder(player1, player2);
	}

	public void nextTurn()
	{
		turnCounter = (turnCounter + 1) % 2;
		
		currentPlayer = players[turnCounter];
	}
	
	public void resetTurns()
	{
		players[0].reset();
		players[1].reset();
		turnCounter = 0;
		
		currentPlayer = players[turnCounter];
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public Player getPlayer1()
	{
		return players[0];
	}
	
	public Player getPlayer2()
	{
		return players[1];
	}
	
	public int getTurnCounter()
	{
		return turnCounter;
	}
	
	public void setTurnCounter(int counter)
	{
		turnCounter = counter;
	}
	
	public void initializeRandomPlayerOrder(Player p1, Player p2)
	{
		double rand = Math.random();
		
		if (rand <= initialTurnChance)
		{
			players[0] = p1;
			players[1] = p2;
		}
		else
		{
			players[0] = p2;
			players[1] = p1;
		}
		
		currentPlayer = players[0];
	}
	
	private final Player[] players;
	private final int maxPlayerCount = 2;
	private final double initialTurnChance = 0.5;
	
	private int turnCounter;
	private Player currentPlayer;
	
	private void initializePlayerOrder(Player p1, Player p2)
	{
		players[0] = p1;
		players[1] = p2;

		currentPlayer = players[0];
	}
}