package prockot.sos4.web;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import packets.Packet;

public class NetworkCommunication 
{
	public NetworkCommunication(String IP_Address) throws UnknownHostException, ConnectException, IOException
	{
		portNumber = new NetworkDetails().port();
		
		socket = new Socket(IP_Address, portNumber);
		writer = new ObjectOutputStream(socket.getOutputStream());
		reader = new ObjectInputStream(socket.getInputStream());
	}
	
	public void writeToServer(Packet packet) throws IOException
	{
		writer.writeObject(packet);
	}
	
	public Packet readFromServer() throws IOException, ClassNotFoundException
	{
		return (Packet) reader.readObject();
	}
	
	private final Socket socket;
	private final ObjectOutputStream writer;
	private final ObjectInputStream reader;

	private final int portNumber;
}