package prockot.sos4.view.play.multiplayer;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ComboBox;
import packets.PlayerMovePacket;
import prockot.sos4.model.game.GamePlayModel;
import prockot.sos4.view.play.SOS_Grid;
import prockot.sos4.web.NetworkCommunication;

public class SOS_GridMultiplayer extends SOS_Grid 
{	
	public SOS_GridMultiplayer(NetworkCommunication aNetComms, GamePlayModel playModel)
	{
		super(playModel);
		
		netComms = aNetComms;
	}
		
	public void updateTileFromMultiplayerUserInput(int row, int column, String letter)
	{
		playModel.doPlayerTurn(row, column, letter);
		
		Platform.runLater(() ->
		{
			@SuppressWarnings("unchecked")
			ComboBox<String> tile = (ComboBox<String>)getNodeByRowColumnIndex(row, column);
			tile.setPromptText(letter);
			tile.setDisable(true);
		});
	}
	
	@Override
	public void reset()
	{
		Platform.runLater(() -> 
		{
			for (int i = 0; i < boardSize; i++)
			{
				for (int j = 0; j < boardSize; j++)
				{
					@SuppressWarnings("unchecked")
					ComboBox<String> tile = (ComboBox<String>)getNodeByRowColumnIndex(j, i);
					
					if (tile.getSelectionModel().getSelectedItem() != null)
					{
						tile.getSelectionModel().clearSelection();
					}
					
					tile.setDisable(false);
					tile.setPromptText("");
				}
			}
		});
	}
	
	@Override
	protected ChangeListener<? super String> tileUpdateListener(int row, int column, ComboBox<String> tile)
	{
		return (obj, oldVal, newVal) -> 
		{
			tile.setDisable(true);
			
			if (newVal != null)
			{
				playModel.doPlayerTurn(row, column, newVal);
				
				new Thread(() -> 
				{						
					try 
					{
						netComms.writeToServer(new PlayerMovePacket(row, column, newVal.charAt(0)));
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}).start();
			}	
		};
	}
	
	private final NetworkCommunication netComms;
}