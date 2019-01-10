package prockot.sos4.view;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import prockot.sos4.view.init.GameInitViewLocal;
import prockot.sos4.view.init.GameInitViewMultiplayer;
import prockot.sos4.view.popup.MultiOrLocalHelpPopup;

public class MultiplayerOrLocalSelectView extends BorderPane 
{
	public MultiplayerOrLocalSelectView()
	{
		createAndLayoutControls();
		listenToControls();
	}
	
	private final String[] gameTypes = {"Local", "Multiplayer"};
	
	private VBox vbControls;
	private Label lblTitle;
	private ComboBox<String> cbGameType;
	private Button btContinue;
	private Button btHelp;
	
	private void listenToControls()
	{
		btContinue.disableProperty().bind(cbGameType.valueProperty().isNull());
		
		btContinue.setOnAction(e -> 
		{
			if (selectionEquals(cbGameType, "Local"))
			{
				setCenter(new GameInitViewLocal());
			}
			else
			{
				setCenter(new GameInitViewMultiplayer());
			}
		});
		
		btHelp.setOnAction(e -> new MultiOrLocalHelpPopup());
	}
	
	private void createAndLayoutControls()
	{
		vbControls = new VBox(20);
		lblTitle = new Label("Local or Multiplayer Game?");
		cbGameType = new ComboBox<String>();
		btContinue = new Button("Continue");
		btHelp = new Button("?");
		
		lblTitle.setId("paragraph-label");
		
		cbGameType.setPromptText("Choose one...");
		cbGameType.setId("entry-combo-box");
		setSelectableGameTypes();
		
		VBox.setMargin(btHelp, new Insets(30, 0, 0, 0));
		
		vbControls.getChildren().addAll(lblTitle, cbGameType, btContinue, btHelp);

		setCenter(vbControls);
	}
	
	private boolean selectionEquals(ComboBox<String> cb, String item)
	{
		return cb.getSelectionModel().getSelectedItem().equalsIgnoreCase(item);
	}
	
	private void setSelectableGameTypes()
	{
		cbGameType.getItems().addAll(gameTypes);
	}
}