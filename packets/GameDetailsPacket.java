package packets;

public class GameDetailsPacket extends Packet
{
	public GameDetailsPacket(int aBoardSize, String aGameMode, String aHostName) 
	{
		boardSize = aBoardSize;
		gameMode = aGameMode;
		hostName = aHostName;
	}

	@Override
	public PacketHeader getPacketHeader()
	{
		return PacketHeader.GameDetails;
	}
	
	public int getBoardSize() 
	{
		return boardSize;
	}
	
	public String getGameMode() 
	{
		return gameMode;
	}
	
	public String getHostName() 
	{
		return hostName;
	}
	
	@Override
	public String toString() 
	{
		return "GameDetailsPacket [boardSize=" + boardSize + ", gameMode=" + gameMode + ", hostName=" + hostName + "]";
	}

	private static final long serialVersionUID = 8L;
	private int boardSize;
	private String gameMode;
	private String hostName;
}
