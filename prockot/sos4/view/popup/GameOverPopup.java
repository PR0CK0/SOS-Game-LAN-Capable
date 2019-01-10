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

public class GameOverPopup extends Stage
{	
	public GameOverPopup(String winner)
	{
		layoutContents(winner);

		show();
		
		btOK.setOnAction(e -> close());
	}
	
	private final VBox root = new VBox(20);
	private final Button btOK = new Button("OK!");
	
	private final int SCENE_WIDTH = 500;
	private final int SCENE_HEIGHT = 200;
	private final String SCENE_TITLE = "Game Over!";
	
	private Scene gameOverScene;
	private Label lblText;
	
	private void layoutContents(String winner)
	{
		gameOverScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		setScene(gameOverScene);
		initModality(Modality.APPLICATION_MODAL);
		setResizable(false);
		setTitle(SCENE_TITLE);
		
		root.setAlignment(Pos.CENTER);
		
		lblText = new Label("Winner: " + winner);
		lblText.setStyle("-fx-font-size: 2em; -fx-font-style: italic"); 
		
		btOK.setStyle("-fx-background-radius: 0, 0, 0, 0; -fx-font-size: 1.10em");
		
		lblText.setTextAlignment(TextAlignment.CENTER);
		lblText.setPadding(new Insets(0, 10, 0, 10));
		lblText.setWrapText(true);
		
		root.getChildren().addAll(lblText, btOK);
	}
}