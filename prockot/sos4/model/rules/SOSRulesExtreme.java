package prockot.sos4.model.rules;
import prockot.sos4.model.core.GameBoard;
import prockot.sos4.model.core.Player;

public class SOSRulesExtreme extends SOSRules
{	
	public SOSRulesExtreme(GameBoard board)
	{
		super(board);
	}
	
	@Override
	public void reset()
	{
		super.reset();
		scoreModifier = 1;
	}
	
	@Override
	protected int checkInput_O(int row, int column, Player player)
	{		
		int addedScore = 0;
	
		checkScore = checkVerticalSOS_O(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkHorizontalSOS_O(row, column, player);
		addedScore +=  checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkLRUpwardDiagonalSOS_O(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkLRDownwardDiagonalSOS_O(row, column, player);
		addedScore +=  checkScore * scoreModifier;
		updateCheckScore();
		
		return addedScore;
	}
	
	@Override
	protected int checkInput_S(int row, int column, Player player)
	{
		int addedScore = 0;
		
		checkScore = checkDownSOS_S(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkUpSOS_S(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkLeftSOS_S(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkRightSOS_S(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkLeftUpwardDiagonalSOS_S(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkRightUpwardDiagonalSOS_S(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkLeftDownwardSOS_S(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		checkScore = checkRightDownwardDiagonalSOS_S(row, column, player);
		addedScore += checkScore * scoreModifier;
		updateCheckScore();
		
		return addedScore;
	}

	private final int SCORE_MODIFIER_BASE = 2;
	
	private int scoreModifier = 1;
	private int checkScore = 0;
	
	private void updateCheckScore()
	{
		if (checkScore == 1)
		{
			increaseScoreModifier();
			checkScore = 0;
		}		
	}
	
	private void increaseScoreModifier()
	{
		scoreModifier *= SCORE_MODIFIER_BASE;
	}
}