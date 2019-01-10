package prockot.sos4.model.core;

public class SOS 
{
	public SOS(int aStartIndexRow, int aStartIndexCol, int aMiddleIndexRow, int aMiddleIndexCol,
			int aEndIndexRow, int aEndIndexCol, Player aPlayer) 
	{
		startIndexRow = aStartIndexRow;
		startIndexCol = aStartIndexCol;
		middleIndexRow = aMiddleIndexRow;
		middleIndexCol = aMiddleIndexCol;
		endIndexRow = aEndIndexRow;
		endIndexCol = aEndIndexCol;
		player = aPlayer;
	}

	public int getStartIndexRow() 
	{
		return startIndexRow;
	}
	
	public int getStartIndexCol() 
	{
		return startIndexCol;
	}
	
	public int getEndIndexRow() 
	{
		return endIndexRow;
	}
	
	public int getEndIndexCol() 
	{
		return endIndexCol;
	}
	
	public int getMiddleIndexRow() 
	{
		return middleIndexRow;
	}

	public int getMiddleIndexCol()
	{
		return middleIndexCol;
	}

	public Player getPlayer() 
	{
		return player;
	}
	
	public void setPlayer(Player aPlayer)
	{
		player = aPlayer;
	}
	
	public boolean intersects(SOS other)
	{
		if (
			 (startIntersects(other) || middleIntersects(other) || endIntersects(other)) 
			   && 
			 !getPlayer().equals(other.getPlayer())
		   )
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + endIndexCol;
		result = prime * result + endIndexRow;
		result = prime * result + startIndexCol;
		result = prime * result + startIndexRow;
		result = prime * result + middleIndexCol;
		result = prime * result + middleIndexRow;
		result = prime * result + player.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;			
		}
		if (obj == null)
		{
			return false;			
		}
		if (getClass() != obj.getClass())
		{
			return false;			
		}
		
		SOS other = (SOS) obj;
		
		if (endIndexCol != other.endIndexCol || endIndexRow != other.endIndexRow
				|| startIndexCol != other.startIndexCol || startIndexRow != other.startIndexRow
				|| middleIndexCol != other.middleIndexCol || middleIndexRow != other.middleIndexRow
				|| !player.equals(other.player))
		{
			return false;			
		}
		
		return true;
	}
	
	@Override
	public String toString()
	{
		return "(" + startIndexRow + ", " + startIndexCol + ")"
				+ "(" + middleIndexRow + ", " + middleIndexCol + ")"
				+ "(" + endIndexRow + ", " + endIndexCol + ") - "
				+ player.getName();
	}
	
	private int startIndexRow;
	private int startIndexCol;
	private int endIndexRow;
	private int endIndexCol;
	private int middleIndexRow;
	private int middleIndexCol;
	private Player player;
	
	private boolean startIntersects(SOS other)
	{
		return (getStartIndexRow() == other.getStartIndexRow() || getStartIndexRow() == other.getEndIndexRow())
				&& (getStartIndexCol() == other.getStartIndexCol() || getStartIndexCol() == other.getEndIndexCol());
	}
	
	private boolean middleIntersects(SOS other)
	{
		return getMiddleIndexRow() == other.getMiddleIndexRow() 
				&& getMiddleIndexCol() == other.getMiddleIndexCol();
	}
	
	private boolean endIntersects(SOS other)
	{
		return (getEndIndexRow() == other.getStartIndexRow() || getEndIndexRow() == other.getEndIndexRow())
				&& (getEndIndexCol() == other.getStartIndexCol() || getEndIndexCol() == other.getEndIndexCol());	
	}
}