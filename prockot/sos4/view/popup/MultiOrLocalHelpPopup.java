package prockot.sos4.view.popup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MultiOrLocalHelpPopup extends Stage
{
	public MultiOrLocalHelpPopup()
	{
		lblHelp = new Label();
		layoutContents();
		
		show();
		
		btOK.setOnAction(e -> close());
	}
	
	private Label lblHelp;
	
	private final VBox root = new VBox(20);
	private final Button btOK = new Button("OK!");
	
	private final int SCENE_WIDTH = 250;
	private final int SCENE_HEIGHT = 250;
	private final String SCENE_TITLE = "Help!";
	
	private Scene helpScene;
	
	private void layoutContents()
	{
		helpScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		setScene(helpScene);
		initModality(Modality.APPLICATION_MODAL);
		setResizable(false);
		setTitle(SCENE_TITLE);
		
		root.setAlignment(Pos.CENTER);
		
		lblHelp = new Label("Local allows you to play a single instance of the game on one computer.\n\n"
				+ "Multiplayer allows you to play with another person on a different comupter, "
				+ "as long as you are both on the same network.");
		
		lblHelp.setTextAlignment(TextAlignment.CENTER);
		lblHelp.setPadding(new Insets(0, 10, 0, 10));
		lblHelp.setWrapText(true);
		lblHelp.setStyle("-fx-font-size: 1.30em"); 
		
		btOK.setStyle("-fx-background-radius: 0, 0, 0, 0; -fx-font-size: 1.10em");
		
		root.getChildren().addAll(lblHelp, btOK);
	}
}