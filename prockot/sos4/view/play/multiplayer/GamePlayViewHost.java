package prockot.sos4.view.play.multiplayer;
import java.io.IOException;
import java.net.SocketException;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import packets.GameRestartPacket;
import packets.Packet;
import packets.PacketHeader;
import packets.PlayerMovePacket;
import prockot.sos4.model.game.GamePlayModelMultiplayer;
import prockot.sos4.view.play.GameHistoryTable;
import prockot.sos4.view.play.SOS_SoundPlayer;
import prockot.sos4.view.play.GameTimer;
import prockot.sos4.view.popup.GameOverPopup;
import prockot.sos4.web.NetworkCommunication;

// Hierarchy broken... meant to extend GamePlayView and superclass GamePlayViewClient (no restart button for client)
// Also hacky, just to get full multiplayer working

// Inappropriate intimacy
// Feature envy

public class GamePlayViewHost extends BorderPane 
{		
	public GamePlayViewHost(String aType, int aPlayerTurnIndex, GamePlayModelMultiplayer aPlayModel, NetworkCommunication aNetComms)
	{
		playModel = aPlayModel;
		
		netComms = aNetComms;
		playerTurnIndex = aPlayerTurnIndex;
		multiplayerPlayerType = aType;
		
		createAndLayoutControls();		
		observeModelAndUpdateView();
		handleButtonInput();
		historyTable.initializeEntries(playModel.getCurrentPlayer().getName());
		
		serverCommunicationLoop();
	}
		
	protected void serverCommunicationLoop()
	{		
		Thread gameCommunicationThread = new Thread(() -> 
		{	
			while (true)
			{
				try 
				{
					if (!isMyTurn())
					{
						Platform.runLater(() -> 
						{
							btRestart.setDisable(true);
							
							gameGrid.setDisable(true);
							lblCurrentPlayer.setText("Waiting on other player...");
						});
						
						Packet otherPlayerPacket = netComms.readFromServer();
						
						if (otherPlayerPacket.getPacketHeader() == PacketHeader.PlayerMove)
						{
							updateViewFromMultiplayerMove(otherPlayerPacket);
						}
						else
						{
							updateViewFromHostRestart(otherPlayerPacket);
						}
					}
					else
					{
						Platform.runLater(() -> lblCurrentPlayer.setText("My turn!"));
						Thread.sleep(200);
					}
				} 
				catch (SocketException e)
				{
					Platform.runLater(() -> historyTable.addEntry("HOST DISCONNECTED"));
					break;
				}
				catch (IOException | ClassNotFoundException | InterruptedException e) 
				{
					e.printStackTrace();
					break;
				}
			}
		});

		gameCommunicationThread.setDaemon(true);
		gameCommunicationThread.start();
	}
	
	protected void observeModelAndUpdateView()
	{
		playModel.setOnTurnPlayed(() -> 
		{
			updateLabels();
			gameGrid.drawTiles();
		});
		
		playModel.setOnGameOver(() -> 
		{
			// Full board reset has 50/50 chance to get turns wrong, game is still playable
			if (multiplayerPlayerType.equalsIgnoreCase("HOST"))
			{
				playerTurnIndex = 1;
				playModel.setTurnCounter(1);
			}
			else
			{
				playerTurnIndex = 0;
				playModel.setTurnCounter(1);
			}
			
			Platform.runLater(() -> 
			{				
				timer.stop();
				new GameOverPopup(playModel.getWinner());
			});
		});
		
		playModel.setOnNewSOS(() ->
		{
			soundPlayer.playSound(playModel.getNewSOSCount());
		});
	}
	
