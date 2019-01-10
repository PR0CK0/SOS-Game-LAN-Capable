package prockot.sos4.model.init;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameInitModelLocal extends GameInitModel
{
	public String getPlayer1Name() 
    {
        return player1Name.get();
    }
	
	public String getPlayer2Name()
	{
		return player2Name.get();
	}

    public void setPlayer1Name(String name) 
    {
        player1Name.set(name);
    }
    
    public void setPlayer2Name(String name)
    {
    	player2Name.set(name);
    }
    
	private final StringProperty player1Name = new SimpleStringProperty();
	private final StringProperty player2Name = new SimpleStringProperty();
}