package prockot.sos4.model.rules;
import java.util.HashSet;
import prockot.sos4.model.core.GameBoard;
import prockot.sos4.model.core.Player;
import prockot.sos4.model.core.SOS;

public class SOSRules 
{
	public SOSRules(GameBoard aBoard)
	{
		board = aBoard;
		boardSize = board.getSize();
		SOSesOnGameBoard = new HashSet<SOS>();
		newSOScount = 0;
	}
	
	public SOSRules create(String type)
	{
		if (type.equalsIgnoreCase("normal"))
		{
			return new SOSRules(board);
		}
		else if (type.equalsIgnoreCase("extreme"))
		{
			return new SOSRulesExtreme(board);
		}
		else
		{
			return new SOSRulesCombat(board);
		}
	}
	
	public int getNewScore(int row, int column, String letter, Player player)
	{
		newSOScount = 0;
		
		if (letter.equalsIgnoreCase("O"))
		{
			return checkInput_O(row, column, player);
		}
		else
		{
			return checkInput_S(row, column, player);
		}
	}
	
	public void reset()
	{
		SOSesOnGameBoard.clear();
	}
	
	public HashSet<SOS> getPlayerSOSes() 
	{
		return SOSesOnGameBoard;
	}
	
	public int getNewSOScount()
	{
		return newSOScount;
	}
	
	protected void incrementNewSOSCounter()
	{
		newSOScount++;
	}
	
	protected int checkInput_O(int row, int column, Player player)
	{
		int addedScore = 0;
		
		addedScore += checkVerticalSOS_O(row, column, player);
		addedScore += checkHorizontalSOS_O(row, column, player);
		addedScore += checkLRUpwardDiagonalSOS_O(row, column, player);
		addedScore += checkLRDownwardDiagonalSOS_O(row, column, player);
		
		return addedScore;
	}
	
	protected int checkInput_S(int row, int column, Player player)
	{
		int addedScore = 0;
		
		addedScore += checkDownSOS_S(row, column, player);
		addedScore += checkUpSOS_S(row, column, player);
		addedScore += checkLeftSOS_S(row, column, player);
		addedScore += checkRightSOS_S(row, column, player);
		addedScore += checkLeftUpwardDiagonalSOS_S(row, column, player);
		addedScore += checkRightUpwardDiagonalSOS_S(row, column, player);
		addedScore += checkLeftDownwardSOS_S(row, column, player);
		addedScore += checkRightDownwardDiagonalSOS_S(row, column, player);
		
		return addedScore;
	}
	
	protected int getUpdatedScore(SOS sosToAdd)
	{
		SOSesOnGameBoard.add(sosToAdd);
		incrementNewSOSCounter();
		
		return scoreAmount;
	}
	
