package prockot.sos4.model.core;

public class Player
{	
	public Player(String aName) 
	{
		name = aName;
		score = 0;
	}

	public void addScore(int aScore) 
	{
		score += aScore;
	}
	
	public void subtractScore(int aScore)
	{
		score -= aScore;
	}
	
	public void reset()
	{
		score = 0;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public long getScore() 
	{
		return score;
	}

	@Override
	public String toString()
	{
		return name + "\nScore: " + score;
	}

	private final String name;     
	private long score;
}                   