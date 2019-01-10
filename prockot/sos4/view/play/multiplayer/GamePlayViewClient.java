package prockot.sos4.view.play.multiplayer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import prockot.sos4.model.game.GamePlayModelMultiplayer;
import prockot.sos4.web.NetworkCommunication;

public class GamePlayViewClient extends GamePlayViewHost 
{
	public GamePlayViewClient(String aPlayerType, int aPlayerTurnIndex, GamePlayModelMultiplayer aPlayModel, NetworkCommunication aNetComms)
	{
		super(aPlayerType, aPlayerTurnIndex, aPlayModel, aNetComms);
	}
	
	@Override
	protected void handleButtonInput()
	{
		btQuit.setOnAction(e -> Platform.exit());
	}
	
	@Override
	protected void setupControlVBox()
	{
		btQuit.setFocusTraversable(false);
		
		Label lblPrivileges = new Label("Client\noptions:");
		hbControls.getChildren().addAll(lblPrivileges, btQuit);
		vbGameInfo.getChildren().addAll(lblGameMode, timer, historyTable, hbControls);
	}
}