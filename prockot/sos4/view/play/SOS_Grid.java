package prockot.sos4.view.play;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import prockot.sos4.model.core.Player;
import prockot.sos4.model.core.SOS;
import prockot.sos4.model.game.GamePlayModel;

public abstract class SOS_Grid extends GridPane
{
	public SOS_Grid(GamePlayModel aPlayModel)
	{
		playModel = aPlayModel;
		boardSize = playModel.getBoardSize();
		
		layoutContents();
	}
	
	public abstract void reset();
	protected abstract ChangeListener<? super String> tileUpdateListener(int row, int column, ComboBox<String> tile);
	
	public void drawTiles()
	{	
		for (int i = 0; i < boardSize; i++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				int currentRow = j;
				int currentColumn = i;

				List<SOS> SOSesToUpate = new ArrayList<SOS>();
				
				for(SOS sos : playModel.getPlayerSOSes())
				{
					if ((currentRow == sos.getStartIndexRow() && currentColumn == sos.getStartIndexCol())
					     || (currentRow == sos.getEndIndexRow() && currentColumn == sos.getEndIndexCol())
					     || (currentRow == sos.getMiddleIndexRow() && currentColumn == sos.getMiddleIndexCol()))
					{
						  SOSesToUpate.add(sos);
					}
				}
				
				Player player = null;
				boolean isTileOwnedByBothPlayers = false;
				
				for(SOS sos : SOSesToUpate)
				{
					if (player == null)
					{
						player = sos.getPlayer();
					}
					else
					{
						if (!player.equals(sos.getPlayer()))
						{
							isTileOwnedByBothPlayers = true;
							break;
						}
					}
				}
				
				if (player != null)
				{
					if(isTileOwnedByBothPlayers)
					{
						styleBothPlayerTile(j, i);
					}
					else if (playModel.getPlayer1().equals(player))
					{
						stylePlayer1OwnedTile(j, i);
					}
					else
					{
						stylePlayer2OwnedTile(j, i);
					}
				}
				else
				{
					styleDefaultTile(j, i);
				}
			}
		}
	}
	
	protected Node getNodeByRowColumnIndex(int row, int column) 
	{
	    for (Node node : getChildren()) 
	    {
	        if (getRowIndex(node) == row && getColumnIndex(node) == column) 
	        {
	            return node;
	        }
	    }
	    
	    return null;
	}
	
	protected GamePlayModel playModel;
	
	protected int boardSize;
	
	private final String[] ENTERABLE_LETTERS = {"S", "O"};
	private final String player1Color = "DODGERBLUE";
	private final String player2Color = "RED";
	
	private void layoutContents()
	{
		for (int i = 0; i < boardSize; i++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				int row = j;
				int column = i;
				
				ComboBox<String> tile = new ComboBox<String>();
				tile.getItems().addAll(ENTERABLE_LETTERS);
				add(tile, column, row);
				styleDefaultTile(row, column);
				
				tile.getSelectionModel().selectedItemProperty().addListener(tileUpdateListener(row, column, tile));
			}
		}
		
		setVgap(3);
		setHgap(3);
		setAlignment(Pos.CENTER);
	}
	
	private void styleDefaultTile(int row, int column)
	{
		@SuppressWarnings("unchecked")
		ComboBox<String> tile = (ComboBox<String>)getNodeByRowColumnIndex(row, column);
		
		tile.setStyle("-fx-background-radius: 0,0,0,0; -fx-background-color: LIGHTGRAY; "
				+ "-fx-font-size: 1.5em; -fx-font-weight: BOLD");
		tile.setPadding(new Insets(10, 0, 10, 0));
	}
	
	private void stylePlayer1OwnedTile(int row, int column)
	{
		@SuppressWarnings("unchecked")
		ComboBox<String> tile = (ComboBox<String>)getNodeByRowColumnIndex(row, column);

		tile.setStyle("-fx-background-radius: 0,0,0,0; -fx-background-color: " + player1Color + "; "
				+ "-fx-font-size: 1.5em; -fx-font-weight: BOLD");
	}
	
	private void stylePlayer2OwnedTile(int row, int column)
	{
		@SuppressWarnings("unchecked")
		ComboBox<String> tile = (ComboBox<String>)getNodeByRowColumnIndex(row, column);

		tile.setStyle("-fx-background-radius: 0,0,0,0; -fx-background-color: " + player2Color + "; "
				+ "-fx-font-size: 1.5em; -fx-font-weight: BOLD");
	}
	
	private void styleBothPlayerTile(int row, int column)
	{
		Color p1Color = Color.DODGERBLUE;
		Color p2Color = Color.RED;
		
		double bothRed = (p1Color.getRed() + p2Color.getRed()) / 2;
		double bothGreen = (p1Color.getGreen() + p2Color.getGreen()) / 2;
		double bothBlue = (p1Color.getBlue() + p2Color.getBlue()) / 2;
		
		String bothPlayerColorHex = String.format("#%02X%02X%02X", 
				(int)(bothRed * 255), (int)(bothGreen * 255), (int)(bothBlue * 255));
				
		@SuppressWarnings("unchecked")
		ComboBox<String> tile = (ComboBox<String>)getNodeByRowColumnIndex(row, column);

		tile.setStyle("-fx-background-radius: 0,0,0,0; -fx-background-color: " + bothPlayerColorHex + "; "
				+ "-fx-font-size: 1.5em; -fx-font-weight: BOLD");
	}
}