	protected void handleButtonInput()
	{
		btRestart.setOnAction(e -> 
		{
			boolean isHostTurn = Math.random() <= 0.5;
			
			try
			{
				netComms.writeToServer(new GameRestartPacket(isHostTurn));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			
			playModel.resetGame();
			
			if (isHostTurn)
			{
				if (playerTurnIndex == 0)
				{
					playModel.setTurnCounter(0);					
				}
				else
				{
					playModel.setTurnCounter(1);
				}
				
				Platform.runLater(() -> 
				{
					gameGrid.setDisable(false);
					lblCurrentPlayer.setText("My turn!");
				});
			}
			else
			{				
				if (playerTurnIndex == 0)
				{
					playModel.setTurnCounter(1);					
				}
				else
				{
					playModel.setTurnCounter(0);
				}
				
				Platform.runLater(() -> 
				{
					gameGrid.setDisable(true);
					lblCurrentPlayer.setText("Waiting on other player...");
				});
			}
						
			restart();
		});
		
		btQuit.setOnAction(e -> Platform.exit());
	}
	
	protected void restart()
	{
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
	
	protected void updateLabels() 
	{
		Platform.runLater(() ->
		{
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
		});
	}
	
	protected void createAndLayoutControls()
	{	
		vbCurrentPlayer = new VBox();
		lblCurrentPlayer = new Label();
		
		hbPlayerInfo = new HBox(50);
		lblPlayer1 = new Label("" + playModel.getPlayer1());
		lblPlayer2 = new Label("" + playModel.getPlayer2());
		
		gameGrid = new SOS_GridMultiplayer(netComms, playModel);
		
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
		
		setCenter(gameGrid);
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
		
		Label lblPrivileges = new Label("Host\noptions:");
		hbControls.getChildren().addAll(lblPrivileges, btRestart, btQuit);
		vbGameInfo.getChildren().addAll(lblGameMode, timer, historyTable, hbControls);
	}
	
	protected final String multiplayerPlayerType;
	
	protected final GamePlayModelMultiplayer playModel;
	
	protected NetworkCommunication netComms;
	
	protected VBox vbCurrentPlayer;
	protected Label lblCurrentPlayer;
	protected HBox hbPlayerInfo;
	protected Label lblPlayer1;
	protected Label lblPlayer2;
	protected SOS_GridMultiplayer gameGrid;
	protected VBox vbGameInfo;
	protected Label lblGameMode;
	protected GameTimer timer;
	protected GameHistoryTable historyTable;
	protected HBox hbControls;
	protected Button btRestart;
	protected Button btQuit;
	protected SOS_SoundPlayer soundPlayer;
	
	private int playerTurnIndex;
	
	private void updateViewFromMultiplayerMove(Packet otherPlayerPacket)
	{
		PlayerMovePacket move = (PlayerMovePacket) otherPlayerPacket;

		gameGrid.updateTileFromMultiplayerUserInput(move.getRow(), move.getColumn(), String.valueOf(move.getSymbol()).toUpperCase());
		
		Platform.runLater(() -> 
		{	
			btRestart.setDisable(false);
			
			gameGrid.setDisable(false);
		});
	}
	
	private void updateViewFromHostRestart(Packet otherPlayerPacket)
	{
		GameRestartPacket gameRestartPacket = (GameRestartPacket) otherPlayerPacket;
		
		playModel.resetGame();
		
		if (gameRestartPacket.isHostTurn())
		{
			if (playerTurnIndex == 0)
			{
				playModel.setTurnCounter(1);
			}
			else
			{
				playModel.setTurnCounter(0);
			}
			
			Platform.runLater(() -> 
			{
				gameGrid.setDisable(true);
				lblCurrentPlayer.setText("Waiting on other player...");
			});
		}
		else
		{
			if (playerTurnIndex == 0)
			{
				playModel.setTurnCounter(0);
			}
			else
			{
				playModel.setTurnCounter(1);
			}
			
			Platform.runLater(() -> 
			{
				gameGrid.setDisable(false);
				lblCurrentPlayer.setText("My turn!");
			});
		}
																
		restart();
	}
	
	private boolean isMyTurn()
	{
		return playModel.getTurnCounter() == playerTurnIndex;
	}
}