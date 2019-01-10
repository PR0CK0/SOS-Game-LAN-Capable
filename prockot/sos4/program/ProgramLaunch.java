package prockot.sos4.program;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prockot.sos4.view.MultiplayerOrLocalSelectView;

public class ProgramLaunch extends Application 
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{		
		Scene rootScene = new Scene(new MultiplayerOrLocalSelectView(), PROGRAM_WIDTH, PROGRAM_HEIGHT);
		rootScene.getStylesheets().add(ProgramLaunch.class.getResource("/prockot/sos4/resources/style.css").toExternalForm());
		primaryStage.setMinWidth(PROGRAM_WIDTH);
		primaryStage.setMinHeight(PROGRAM_HEIGHT);
		primaryStage.setTitle(PROGRAM_TITLE);
		primaryStage.setScene(rootScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) 
	{
		try
		{			
			launch();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Could not launch application. Ruh roh.");
		}
	}
	
	private final int PROGRAM_WIDTH = 1000;
	private final int PROGRAM_HEIGHT = 800;
	private final String PROGRAM_TITLE = "SOS v4 - Tyler Procko, SE320 ERAU Daytona (Fall 2018)";
}