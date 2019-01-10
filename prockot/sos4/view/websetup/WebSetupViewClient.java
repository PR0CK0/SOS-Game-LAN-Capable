package prockot.sos4.view.websetup;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class WebSetupViewClient extends BorderPane 
{
	public WebSetupViewClient(String aPlayerName)
	{
		playerName = aPlayerName;
				
		createAndLayoutControls();
		listenToControls();
	}
		
	private final String playerName;
	
	private VBox vbContent;
	private Label lblTitle;
	private Label lblInfo;
	private IP_AddressEntryModule IP_Entry;
	private Label lblStatus;
	private Button btStart;
	
	private NetworkCommunication netComms;
	
	private void listenToControls()
	{
		btStart.disableProperty().bind(IP_Entry.hasValidIP());
		btStart.setOnAction(e -> connectToServerAsClient());
	}

	private void connectToServerAsClient()
	{
		Thread clientSetupThread = new Thread(() -> 
		{
			try 
			{				
				netComms = new NetworkCommunication(IP_Entry.getHostIP());
				
				ClientNamePacket thisPlayerName = new ClientNamePacket(playerName);
				netComms.writeToServer(thisPlayerName);
				
				Platform.runLater(() -> 
				{
					lblStatus.setText("Waiting on host...");
					lblStatus.setId("status-label");
				});
				
				GameDetailsPacket gameDetailsFromHost = (GameDetailsPacket) netComms.readFromServer();
				GameRestartPacket whoIsInitialPlayer = (GameRestartPacket) netComms.readFromServer();
				
				createAndStartGame(gameDetailsFromHost, whoIsInitialPlayer);
			} 
			catch (UnknownHostException e) 
			{
				Platform.runLater(() -> lblStatus.setText("Unknown host! Try again, and make sure you get it right!"));
			} 
			catch (ConnectException e)
			{
				Platform.runLater(() -> lblStatus.setText("Your friend is not hosting a game. They must start one before you attempt to connect."));
			}
			catch (IOException e) 
			{
				Platform.runLater(() -> lblStatus.setText("Something really bad occurred... Are you sure your friend is hosting a game?"));
			}
			catch (ClassNotFoundException e) 
			{
				Platform.runLater(() -> lblStatus.setText("Error loading game details from host."));
			}
		});
		
		clientSetupThread.setDaemon(true);
		clientSetupThread.start();
	}
	
	private void createAndStartGame(GameDetailsPacket gameDetailsFromHost, GameRestartPacket whoIsInitialPlayer)
	{
		GamePlayModelMultiplayer playModel;
		Player player1;
		Player player2;
		int initialTurnIndex;
		
		if (!whoIsInitialPlayer.isHostTurn())
		{
			player1 = new Player(playerName);
			player2 = new Player(gameDetailsFromHost.getHostName());
			playModel = new GamePlayModelMultiplayer(gameDetailsFromHost.getBoardSize(), player1, player2, gameDetailsFromHost.getGameMode());
			initialTurnIndex = 0; 
		}
		else
		{
			player1 = new Player(gameDetailsFromHost.getHostName());
			player2 = new Player(playerName);
			playModel = new GamePlayModelMultiplayer(gameDetailsFromHost.getBoardSize(), player1, player2, gameDetailsFromHost.getGameMode());
			initialTurnIndex = 1;
		}
						
		Platform.runLater(() -> 
		{			
			GamePlay game = new GamePlay("Client", initialTurnIndex, playModel, netComms);
			setCenter(game.getView());					
		});
	}
	
	private void createAndLayoutControls()
	{
		vbContent = new VBox(20);
		lblTitle = new Label("SOS Client Setup");
		lblInfo = new Label("Enter the host's IP address, " + playerName + "."
				+ "\nIf you're playing on the same computer, you can just enter 127 . 0 . 0 . 1");
		
		IP_Entry = new IP_AddressEntryModule();
		lblStatus = new Label();
		btStart = new Button("Start game!");
		
		lblTitle.setId("big-label");
		lblInfo.setId("paragraph-label");
		
		lblStatus.setVisible(false);
		lblStatus.setId("error-label");					
		
		vbContent.setAlignment(Pos.CENTER);
		vbContent.getChildren().addAll(lblTitle, lblInfo, IP_Entry, lblStatus, btStart);
		setCenter(vbContent);
	}
}