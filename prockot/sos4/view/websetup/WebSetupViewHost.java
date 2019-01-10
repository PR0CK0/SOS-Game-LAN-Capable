package prockot.sos4.view.websetup;
import java.io.IOException;
import java.net.UnknownHostException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import packets.ClientNamePacket;
import packets.GameDetailsPacket;
import packets.GameRestartPacket;
import prockot.sos4.model.core.Player;
import prockot.sos4.model.game.GamePlayModelMultiplayer;
import prockot.sos4.view.play.multiplayer.GamePlay;
import prockot.sos4.web.NetworkCommunication;
import prockot.sos4.web.server.GameSetupServer;

public class WebSetupViewHost extends BorderPane 
{
	public WebSetupViewHost(String aPlayerName, int aBoardSize, String aGamemode)
	{
		playerName = aPlayerName;
		boardSize = aBoardSize;
		gamemode = aGamemode;
		
		server = new GameSetupServer();
		server.startServer();
		
		createAndLayoutControls();
		
		initializeGameAsHost();
	}
	
	private final String playerName;
	private final int boardSize;
	private final String gamemode;
	private final String infoLabelDefaultText = "Waiting for client";
	
	private final String hostIP = "localhost";
	private final GameSetupServer server;
	
	private VBox vbContent;
	private Label lblTitle;
	private Label lblIPAddress;
	private Label lblInfo;
	
	private NetworkCommunication netComms;
		
	private void initializeGameAsHost()
	{
		Thread hostSetupThread = new Thread(() -> 
		{		
			try 
			{
				netComms = new NetworkCommunication(hostIP);
				
				netComms.writeToServer(new GameDetailsPacket(boardSize, gamemode, playerName));
				
				ClientNamePacket otherPlayerName = (ClientNamePacket) netComms.readFromServer();	
				GameRestartPacket whoIsInitialPlayer = (GameRestartPacket) netComms.readFromServer();
												
				createAndStartGame(otherPlayerName, whoIsInitialPlayer);
			} 
			catch (UnknownHostException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException | ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
		});
		
		hostSetupThread.setDaemon(true);
		hostSetupThread.start();
	}
	
	private void createAndStartGame(ClientNamePacket otherPlayerName, GameRestartPacket whoIsInitialPlayer)
	{
		GamePlayModelMultiplayer playModel;
		Player player1;
		Player player2;
		int initialTurnIndex;
		
		if (whoIsInitialPlayer.isHostTurn())
		{
			player1 = new Player(playerName);
			player2 = new Player(otherPlayerName.getName());
			playModel = new GamePlayModelMultiplayer(boardSize, player1, player2, gamemode);
			initialTurnIndex = 0;
		}
		else
		{
			player1 = new Player(otherPlayerName.getName());
			player2 = new Player(playerName);
			playModel = new GamePlayModelMultiplayer(boardSize, player1, player2, gamemode);
			initialTurnIndex = 1;
		}
						
		Platform.runLater(() -> 
		{					
			GamePlay game = new GamePlay("Host", initialTurnIndex, playModel, netComms);
			setCenter(game.getView());					
		});
	}
	
	private void createAndLayoutControls()
	{
		vbContent = new VBox(20);
		
		lblTitle = new Label("SOS Host Setup\n" + gamemode + " game mode - Board size: " + boardSize);
		lblIPAddress = new Label("Your IP Address: " + server.getHostPrivateNetworkIP());
		lblInfo = new AnimatedLabel(infoLabelDefaultText);
		
		lblTitle.setId("big-label");
		lblIPAddress.setId("paragraph-label");
		
		vbContent.setAlignment(Pos.CENTER);
		vbContent.getChildren().addAll(lblTitle, lblIPAddress, lblInfo);
		
		setCenter(vbContent);
	}
}