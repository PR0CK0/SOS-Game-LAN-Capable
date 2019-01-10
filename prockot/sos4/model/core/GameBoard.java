package prockot.sos4.model.core;

public class GameBoard 
{
	public GameBoard(int aSize)
	{
		size = aSize;
		board = new String[size][size];
		
		initializeBoard();
	}
	
	public void update(int row, int column, String letter)
	{
		board[row][column] = letter;
	}
	
	public boolean isPlayable() 
	{
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board.length; j++)
			{
				if (board[i][j].isEmpty())
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isLetterAtEqualTo(int row, int column, String letter)
	{
		if (board[row][column].equals(letter))
		{
			return true;
		}
		
		return false;
	}
	
	public void reset()
	{
		initializeBoard();
	}
	
	public int getSize()
	{
		return size;
	}
	
	@Override
	public String toString()
	{
		String grid = "";
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board.length; j++)
			{
				grid += board[i][j] + " ";
			}
			grid += "\n";
		}
		
		return grid;
	}
	
	private final String[][] board;
	private final int size;
	
	private void initializeBoard()
	{
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board.length; j++)
			{
				board[i][j] = "";
			}
		}
	}
}