package packets;
public class PlayerMovePacket extends Packet
{
	public PlayerMovePacket(int row, int column, char symbol)
	{
		super();
		this.row = row;
		this.column = column;
		this.symbol = symbol;
	}
	
	@Override
	public PacketHeader getPacketHeader()
	{
		return PacketHeader.PlayerMove;
	}
	
	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getColumn()
	{
		return column;
	}

	public void setColumn(int column)
	{
		this.column = column;
	}

	public char getSymbol()
	{
		return symbol;
	}

	public void setSymbol(char symbol)
	{
		this.symbol = symbol;
	}

	@Override
	public String toString()
	{
		return "MovePacket [row=" + row + ", column=" + column + ", symbol=" + symbol + "]";
	}

	private static final long serialVersionUID = 7L;
	private int row;
	private int column;
	private char symbol;
}