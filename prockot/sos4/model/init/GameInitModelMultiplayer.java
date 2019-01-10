package prockot.sos4.model.init;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameInitModelMultiplayer extends GameInitModel
{
	public String getPlayerName() 
    {
        return playerName.get();
    }

    public void setPlayerName(String name) 
    {
        playerName.set(name);
    }

	private final StringProperty playerName = new SimpleStringProperty();
}