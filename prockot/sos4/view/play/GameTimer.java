package prockot.sos4.view.play;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameTimer extends VBox
{
    public GameTimer() 
    {
    	currentTime = 0.0;

    	layoutContents();
    	setupTimerTimeline();
    }
    
    public void start()
    {
    	timelineTimer.play();
    }
    
    public double getCurrentTime()
    {
    	return currentTime;
    }
    
    public void reset()
    {
    	currentTime = 0.0;
    	start();
    }
    
    public void stop()
    {
    	timelineTimer.pause();
    }
    
    private final Timeline timelineTimer = new Timeline();
    private final Label labelTimer = new Label();
    
    private double currentTime;
    
    private void displayedTimerLoop()
    {
    	currentTime += 0.1;    	
    	labelTimer.setText(String.format("%d", (int) currentTime) + "s");
    }
    
    private void layoutContents()
    {
    	setAlignment(Pos.CENTER);
    	labelTimer.setTextFill(Color.DARKSLATEGRAY);
	    labelTimer.setStyle("-fx-font-size: 3em;");    
		getChildren().add(labelTimer);
	}
    
    private void setupTimerTimeline()
    {
		timelineTimer.setCycleCount(Timeline.INDEFINITE);
		timelineTimer.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), e -> displayedTimerLoop()));
    }
}