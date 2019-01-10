package prockot.sos4.web.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import packets.ClientNamePacket;
import packets.GameDetailsPacket;
import packets.GameRestartPacket;
import prockot.sos4.web.NetworkDetails;

public class GameSetupServer extends VBox 
{	
	public GameSetupServer()
	{		
		log = new ServerLog();
	}
	
	public void startServer()
	{	
		log.addEntry("Server starting.\n");
		
		Thread serverThread = new Thread(() -> 
		{
			try
			{
				serverSocket = new ServerSocket(new NetworkDetails().port());
				Platform.runLater(() -> log.addEntry(new Date() + " : server started. Waiting for players.\n"));
				
				setupHostSocket();
				GameDetailsPacket gameDetails = (GameDetailsPacket) fromHostPlayer.readObject();
				Platform.runLater(() -> 
				{
					log.addEntry(new Date() + " : " + gameDetails.getHostName() + " joined as HOST.\n");
					log.addEntry("Game specified: " + gameDetails.getGameMode() + " - size of " + gameDetails.getBoardSize() + "\n");
				});
								
				setupClientSocket();
				ClientNamePacket clientPlayerName = (ClientNamePacket) fromClientPlayer.readObject();
				Platform.runLater(() -> log.addEntry(new Date() + " : " + clientPlayerName + " joined as CLIENT.\n"));
				
				sendInfoToStartGame(gameDetails, clientPlayerName);
									
				Thread clientListeningThread = new GameThreadClient(toClientPlayer, fromHostPlayer, log);
				clientListeningThread.start();
				
				Thread hostListeningThread = new GameThreadHost(toHostPlayer, fromClientPlayer, log);
				hostListeningThread.start();
			}
			catch (BindException e)
			{
				Platform.runLater(() -> log.addEntry("The specified port is already in use.\n"
						+ "The port may not have shut down when the app was last closed, \n"
						+ "or you still have an instance open."));
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
				Platform.runLater(() -> log.addEntry("Error in server.\nPotentially an issue with data streams"
						+ " or with serialized class file(s) (GameInfo) not being found.\n"));
			}
		});
		
		serverThread.setDaemon(true);
		serverThread.start();
	}
	
	public String getHostPrivateNetworkIP()
	{
		try 
		{
			InetAddress address = InetAddress.getLocalHost();
			return address.getHostAddress();
		} 
		catch (UnknownHostException e) 
		{
			log.addEntry("Problem obtaining host address.\n");
			return null;
		}		
	}
		
	private final ServerLog log;
	
	private ServerSocket serverSocket;
	private Socket hostPlayer;
	private Socket clientPlayer;
	
	private ObjectInputStream fromHostPlayer;
	private ObjectOutputStream toHostPlayer;
	private ObjectInputStream fromClientPlayer;
	private ObjectOutputStream toClientPlayer;	
	
	private void setupHostSocket() throws IOException
	{
		hostPlayer = serverSocket.accept();				
		toHostPlayer = new ObjectOutputStream(hostPlayer.getOutputStream());
		fromHostPlayer = new ObjectInputStream(hostPlayer.getInputStream());
	}
	
	private void setupClientSocket() throws IOException
	{
		clientPlayer = serverSocket.accept();
		toClientPlayer = new ObjectOutputStream(clientPlayer.getOutputStream());		
		fromClientPlayer = new ObjectInputStream(clientPlayer.getInputStream());
	}
	
	private void sendInfoToStartGame(GameDetailsPacket gameDetails, ClientNamePacket clientPlayerName) throws IOException
	{
		toHostPlayer.writeObject(clientPlayerName);
		toClientPlayer.writeObject(gameDetails);
		
		initializeRandomPlayerOrder();		
	}
	
	private void initializeRandomPlayerOrder() throws IOException
	{
		if (isHostStartingFirst())
		{
			toHostPlayer.writeObject(new GameRestartPacket(true));
			toClientPlayer.writeObject(new GameRestartPacket(true));
		}
		else
		{
			toHostPlayer.writeObject(new GameRestartPacket(false));
			toClientPlayer.writeObject(new GameRestartPacket(false));
		}
	}
	
	private boolean isHostStartingFirst()
	{
		return Math.random() <= 0.5;
	}
}