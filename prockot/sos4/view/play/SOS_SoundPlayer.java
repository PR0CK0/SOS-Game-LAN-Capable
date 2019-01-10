package prockot.sos4.view.play;
import java.nio.file.Paths;
import javafx.scene.media.AudioClip;

public class SOS_SoundPlayer 
{
	public SOS_SoundPlayer()
	{
		player = new AudioClip(Paths.get(SOSsoundPath).toUri().toString());
		setupPlayer();
	}
	
	public void playSound(int amount)
	{
		Thread soundThread = new Thread(() -> 
		{
			for (int i = 0; i < amount; i++)
			{
				player.play();
				
				try 
				{
					Thread.sleep(soundDelay);
				} 
				catch (InterruptedException e) 
				{
					System.err.println("Thread exception when playing SOS sound.\n");
					e.printStackTrace();
				}
			}
		});
		
		soundThread.start();
	}
	
	public void stopSound()
	{
		player.stop();
	}
	
	public boolean isSoundPlaying()
	{
		return player.isPlaying();
	}
	
	private final AudioClip player;
	private final String SOSsoundPath = "prockot/sos4/resources/SOS_sound.mp3";
	private final double soundVolume = 0.75;
	private final int soundDelay = 1000;
	
	private void setupPlayer()
	{
		player.setVolume(soundVolume);
	}
}