package prockot.sos4.view.websetup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AnimatedLabel extends Label
{
	public AnimatedLabel(String aDefaultText)
	{
		defaultText = aDefaultText;
		animateText();
	}
	
	public void stopTextAnimation()
	{
		timeline.stop();
	}
	
	public void setDefaultAnimationText(String text)
	{
		defaultText = text;
	}
	
	private final double loopTime = 0.75;
	private final Timeline timeline = new Timeline();
	
	private String defaultText;
	
	private void animateText()
	{
		setText(defaultText);
		setId("status-label");
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(loopTime), e -> animationLoop()));
		timeline.play();
	}
	
	private void animationLoop()
	{
		String info = getText();
		
		if (info.equals(defaultText + "..."))
		{
			setText(defaultText);
		}
		else
		{
			setText(getText() + ".");
		}
	}
}