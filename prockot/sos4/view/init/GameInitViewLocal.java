package prockot.sos4.view.init;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import prockot.sos4.model.core.Player;
import prockot.sos4.model.game.GamePlayModelLocal;
import prockot.sos4.model.init.GameInitModelLocal;
import prockot.sos4.view.ValidTextField;
import prockot.sos4.view.play.local.GamePlayViewLocal;

public class GameInitViewLocal extends GameInitView
{	
	public GameInitViewLocal()
	{
		super();
		
		initModel = new GameInitModelLocal();
	}
	
	@Override
	protected void updateModelFromListeners()
	{
		tfPlayer1Name.textProperty().addListener((obj, oldText, newText) -> initModel.setPlayer1Name(newText));
		tfPlayer2Name.textProperty().addListener((obj, oldText, newText) -> initModel.setPlayer2Name(newText));
		cbBoardSizeSelect.valueProperty().addListener((obj, oldVal, newVal) -> initModel.setBoardSize(newVal));
		cbGameModeSelect.valueProperty().addListener((obj, oldVal, newVal) -> initModel.setGamemode(newVal));
		
		btStartGame.disableProperty().bind(tfPlayer1Name.textProperty().isEmpty()
				.or(tfPlayer2Name.textProperty().isEmpty())
				.or(cbBoardSizeSelect.valueProperty().isNull())
				.or(cbGameModeSelect.valueProperty().isNull()));
	}
	
	@Override
	protected void listenToControls()
	{
		btStartGame.setOnAction(e -> 
		{
			GamePlayModelLocal model = new GamePlayModelLocal(initModel.getBoardSize(), 
					new Player(initModel.getPlayer1Name()), new Player(initModel.getPlayer2Name()), initModel.getGamemode());
			GamePlayViewLocal view = new GamePlayViewLocal(model);
			setCenter(view);
		});
	}
	
	@Override
	protected void createAndLayoutControls()
	{
		super.createAndLayoutControls();
		
		lblTitle = new Label("Local SOS Game Setup");
		tfPlayer1Name = new ValidTextField(entryRegex, maxNameLength);
		tfPlayer2Name = new ValidTextField(entryRegex, maxNameLength);
		btStartGame.setText("Start game!");
		
		lblTitle.setId("paragraph-label");
		
		tfPlayer1Name.setMaxWidth(200);
		tfPlayer1Name.setPromptText("Player 1 name...");
		
		tfPlayer2Name.setMaxWidth(200);
		tfPlayer2Name.setPromptText("Player 2 name...");
		
		vbControls.getChildren().addAll(lblTitle, tfPlayer1Name, tfPlayer2Name, cbBoardSizeSelect, cbGameModeSelect, btStartGame);
		vbControls.setAlignment(Pos.CENTER);
		
		setCenter(vbControls);
	}
	
	private TextField tfPlayer1Name;
	private TextField tfPlayer2Name;
	
	private GameInitModelLocal initModel;
}