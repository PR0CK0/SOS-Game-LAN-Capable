package prockot.sos4.view.play;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class GameHistoryTable extends VBox
{
	public GameHistoryTable()
	{
		layoutContents();
	}
	
	public void addEntry(String entry)
	{
		Platform.runLater(() -> lvHistory.getItems().add(entry));
	} 
	
	public void initializeEntries(String currentPlayer)
	{
		String initialPlayer = currentPlayer + "'s turn";
		String initialScore = "Score: 0 to 0";
		String initialTime = "Turn start time: 0.0s";
		
		reset();
		
		Platform.runLater(() -> lvHistory.getItems().addAll(initialPlayer, initialScore, initialTime));
	}
	
	private final ListView<String> lvHistory = new ListView<String>();
	private final int tableMinWidth = 200;
	private final int tableMinHeight = 500;
	
	private void reset()
	{
		Platform.runLater(() -> lvHistory.getItems().clear());
	}
	
	private void layoutContents()
	{
		getChildren().add(lvHistory);
		lvHistory.setMaxWidth(tableMinWidth);
		lvHistory.setMinSize(tableMinWidth, tableMinHeight);
		VBox.setMargin(lvHistory, new Insets(0, 0, 0, 10));
	}
}