package prockot.sos4.web.server;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerLog extends Stage
{
	public ServerLog()
	{
		layoutContents();
		show();
	}
	
	public void addEntry(String entry)
	{
		log.appendText(" - " + entry);
	}
	
	private final VBox root = new VBox();
	private final TextArea log = new TextArea();
	
	private final int SCENE_WIDTH = 500;
	private final int SCENE_HEIGHT = 200;
	private final String SCENE_TITLE = "Server Log";
	
	private Scene scene;
	
	private void layoutContents()
	{
		scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		setScene(scene);
		setMinWidth(SCENE_WIDTH);
		setMaxWidth(SCENE_WIDTH);
		setMinHeight(SCENE_HEIGHT);	
		setMaxHeight(SCENE_HEIGHT);
		setTitle(SCENE_TITLE);
		
		log.setEditable(false);
		
		root.getChildren().add(log);
	}
}