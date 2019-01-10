package prockot.sos4.view.play;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import prockot.sos4.model.game.GamePlayModel;

public abstract class GamePlayView extends BorderPane
{
	public GamePlayView(GamePlayModel aPlayModel)
	{
		playModel = aPlayModel;
		
		createAndLayoutControls();
		observeModelAndUpdateView();
		handleButtonInput();
	}
	
	protected abstract void observeModelAndUpdateView();
	protected abstract void handleButtonInput();
	protected abstract void restart();
	
	protected void updateLabels() 
	{		
		lblCurrentPlayer.setText(playModel.getCurrentPlayer().getName() + "'s turn!");
		
		lblPlayer1.setText("" + playModel.getPlayer1());
		lblPlayer2.setText("" + playModel.getPlayer2());
		
		int scoreCount = playModel.getNewSOSCount();
		
		if (scoreCount > 0)
		{
			historyTable.addEntry(playModel.getCurrentPlayer().getName() 
					+ " scored " + scoreCount + " points!");
		}
		
		String turnTime = String.format("%.1f", timer.getCurrentTime());
		
		historyTable.addEntry("Turn end time: " + turnTime + "s");
		historyTable.addEntry("");
		historyTable.addEntry(playModel.getCurrentPlayer().getName() + "'s turn");
		historyTable.addEntry("Score: " + playModel.getPlayer1().getScore() 
				+ " to " + playModel.getPlayer2().getScore());
		historyTable.addEntry("Turn start time: " + turnTime + "s");
	}
	
	protected void createAndLayoutControls()
	{
		vbCurrentPlayer = new VBox();
		lblCurrentPlayer = new Label(playModel.getCurrentPlayer().getName() + "'s turn!");
		
		hbPlayerInfo = new HBox(50);
		lblPlayer1 = new Label("" + playModel.getPlayer1());
		lblPlayer2 = new Label("" + playModel.getPlayer2());
				
		vbGameInfo = new VBox(5);
		lblGameMode = new Label("Game mode: " + playModel.getGameMode().toUpperCase());
		timer = new GameTimer();
		historyTable = new GameHistoryTable();
		hbControls = new HBox(20);
		btRestart = new Button("Restart");
		btQuit = new Button("Quit");
		
		soundPlayer = new SOS_SoundPlayer();
						
		setupTopVBox();
		setupControlVBox();
		timer.start();
		
		setTop(vbCurrentPlayer);
		setLeft(vbGameInfo);	
	}
	
	protected void setupTopVBox()
	{
		lblCurrentPlayer.setId("big-label");
		lblPlayer1.setId("big-label-player1");		
		lblPlayer2.setId("big-label-player2");
		
		hbPlayerInfo.getChildren().addAll(lblPlayer1, lblPlayer2);
		vbCurrentPlayer.getChildren().addAll(lblCurrentPlayer, hbPlayerInfo);
	}
	
	protected void setupControlVBox()
	{		
		btRestart.setFocusTraversable(false);
		btQuit.setFocusTraversable(false);
		
		hbControls.getChildren().addAll(btRestart, btQuit);
		vbGameInfo.getChildren().addAll(lblGameMode, timer, historyTable, hbControls);
	}
	
	protected final GamePlayModel playModel;
	
	protected VBox vbCurrentPlayer;
	protected Label lblCurrentPlayer;
	protected HBox hbPlayerInfo;
	protected Label lblPlayer1;
	protected Label lblPlayer2;
	protected VBox vbGameInfo;
	protected Label lblGameMode;
	protected GameTimer timer;
	protected GameHistoryTable historyTable;
	protected HBox hbControls;
	protected Button btRestart;
	protected Button btQuit;
	protected SOS_SoundPlayer soundPlayer;
}