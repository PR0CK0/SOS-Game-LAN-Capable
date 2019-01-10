package prockot.sos4.model.init;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class GameInitModel
{
	public int getBoardSize()
    {
    	return boardSize.get();
    }
    
    public String getGamemode()
    {
    	return gameMode.get();
    }
    
    public void setBoardSize(Integer size)
    {
    	boardSize.set(size);
    }
    
    public void setGamemode(String aGameMode)
    {
    	gameMode.set(aGameMode);
    }
	
	private final IntegerProperty boardSize = new SimpleIntegerProperty();
	private final StringProperty gameMode =  new SimpleStringProperty();
}