	protected int checkVerticalSOS_O(int row, int column, Player player) 
	{
		if (notAtTopOrBottom(row)
				&& board.isLetterAtEqualTo(row - 1, column, "S") 
				&& board.isLetterAtEqualTo(row + 1, column, "S"))
		{
			SOS sos = new SOS((row - 1), column, row, column, (row + 1), column, player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	protected int checkHorizontalSOS_O(int row, int column, Player player) 
	{
		if (notAtLeftOrRightEdge(column)
				&& board.isLetterAtEqualTo(row, column - 1, "S") 
				&& board.isLetterAtEqualTo(row, column + 1, "S"))
		{
			SOS sos = new SOS(row, (column - 1), row, column, row, (column + 1), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	protected int checkLRUpwardDiagonalSOS_O(int row, int column, Player player) 
	{
		if (notAtEdges(row, column)
				&& board.isLetterAtEqualTo(row + 1, column - 1, "S") 
				&& board.isLetterAtEqualTo(row - 1, column + 1, "S"))
		{
			SOS sos = new SOS((row + 1), (column - 1), row, column, (row - 1), (column + 1), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	protected int checkLRDownwardDiagonalSOS_O(int row, int column, Player player)
	{
		if (notAtEdges(row, column)
				&& board.isLetterAtEqualTo(row - 1, column - 1, "S") 
				&& board.isLetterAtEqualTo(row + 1, column + 1, "S"))
		{
			SOS sos = new SOS((row - 1), (column - 1), row , column, (row + 1), (column + 1), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	protected int checkUpSOS_S(int row, int column, Player player) 
	{		
		if (notAtTopEdge(row)
				&& board.isLetterAtEqualTo(row - 1, column, "O") 
				&& board.isLetterAtEqualTo(row - 2, column, "S"))
		{
			SOS sos = new SOS(row, column, (row - 1), column, (row - 2), column, player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	protected int checkDownSOS_S(int row, int column, Player player) 
	{
		if (notAtBottomEdge(row)
				&& board.isLetterAtEqualTo(row + 1, column, "O") 
				&& board.isLetterAtEqualTo(row + 2, column, "S"))
		{
			SOS sos = new SOS(row, column, (row + 1), column, (row + 2), column, player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	protected int checkLeftSOS_S(int row, int column, Player player) 
	{
		if (notAtLeftEdge(column)
				&& board.isLetterAtEqualTo(row, column - 1, "O") 
				&& board.isLetterAtEqualTo(row, column - 2, "S"))
		{
			SOS sos = new SOS(row, column, row, (column - 1), row, (column - 2), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	protected int checkRightSOS_S(int row, int column, Player player) 
	{
		if (notAtRightEdge(column)
				&& board.isLetterAtEqualTo(row, column + 1, "O") 
				&& board.isLetterAtEqualTo(row, column + 2, "S"))
		{
			SOS sos = new SOS(row, column, row, (column + 1), row, (column + 2), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	protected int checkRightDownwardDiagonalSOS_S(int row, int column, Player player) 
	{		
		if (notAtBottomRightCorner(row, column)
				&& board.isLetterAtEqualTo(row + 1, column + 1, "O") 
				&& board.isLetterAtEqualTo(row + 2, column + 2, "S"))
		{
			SOS sos = new SOS(row, column, (row + 1), (column + 1), (row + 2), (column + 2), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}

	protected int checkLeftDownwardSOS_S(int row, int column, Player player) 
	{		
		if (notAtBottomLeftCorner(row, column)
				&& board.isLetterAtEqualTo(row + 1, column - 1, "O") 
				&& board.isLetterAtEqualTo(row + 2, column - 2, "S"))
		{
			SOS sos = new SOS(row, column, (row + 1), (column - 1), (row + 2), (column - 2), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}

	protected int checkRightUpwardDiagonalSOS_S(int row, int column, Player player) 
	{		
		if (notAtTopRightCorner(row, column)
				&& board.isLetterAtEqualTo(row - 1, column + 1, "O") 
				&& board.isLetterAtEqualTo(row - 2, column + 2, "S"))
		{
			SOS sos = new SOS(row, column, (row - 1), (column + 1), (row - 2), (column + 2), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}

	protected int checkLeftUpwardDiagonalSOS_S(int row, int column, Player player) 
	{		
		if (notAtTopLeftCorner(row, column)
				&& board.isLetterAtEqualTo(row - 1, column - 1, "O") 
				&& board.isLetterAtEqualTo(row - 2, column - 2, "S"))
		{
			SOS sos = new SOS(row, column, (row - 1), (column - 1), (row - 2), (column - 2), player);
			return getUpdatedScore(sos);
		}
		
		return noScoreAmount;
	}
	
	private boolean notAtTopOrBottom(int row)
	{
		return row > 0 && row < boardSize - 1;
	}
	
	private boolean notAtLeftOrRightEdge(int column)
	{
		return column > 0 && column < boardSize - 1;
	}
	
	private boolean notAtEdges(int row, int column)
	{
		return (row > 0 && column > 0) && (row < boardSize - 1 && column < boardSize - 1);
	}
	
	private boolean notAtTopEdge(int row)
	{
		return row > 1;
	}
	
	private boolean notAtBottomEdge(int row)
	{
		return row < boardSize - 2;
	}
	
	private boolean notAtLeftEdge(int column)
	{
		return column > 1;
	}
	
	private boolean notAtRightEdge(int column)
	{
		return column < boardSize - 2;
	}
	
	private boolean notAtBottomRightCorner(int row, int column)
	{
		return notAtBottomEdge(row) && notAtRightEdge(column);
	}
	
	private boolean notAtBottomLeftCorner(int row, int column)
	{
		return notAtBottomEdge(row) && notAtLeftEdge(column);
	}
	
	private boolean notAtTopRightCorner(int row, int column)
	{
		return notAtTopEdge(row) && notAtRightEdge(column);
	}
	
	private boolean notAtTopLeftCorner(int row, int column)
	{
		return notAtTopEdge(row) && notAtLeftEdge(column);
	}
	
	protected final int scoreAmount = 1;
	protected final int noScoreAmount = 0;
	
	private final GameBoard board;
	private final int boardSize;
	private final HashSet<SOS> SOSesOnGameBoard;
	
	private int newSOScount;
}