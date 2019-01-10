package prockot.sos4.web.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import javafx.application.Platform;
import packets.PlayerMovePacket;

public class GameThreadHost extends Thread
{
	public GameThreadHost(ObjectOutputStream aToHostPlayer, ObjectInputStream aFromClientPlayer, ServerLog aLog)
	{
		toHostPlayer = aToHostPlayer;
		fromClientPlayer = aFromClientPlayer;
		
		log = aLog;
		log.addEntry("Host thread started.\n");
		
		setDaemon(true);
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				PlayerMovePacket clientPlayerMove = (PlayerMovePacket) fromClientPlayer.readObject();
				toHostPlayer.writeObject(clientPlayerMove);
				
				log.addEntry("Client packet received by host thread in server: " + clientPlayerMove + "\n");
			}
			catch (SocketException e)
			{
				Platform.runLater(() -> log.addEntry("Client has disconnected. Close the application.\n"));
				break;
			}
			catch (IOException e)
			{
				Platform.runLater(() -> log.addEntry("General I/O exception catch. Client may have disconnected,\n"
						+ "or there was a network issue."));
				break;
			}
			catch (ClassNotFoundException e)
			{
				Platform.runLater(() -> log.addEntry("Error reading client move, or writing it to host (self).\n"));		
				break;
			}
		}
	}

	private final ObjectOutputStream toHostPlayer;
	private final ObjectInputStream fromClientPlayer;
	
	private final ServerLog log;
}