package prockot.sos4.web.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Platform;
import packets.GameRestartPacket;
import packets.Packet;
import packets.PacketHeader;
import packets.PlayerMovePacket;

public class GameThreadClient extends Thread
{
	public GameThreadClient(ObjectOutputStream aToClientPlayer, ObjectInputStream aFromHostPlayer, ServerLog aLog)
	{
		toClientPlayer = aToClientPlayer;
		fromHostPlayer = aFromHostPlayer;
		
		log = aLog;
		log.addEntry("Client thread started.\n");
		
		setDaemon(true);
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				Packet hostPacket = (Packet) fromHostPlayer.readObject();
				
				if (hostPacket.getPacketHeader() == PacketHeader.PlayerMove)
				{
					hostPacket = (PlayerMovePacket) hostPacket;
					toClientPlayer.writeObject(hostPacket);
				}
				else 
				{
					hostPacket = (GameRestartPacket) hostPacket;
					toClientPlayer.writeObject(hostPacket);
				}							
				
				log.addEntry("Host packet received by client thread in server: " + hostPacket + "\n");
			}
			catch (ClassNotFoundException | IOException e)
			{
				Platform.runLater(() -> log.addEntry("Error reading host (self) move, or writing it to client.\n"));
			}
		}
	}
	
	private final ObjectOutputStream toClientPlayer;
	private final ObjectInputStream fromHostPlayer;
		
	private final ServerLog log;
}