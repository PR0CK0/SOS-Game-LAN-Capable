package prockot.sos4.view.play.multiplayer;
import javafx.scene.layout.BorderPane;
import prockot.sos4.model.game.GamePlayModelMultiplayer;
import prockot.sos4.web.NetworkCommunication;

// Middle man
// Lazy class
// Unnecessary coupling

public class GamePlay 
{
	public GamePlay(String multiplayerPlayerType, int turnIndex, GamePlayModelMultiplayer model, NetworkCommunication netComms)
	{		
		if (multiplayerPlayerType.equalsIgnoreCase("Host"))
		{
			view = new GamePlayViewHost("Host", turnIndex, model, netComms);			
		}
		else
		{
			view = new GamePlayViewClient("Client", turnIndex, model, netComms);
		}
	}
	
	public BorderPane getView()
	{
		return view;
	}
	
	private final GamePlayViewHost view;
}