package prockot.sos4.view.play.local;
import javafx.application.Platform;
import prockot.sos4.model.game.GamePlayModel;
import prockot.sos4.view.play.GamePlayView;
import prockot.sos4.view.popup.GameOverPopup;

public class GamePlayViewLocal extends GamePlayView
{
	public GamePlayViewLocal(GamePlayModel aPlayModel)
	{
		super(aPlayModel);
		
		historyTable.initializeEntries(playModel.getCurrentPlayer().getName());		
	}
				
	@Override
	protected void observeModelAndUpdateView()
	{
		playModel.setOnTurnPlayed(() -> 
		{
			updateLabels();
			gameGrid.drawTiles();
		});
		
		playModel.setOnGameOver(() -> 
		{
			Platform.runLater(() -> timer.stop());
			new GameOverPopup(playModel.getWinner());
		});
		
		playModel.setOnNewSOS(() ->
		{
			soundPlayer.playSound(playModel.getNewSOSCount());
		});
	}
	
	@Override
	protected void handleButtonInput()
	{
		btRestart.setOnAction(e -> restart());
		btQuit.setOnAction(e -> Platform.exit());
	}
	
	@Override
	protected void restart()
	{
		playModel.resetGame();

		if (soundPlayer.isSoundPlaying())
		{
			soundPlayer.stopSound();
		}

		gameGrid.reset();
		timer.reset();
		
		gameGrid.drawTiles();
		updateLabels();
		historyTable.initializeEntries(playModel.getCurrentPlayer().getName());
	}	
	
	@Override
	protected void createAndLayoutControls()
	{
		super.createAndLayoutControls();
		
		gameGrid = new SOS_GridLocal(playModel);
		setCenter(gameGrid);
	}
	
	private SOS_GridLocal gameGrid;
}