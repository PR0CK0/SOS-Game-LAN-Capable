package prockot.sos4.view.init;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public abstract class GameInitView extends BorderPane
{
	public GameInitView()
	{
		createAndLayoutControls();
		listenToControls();
		updateModelFromListeners();
	}
		
	protected abstract void listenToControls();
	protected abstract void updateModelFromListeners();
	
	protected void createAndLayoutControls()
	{
		vbControls = new VBox(12);
		lblTitle = new Label();
		cbBoardSizeSelect = new ComboBox<Integer>();
		cbGameModeSelect = new ComboBox<String>();
		btStartGame = new Button();
		
		lblTitle.setFocusTraversable(true);
		lblTitle.setId("paragraph-label");
		
		cbBoardSizeSelect.setMaxWidth(200);
		cbBoardSizeSelect.setPromptText("Board size...");
		setSelectableBoardSizes(cbBoardSizeSelect);
		cbBoardSizeSelect.setId("entry-combo-box");
		
		cbGameModeSelect.setMaxWidth(200);
		cbGameModeSelect.setPromptText("Game mode...");
		setSelectableGamemodes(cbGameModeSelect);
		cbGameModeSelect.setId("entry-combo-box");
		
		VBox.setMargin(btStartGame, new Insets(20, 0, 0, 0));
		
		addTooltips();
	}
	
	protected final String entryRegex = "[a-z A-Z 0-9]";
	protected final int maxNameLength = 12;
	protected final Integer[] boardSizes = {3, 4, 5, 6, 7, 8, 9, 10};
	protected final String[] gameModes = {"Normal", "Extreme", "Combat"};
	protected final String[] userModes = {"Host", "Client"};
	
	protected VBox vbControls;
	protected Label lblTitle;
	protected ComboBox<Integer> cbBoardSizeSelect;
	protected ComboBox<String> cbGameModeSelect;
	protected Button btStartGame;
		
	private void setSelectableBoardSizes(ComboBox<Integer> cb)
	{
		cb.getItems().addAll(boardSizes);
	}
	
	private void setSelectableGamemodes(ComboBox<String> cb)
	{
		cb.getItems().addAll(gameModes);
	}
	
	private void addTooltips()
	{
		Tooltip boardSize = new Tooltip("The board size value. Selecting 3 will give a 3x3 grid.\n"
				+ " You can select up to a size 10 grid.");
		boardSize.setId("paragraph-label");
		
		Tooltip gamemode = new Tooltip("The game mode value. Normal follows normal SOS rules.\n"
				+ "Extreme doubles the point value after each SOS scored, i.e. 1, 2, 4, 8, 16, 32...\n"
				+ "Combat allows you to 'steal' the other player's SOSes, taking their points too.");
		gamemode.setId("paragraph-label");
		
		cbBoardSizeSelect.setTooltip(boardSize);
		cbGameModeSelect.setTooltip(gamemode);
	}
}