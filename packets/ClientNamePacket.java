package packets;

public class ClientNamePacket extends Packet
{
	public ClientNamePacket(String aName)
	{
		name = aName;
	}
	
	@Override
	public PacketHeader getPacketHeader()
	{
		return PacketHeader.ClientName;
	}
	
	public String getName() 
	{
		return name;
	}

	@Override
	public String toString()
	{
		return name;
	}

	private static final long serialVersionUID = 10L;
	private String name;
}
