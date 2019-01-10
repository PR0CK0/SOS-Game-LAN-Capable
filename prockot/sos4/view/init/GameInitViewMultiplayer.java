package prockot.sos4.view.init;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import prockot.sos4.model.init.GameInitModelMultiplayer;
import prockot.sos4.view.ValidTextField;
import prockot.sos4.view. websetup.WebSetupViewClient;
import prockot.sos4.view. websetup.WebSetupViewHost;

public class GameInitViewMultiplayer extends GameInitView
{
	public GameInitViewMultiplayer()
	{
	    super();
		
	    initModel = new GameInitModelMultiplayer();
	}
	
	@Override
	protected void updateModelFromListeners()
	{
		tfPlayerName.textProperty().addListener((obj, oldText, newText) -> initModel.setPlayerName(newText));
		cbBoardSizeSelect.valueProperty().addListener((obj, oldVal, newVal) -> initModel.setBoardSize(newVal));
		cbGameModeSelect.valueProperty().addListener((obj, oldVal, newVal) -> initModel.setGamemode(newVal));
	}
	
	@Override
	protected void listenToControls()
	{		
		cbUserMode.valueProperty().addListener((obj, oldVal, newVal) -> updateControlsFromUsermode(newVal));
		
		btStartGame.setOnAction(e -> 
		{
			if (cbUserMode.getSelectionModel().getSelectedItem().equalsIgnoreCase("Host"))
			{
				WebSetupViewHost hostView = new WebSetupViewHost(initModel.getPlayerName(), initModel.getBoardSize(),
						initModel.getGamemode());
				
				setCenter(hostView);
			}
			else
			{
				setCenter(new WebSetupViewClient(initModel.getPlayerName()));
			}
		});
	}
	
	@Override
	protected void createAndLayoutControls()
	{
		super.createAndLayoutControls();
		
		lblTitle = new Label("Multiplayer SOS Game Setup");
		cbUserMode = new ComboBox<String>();
		tfPlayerName = new ValidTextField(entryRegex, maxNameLength);
		btStartGame.setText("Continue to network setup");
		lblInfo = new Label("Select if you want to host, or be a client.");
		
		lblTitle.setId("paragraph-label");
		
		cbUserMode.setMaxWidth(200);
		cbUserMode.setPromptText("Start game as...");
		setSelectableUsermodes();
		cbUserMode.setId("entry-combo-box");
		
		tfPlayerName.setMaxWidth(200);
		tfPlayerName.setPromptText("Player name...");
		tfPlayerName.setDisable(true);
		
		cbBoardSizeSelect.setDisable(true);
		cbGameModeSelect.setDisable(true);
		btStartGame.setDisable(true);
		
		lblInfo.setPadding(new Insets(30, 0, 0, 0));
		lblInfo.setId("status-label");
				
		vbControls.getChildren().addAll(lblTitle, cbUserMode, tfPlayerName,
				cbBoardSizeSelect, cbGameModeSelect, btStartGame, lblInfo);
		
		setCenter(vbControls);
	}
	
	private final GameInitModelMultiplayer initModel;
	
	private ComboBox<String> cbUserMode;
	private TextField tfPlayerName;
	private Label lblInfo;
	
	private void updateControlsFromUsermode(String selectedUserMode)
	{		
		if (selectedUserMode.equalsIgnoreCase("Host"))
		{
			btStartGame.disableProperty().bind(tfPlayerName.textProperty().isEmpty()
					.or(cbBoardSizeSelect.valueProperty().isNull())
					.or(cbGameModeSelect.valueProperty().isNull())
					.or(cbUserMode.valueProperty().isNull()));		
			
			cbBoardSizeSelect.setDisable(false);
			cbGameModeSelect.setDisable(false);
			
			lblInfo.setText("Enter all the parameters of the game you want to play.");
		}
		else
		{
			btStartGame.disableProperty().bind(tfPlayerName.textProperty().isEmpty()
					.or(cbUserMode.valueProperty().isNull()));	
			
			cbBoardSizeSelect.setDisable(true);
			cbBoardSizeSelect.getSelectionModel().clearSelection();
			cbGameModeSelect.setDisable(true);
			cbGameModeSelect.getSelectionModel().clearSelection();
			
			lblInfo.setText("Just put in your name. The host will put in the game info.");
		}
		
		tfPlayerName.clear();
		tfPlayerName.setDisable(false);
	}

	private void setSelectableUsermodes()
	{
		cbUserMode.getItems().addAll(userModes);
	}
}