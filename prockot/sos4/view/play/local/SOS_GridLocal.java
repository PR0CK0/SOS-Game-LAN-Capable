package prockot.sos4.view.play.local;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ComboBox;
import prockot.sos4.model.game.GamePlayModel;
import prockot.sos4.view.play.SOS_Grid;

public class SOS_GridLocal extends SOS_Grid
{
	public SOS_GridLocal(GamePlayModel playModel)
	{
		super(playModel);
	}
	
	@Override
	public void reset()
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
					tile.setDisable(false);
				}
			}
		}
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
			}	
		};
	}
}